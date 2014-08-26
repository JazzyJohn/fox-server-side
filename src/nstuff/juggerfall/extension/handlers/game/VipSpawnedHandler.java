package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.PVEGameRule;
import nstuff.juggerfall.extension.models.PawnModel;
import nstuff.juggerfall.extension.pawn.Pawn;

/**
 * Created by Ivan.Ochincenko on 04.08.14.
 */
public class VipSpawnedHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        PawnModel pawnModel  =(PawnModel)data.getClass("pawn");
        Pawn pawn = new Pawn(pawnModel);
        ((MainExtension)getParentExtension()).viewManager.addView(pawn);
        pawn.owner = null;
        pawn.player = null;

        ((PVEGameRule)((MainExtension) getParentExtension()).gameRule).SetVipId(pawn.id);
    }
}