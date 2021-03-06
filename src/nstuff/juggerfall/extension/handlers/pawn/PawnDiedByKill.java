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
        if(pawn==null||!pawn.isOwner(user)){
            return;
        }
        MainExtension extension = ((MainExtension) getParentExtension());
        extension.viewManager.deleteViewLocal(pawn.id);
        extension.clearEarlyWeapon(pawn);
        if(pawn.owner!=null){
        //	pawn.player.death++;

        	int player = data.getInt("player");
        	if(player!=-1){
                User killerUser = extension.getApi().getUserById(player);
                if(killerUser!=null) {
                    Player killer = ((Player) killerUser.getProperty("player"));
                    if(killer.team!=pawn.team)
                        extension.gameRule.kill(killer.team);
                   // killer.kill++;
                }
        	}else{
                extension.gameRule.deadByAI(pawn.team);
            }
        }else{
            int player = data.getInt("player");
            if(player!=-1){
                User killerUser = extension.getApi().getUserById(player);
                if(killerUser!=null){
                    Player killer =((Player)killerUser.getProperty("player"));
                    if(killer.team!=pawn.team)
                        extension.gameRule.aIDeath(pawn, killer.team);
                    //killer.aikill++;
                }

            }else{
                extension.gameRule.aIDeath(pawn);
            }
            extension.gameRule.director.DeadPawn(pawn);
        }

        send(PawnHandlerManager.RequestName_PawnDiedByKill,data,((MainExtension)getParentExtension()).getOther(user));
    }
}

