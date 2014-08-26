package nstuff.juggerfall.extension.handlers.other;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.OtherHandlerManager;

/**
 * Created by 804129 on 02.08.14.
 */
public class InvokeProjectileCallHandler  extends BaseClientRequestHandler {
    @Override
    public void handleClientRequest(User user, ISFSObject data) {

        send(OtherHandlerManager.RequestName_InvokeProjectileCall,data,((MainExtension)getParentExtension()).getOther(user));
    }
}