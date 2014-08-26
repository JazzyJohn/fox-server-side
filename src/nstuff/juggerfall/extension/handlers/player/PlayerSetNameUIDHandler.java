package nstuff.juggerfall.extension.handlers.player;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.player.Player;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PlayerSetNameUIDHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        Player player =(Player)user.getProperty("player");
        player.name = data.getUtfString("name");
        player.uid = data.getUtfString("uid");
        ((MainExtension)getParentExtension()).updatePlayerInfo(user);

    }


}
