package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.GameSettingModel;
import nstuff.juggerfall.extension.models.PVPGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PVPGameRule extends  GameRule {

    public int[] teamKill;

    public boolean firstSpawn = true;

    @Override
    public void kill(int team) {
        if(isPractice){
            return;
        }
        team =team-1;
        teamKill[team]++;
        teamScore[team]++;
        checkGameEnd();
    }

    @Override
    public void spawn(int team) {
        if(firstSpawn){
            firstSpawn= false;
            director.activateAll();
        }
    }

    @Override
    public void playerDeath(Pawn dead) {

    }

    @Override
    public void aIDeath(Pawn death) {

    }

    @Override
    public void aIDeath(Pawn dead, int team) {
        if(isPractice){
            return;
        }
        team =team-1;
        teamKill[team]++;
        teamScore[team]++;
        checkGameEnd();
    }


    @Override
    public void init(Room room) {
        super.init(room);
        ISFSObject object = room.getVariable("gameVar").getSFSObjectValue();
        GameSettingModel settings =(GameSettingModel) object.getClass("gameSetting");
        int teamCount = settings.teamCount;
        teamKill = new int[teamCount];
        teamScore = new int[teamCount];
        canUseRobot = true;


    }

    @Override
    public void reload() {
        super.reload();
        teamKill = new int[teamKill.length];
        firstSpawn = false;
    }

    @Override
    public GameRuleModel getModel(){
        PVPGameRuleModel model = new PVPGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.teamKill = new ArrayList<Integer>();
        model.teamScore = new ArrayList<Integer>();
        for(int i =0; i <teamKill.length;i++){
            model.teamKill.add(teamKill[i]);
            model.teamScore.add(teamScore[i]);

        }

        return model;
    }

    @Override
    public void robotEnter(int team) {

    }

    @Override
    public void deadByAI(int team) {
        if(isPractice){
            return;
        }
        team = 2-team;
        teamKill[team]++;
        teamScore[team]++;
        checkGameEnd();
    }
}
