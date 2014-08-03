package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.PVPGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PVPGameRule extends  GameRule {

    public int[] teamKill;



    @Override
    public void Kill(int team) {
        team =team-1;
        teamKill[team]++;
        teamScore[team]++;
        CheckGameEnd();
    }

    @Override
    public void Spawn(int team) {

    }

    @Override
    public void PlayerDeath(int team) {

    }

    @Override
    public void AIDeath(Pawn death) {

    }


    @Override
    public void Init(Room room) {
        super.Init(room);
        int teamCount = room.getVariable("teamCount").getIntValue();
        teamKill = new int[teamCount];
        teamScore = new int[teamCount];
        canUseRobot = true;
        extension.trace("ROOM START");

    }

    @Override
    public void Reload() {
        super.Reload();
        teamKill = new int[teamKill.length];
        teamScore = new int[teamKill.length];
    }

    @Override
    public GameRuleModel GetModel(){
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
}
