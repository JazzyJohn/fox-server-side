package nstuff.juggerfall.extension.handlermanagers;

import nstuff.juggerfall.extension.handlers.weapon.WeaponShootHandler;
import nstuff.juggerfall.extension.handlers.weapon.WeaponSpawnHandler;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class WeaponHandlerManager extends AbstractHandlerManager  {

    public static final String RequestName_WeaponSpawn = "weaponSpawn";

    public static final String RequestName_WeaponShoot= "weaponShoot";

    @Override
    public void Init() {
        extension.addClientHandler(RequestName_WeaponSpawn, WeaponSpawnHandler.class);

        extension.addClientHandler(RequestName_WeaponShoot, WeaponShootHandler.class);
    }
}
