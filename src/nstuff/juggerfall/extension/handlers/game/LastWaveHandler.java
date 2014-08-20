package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.HuntGameRule;
import nstuff.juggerfall.extension.gamerule.PVPJuggerFightGameRule;

/**
 * Created by Ivan.Ochincenko on 20.08.14.
 */
public class LastWaveHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        if(user==((MainExtension) getParentExtension()).masterInfo){
            ((HuntGameRule)((MainExtension) getParentExtension()).gameRule).LastWave();
        }

    }
}

