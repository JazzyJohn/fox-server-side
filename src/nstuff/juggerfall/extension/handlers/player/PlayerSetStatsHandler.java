package nstuff.juggerfall.extension.handlers.player;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.models.PlayerModel;
import nstuff.juggerfall.extension.player.Player;

public class PlayerSetStatsHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User user, ISFSObject data) {

        Player player =(Player)user.getProperty("player");
        player.updateModel((PlayerModel) data.getClass("stats"));
        ((MainExtension)getParentExtension()).updatePlayerInfo(user);
	}
}
