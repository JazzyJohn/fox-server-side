package nstuff.juggerfall.extension.handlermanagers;


import nstuff.juggerfall.extension.handlers.other.InvokeProjectileCallHandler;
import nstuff.juggerfall.extension.handlers.other.NextRoomHandler;
import nstuff.juggerfall.extension.handlers.other.RegisterSceneViewHandler;
import nstuff.juggerfall.extension.handlers.other.SimplePrefabSpawn;


/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class OtherHandlerManager extends AbstractHandlerManager {

    public static final String RequestName_InvokeProjectileCall = "invokeProjectileCall";

    public static final String RequestName_SimplePrefabSpawn = "simplePrefabSpawn";

    public static final String RequestName_VipSpawned = "vipSpawned";

    public static final String RequestName_RegisterSceneView = "registerSceneView";

    public static final String RequestName_NextRoom = "nextRoom";

    @Override
    public void Init() {
        extension.addClientHandler(RequestName_InvokeProjectileCall, InvokeProjectileCallHandler.class);

        extension.addClientHandler(RequestName_SimplePrefabSpawn, SimplePrefabSpawn.class);

        extension.addClientHandler(RequestName_VipSpawned, NextRoomHandler.class);

        extension.addClientHandler(RequestName_RegisterSceneView, RegisterSceneViewHandler.class);

        extension.addClientHandler(RequestName_NextRoom, NextRoomHandler.class);

    }


}
