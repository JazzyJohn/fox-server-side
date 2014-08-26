package nstuff.juggerfall.extension.handlers.other;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.OtherHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.player.Player;

/**
 * Created by Ivan.Ochincenko on 20.08.14.
 */
public class EnterRobotSuccessHandler extends BaseClientRequestHandler {
    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        MainExtension extension = (MainExtension)getParentExtension();
        if(extension.masterInfo!=user){
            return;
        }
        User robotDriver = extension.getParentRoom().getUserById(data.getInt("userId"));
        Pawn pawn = (Pawn)extension.viewManager.getView(data.getInt("robotId"));
        pawn.owner = robotDriver;

        extension.gameRule.robotEnter(((Player) robotDriver.getProperty("player")).team);

        send(OtherHandlerManager.RequestName_EnterRobotSuccess, data, extension.getOther(null));
    }

}
