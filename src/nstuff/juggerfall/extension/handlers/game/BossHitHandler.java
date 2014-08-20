package nstuff.juggerfall.extension.handlers.game;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gamerule.HuntGameRule;
import nstuff.juggerfall.extension.pawn.Pawn;


/**
 * Created by Ivan.Ochincenko on 20.08.14.
 */
public class BossHitHandler  extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        Pawn pawn = (Pawn)((MainExtension)getParentExtension()).viewManager.GetView(data.getInt("pawnId"));
        if(pawn.team!=0){
            ((HuntGameRule)((MainExtension) getParentExtension()).gameRule).BossDamage(pawn.team);
        }
    }
}