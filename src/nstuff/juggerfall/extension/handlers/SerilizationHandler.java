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

        data.putBool("read",true);
        send(MainExtension.RequestName_Serilization,data,((MainExtension)getParentExtension()).GetOther(sender),true);
	}

}
