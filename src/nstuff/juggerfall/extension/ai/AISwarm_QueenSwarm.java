package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.gameplay.QueenEgg;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 804129 on 24.08.14.
 */
public class AISwarm_QueenSwarm extends AISwarm {
    public Pawn queen;

    public String queenPrefab;

    public int maxSwarmling;

    public int _alreadySpawn;

    public List<QueenEgg> eggs = new ArrayList<QueenEgg>();

    @Override
    public void AgentKill(Pawn pawn) {
        super.AgentKill(pawn);
        if(pawn==queen){
            Deactivate();
        }
    }

    @Override
    public void AgentSpawn(Pawn pawn) {
        super.AgentSpawn(pawn);
        if(pawn.type==queenPrefab){
            queen=pawn;
        }
    }

    @Override
    public void LoadFromSFSObject(ISFSObject swarm) {
        super.LoadFromSFSObject(swarm);
        queenPrefab= swarm.getUtfString("queen");
        maxSwarmling = swarm.getInt("maxSwarmling");
    }

    @Override
    public void Activate() {
        super.Activate();
        int pointId = rand.nextInt(allPoint.size());
        director.extension.sender.SpawnOnPoint(queenPrefab,swarmId,pointId,allPoint.get(pointId).coords);

    }

    @Override
    public void Update(long delta) {
        if(isActive&&_alreadySpawn < maxSwarmling){
            while (eggs.size()>0&& eggs.get(0).IsReady()){
                QueenEgg point = eggs.get(0);
                eggs.remove(0);

                director.extension.sender.SpawnOnPoint(allBots.get(rand.nextInt(allBots.size())),swarmId,0,point.position);
                _alreadySpawn++;
                director.extension.viewManager. DeleteView(point.id);
                if (_alreadySpawn >= maxSwarmling)
                {
                    break;
                }
            }

        }
    }
    public void AddEgg(QueenEgg queenEgg){
        eggs.add(queenEgg);
    }

    public void RemoveEgg(QueenEgg queenEgg) {
        eggs.remove(queenEgg);
    }
}
