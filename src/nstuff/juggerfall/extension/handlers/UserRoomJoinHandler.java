package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PlayerHandlerManager;
import nstuff.juggerfall.extension.player.PlayerManager;

import java.util.Arrays;

public class UserRoomJoinHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
	    User user = (User)event.getParameter(SFSEventParam.USER);
	  
        MainExtension extension = (MainExtension) getParentExtension();
        ISFSObject res = new SFSObject();
        if (extension.masterInfo == null){
            extension.masterInfo = user;
            extension.setMasterInitialized(true);
            UserVariable var = new SFSUserVariable("Master", true);
            getApi().setUserVariables(user, Arrays.asList(var));
            res.putBool("Master",true);
        }
    	PlayerManager playermanager =extension.playerManager;
		playermanager.AddPlayer(user);

        extension.updatePlayerInfo(user);

		res.putSFSArray("owners", playermanager.getAllPlayerToSend());

        res.putSFSArray("views", extension.viewManager.getAllViewForStart());
        res.putIntArray("deleteSceneView", extension.viewManager.getAllDeleteSceneViewForStart());
        extension.gameRule.director.AddInfo(res);
        extension.gameRule.addInfo(res);
        res.putClass("gameRule",  extension.gameRule.getModel());
       	send(PlayerHandlerManager.RequestName_InitPlayers, res, user);

        extension.playerJoin(user);
	}

}
