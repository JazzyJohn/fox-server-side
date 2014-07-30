package nstuff.juggerfall.extension;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.filter.FilterAction;
import com.smartfoxserver.v2.extensions.filter.SFSExtensionFilter;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class CustomLogFilter extends SFSExtensionFilter {
    @Override
    public void destroy() {

    }

    @Override
    public FilterAction handleClientRequest(String s, User user, ISFSObject isfsObject) throws SFSException {
        trace("Command "+s+ " User "+ user.toString() +" ISFDObject "+ isfsObject.toString());
        return FilterAction.CONTINUE;
    }

    @Override
    public FilterAction handleServerEvent(ISFSEvent isfsEvent) throws SFSException {
        trace("ISFSEvent "+ isfsEvent.toString());
        return FilterAction.CONTINUE;
    }
}
