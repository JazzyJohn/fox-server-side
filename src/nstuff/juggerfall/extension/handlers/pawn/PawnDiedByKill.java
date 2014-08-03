package nstuff.juggerfall.extension.handlers.pawn;

import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.player.Player;

public class PawnDiedByKill extends BaseClientRequestHandler {
    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        Pawn pawn = (Pawn)((MainExtension)getParentExtension()).viewManager.GetView(data.getInt("viewId"));
        if(!pawn.IsOwner(user)){
            return;
        }
        MainExtension extension = ((MainExtension) getParentExtension());
        extension.viewManager.DeleteViewLocal(pawn.id);
        if(pawn.owner!=null){
        	pawn.player.death++;
        	extension.gameRule.PlayerDeath(pawn.team);
        	int player = data.getInt("player");
        	if(player!=-1){
        		Player killer =((Player)extension.getApi().getUserById(player).getProperty("player"));
        		extension.gameRule.Kill(killer.team);
        		killer.kill++;
        	}
        }

        send(PawnHandlerManager.RequestName_PawnDiedByKill,data,((MainExtension)getParentExtension()).GetOther(user));
    }
}

