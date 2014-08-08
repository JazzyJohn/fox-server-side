package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

/**
 * Created by Ivan.Ochincenko on 29.07.14.
 */
public class BaseAddRoomHandler extends BaseServerEventHandler
{
    @Override
    public void handleServerEvent(ISFSEvent event)
    {

        Room room  =  (Room)event.getParameter(SFSEventParam.ROOM);
        room.getVariable("map").setGlobal(true);
        room.setHidden(!room.getVariable("visible").getBoolValue());
    }
}