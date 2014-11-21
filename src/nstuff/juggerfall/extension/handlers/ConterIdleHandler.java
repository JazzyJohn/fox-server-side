package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.ZoneExtension;

/**
 * Created by vania_000 on 17.09.2014.
 */
public class ConterIdleHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User sender, ISFSObject data) {
        ISFSObject res = new SFSObject();
        res.putLong("t",System.currentTimeMillis());
        this.send(ZoneExtension.CONTER_IDLE_REQUEST, res, sender,true);

    }

}