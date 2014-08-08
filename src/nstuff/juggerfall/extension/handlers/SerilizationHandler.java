package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;

public class SerilizationHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User sender, ISFSObject data) {
		// TODO Auto-generated method stub

        data.putBool("read",true);
        send(MainExtension.RequestName_Serilization,data,((MainExtension)getParentExtension()).GetOther(sender),true);
	}

}
