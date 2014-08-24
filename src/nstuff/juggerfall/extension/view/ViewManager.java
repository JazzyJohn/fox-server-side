package nstuff.juggerfall.extension.view;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gameplay.QueenEgg;
import nstuff.juggerfall.extension.gameplay.SimpleDestroyableView;
import nstuff.juggerfall.extension.other.SimpleNetView;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.weapon.Weapon;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ViewManager {
	private Map<Integer ,NetView> allView = new ConcurrentHashMap<Integer ,NetView>();

    public List<Integer> deleteSceneView = new ArrayList<Integer>();

    public MainExtension extension;



    public void DeleteView(Integer id) {
      DeleteView(null,id);

    }
    public void DeleteView(User sender,Integer id) {
        NetView view  = allView.get(id);
        view.Delete();
        if(view instanceof SimpleNetView){
            SimpleNetView simple = (SimpleNetView)view;
            switch (simple.prefType){
                case EGG:
                    extension.gameRule.director.RemoveEgg((QueenEgg)simple);
                    break;
            }
        }
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
        allView.clear();
        deleteSceneView.clear();

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
                    res.putBool("isAI", pawn.isAi);
                    if(pawn.isAi){
                        res.putInt("group",pawn.aiSwarmId);
                        res.putInt("home",pawn.aihome);
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
                case NET_VIEW_TYPE_SIMPLE_DESTROYABLE:
                    SimpleDestroyableView simpelDestView =(SimpleDestroyableView)view;
                    res.putClass("model",simpelDestView.model);

                    break;
            }

            sfsa.addSFSObject(res);

        }
        return sfsa;
    }

    public  SFSArray RemovePlayerView(int ownerId){
        SFSArray sfsa = new SFSArray();
        Iterator<Map.Entry<Integer ,NetView>> iterator = allView.entrySet().iterator();
        while(iterator.hasNext()){

            Map.Entry<Integer ,NetView> entry = iterator.next();
            NetView view =entry.getValue();
            switch(view.viewType){
                case  NET_VIEW_TYPE_PAWN:
                    Pawn pawn =(Pawn)view;
                    if(pawn.owner!=null&&pawn.owner.getId()==ownerId){
                        pawn.DeleteLocal();
                        sfsa.addInt(view.id);
                        iterator.remove();
                    }

                break;
                case  NET_VIEW_TYPE_WEAPON:
                    Weapon weapon =(Weapon)view;
                    if(weapon.owner!=null&&weapon.owner.owner!=null&&weapon.owner.owner.getId()==ownerId){
                        weapon.DeleteLocal();
                        sfsa.addInt(view.id);
                        iterator.remove();
                    }
                    break;
			default:
				break;
            }
        }
        return sfsa;
    }

    public boolean HasView(int id) {

       return  allView.containsKey(id);
    }

    public void DeleteSceneView(User sender, int id) {
        DeleteView(sender,id);
        deleteSceneView.add(id);
    }

    public List<Integer> GetAllDeleteSceneViewForStart() {
       return deleteSceneView;
    }
}
