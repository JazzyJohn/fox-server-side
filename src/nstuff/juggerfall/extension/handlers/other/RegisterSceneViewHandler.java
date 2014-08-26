package nstuff.juggerfall.extension.handlers.other;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.other.PREFABTYPE;
import nstuff.juggerfall.extension.other.SimpleNetView;

/**
 * Created by Ivan.Ochincenko on 04.08.14.
 */
public class RegisterSceneViewHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        SimpleNetModel model  =(SimpleNetModel)data.getClass("model");
        SimpleNetView view = new SimpleNetView(model, PREFABTYPE.SIMPLE);
        ((MainExtension)getParentExtension()).viewManager.addView(view);
    }

}