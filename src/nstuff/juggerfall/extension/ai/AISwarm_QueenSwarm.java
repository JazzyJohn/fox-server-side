package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.gameplay.QueenEgg;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
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
    public void agentKill(Pawn pawn) {
        super.agentKill(pawn);
        if(pawn==queen){
            deactivate();
        }
    }

    @Override
    public void agentSpawn(Pawn pawn) {
        super.agentSpawn(pawn);
        if(pawn.type==queenPrefab){
            queen=pawn;
        }
    }

    @Override
    public void loadFromSFSObject(ISFSObject swarm) {
        super.loadFromSFSObject(swarm);
        queenPrefab= swarm.getUtfString("queen");
        maxSwarmling = swarm.getInt("maxSwarmling");
    }

    @Override
    public void activate() {
        super.activate();
        int pointId = rand.nextInt(allPoint.size());
        director.extension.sender.spawnOnPoint(queenPrefab, swarmId, pointId, allPoint.get(pointId).coords);

    }

    @Override
    public void update(long delta) {
        if(isActive&&_alreadySpawn < maxSwarmling){
            while (eggs.size()>0&& eggs.get(0).isReady()){
                QueenEgg point = eggs.get(0);
                eggs.remove(0);

                director.extension.sender.spawnOnPoint(allBots.get(rand.nextInt(allBots.size())), swarmId, 0, point.position);
                _alreadySpawn++;
                director.extension.viewManager.deleteView(point.id);
                if (_alreadySpawn >= maxSwarmling)
                {
                    break;
                }
            }

        }
    }
    public void addEgg(QueenEgg queenEgg){
        eggs.add(queenEgg);
    }

    public void removeEgg(QueenEgg queenEgg) {
        eggs.remove(queenEgg);
    }
}
