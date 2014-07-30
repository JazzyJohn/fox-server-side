package nstuff.juggerfall.extension.nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import nstuff.juggerfall.extension.MainExtension;

/**
 * Created by OldDog on 30.07.2014.
 */
public class JoinRoomHandler
    extends BaseServerEventHandler
{
    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException
    {
         User user = (User) event.getParameter(SFSEventParam.USER);

        MainExtension extension = (MainExtension) getParentExtension();

        if (extension.masterInfo == null){
            extension.masterInfo = user;
            user.setVariable(new SFSUserVariable("Master",true ));
        }

    }
}