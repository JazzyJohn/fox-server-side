package nstuff.juggerfall.extension.weapon;

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

    public Integer lateId=0;

    public boolean state;

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
    public void update(NetViewModel view) {
        sirWeapon = (WeaponModel)view;
    }

    @Override
    public void delete() {
      
        if(lateId!=0){
            manager.extension.clearEarlyWeapon(this);
        }
    }
    @Override
    public void deleteLocal() {

        if(lateId!=0){
            manager.extension.clearEarlyWeapon(this);
        }
    }

    @Override
    public void clearRef() {
        owner = null;
    }
    @Override
    public boolean needDelete(int ownerId) {
        return owner!=null&&owner.owner!=null&&owner.owner.getId()==ownerId;
    }

    public void setState(Boolean state) {
        this.state = state;
        sirWeapon.state = state;
    }
}
