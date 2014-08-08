package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import nstuff.juggerfall.extension.MainExtension;

public class UserDisconnectHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {

        User user = (User)event.getParameter(SFSEventParam.USER);
        ISFSObject res = new SFSObject();
        res.putSFSArray ("views",((MainExtension) getParentExtension()).viewManager.RemovePlayerView(user.getId()));
        res.putInt("playerId",user.getId());
        send(MainExtension.RequestName_PlayerLeave, res, ((MainExtension) getParentExtension()).GetOther(user));
	}

}
