package nstuff.juggerfall.extension;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import nstuff.juggerfall.extension.handlers.*;

/**
 * Created by Ivan.Ochincenko on 29.07.14.
 */
public class ZoneExtension extends AbstractExtension {

    public static final String CONTER_IDLE_REQUEST ="conterIdle";


    @Override
    public void init() {

        trace(ExtensionLogLevel.INFO,"ZoneExtension is Up");


        addEventHandler(SFSEventType.ROOM_ADDED, BaseAddRoomHandler.class);
        addEventHandler(SFSEventType.USER_LOGIN, LoginHandler.class);
        addRequestHandler(CONTER_IDLE_REQUEST,ConterIdleHandler.class);
        addEventHandler(SFSEventType.ROOM_REMOVED, RoomRemovedHandler.class);
       // addEventHandler(SFSEventType.USER_DISCONNECT, UserDisconnectZoneHandler.class);
    }

}


