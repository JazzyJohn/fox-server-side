package nstuff.juggerfall.extension.utils;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;

import java.util.Date;

/**
 * Created by Ivan.Ochincenko on 29.07.14.
 */
public class ServerTimeHandler extends BaseClientRequestHandler
{
    @Override
    public void handleClientRequest(User sender, ISFSObject params)
    {
        ISFSObject res = new SFSObject();
        Date date = new Date();
        res.putLong("t", date.getTime());
        this.send(MainExtension.RequestName_GetTime, res, sender);
    }
}
