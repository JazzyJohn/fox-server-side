package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.RunnerGameRule;

/**
 * Created by 804129 on 24.08.14.
 */
public class MapDataHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {

        ((MainExtension) getParentExtension()).gameRule.director.ReadFromSFSObject(data);
    }
}
