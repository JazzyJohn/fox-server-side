package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 19.08.14.
 */
public class RemoteDamageOnPawnHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        try {
            Pawn pawn = (Pawn) ((MainExtension) getParentExtension()).viewManager.getView(data.getInt("pawnId"));
            if (pawn != null) {
                send(PawnHandlerManager.RequestName_RemoteDamageOnPawn, data, pawn.getOwner());
            }
        }catch (Exception e){
            getParentExtension().trace(ExtensionLogLevel.INFO,data.getInt("pawnId"));
        }

    }
}
