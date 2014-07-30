package nstuff.juggerfall.extension.weapon;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.view.NetView;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class Weapon  extends NetView implements SerializableSFSType {
    public String type;

    public Pawn owner;

    public Weapon(){

    }

    @Override
    public void Update(NetView view) {

    }

    @Override
    public void Delete() {
        if(owner.weapon==this){
            owner.weapon = null;
        }
    }

    @Override
    public void ClearRef() {
        owner = null;
    }
}
