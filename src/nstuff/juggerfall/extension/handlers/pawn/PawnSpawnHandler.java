package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.models.PawnModel;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.player.Player;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PawnSpawnHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        PawnModel pawnModel  =(PawnModel)data.getClass("pawn");
        Pawn pawn = new Pawn(pawnModel);
        MainExtension extension=(MainExtension)getParentExtension();
        extension.viewManager.addView(pawn);
        ISFSObject res = new SFSObject();
        if(data.getBool("Scene")){
            pawn.owner = null;
            pawn.player = null;
            pawn.isAi = data.getBool("isAI");
            if(pawn.isAi){
                pawn.aiSwarmId =data.getInt("group");
                pawn.aihome = data.getInt("home");
                extension.gameRule.director.AddPawn(pawn);
            }

        }else{
            res.putInt("ownerId",user.getId());
            pawn.owner = user;
            pawn.setPlayer((Player) user.getProperty("player"));
            if(!data.getBool("isAI")){
                extension.gameRule.spawn(pawn.team);
            }
            pawn.isAi = data.getBool("isAI");
        }
        res.putBool("isAI", data.getBool("isAI"));
        res.putClass("pawn",pawnModel);
        res.putIntArray("stims",data.getIntArray("stims"));
        send(PawnHandlerManager.RequestName_PawnSpawn,res,extension.getOther(user));
    }
}
