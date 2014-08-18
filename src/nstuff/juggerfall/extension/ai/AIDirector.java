package nstuff.juggerfall.extension.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 18.08.14.
 */
public class AIDirector {

    protected List<Integer> activeSwarm;

    public void UpdateSwarm(Collection<Integer> ids) {
        if(activeSwarm==null){
            activeSwarm =new ArrayList<Integer>();
        }else{
            activeSwarm.clear();
        }
        for(Integer id : ids){
            activeSwarm.add(id);
        }

    }

    public boolean HasList(){
        return activeSwarm!=null;
    }

    public List<Integer> GetList(){
        return activeSwarm;
    }
}
