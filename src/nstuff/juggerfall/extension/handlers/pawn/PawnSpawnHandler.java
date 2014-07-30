package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.player.Player;

import java.util.List;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PawnSpawnHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        Pawn pawn  =(Pawn)data.getClass("pawn");
        ((MainExtension)getParentExtension()).viewManager.AddView(pawn);
        if(data.getBool("AI")){
            pawn.owner = null;
            pawn.player = null;
        }else{
            pawn.owner = user;
            pawn.SetPlayer((Player) user.getProperty("player"));

        }
        ISFSObject res = new SFSObject();
        res.putClass("pawn",pawn);
        res.putInt("ownerId",user.getId());
        send(PawnHandlerManager.RequestName_PawnSpawn,res,((MainExtension)getParentExtension()).GetOther(user));
    }
}
