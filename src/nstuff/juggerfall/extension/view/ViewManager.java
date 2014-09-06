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



    public void deleteView(Integer id) {
      deleteView(null, id);

    }
    public void deleteView(User sender, Integer id) {
        NetView view  = allView.get(id);
        view.delete();
        if(view instanceof SimpleNetView){
            SimpleNetView simple = (SimpleNetView)view;
            switch (simple.prefType){
                case EGG:
                    extension.gameRule.director.RemoveEgg((QueenEgg)simple);
                    break;
            }
        }
        allView.remove(id);

        extension.deleteView(sender, id);

    }

    public void addView(NetView view) {
        allView.put(view.id,view);
        view.manager = this;
    }
    public NetView getView(int id){
        return allView.get(id);
    }

    public void reload(){
        for(NetView view : allView.values()){
            view.clearRef();
        }
        allView.clear();
        deleteSceneView.clear();

    }

    public void deleteViewLocal(int id) {
        NetView view  = allView.get(id);
        view.deleteLocal();
        allView.remove(id);
    }


    public ISFSArray getAllViewForStart() {
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
                    if(pawn.isAi&&pawn.owner==null){
                        res.putInt("group",pawn.aiSwarmId);
                        res.putInt("home",pawn.aihome);
                    }
                    break;
                case  NET_VIEW_TYPE_WEAPON:
                    Weapon weapon =(Weapon)view;
                    res.putClass("weapon",weapon.sirWeapon);
                    if(weapon.owner==null){
                        res.putInt("pawnId", weapon.lateId);
                    }else {
                        res.putInt("pawnId", weapon.owner.id);
                    }
                    break;
                case  NET_VIEW_TYPE_SIMPLE:
                    SimpleNetView simpelView =(SimpleNetView)view;
                    res.putClass("model",simpelView.model);
                    simpelView.addData(res);
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

    public  SFSArray removePlayerView(int ownerId){
        SFSArray sfsa = new SFSArray();
        Iterator<Map.Entry<Integer ,NetView>> iterator = allView.entrySet().iterator();
        while(iterator.hasNext()){

            Map.Entry<Integer ,NetView> entry = iterator.next();
            NetView view =entry.getValue();
            if(view.needDelete(ownerId)){
                view.deleteLocal();
                sfsa.addInt(view.id);
                iterator.remove();
            }

        }
        return sfsa;
    }

    public boolean hasView(int id) {

       return  allView.containsKey(id);
    }

    public void deleteSceneView(User sender, int id) {
        deleteView(sender, id);
        deleteSceneView.add(id);
    }

    public List<Integer> getAllDeleteSceneViewForStart() {
       return deleteSceneView;
    }
}
