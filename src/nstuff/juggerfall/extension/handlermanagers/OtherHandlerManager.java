package nstuff.juggerfall.extension.handlermanagers;


import nstuff.juggerfall.extension.handlers.other.*;


/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class OtherHandlerManager extends AbstractHandlerManager {

    public static final String RequestName_InvokeProjectileCall = "invokeProjectileCall";

    public static final String RequestName_SimplePrefabSpawn = "simplePrefabSpawn";

    public static final String RequestName_VipSpawned = "vipSpawned";

    public static final String RequestName_RegisterSceneView = "registerSceneView";

    public static final String RequestName_NextRoom = "nextRoom";

    public static final String RequestName_NextRoute = "nextRoute";

    public static final String RequestName_GameRuleArrived = "gameRuleArrived";
    @Override
    public void Init() {
        extension.addClientHandler(RequestName_InvokeProjectileCall, InvokeProjectileCallHandler.class);

        extension.addClientHandler(RequestName_SimplePrefabSpawn, SimplePrefabSpawn.class);

        extension.addClientHandler(RequestName_VipSpawned, VipSpawnedHandler.class);

        extension.addClientHandler(RequestName_RegisterSceneView, RegisterSceneViewHandler.class);

        extension.addClientHandler(RequestName_NextRoute, NextRouteHandler.class);

        extension.addClientHandler(RequestName_NextRoom, NextRoomHandler.class);

        extension.addClientHandler(RequestName_GameRuleArrived, GameRuleArrived.class);
    }


}
