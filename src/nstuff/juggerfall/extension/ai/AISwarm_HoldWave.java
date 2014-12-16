package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 804129 on 24.08.14.
 */
public class AISwarm_HoldWave extends AISwarm_QuantizeWave {

    protected int waveWithBossCount;

    protected boolean wasBoss;

    protected int bossCount=1;

    protected  int _spawnedBossCount=0;

    protected List<String> allBosses= new ArrayList<String>();


    @Override
    public void update(long delta) {
        if(isActive&&_alreadySpawn < maxSpawnCount[_curWave]){
            for(int i =0;i< allPoint.size();i++){
                SpawnPoint point = allPoint.get(i);
                if(point.isActive()){
                    String prefab;
                    if((_curWave+1)%waveWithBossCount==0&&_spawnedBossCount<bossCount){
                        _spawnedBossCount++;
                        prefab =allBosses.get(rand.nextInt(allBosses.size()));
                    }else{
                        prefab =allBots.get(rand.nextInt(allBots.size()));
                    }
                    director.extension.sender.spawnOnPoint(prefab, swarmId, i, point.coords);
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
    protected void nextSwarmWave() {
        _alreadyDead =0;
        _alreadySpawn = 0;
        _spawnedBossCount=0;
        _curWave++;

        director.nextWave();
        if(_curWave>=needToKill.length){
            _curWave--;
            waveWithBossCount =1;
            maxSpawnCount[_curWave]=Math.min((int)Math.round(maxSpawnCount[_curWave]+maxSpawnCount[_curWave]*0.2),MAX_BOT_ON_WAVE);
            needToKill[_curWave]=Math.min((int)Math.round(needToKill[_curWave]+needToKill[_curWave]*0.2),MAX_BOT_ON_WAVE);
            if(bossCount*2< needToKill[_curWave]){
                bossCount++;
            }

        }
        director.extension.sender.sendNextWave(swarmId);
    }

    public void loadFromSFSObject(ISFSObject swarm) {
        super.loadFromSFSObject(swarm);
        for(String bot :swarm.getUtfStringArray("bosses")){
            allBosses.add(bot);
        }
        waveWithBossCount = swarm.getInt("waveWithBossCount");


    }
}
