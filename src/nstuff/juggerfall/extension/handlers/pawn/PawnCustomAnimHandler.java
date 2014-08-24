package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;

/**
 * Created by Ivan.Ochincenko on 31.07.14.
 */
public class PawnCustomAnimHandler extends BaseClientRequestHandler {
    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        Pawn pawn = (Pawn)((MainExtension)getParentExtension()).viewManager.GetView(data.getInt("pawnId"));
        if(!pawn.IsOwner(user)){
            return;
        }

        send(PawnHandlerManager.RequestName_CustomAnimStart,data,((MainExtension)getParentExtension()).GetOther(user));
    }
}
