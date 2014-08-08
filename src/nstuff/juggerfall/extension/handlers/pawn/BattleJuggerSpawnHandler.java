package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.PVPJuggerFightGameRule;
import nstuff.juggerfall.extension.models.PawnModel;
import nstuff.juggerfall.extension.pawn.Pawn;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class BattleJuggerSpawnHandler extends PawnSpawnHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        super.handleClientRequest(user,data);
        PawnModel pawnModel  =(PawnModel)data.getClass("pawn");
        Pawn pawn = (Pawn)((MainExtension)getParentExtension()).viewManager.GetView(pawnModel.id);
        ((PVPJuggerFightGameRule)((MainExtension) getParentExtension()).gameRule).SetJuggerPawn(pawn);

    }
}
