package nstuff.juggerfall.extension.weapon;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.models.WeaponModel;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.view.NetView;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class Weapon  extends NetView implements SerializableSFSType {
    public String type;

    public transient Pawn owner;

    public Weapon(){

    }

    public Weapon(WeaponModel weaponModel) {
    	type = weaponModel.type;
    	id = weaponModel.id;
	}

	@Override
    public void Update(NetViewModel view) {

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
