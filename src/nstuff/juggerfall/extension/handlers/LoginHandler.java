package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

import java.util.Arrays;

/**
 * Created by Ivan.Ochincenko on 19.08.14.
 */
public class LoginHandler extends BaseServerEventHandler
    {
        @Override
        public void handleServerEvent(ISFSEvent event)
        {

            User user  =  (User)event.getParameter(SFSEventParam.USER);
            ISFSObject data = (ISFSObject) event.getParameter(SFSEventParam.OBJECT);
            UserVariable var = new SFSUserVariable("playerName",data.getUtfString("playerName")) ;
            getApi().setUserVariables(user, Arrays.asList(var));
        }
    }