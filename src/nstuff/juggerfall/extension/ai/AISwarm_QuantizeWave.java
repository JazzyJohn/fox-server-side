package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.baseobject.TimeUpdateEntity;
import nstuff.juggerfall.extension.models.Vector3Model;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
    public void LoadFromSFSObject(ISFSObject swarm) {
        super.LoadFromSFSObject(swarm);
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
    public void Update(long delta) {
        if(isActive&&_alreadySpawn < maxSpawnCount[_curWave]){
            for(int i =0;i< allPoint.size();i++){
                SpawnPoint point = allPoint.get(i);
                if(point.IsActive()){
                    director.extension.sender.SpawnOnPoint(allBots.get(rand.nextInt(allBots.size())),swarmId,i,point.coords);
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
    public void AgentKill(Pawn pawn) {
        super.AgentKill(pawn);
        _alreadyDead++;
        if (_alreadyDead >= maxSpawnCount[_curWave] || _alreadyDead >= needToKill[_curWave]) {

            NextSwarmWave();
        }
    }

    @Override
    public ISFSObject WriteToSFSObject() {
        ISFSObject data=super.WriteToSFSObject();
        data.putInt("alreadyDead",_alreadyDead);
        data.putInt("curWave",_curWave);
        return data;
    }

    @Override
    public void AgentSpawn(Pawn pawn) {
        super.AgentSpawn(pawn);
        ///_alreadySpawn++;
    }

    private void NextSwarmWave() {
        _alreadyDead =0;
        _alreadySpawn = 0;
        _curWave++;


        if(_curWave>=needToKill.length){
            Deactivate();
        }else{
            director.extension.sender.SendNextWave(swarmId);
        }
    }
}
