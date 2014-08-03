package nstuff.juggerfall.extension.handlermanagers;


import nstuff.juggerfall.extension.handlers.other.*;


/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class OtherHandlerManager extends AbstractHandlerManager {

    public static final String RequestName_InvokeProjectileCall = "invokeProjectileCall";

    public static final String RequestName_SimplePrefabSpawn = "simplePrefabSpawn";

    @Override
    public void Init() {
        extension.addClientHandler(RequestName_InvokeProjectileCall, InvokeProjectileCallHandler.class);

        extension.addClientHandler(RequestName_SimplePrefabSpawn, SimplePrefabSpawn.class);

    }


}
