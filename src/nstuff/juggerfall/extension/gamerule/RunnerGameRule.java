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
    public void init(Room room) {
        super.init(room);
        teamScore= new float[2];
    }

    @Override
    public void kill(int team) {

    }

    @Override
    public void spawn(int team) {

    }
    @Override
    protected void checkGameEnd() {
        extension.updateGame();
    }


    public void nextRoom() {
        teamScore[0]++;
        extension.updateGame();
    }

    @Override
    public void playerDeath(Pawn dead) {
        gameFinish();
    }

    @Override
    public void aIDeath(Pawn dead) {
        teamScore[1]++;
    }

    @Override
    public void aIDeath(Pawn dead, int team) {

    }

    @Override
    public RunnerGameRuleModel getModel() {
        RunnerGameRuleModel model = new RunnerGameRuleModel();
        model.isGameEnded = isGameEnded;

        model.teamScore = new ArrayList<Integer>();
        for (float aTeamScore : teamScore) {
            model.teamScore.add((int)aTeamScore);

        }


        return null;
    }

    @Override
    public void robotEnter(int team) {

    }

    @Override
    public void deadByAI(int team) {

    }


}
