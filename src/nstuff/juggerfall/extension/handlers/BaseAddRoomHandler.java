package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.variables.RoomVariable;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 29.07.14.
 */
public class BaseAddRoomHandler extends BaseServerEventHandler
{
    @Override
    public void handleServerEvent(ISFSEvent event)
    {

        Room room  =  (Room)event.getParameter(SFSEventParam.ROOM);
        List<RoomVariable> list = new ArrayList<RoomVariable>();
        RoomVariable map = room.getVariable("map");
        map.setGlobal(true);
        list.add(map);
        getApi().setRoomVariables(null,room,list);


        room.setHidden(!room.getVariable("visible").getBoolValue());
    }
}