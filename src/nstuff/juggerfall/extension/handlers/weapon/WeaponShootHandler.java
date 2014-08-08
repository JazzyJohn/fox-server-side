package nstuff.juggerfall.extension.handlers.weapon;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.WeaponHandlerManager;
import nstuff.juggerfall.extension.weapon.Weapon;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class WeaponShootHandler  extends BaseClientRequestHandler {
    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        Weapon weapon = (Weapon)((MainExtension)getParentExtension()).viewManager.GetView(data.getInt("id"));
        if(!weapon.owner.IsOwner(user)){
            return;
        }

        send(WeaponHandlerManager.RequestName_WeaponShoot,data,((MainExtension)getParentExtension()).GetOther(user));
    }
}
