package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.player.Player;

import java.util.List;

/**
 * Created by 804129 on 24.08.14.
 */
public class AISwarm_PointAttack extends AISwarm_PVPBots {
    private int secondTeamSpawnCount;





    @Override
    public void loadFromSFSObject(ISFSObject swarm) {
        super.loadFromSFSObject(swarm);
        secondTeamSpawnCount = swarm.getInt("secondTeamSpawnCount");


    }

    @Override
    protected int getHome(int team) {
        if(team==1){
            return rand.nextInt(secondTeamSpawnCount);
        }else{
            return secondTeamSpawnCount +rand.nextInt(allPoint.size()-secondTeamSpawnCount);
        }
    }
}
