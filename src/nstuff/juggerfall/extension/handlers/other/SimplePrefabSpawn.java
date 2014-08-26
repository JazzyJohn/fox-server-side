package nstuff.juggerfall.extension.handlers.other;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gameplay.QueenEgg;
import nstuff.juggerfall.extension.handlermanagers.OtherHandlerManager;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.other.PREFABTYPE;
import nstuff.juggerfall.extension.other.SimpleNetView;

/**
 * Created by 804129 on 02.08.14.
 */
public class SimplePrefabSpawn extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        SimpleNetModel model  =(SimpleNetModel)data.getClass("model");
        PREFABTYPE type = PREFABTYPE.fromInteger(data.getInt("preftype"));
        SimpleNetView view= null;
        switch (type){
            case EGG:
                QueenEgg egg = new QueenEgg(model,type);
                egg.spawnId = data.getInt("spawnid");
                egg.readyDelay =  data.getLong("delay");

                ((MainExtension)getParentExtension()).gameRule.director.AddEgg(egg);
                view= egg;
                break;
            case SIMPLE:
                view = new SimpleNetView(model,type);
                break;
        }

        ((MainExtension)getParentExtension()).viewManager.addView(view);
        send(OtherHandlerManager.RequestName_SimplePrefabSpawn, data, ((MainExtension) getParentExtension()).getOther(user));

    }
}
