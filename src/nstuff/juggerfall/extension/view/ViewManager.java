package nstuff.juggerfall.extension.view;

import java.util.HashMap;
import java.util.Map;


import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.pawn.Pawn;

public class ViewManager {
	private Map<Integer ,NetView> allView = new HashMap<Integer ,NetView>();

    public MainExtension extension;



    public void DeleteView(Integer id) {
      DeleteView(null,id);

    }
    public void DeleteView(User sender,Integer id) {
        NetView view  = allView.get(id);
        view.Delete();
        allView.remove(id);

        extension.DeleteView(sender, id);

    }

    public void AddView(NetView view) {
        allView.put(view.id,view);
        view.manager = this;
    }
    public NetView GetView(int id){
        return allView.get(id);
    }

    public void Reload(){
        for(NetView view : allView.values()){
            view.ClearRef();
        }
        allView = new HashMap<Integer ,NetView>();

    }

    public void DeleteViewLocal(int id) {
        NetView view  = allView.get(id);
        view.DeleteLocal();
        allView.remove(id);
    }


}
