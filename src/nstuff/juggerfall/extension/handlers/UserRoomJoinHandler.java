package nstuff.juggerfall.extension.handlers;

import java.util.Arrays;
import java.util.List;

import javax.naming.directory.InvalidSearchFilterException;

import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PlayerHandlerManager;
import nstuff.juggerfall.extension.player.PlayerManager;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserRoomJoinHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
	    User user = (User)event.getParameter(SFSEventParam.USER);
	  
        MainExtension extension = (MainExtension) getParentExtension();
        ISFSObject res = new SFSObject();
        if (extension.masterInfo == null){
            extension.masterInfo = user;
            UserVariable var = new SFSUserVariable("Master", true);
            getApi().setUserVariables(user, Arrays.asList(var));
            res.putBool("Master",true);
        }
    	PlayerManager playermanager =extension.playerManager;
		playermanager.AddPlayer(user);

        ((MainExtension)getParentExtension()).UpdatePlayerInfo(user);

		try {
			res.putSFSArray("owners", playermanager.GetAllPalyerToSend());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			getParentExtension().trace(e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			getParentExtension().trace(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			getParentExtension().trace(e);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			getParentExtension().trace(e);
		}
        res.putSFSArray("views", extension.viewManager.GetAllViewForStart());
		send(PlayerHandlerManager.RequestName_InitPlayers,res,user);
        ((MainExtension)getParentExtension()).PlayerJoin(user);
	}

}
