package nstuff.juggerfall.extension.handlers.weapon;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.WeaponHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.weapon.Weapon;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class WeaponStateHandler extends BaseClientRequestHandler {
    @Override
    public void handleClientRequest(User user, ISFSObject data) {

        Weapon weapon = (Weapon)((MainExtension)getParentExtension()).viewManager.getView(data.getInt("id"));

        if(weapon.owner==null){
            Pawn owner  =(Pawn)((MainExtension)getParentExtension()).viewManager.getView(weapon.lateId);
            if(owner!=null){
                weapon.owner = owner;
                owner.addWeapon(weapon);
            }
        }else{
            if(!weapon.owner.isOwner(user)){
                return;
            }
        }

        send(WeaponHandlerManager.RequestName_WeaponState,data,((MainExtension)getParentExtension()).getOther(user));
    }
}
