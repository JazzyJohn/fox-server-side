package nstuff.juggerfall.extension.handlermanagers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import nstuff.juggerfall.extension.handlers.pawn.*;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.List;


/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PawnHandlerManager extends AbstractHandlerManager {

    public static final String RequestName_PawnSpawn = "pawnSpawn";

    public static final String RequestName_PawnUpdate = "pawnUpdate";

    public static final String RequestName_PawnKick = "pawnStartKick";

    public static final String RequestName_PawnChangeShootAnimState = "pawnChangeShootAnimState";

    public static final String RequestName_PawnActiveState = "pawnActiveState";

    public static final String RequestName_PawnTaunt = "pawnTaunt";

    public static final String RequestName_PawnKnockOut = "pawnKnockOut";
    @Override
    public void Init() {
        extension.addClientHandler(RequestName_PawnSpawn, PawnSpawnHandler.class);

        extension.addClientHandler(RequestName_PawnUpdate, PawnUpdateHandler.class);

        extension.addClientHandler(RequestName_PawnKick, PawnKickHandler.class);

        extension.addClientHandler(RequestName_PawnChangeShootAnimState, PawnChangeShootAnimStateHandler.class);

        extension.addClientHandler(RequestName_PawnActiveState, PawnActiveStateHandler.class);

        extension.addClientHandler(RequestName_PawnTaunt, PawnTauntHandler.class);

        extension.addClientHandler(RequestName_PawnKnockOut, PawnKnockOutHandler.class);

    }

    public void UpdatePawnInfo(User user, Pawn pawn,boolean UDP) {
        ISFSObject res = new SFSObject();
        res.putClass("pawn",pawn);
        List<User> targets =extension.getParentRoom().getUserList();
        if(user!=null){
            targets.remove(user);
        }
        extension.send(RequestName_PawnUpdate,res,targets,UDP);
    }
}
