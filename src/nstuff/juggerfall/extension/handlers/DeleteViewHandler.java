package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.view.NetView;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class DeleteViewHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User sender, ISFSObject data) {
        // TODO Auto-generated method stub
        int id = data.getInt("id");
        ((MainExtension) getParentExtension()).viewManager.DeleteView(sender,id);


    }

}