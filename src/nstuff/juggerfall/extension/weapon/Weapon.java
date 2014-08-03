package nstuff.juggerfall.extension.weapon;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.models.WeaponModel;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.view.NetView;
import nstuff.juggerfall.extension.view.NetViewType;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class Weapon  extends NetView {
    public String type;

    public transient Pawn owner;

    public WeaponModel sirWeapon;

    public Weapon(){
        viewType= NetViewType.NET_VIEW_TYPE_WEAPON;
    }

    public Weapon(WeaponModel weaponModel) {
    	type = weaponModel.type;
    	id = weaponModel.id;
        viewType= NetViewType.NET_VIEW_TYPE_WEAPON;
        sirWeapon = weaponModel;
	}

	@Override
    public void Update(NetViewModel view) {
        sirWeapon = (WeaponModel)view;
    }

    @Override
    public void Delete() {
        if(owner.weapon==this){
            owner.weapon = null;
        }
    }
    @Override
    public void DeleteLocal() {
        if(owner.weapon==this){
            owner.weapon = null;
        }
    }

    @Override
    public void ClearRef() {
        owner = null;
    }
}
