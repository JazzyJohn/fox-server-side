package nstuff.juggerfall.extension.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;
import nstuff.juggerfall.extension.player.Player;
import nstuff.juggerfall.extension.view.NetView;
import nstuff.juggerfall.extension.weapon.Weapon;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class Pawn extends NetView implements SerializableSFSType {

    public String type;

    public int wallState;

    public int team;

    public int characterState;

    public boolean active;

    public boolean isDead;

    public transient Player player;

    public transient User owner;

    public transient Weapon weapon;

    public Pawn(){

    }

    @Override
    public void Update(NetView view) {
        Pawn pawn  =(Pawn)view;
        wallState = pawn.wallState;
        characterState = pawn.characterState;
    }

    @Override
    public void Delete() {
        if(weapon!=null){
             manager.DeleteView(weapon.id);
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
