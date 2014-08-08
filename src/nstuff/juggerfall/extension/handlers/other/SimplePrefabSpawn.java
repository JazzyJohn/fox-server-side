package nstuff.juggerfall.extension.handlers.other;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.OtherHandlerManager;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.other.SimpleNetView;

/**
 * Created by 804129 on 02.08.14.
 */
public class SimplePrefabSpawn extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        SimpleNetModel model  =(SimpleNetModel)data.getClass("model");
        SimpleNetView view = new SimpleNetView(model);
        ((MainExtension)getParentExtension()).viewManager.AddView(view);
        send(OtherHandlerManager.RequestName_SimplePrefabSpawn, data, ((MainExtension) getParentExtension()).GetOther(user));

    }
}
