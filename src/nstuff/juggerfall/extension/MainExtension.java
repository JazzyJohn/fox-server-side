package nstuff.juggerfall.extension;


import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;

import nstuff.juggerfall.extension.handlers.BaseAddRoomHandler;
import nstuff.juggerfall.extension.handlers.PlayerInitHandler;
import nstuff.juggerfall.extension.handlers.SerilizationHandler;
import nstuff.juggerfall.extension.handlers.ServerTimeHandler;
import nstuff.juggerfall.extension.handlers.TransitRPCHandler;
import nstuff.juggerfall.extension.handlers.UserRoomJoinHandler;
import nstuff.juggerfall.extension.player.PlayerManager;
import nstuff.juggerfall.extension.view.ViewManager;

public class MainExtension extends SFSExtension {

    public static final String RequestName_GetTime ="getTime";

    public static final String RequestName_TransitRPC="transitRPC";
    
    public static final String RequestName_InitPlayer = "playerSpawm";
    
    public static final String RequestName_InitPlayers = "playersSpawm";
    
    public static final String RequestName_Serilization  ="serilization";

    public PlayerManager playerManager;
    
    public ViewManager viewManager;
	@Override
	public void init() {
		// TODO Auto-generated method stub
		trace(ExtensionLogLevel.INFO,"MainExtension is Up");
			
		BaseConfigurator();
        addRequestHandler(RequestName_GetTime,ServerTimeHandler.class);

        addRequestHandler(RequestName_TransitRPC,TransitRPCHandler.class);
        
        addRequestHandler(RequestName_InitPlayer,PlayerInitHandler.class);

        addRequestHandler(RequestName_Serilization,SerilizationHandler.class);
        
        addEventHandler(SFSEventType.USER_JOIN_ROOM, UserRoomJoinHandler.class);
	}
	private void BaseConfigurator() {
	
		playerManager = new PlayerManager();
		
		viewManager = new ViewManager();
		
		playerManager.viewManager = viewManager;
	}

}
