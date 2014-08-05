package nstuff.juggerfall.extension.handlers.other;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.PVEGameRule;

/**
 * Created by Ivan.Ochincenko on 05.08.14.
 * Class for request about next point in VIP Route in Expedition GameRule
 */
public class NextRouteHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        PVEGameRule pveGameRule = (PVEGameRule) ((MainExtension) getParentExtension()).gameRule;
        pveGameRule.AddRoute(data.getInt("nextRoute"));
    }

}