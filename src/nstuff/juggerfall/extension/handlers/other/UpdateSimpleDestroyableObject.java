package nstuff.juggerfall.extension.handlers.other;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gameplay.SimpleDestroyableView;
import nstuff.juggerfall.extension.handlermanagers.OtherHandlerManager;
import nstuff.juggerfall.extension.models.SimpleDestroyableModel;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.other.SimpleNetView;

/**
 * Created by 804129 on 07.08.14.
 */
public class UpdateSimpleDestroyableObject extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject data) {
        SimpleDestroyableModel model  =(SimpleDestroyableModel)data.getClass("model");
        SimpleDestroyableView view;
        if(  ((MainExtension)getParentExtension()).viewManager.HasView(model.id)){
            view = (SimpleDestroyableView) ((MainExtension)getParentExtension()).viewManager.GetView(model.id);
        }else{
            view = new SimpleDestroyableView(model);
            ((MainExtension)getParentExtension()).viewManager.AddView(view);
        }
        send(OtherHandlerManager.RequestName_UpdateSimpleDestroyableObject, data, ((MainExtension) getParentExtension()).GetOther(user));

    }
}
