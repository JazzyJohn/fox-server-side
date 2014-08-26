package nstuff.juggerfall.extension.handlermanagers;


import nstuff.juggerfall.extension.handlers.other.*;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class OtherHandlerManager extends AbstractHandlerManager {

    public static final String RequestName_InvokeProjectileCall = "invokeProjectileCall";

    public static final String RequestName_SimplePrefabSpawn = "simplePrefabSpawn";

    public static final String RequestName_RegisterSceneView = "registerSceneView";

    public static final String RequestName_UpdateSimpleDestroyableObject = "updateSimpleDestroyableObject";

    public static final String RequestName_EnterRobot = "enterRobot";

    public static final String RequestName_EnterRobotSuccess = "enterRobotSuccess";


    @Override
    public void init() {
        extension.addClientHandler(RequestName_InvokeProjectileCall, InvokeProjectileCallHandler.class);

        extension.addClientHandler(RequestName_SimplePrefabSpawn, SimplePrefabSpawn.class);

        extension.addClientHandler(RequestName_RegisterSceneView, RegisterSceneViewHandler.class);

        extension.addClientHandler(RequestName_UpdateSimpleDestroyableObject, UpdateSimpleDestroyableObject.class);

        extension.addClientHandler(RequestName_EnterRobot,EnterRobotHandler.class);

        extension.addClientHandler(RequestName_EnterRobotSuccess,EnterRobotSuccessHandler.class);
    }


}
