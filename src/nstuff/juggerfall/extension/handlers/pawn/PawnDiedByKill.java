package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.player.Player;

public class PawnDiedByKill extends BaseClientRequestHandler {
    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        Pawn pawn = (Pawn)((MainExtension)getParentExtension()).viewManager.getView(data.getInt("viewId"));
        if(!pawn.isOwner(user)){
            return;
        }
        MainExtension extension = ((MainExtension) getParentExtension());
        extension.viewManager.deleteViewLocal(pawn.id);
        if(pawn.owner!=null){
        	pawn.player.death++;
        	extension.gameRule.playerDeath(pawn);
        	int player = data.getInt("player");
        	if(player!=-1){
        		Player killer =((Player)extension.getApi().getUserById(player).getProperty("player"));
        		extension.gameRule.kill(killer.team);
        		killer.kill++;
        	}
        }else{
            int player = data.getInt("player");
            if(player!=-1){
                Player killer =((Player)extension.getApi().getUserById(player).getProperty("player"));
                extension.gameRule.aIDeath(pawn, killer.team);
            }else{
                extension.gameRule.aIDeath(pawn);
            }
            extension.gameRule.director.DeadPawn(pawn);
        }

        send(PawnHandlerManager.RequestName_PawnDiedByKill,data,((MainExtension)getParentExtension()).getOther(user));
    }
}

