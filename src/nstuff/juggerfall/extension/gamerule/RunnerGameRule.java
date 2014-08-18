package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import nstuff.juggerfall.extension.models.RunnerGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;

/**
 * Created by Ivan.Ochincenko on 04.08.14.
 */
public class RunnerGameRule extends GameRule {

    @Override
    public void Init(Room room) {
        super.Init(room);
        teamScore= new int[2];
    }

    @Override
    public void Kill(int team) {

    }

    @Override
    public void Spawn(int team) {

    }
    @Override
    protected void CheckGameEnd() {
        extension.UpdateGame();
    }


    public void NextRoom() {
        teamScore[0]++;
        extension.UpdateGame();
    }

    @Override
    public void PlayerDeath(int team) {
        GameFinish();
    }

    @Override
    public void AIDeath(Pawn dead) {
        teamScore[1]++;
    }

    @Override
    public void AIDeath(Pawn dead, int team) {

    }

    @Override
    public RunnerGameRuleModel GetModel() {
        RunnerGameRuleModel model = new RunnerGameRuleModel();
        model.isGameEnded = isGameEnded;

        model.teamScore = new ArrayList<Integer>();
        for (int aTeamScore : teamScore) {
            model.teamScore.add(aTeamScore);

        }


        return null;
    }


}
