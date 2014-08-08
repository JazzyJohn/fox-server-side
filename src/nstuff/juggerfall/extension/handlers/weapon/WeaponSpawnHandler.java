package nstuff.juggerfall.extension.handlers.weapon;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.WeaponHandlerManager;
import nstuff.juggerfall.extension.models.WeaponModel;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.weapon.Weapon;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class WeaponSpawnHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        Pawn owner  =(Pawn)((MainExtension)getParentExtension()).viewManager.GetView(data.getInt("pawnId"));
        if(!owner.IsOwner(user)){
            return;
        }

        WeaponModel weaponModel  =(WeaponModel)data.getClass("weapon");
        Weapon weapon = new Weapon(weaponModel);
        ((MainExtension)getParentExtension()).viewManager.AddView(weapon);
        weapon.owner = owner;
        owner.weapon = weapon;
        ISFSObject res = new SFSObject();
        res.putClass("weapon",weapon.sirWeapon);
        res.putInt("pawnId",owner.id);
        send(WeaponHandlerManager.RequestName_WeaponSpawn,res,((MainExtension)getParentExtension()).GetOther(user));
    }
}
