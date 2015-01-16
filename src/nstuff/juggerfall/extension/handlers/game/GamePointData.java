package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.PointGameRule;
import nstuff.juggerfall.extension.handlermanagers.GameHandlerManager;
import nstuff.juggerfall.extension.models.AssaultPointModel;


/**
 * Created by Ivan.Ochincenko on 08.08.14.
 */
public class GamePointData extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        if(!user.containsVariable("Master")||!user.getVariable("Master").getBoolValue()){
            return;
        }
        ISFSArray points = data.getSFSArray("points");
        PointGameRule gameRule =(PointGameRule)((MainExtension) getParentExtension()).gameRule;
        for(int i=0; i<points.size();i++){
            gameRule.readPoint((AssaultPointModel) points.getSFSObject(i));

        }

    }

}
