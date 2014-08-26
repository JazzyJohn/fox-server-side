package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.PVPJuggerFightGameRule;

/**
 * Created by 804129 on 06.08.14.
 */
public class GameRuleDamageBase extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        ((PVPJuggerFightGameRule)((MainExtension) getParentExtension()).gameRule).baseDamaged(data.getInt("team"), data.getInt("damage"));
    }
}
