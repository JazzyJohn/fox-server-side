package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.Collection;

/**
 * Created by 804129 on 24.08.14.
 */
public class AISwarm_QuantizeWave extends AISwarm {
    public int[] maxSpawnCount;

    public int _curWave=0;

    public int[] needToKill;

    private int _alreadySpawn=0;

    private int _alreadyDead=0;

    @Override
    public void loadFromSFSObject(ISFSObject swarm) {
        super.loadFromSFSObject(swarm);
        Collection<Integer> maxSpawn = swarm.getIntArray("maxSpawnCount");
        maxSpawnCount  = new int[maxSpawn.size()];
        int i =0;
        for(Integer val :maxSpawn){
            maxSpawnCount[i]=val;
            i++;
        }
        Collection<Integer> needTo = swarm.getIntArray("needToKill");
        needToKill  = new int[needTo.size()];
        i =0;
        for(Integer val :needTo){
            needToKill[i]=val;
            i++;
        }

    }
    @Override
    public void update(long delta) {
        if(isActive&&_alreadySpawn < maxSpawnCount[_curWave]){
            for(int i =0;i< allPoint.size();i++){
                SpawnPoint point = allPoint.get(i);
                if(point.isActive()){
                    director.extension.sender.spawnOnPoint(allBots.get(rand.nextInt(allBots.size())), swarmId, i, point.coords);
                    _alreadySpawn++;
                }
                if (_alreadySpawn >= maxSpawnCount[_curWave])
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
        if (_alreadyDead >= maxSpawnCount[_curWave] || _alreadyDead >= needToKill[_curWave]) {

            nextSwarmWave();
        }
    }

    @Override
    public ISFSObject writeToSFSObject() {
        ISFSObject data=super.writeToSFSObject();
        data.putInt("alreadyDead",_alreadyDead);
        data.putInt("curWave",_curWave);
        return data;
    }

    @Override
    public void agentSpawn(Pawn pawn) {
        super.agentSpawn(pawn);
        ///_alreadySpawn++;
    }

    private void nextSwarmWave() {
        _alreadyDead =0;
        _alreadySpawn = 0;
        _curWave++;


        if(_curWave>=needToKill.length){
            deactivate();
        }else{
            director.extension.sender.sendNextWave(swarmId);
        }
    }
}
