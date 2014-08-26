package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;

/**
 * Created by Ivan.Ochincenko on 01.08.14.
 */
public class PawnDetonateHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User sender, ISFSObject data) {

        int id = data.getInt("id");
        Pawn pawn = (Pawn)((MainExtension)getParentExtension()).viewManager.getView(data.getInt("id"));
        if(!pawn.isOwner(sender)){
            return;
        }
        ((MainExtension) getParentExtension()).viewManager.deleteViewLocal(id);

        send(PawnHandlerManager.RequestName_PawnDetonate,data,((MainExtension)getParentExtension()).getOther(sender));
    }

}
