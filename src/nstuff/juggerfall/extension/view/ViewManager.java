package nstuff.juggerfall.extension.view;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.other.SimpleNetView;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.weapon.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


    public ISFSArray GetAllViewForStart() {
        SFSArray sfsa = new SFSArray();
        for(NetView view : allView.values()){
            ISFSObject res = new SFSObject();
            switch(view.viewType){
                case  NET_VIEW_TYPE_PAWN:
                    Pawn pawn =(Pawn)view;
                    res.putClass("pawn",pawn.sirPawn);
                    res.putIntArray("stims", new ArrayList<Integer>());
                    if(pawn.owner!=null){
                        res.putInt("ownerId",pawn.owner.getId());
                    }
                    break;
                case  NET_VIEW_TYPE_WEAPON:
                    Weapon weapon =(Weapon)view;
                    res.putClass("weapon",weapon.sirWeapon);
                    res.putInt("pawnId", weapon.owner.id);
                    break;
                case  NET_VIEW_TYPE_SIMPLE:
                    SimpleNetView simpelView =(SimpleNetView)view;
                    res.putClass("model",simpelView.model);
                    break;

            }

            sfsa.addSFSObject(res);

        }
        return sfsa;
    }
}
