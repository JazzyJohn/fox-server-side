package nstuff.juggerfall.extension.handlers;

import java.util.List;

import javax.naming.directory.InvalidSearchFilterException;

import com.smartfoxserver.v2.entities.variables.UserVariable;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PlayerHandlerManager;
import nstuff.juggerfall.extension.player.PlayerManager;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserRoomJoinHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
	    User user = (User)event.getParameter(SFSEventParam.USER);
    	PlayerManager playermanager =((MainExtension)getParentExtension()).playerManager;
		playermanager.AddPlayer(user);

        ((MainExtension)getParentExtension()).UpdatePlayerInfo(user);
		ISFSObject res = new SFSObject();
		res.putSFSArray("owners", playermanager.GetAllPalyerToSend());
		send(PlayerHandlerManager.RequestName_InitPlayers,res,user);
        ((MainExtension)getParentExtension()).PlayerJoin(user);
	}

}
