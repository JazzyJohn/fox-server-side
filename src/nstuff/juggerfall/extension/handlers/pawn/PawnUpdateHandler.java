package nstuff.juggerfall.extension.handlers.pawn;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.*;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.PawnHandlerManager;
import nstuff.juggerfall.extension.models.PawnModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.Iterator;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PawnUpdateHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {

        ISFSArray pawns = data.getSFSArray("pawns");
        ISFSArray verifyPawns = new SFSArray();
        MainExtension extension=(MainExtension)getParentExtension();

        if(user.getId() ==extension.masterInfo.getId()){
            extension.getMasterWatcher().updateMasterTime();
        }
        for(Iterator<SFSDataWrapper> iterator =pawns.iterator();iterator.hasNext();){
            ISFSObject incPawn  =(ISFSObject)iterator.next().getObject();

            Pawn pawn = (Pawn)extension.viewManager.getView(incPawn.getInt("id"));
            if(pawn==null){
                continue;
            }
            if(!pawn.isOwner(user)){
                //getParentExtension();
                extension.trace("Not owner");
                continue;
            }
            verifyPawns.addSFSObject(incPawn);
            pawn.update(incPawn);
        }
        ISFSObject verifyData = new SFSObject();
        verifyData.putSFSArray("pawns",verifyPawns);
        extension.send(PawnHandlerManager.RequestName_PawnUpdate, verifyData, extension.getOther(user),true);

    }
}
