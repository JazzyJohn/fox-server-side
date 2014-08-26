package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.pawn.Pawn;

/**
 * Created by 804129 on 24.08.14.
 */
public class AISwarm_SimpleWave extends AISwarm {
    public int maxSpawnCount;

    public int needToKill;

    private int _alreadySpawn=0;

    private int _alreadyDead=0;

    @Override
    public void loadFromSFSObject(ISFSObject swarm) {
        super.loadFromSFSObject(swarm);

        maxSpawnCount  =  swarm.getInt("maxSpawnCount");

        needToKill= swarm.getInt("needToKill");


    }
    @Override
    public void update(long delta) {
        if(isActive&&_alreadySpawn < maxSpawnCount){
            for(int i =0;i< allPoint.size();i++){
                SpawnPoint point = allPoint.get(i);
                if(point.isActive()){
                    director.extension.sender.spawnOnPoint(allBots.get(rand.nextInt(allBots.size())), swarmId, i, point.coords);
                    _alreadySpawn++;
                }
                if (_alreadySpawn >= maxSpawnCount)
                {
                    break;
                }
            }
        }

    }

    @Override
    public void agentKill(Pawn pawn) {
        super.agentKill(pawn);
        _alreadyDead++;
        if (_alreadyDead >=maxSpawnCount || _alreadyDead >= needToKill) {

            deactivate();
        }
    }

    @Override
    public ISFSObject writeToSFSObject() {
        ISFSObject data=super.writeToSFSObject();
        data.putInt("alreadyDead",_alreadyDead);
        return data;
    }

    @Override
    public void agentSpawn(Pawn pawn) {
        super.agentSpawn(pawn);
        ///_alreadySpawn++;
    }


}
