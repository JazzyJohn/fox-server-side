package nstuff.juggerfall.extension.handlers;

import java.util.List;

import nstuff.juggerfall.extension.MainExtension;


import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class PlayerInitHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User user, ISFSObject data) {
		// TODO Auto-generated method stub
		((MainExtension)getParentExtension()).playerManager.AddPlayer(user);		
		List<User> targets = getParentExtension().getParentRoom().getUserList();
		targets.remove(user);
		send(MainExtension.RequestName_InitPlayer,data,targets,true);
	}
}
