package nstuff.juggerfall.extension.handlers.other;

/**
 * Created by Ivan.Ochincenko on 04.08.14.
 */

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.RunnerGameRule;

/**
 * Created by Ivan.Ochincenko on 04.08.14.
 */
public class NextRoomHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {

        ((RunnerGameRule)((MainExtension) getParentExtension()).gameRule).NextRoom();
    }
}