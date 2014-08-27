package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import nstuff.juggerfall.extension.baseobject.TimeUpdateEntity;
import nstuff.juggerfall.extension.models.Vector3Model;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 804129 on 24.08.14.
 */
public class AISwarm implements TimeUpdateEntity {

    protected List<String> allBots= new ArrayList<String>();

    protected List<SpawnPoint> allPoint  = new ArrayList<SpawnPoint>();

    protected List<Pawn> allPawn = new ArrayList<Pawn>();

    protected int swarmId;

    protected boolean  isActive;

    protected AIDirector director;

    public Random rand= new Random();

    public void init(AIDirector director, int id){
        this.director = director;
        swarmId= id;
        director.extension.addToUpdate(this);
    }

    @Override
    public void update(long delta) {
        if(isActive){
            for(int i =0;i< allPoint.size();i++){
                SpawnPoint point = allPoint.get(i);
                if(point.isActive()){
                    director.extension.sender.spawnOnPoint(allBots.get(rand.nextInt(allBots.size())), swarmId, i, point.coords);

                }
            }
        }

    }

    public void activate(){
        isActive= true;
        director.extension.sender.sendSwarmChange(swarmId, isActive);

    }

    public void deactivate(){
        isActive= false;
        director.extension.sender.sendSwarmChange(swarmId, isActive);
        director.SwarmEnd(swarmId);
    }

    public void loadFromSFSObject(ISFSObject swarm) {
        for(String bot :swarm.getUtfStringArray("bots")){
            allBots.add(bot);
        }
        ISFSArray points = swarm.getSFSArray("points");
        for(int i=0; i<points.size();i++){

            allPoint.add(new SpawnPoint((Vector3Model)points.getClass(i),swarm.getLong("timeDelay")));
        }
    }

    public void agentKill(Pawn pawn){
        allPoint.get(pawn.aihome).deadPawn();
        allPawn.remove(pawn);
    }

    public void agentSpawn(Pawn pawn) {
        allPawn.add(pawn);
        allPoint.get(pawn.aihome).setPawnId(pawn.id);
    }

    public ISFSObject writeToSFSObject() {
        ISFSObject data = new SFSObject();
        data.putBool("active",isActive);
        return data;
    }


}
