package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.PVPGameRuleModel;
import nstuff.juggerfall.extension.models.PVPHuntGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class HuntGameRule extends  GameRule {





    @Override
    public void Kill(int team) {

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
    public void AIDeath(Pawn dead, int team) {
        team =team-1;
        teamScore[team]++;
        CheckGameEnd();

    }


    @Override
    public void Init(Room room) {
        super.Init(room);
        int teamCount = room.getVariable("teamCount").getIntValue();

        teamScore = new int[teamCount];
        canUseRobot = true;
        extension.trace("ROOM START");

    }

    @Override
    public void Reload() {
        super.Reload();


    }

    @Override
    public GameRuleModel GetModel(){
        PVPHuntGameRuleModel model = new PVPHuntGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.teamScore = new ArrayList<Integer>();
        for (int aTeamScore : teamScore) {
            model.teamScore.add(aTeamScore);
        }
        return model;
    }
}
