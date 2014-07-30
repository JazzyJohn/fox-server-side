package nstuff.juggerfall.extension.handlers;

import java.util.List;

import javax.naming.directory.InvalidSearchFilterException;

import nstuff.juggerfall.extension.MainExtension;
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
		// TODO Auto-generated method stub
		User user = (User)event.getParameter(SFSEventParam.USER);
		PlayerManager playermanager =((MainExtension)getParentExtension()).playerManager;
		playermanager.AddPlayer(user);		
		List<User> targets = getParentExtension().getParentRoom().getUserList();
		ISFSObject res = new SFSObject();
		res.putInt("owner", user.getId());
		send(MainExtension.RequestName_InitPlayer,res,targets);
		res = new SFSObject();
		res.putIntArray("owners", playermanager.GetAllPalyerToSend());
		send(MainExtension.RequestName_InitPlayers,res,targets);
		
	}

}
