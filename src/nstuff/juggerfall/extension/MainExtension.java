package nstuff.juggerfall.extension;


import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import nstuff.juggerfall.extension.baseobject.TimeUpdateEntity;
import nstuff.juggerfall.extension.gamerule.GameRule;
import nstuff.juggerfall.extension.gamerule.PVPGameRule;
import nstuff.juggerfall.extension.handlermanagers.OtherHandlerManager;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.handlermanagers.PlayerHandlerManager;
import nstuff.juggerfall.extension.handlermanagers.WeaponHandlerManager;
import nstuff.juggerfall.extension.handlers.*;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.player.PlayerManager;
import nstuff.juggerfall.extension.view.ViewManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainExtension extends SFSExtension {


    public static final String RequestName_GetTime ="getTime";

    public static final String RequestName_TransitRPC="transitRPC";

    public static final String RequestName_GameUpdate  ="gameUpdate";

    public static final String RequestName_NextMap  ="nextMap";

    public static final String RequestName_Serilization  ="serilization";

    public static final String RequestName_DeleteView = "deleteView";

    public static final String RequestName_GameStart = "gameStart";

    public static final String RequestName_NewMaster = "newMaster";

    public static final String RequestName_PlayerLeave ="playerLeave";

    public PlayerHandlerManager playerHandlerManager;

    public PawnHandlerManager pawnHandlerManager;

    public WeaponHandlerManager weaponHandlerManager;

    public OtherHandlerManager otherHandlerManager;

    public PlayerManager playerManager;

    public ViewManager viewManager;

    public GameRule gameRule;

    SecondRunner secondRunner;

    // Keeps a reference to the task execution
    ScheduledFuture<?> taskHandle;


    public User masterInfo;

    public void ChoiceMaster(User exMaster) throws SFSVariableException {

        List<User> list = getParentRoom().getUserList();
        if (list.size()!= 0){

            for (int i=0; i<list.size(); i++){
                User user = list.get(i);
                if (user !=exMaster ){
                    masterInfo = user;
                    user.setVariable(new SFSUserVariable("Master",true ));
                    SendUserMasterNotification();
                    return;
                }


            }

        }
    }




    @Override
	public void init() {

		
		trace(ExtensionLogLevel.INFO,"MainExtension is Initializing");
			
		BaseConfigurator();


        playerHandlerManager.Init();

        pawnHandlerManager.Init();

        weaponHandlerManager.Init();

        otherHandlerManager.Init();

        addRequestHandler(RequestName_GetTime,ServerTimeHandler.class);

        addRequestHandler(RequestName_TransitRPC,TransitRPCHandler.class);
        
        addRequestHandler(RequestName_Serilization,SerilizationHandler.class);

        addRequestHandler(RequestName_DeleteView,DeleteViewHandler.class);
        
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

        gameRule.extension =this;

        gameRule.Init(getParentRoom());

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

        SmartFoxServer sfs = SmartFoxServer.getInstance();

        secondRunner = new SecondRunner();

        taskHandle = sfs.getTaskScheduler().scheduleAtFixedRate(secondRunner, 0, 1, TimeUnit.SECONDS);

        secondRunner.allUpdate.add(gameRule);
	}


    @Override
    public void destroy() {
        taskHandle.cancel(true);
        super.destroy();

    }

    public void UpdatePlayerInfo(User user) {
        playerHandlerManager.UpdatePlayerInfo(user);

    }
    public void UpdatePawnInfo(User user,Pawn pawn,boolean UDP) {
        pawnHandlerManager.UpdatePawnInfo(user, pawn, UDP);

    }
    public void UpdateGame(){
        List<User> targets = getParentRoom().getUserList();
        ISFSObject res = new SFSObject();
        res.putClass("game", gameRule.GetModel());
        send(RequestName_GameUpdate, res, targets);
    }

    public void PlayerJoin(User user) {
        gameRule.PlayerJoin(user);
    }

    public List<User> GetOther(User user) {
        List<User> targets = getParentRoom().getUserList();
        targets.remove(user);
        return targets;
    }

    public void DeleteView(User sender, int id) {
        List<User> targets =getParentRoom().getUserList();
        if(sender!=null){
            targets.remove(sender);
        }
        ISFSObject res = new SFSObject();
        res.putInt("id", id);
        send(RequestName_DeleteView, res, targets);
    }

    public void ReloadMap() {
        viewManager.Reload();
        List<User> targets =getParentRoom().getUserList();
        ISFSObject res = new SFSObject();
        res.putUtfString("map", getParentRoom().getVariable("map").getStringValue());
        send(RequestName_NextMap, res, targets);
    }

    private class SecondRunner implements Runnable
    {
        private long lastTime = 0;

        private List<TimeUpdateEntity> allUpdate = new ArrayList<TimeUpdateEntity>();
        public void run()
        {

            for(TimeUpdateEntity entity: allUpdate){
                Date deltaDate = new Date();
                long delta = deltaDate.getTime()-lastTime;
                entity.Update(delta);
            }
            Date date = new Date();
            lastTime = date.getTime();
        }
    }

	public void StartGameEvent() {
		  ISFSObject res = new SFSObject();
		
	      send(RequestName_GameStart, res, masterInfo);
	}

    private void SendUserMasterNotification() {
        ISFSObject res = new SFSObject();
        gameRule.AddMasterInfo(res);
        send(RequestName_NewMaster, res, masterInfo);

    }
}
