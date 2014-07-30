package nstuff.juggerfall.extension.handlers.player;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PlayerHandlerManager;
import nstuff.juggerfall.extension.player.Player;

import java.util.List;

public class PlayerSetTeamHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User user, ISFSObject data) {

        Player player =(Player)user.getProperty("player");
        player.team = data.getInt("team");
        ((MainExtension)getParentExtension()).UpdatePlayerInfo(user);
	}
}
