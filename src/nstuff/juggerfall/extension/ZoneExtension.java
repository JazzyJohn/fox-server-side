package nstuff.juggerfall.extension;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import nstuff.juggerfall.extension.handlers.BaseAddRoomHandler;
import nstuff.juggerfall.extension.handlers.LoginHandler;
import nstuff.juggerfall.extension.handlers.UserDisconnectZoneHandler;

/**
 * Created by Ivan.Ochincenko on 29.07.14.
 */
public class ZoneExtension extends SFSExtension {




    @Override
    public void init() {

        trace(ExtensionLogLevel.INFO,"ZoneExtension is Up");


        addEventHandler(SFSEventType.ROOM_ADDED, BaseAddRoomHandler.class);
        addEventHandler(SFSEventType.USER_LOGIN, LoginHandler.class);
       // addEventHandler(SFSEventType.USER_DISCONNECT, UserDisconnectZoneHandler.class);
    }

}


