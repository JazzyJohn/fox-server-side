package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import nstuff.juggerfall.extension.MainExtension;

import java.util.List;

public class UserDisconnectHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {

        User user = (User)event.getParameter(SFSEventParam.USER);

        List<Room> rooms = (List<Room>) event.getParameter(SFSEventParam.JOINED_ROOMS);
        for(Room room : rooms){
            if(room.isGame()){
                MainExtension extension = (MainExtension)  room.getExtension();
                extension.playerLeave(user);
                if (extension.masterInfo == user){
                    extension.choiceMaster(user);
                    user.setVariable(new SFSUserVariable("Master",false ));
                }
            }
        }
	}

}
