package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.models.PawnModel;
import nstuff.juggerfall.extension.pawn.Pawn;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PawnUpdateHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        PawnModel incPawn  =(PawnModel)data.getClass("pawn");
        Pawn pawn = (Pawn)((MainExtension)getParentExtension()).viewManager.GetView(incPawn.id);
        if(!pawn.IsOwner(user)){
            return;
        }

        pawn.Update(incPawn);
        ((MainExtension)getParentExtension()).UpdatePawnInfo(user,pawn,true);
    }
}
