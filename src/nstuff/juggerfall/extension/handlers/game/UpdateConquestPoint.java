package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;

import nstuff.juggerfall.extension.handlermanagers.GameHandlerManager;


/**
 * Created by Ivan.Ochincenko on 08.08.14.
 */
public class UpdateConquestPoint extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {

        send(GameHandlerManager.RequestName_UpdateConquestPoint,data,((MainExtension)getParentExtension()).GetOther(user));

    }

}
