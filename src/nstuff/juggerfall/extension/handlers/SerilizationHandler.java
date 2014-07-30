package nstuff.juggerfall.extension.handlers;

import java.util.List;

import nstuff.juggerfall.extension.MainExtension;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class SerilizationHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User sender, ISFSObject data) {
		// TODO Auto-generated method stub
		List<User> targets = getParentExtension().getParentRoom().getUserList();
        targets.remove(sender);
        data.putBool("read",true);
        send(MainExtension.RequestName_Serilization,data,targets,true);
	}

}
