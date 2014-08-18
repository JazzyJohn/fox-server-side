package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.GameHandlerManager;

/**
 * Created by Ivan.Ochincenko on 18.08.14.
 */
public class SwarmUpdateHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        if(!user.containsVariable("Master")||!user.getVariable("Master").getBoolValue()){
            return;
        }
        ((MainExtension) getParentExtension()).gameRule.director.UpdateSwarm(data.getIntArray("ids"));
        send(GameHandlerManager.RequestName_SwarmUpdate,data,((MainExtension)getParentExtension()).GetOther(user));

    }

}