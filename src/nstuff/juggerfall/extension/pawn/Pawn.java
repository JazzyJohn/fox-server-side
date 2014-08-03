package nstuff.juggerfall.extension.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.models.PawnModel;
import nstuff.juggerfall.extension.player.Player;
import nstuff.juggerfall.extension.view.NetView;
import nstuff.juggerfall.extension.view.NetViewType;
import nstuff.juggerfall.extension.weapon.Weapon;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class Pawn extends NetView  {

    public String type;

    public int wallState;

    public int team;

    public int characterState;

    public boolean active;

    public boolean isDead;

    public transient Player player;

    public transient User owner;

    public transient Weapon weapon;

    public PawnModel sirPawn;

    public Pawn(){
        viewType= NetViewType.NET_VIEW_TYPE_PAWN;
    }

    public Pawn(PawnModel pawnModel) {
        sirPawn  =(PawnModel)pawnModel;
    	 id =pawnModel.id;
    	 type = pawnModel.type;
    	 wallState = pawnModel.wallState;
    	 team = pawnModel.team;
    	 characterState = pawnModel.characterState;
    	 isDead = pawnModel.isDead;
         viewType= NetViewType.NET_VIEW_TYPE_PAWN;
	}

	@Override
    public void Update(NetViewModel incPawn) {
        sirPawn  =(PawnModel)incPawn;
        wallState = sirPawn.wallState;
        characterState = sirPawn.characterState;
    }



    @Override
    public void Delete() {
        if(weapon!=null){
             manager.DeleteView(weapon.id);
        }
        AfterDeleteLogic();
    }

    @Override
    public void DeleteLocal() {
        if(weapon!=null){
            manager.DeleteViewLocal(weapon.id);
        }
        AfterDeleteLogic();
    }

    private void AfterDeleteLogic(){
        if(team!=0){
            manager.extension.gameRule.PlayerDeath(team-1);
        }
    }
    @Override
    public void ClearRef() {
        weapon = null;
    }

    public void SetPlayer(Player player) {
       this.player = player;
       team = player.team;
    }

    public boolean IsOwner(User user) {
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
}
