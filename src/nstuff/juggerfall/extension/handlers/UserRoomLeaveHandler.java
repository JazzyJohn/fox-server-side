package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PlayerHandlerManager;
import nstuff.juggerfall.extension.player.PlayerManager;

import java.util.Arrays;

public class UserRoomLeaveHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {


        User user = (User)event.getParameter(SFSEventParam.USER);
        MainExtension extension =(MainExtension) user.getLastJoinedRoom().getExtension();
        ISFSObject res = new SFSObject();
        res.putSFSArray("views", extension.viewManager.RemovePlayerView(user.getId()));
        res.putInt("playerId",user.getId());
        send(MainExtension.RequestName_PlayerLeave, res, extension.GetOther(user));
	}

}
