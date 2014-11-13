package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import nstuff.juggerfall.extension.MainExtension;

import java.util.Arrays;
import java.util.List;

public class UserRoomLeaveHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {


        User user = (User)event.getParameter(SFSEventParam.USER);
        Room room =(Room) event.getParameter(SFSEventParam.ROOM);
        MainExtension extension = (MainExtension)  room.getExtension();
        trace(ExtensionLogLevel.INFO,"playerLeave");
        if (extension.masterInfo == user){
            extension.choiceMaster(user);
            UserVariable userVariable =new SFSUserVariable("Master",false );
            getApi().setUserVariables(user, Arrays.asList(userVariable));

        }
        extension.playerLeave(user);
	}

}
