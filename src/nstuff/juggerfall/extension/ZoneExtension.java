package nstuff.juggerfall.extension;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import nstuff.juggerfall.extension.rooms.BaseAddRoomHandler;

/**
 * Created by Ivan.Ochincenko on 29.07.14.
 */
public class ZoneExtension extends SFSExtension {

    private static final String RequestName_GetTime ="getTime";


    @Override
    public void init() {

        trace(ExtensionLogLevel.INFO,"MainExtension is Up");


        addEventHandler(SFSEventType.ROOM_ADDED, BaseAddRoomHandler.class);
    }

}


