package nstuff.juggerfall.extension.handlers.player;

import java.util.List;

import nstuff.juggerfall.extension.MainExtension;


import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.handlermanagers.PlayerHandlerManager;

public class PlayerInitHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User user, ISFSObject data) {
		// TODO Auto-generated method stub
		((MainExtension)getParentExtension()).playerManager.AddPlayer(user);		
		send(PlayerHandlerManager.RequestName_InitPlayer,data,((MainExtension)getParentExtension()).GetOther(user));
	}
}
