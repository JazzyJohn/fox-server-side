package nstuff.juggerfall.extension.pawn;

import com.smartfoxserver.v2.entities.User;
import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.models.PawnModel;
import nstuff.juggerfall.extension.player.Player;
import nstuff.juggerfall.extension.view.NetView;
import nstuff.juggerfall.extension.view.NetViewType;
import nstuff.juggerfall.extension.weapon.Weapon;

import java.util.Collection;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class Pawn extends NetView {

    public String type;

    public int wallState;

    public int team;

    public int characterState;

    public boolean active;



    public transient Player player;

    public transient User owner;

    public transient Weapon weapon;

    public PawnModel sirPawn;

    public boolean isAi;

    public Integer aiSwarmId;

    public Integer aihome;

    public Collection<Integer> stims;

    public Pawn(){
        viewType= NetViewType.NET_VIEW_TYPE_PAWN;
    }

    public Pawn(PawnModel pawnModel) {

         sirPawn  =pawnModel;
    	 id =pawnModel.id;
    	 type = pawnModel.type;
    	 wallState = pawnModel.wallState;
    	 team = pawnModel.team;
    	 characterState = pawnModel.characterState;

         viewType= NetViewType.NET_VIEW_TYPE_PAWN;

    }

	@Override
    public void update(NetViewModel incPawn) {
        sirPawn  =(PawnModel)incPawn;
        wallState = sirPawn.wallState;
        characterState = sirPawn.characterState;
    }



    @Override
    public void delete() {
        if(weapon!=null){
             manager.deleteView(weapon.id);
        }
        AfterDeleteLogic();
    }

    @Override
    public void deleteLocal() {
        if(weapon!=null){
            manager.deleteViewLocal(weapon.id);
        }
        AfterDeleteLogic();
    }

    private void AfterDeleteLogic(){
        if(owner!=null){
            manager.extension.gameRule.playerDeath(this);
        }
    }
    @Override
    public void clearRef() {
        weapon = null;
    }

    @Override
    public boolean needDelete(int ownerId) {
        return owner!=null&&owner.getId()==ownerId;
    }

    public void setPlayer(Player player) {
       this.player = player;
       team = player.team;
    }

    public boolean isOwner(User user) {
        if(owner!=null){
            if(owner!=user){
                return false;
            }
        }else{
            if(!user.containsVariable("Master")||!user.getVariable("Master").getBoolValue()){
                return false;
            }
        }
        return true;
    }

    public User getOwner() {
        if(owner==null){
            return  manager.extension.masterInfo;
        }else{
            return owner;
        }

    }
}
