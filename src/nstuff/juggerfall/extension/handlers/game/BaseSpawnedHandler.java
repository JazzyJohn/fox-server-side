package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gameplay.PVPBase;
import nstuff.juggerfall.extension.gamerule.PVPJuggerFightGameRule;
import nstuff.juggerfall.extension.models.BaseModel;

/**
 * Created by 804129 on 07.08.14.
 */
public class BaseSpawnedHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        BaseModel model  =(BaseModel)data.getClass("model");
        PVPBase base = new PVPBase(model);

        ((PVPJuggerFightGameRule)((MainExtension) getParentExtension()).gameRule).SetBase(base);
    }
}