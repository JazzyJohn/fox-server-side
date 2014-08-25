package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.Collection;

/**
 * Created by 804129 on 24.08.14.
 */
public class AISwarm_SimpleWave extends AISwarm {
    public int maxSpawnCount;

    public int needToKill;

    private int _alreadySpawn=0;

    private int _alreadyDead=0;

    @Override
    public void LoadFromSFSObject(ISFSObject swarm) {
        super.LoadFromSFSObject(swarm);

        maxSpawnCount  =  swarm.getInt("maxSpawnCount");

        needToKill= swarm.getInt("needToKill");


    }
    @Override
    public void Update(long delta) {
        if(isActive&&_alreadySpawn < maxSpawnCount){
            for(int i =0;i< allPoint.size();i++){
                SpawnPoint point = allPoint.get(i);
                if(point.IsActive()){
                    director.extension.sender.SpawnOnPoint(allBots.get(rand.nextInt(allBots.size())),swarmId,i,point.coords);
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
    public void AgentKill(Pawn pawn) {
        super.AgentKill(pawn);
        _alreadyDead++;
        if (_alreadyDead >=maxSpawnCount || _alreadyDead >= needToKill) {

            Deactivate();
        }
    }

    @Override
    public ISFSObject WriteToSFSObject() {
        ISFSObject data=super.WriteToSFSObject();
        data.putInt("alreadyDead",_alreadyDead);
        return data;
    }

    @Override
    public void AgentSpawn(Pawn pawn) {
        super.AgentSpawn(pawn);
        ///_alreadySpawn++;
    }


}
