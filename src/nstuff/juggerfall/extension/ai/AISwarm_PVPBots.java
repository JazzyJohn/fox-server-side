package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.player.Player;

import java.util.List;

/**
 * Created by 804129 on 24.08.14.
 */
public class AISwarm_PVPBots extends AISwarm {
    private int maxBots;

    private int[] inTeamPlayer;

    private int[] inTeamPawns;



    @Override
    public void loadFromSFSObject(ISFSObject swarm) {
        super.loadFromSFSObject(swarm);
        maxBots = swarm.getInt("maxBots");
        inTeamPlayer = new int[2];
        inTeamPawns= new int[2];
        inTeamPawns[0] = 0;
        inTeamPawns[1] = 0;
        countPlayers();

    }

    private void countPlayers() {
        inTeamPlayer[0]=0;inTeamPlayer[1]=0;
        List<User> allPlayer =  director.extension.getParentRoom().getPlayersList();
        for(User user : allPlayer){
            Player player =((Player)user.getProperty("player"));
            if(player.team==1){
                inTeamPlayer[0] ++;
            }else{
                inTeamPlayer[1]++;
            }
        }
    }

    @Override
    public void update(long delta) {
        if(isActive){
            countPlayers();
            for(int i =0;i<2;i++){
                if(maxBots<inTeamPlayer[i]+inTeamPawns[i]) {
                    for (Pawn pawn : allPawn) {
                        if(pawn.team==(i+1)) {
                            director.extension.viewManager.deleteView(pawn.id);
                            inTeamPawns[i]--;
                            break;
                        }
                    }
                }
                if(maxBots>inTeamPlayer[i]+inTeamPawns[i]){
                    int home = rand.nextInt(allPoint.size());
                    inTeamPawns[i]++;
                    director.extension.sender.spawnOnPoint(allBots.get(i), swarmId,home, allPoint.get(home).coords,i+1);
                }
            }


        }

    }

    @Override
    public void agentSpawn(Pawn pawn) {
        super.agentSpawn(pawn);


    }

    @Override
    public void agentKill(Pawn pawn) {
        super.agentKill(pawn);
        if(pawn.team==1){
            inTeamPawns[0]--;
        }else{
            inTeamPawns[1]--;
        }

    }
}
