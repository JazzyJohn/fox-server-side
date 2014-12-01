package nstuff.juggerfall.extension;


import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import nstuff.juggerfall.extension.baseobject.TimeUpdateEntity;
import nstuff.juggerfall.extension.gamerule.GameRule;
import nstuff.juggerfall.extension.gamerule.PVPGameRule;
import nstuff.juggerfall.extension.handlermanagers.*;
import nstuff.juggerfall.extension.handlers.*;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.player.PlayerManager;
import nstuff.juggerfall.extension.view.ViewManager;
import nstuff.juggerfall.extension.weapon.Weapon;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainExtension extends AbstractExtension {


    public static final String RequestName_GetTime ="getTime";

    public static final String RequestName_TransitRPC="transitRPC";

    public static final String RequestName_GameUpdate  ="gameUpdate";

    public static final String RequestName_NextMap  ="nextMap";

    public static final String RequestName_Serilization  ="serilization";

    public static final String RequestName_DeleteView = "deleteView";

    public static final String RequestName_DeleteSceneView = "deleteSceneView";

    public static final String RequestName_GameStart = "gameStart";

    public static final String RequestName_NewMaster = "newMaster";

    public static final String RequestName_PlayerLeave ="playerLeave";

    public PlayerHandlerManager playerHandlerManager;

    public GameHandlerManager gameHandlerManager;

    public PawnHandlerManager pawnHandlerManager;

    public WeaponHandlerManager weaponHandlerManager;

    public OtherHandlerManager otherHandlerManager;

    public PlayerManager playerManager;

    public ViewManager viewManager;

    public GameRule gameRule;

    public Sender sender;

    SecondRunner secondRunner;

    // Keeps a reference to the task execution
    ScheduledFuture<?> taskHandle;

    private Hashtable<Integer,Weapon> earlyWeapons = new Hashtable<Integer, Weapon>();

    public User masterInfo;

    public void choiceMaster(User exMaster) throws SFSVariableException {

        List<User> list = getParentRoom().getUserList();
        if (list.size()!= 0){

            for (int i=0; i<list.size(); i++){
                User user = list.get(i);
                if (user !=exMaster ){
                    masterInfo = user;

                    UserVariable master =new SFSUserVariable("Master",true );
                    getApi().setUserVariables(user, Arrays.asList(master));
                    sendUserMasterNotification();
                    return;
                }


            }

        }
    }




    @Override
	public void init() {

		
		trace(ExtensionLogLevel.INFO,"MainExtension is Initializing");
			
		BaseConfigurator();


        playerHandlerManager.init();

        pawnHandlerManager.init();

        weaponHandlerManager.init();

        otherHandlerManager.init();

        gameHandlerManager.init();

        addRequestHandler(RequestName_GetTime, ServerTimeHandler.class);

        addRequestHandler(RequestName_TransitRPC,TransitRPCHandler.class);
        
        addRequestHandler(RequestName_Serilization,SerilizationHandler.class);

        addRequestHandler(RequestName_DeleteView,DeleteViewHandler.class);

        addRequestHandler(RequestName_DeleteSceneView,DeleteSceneViewHandler.class);
        
        addEventHandler(SFSEventType.USER_JOIN_ROOM, UserRoomJoinHandler.class);

        addEventHandler(SFSEventType.USER_LEAVE_ROOM, UserRoomLeaveHandler.class);

        addEventHandler(SFSEventType.USER_DISCONNECT, UserDisconnectHandler.class);



        addFilter("logFilter",new CustomLogFilter());
        trace(ExtensionLogLevel.INFO,"MainExtension is Initializing Complete");

	}
    public void addClientHandler(java.lang.String requestId, java.lang.Class<?> theClass){
        addRequestHandler(requestId,theClass);
    }

	private void BaseConfigurator() {
        try {
            Class theClass =  Class.forName(getParentRoom().getVariable("ruleClass").getStringValue());
            gameRule = (GameRule)theClass.newInstance();
        } catch (ClassNotFoundException e) {
            gameRule = new PVPGameRule();
            trace(ExtensionLogLevel.INFO,"Game Rule not found  "+e.getStackTrace().toString());
        } catch (InstantiationException e) {
            gameRule = new PVPGameRule();
            trace(ExtensionLogLevel.INFO, "Game Rule InstantiationException   " + e.getStackTrace().toString());
        } catch (IllegalAccessException e) {
            gameRule = new PVPGameRule();
            trace(ExtensionLogLevel.INFO, "Game Rule IllegalAccessException   " + e.getStackTrace().toString());

        }
        sender = new Sender(this);

        gameRule.extension =this;

        gameRule.init(getParentRoom());

        playerManager = new PlayerManager();

        viewManager   = new ViewManager();

        viewManager.extension = this;

        weaponHandlerManager = new WeaponHandlerManager();

        weaponHandlerManager.extension = this;

	    playerHandlerManager= new PlayerHandlerManager();

        playerHandlerManager.extension = this;

        pawnHandlerManager = new PawnHandlerManager();

        pawnHandlerManager.extension = this;

        otherHandlerManager = new OtherHandlerManager();

        otherHandlerManager.extension = this;

        gameHandlerManager = new GameHandlerManager();

        gameHandlerManager.extension = this;

        SmartFoxServer sfs = SmartFoxServer.getInstance();

        secondRunner = new SecondRunner();

        taskHandle = sfs.getTaskScheduler().scheduleAtFixedRate(secondRunner, 0, 1, TimeUnit.SECONDS);

        secondRunner.allUpdate.add(gameRule);
	}

    public void addToUpdate(TimeUpdateEntity updater){
        secondRunner.allUpdate.add(updater);

    }

    @Override
    public void destroy() {
        taskHandle.cancel(true);
        UserVariable userVariable =new SFSUserVariable("Master",false );
        getApi().setUserVariables(masterInfo, Arrays.asList(userVariable));
        super.destroy();

    }

    public void updatePlayerInfo(User user) {
        playerHandlerManager.updatePlayerInfo(user);

    }
    public void updatePawnInfo(User user, Pawn pawn, boolean UDP) {
        pawnHandlerManager.updatePawnInfo(user, pawn, UDP);

    }
    public void updateGame(){
        List<User> targets = getParentRoom().getUserList();
        ISFSObject res = new SFSObject();
        res.putClass("game", gameRule.getModel());
        send(RequestName_GameUpdate, res, targets);
    }

    public void playerJoin(User user) {
        gameRule.playerJoin(user);
    }

    public List<User> getOther(User user) {
        List<User> targets = getParentRoom().getUserList();
        targets.remove(user);
        return targets;
    }

    public void deleteView(User sender, int id) {
        List<User> targets =getParentRoom().getUserList();
        if(sender!=null){
            targets.remove(sender);
        }
        ISFSObject res = new SFSObject();
        res.putInt("id", id);
        send(RequestName_DeleteView, res, targets);
    }

    public void reloadMap() {
        viewManager.reload();
        secondRunner.allUpdate.clear();
        secondRunner.allUpdate.add(gameRule);
        earlyWeapons.clear();
        List<User> targets =getParentRoom().getUserList();
        ISFSObject res = new SFSObject();
        res.putUtfString("map", getParentRoom().getVariable("map").getStringValue());
        send(RequestName_NextMap, res, targets);
    }


    public void earlyWeaponAdd(Weapon weapon) {
        earlyWeapons.put(weapon.lateId,weapon);
    }

    public void checkOwner(User user, Pawn owner){
        if(earlyWeapons.containsKey((owner.id))){
            Weapon weapon=earlyWeapons.get(owner.id);
            weapon.lateId = 0;
            SendWeaponRequest(user,owner,weapon);
        }
    }

    public void SendWeaponRequest(User user, Pawn owner, Weapon weapon) {

        weapon.owner = owner;
        owner.weapon = weapon;
        ISFSObject res = new SFSObject();
        res.putClass("weapon",weapon.sirWeapon);
        res.putInt("pawnId",owner.id);
        send(WeaponHandlerManager.RequestName_WeaponSpawn, res, getOther(user));
    }

    public void clearEarlyWeapon(Integer lateId) {
        earlyWeapons.remove(lateId);
    }

    private class SecondRunner implements Runnable
    {
        private long lastTime = 0;

        private boolean markToClear =false;

        private ConcurrentLinkedQueue<TimeUpdateEntity> allUpdate = new ConcurrentLinkedQueue<TimeUpdateEntity>();
        public void run()
        {
            try {
                for(TimeUpdateEntity entity: allUpdate){

                    long delta = System.currentTimeMillis()-lastTime;
                    entity.update(delta);
                }
                long delta = System.currentTimeMillis()-lastTime;
                update(delta);
                lastTime = System.currentTimeMillis();
                if(markToClear){
                    markToClear = false;
                    allUpdate.clear();
                }
            } catch (RuntimeException e){
                trace(ExtensionLogLevel.ERROR, "Update Exception  " + e.getStackTrace().toString());
            }

        }
    }

    private void update(long delta) {
        if((masterInfo==null||!masterInfo.isConnected()||!masterInfo.isJoinedInRoom(getParentRoom()))&&!getParentRoom().isEmpty()){
            try {
                choiceMaster(null);
            } catch (SFSVariableException e) {
                trace(ExtensionLogLevel.ERROR, "Game Rule IllegalAccessException   " + e.getStackTrace().toString());
            }
        }
    }

    public void startGameEvent() {
		  ISFSObject res = new SFSObject();
		
	      send(RequestName_GameStart, res, masterInfo);
	}

    private void sendUserMasterNotification() {
        ISFSObject res = new SFSObject();
        gameRule.addMasterInfo(res);
        trace(ExtensionLogLevel.INFO, "New Master: " + masterInfo);
        send(RequestName_NewMaster, res, masterInfo);


    }

    public void playerLeave(User user){
        ISFSObject res = new SFSObject();
        playerManager.DeletePlayer(user);
        res.putSFSArray ("views",viewManager.removePlayerView(user.getId()));
        res.putInt("playerId",user.getId());
        send(MainExtension.RequestName_PlayerLeave, res, getOther(user));

    }
}
