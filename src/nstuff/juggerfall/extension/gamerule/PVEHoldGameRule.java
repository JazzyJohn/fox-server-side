package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.HoldPositionGameRuleModel;
import nstuff.juggerfall.extension.models.PVEGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PVEHoldGameRule extends  GameRule {

    public int playerDeath= 0;
    public List<Integer> route = new ArrayList<Integer>();


    @Override
    protected void checkGameEnd() {
        if(playerDeath>=maxScore){
            gameFinish();
        }else{
            extension.updateGame();
        }
    }

    @Override
    public void kill(int team) {

    }

    @Override
    public void spawn(int team) {

    }

    @Override
    public void playerDeath(Pawn dead) {
        playerDeath++;
        teamScore[0]++;
        checkGameEnd();
    }

    @Override
    public void playerJoin(User user) {

    }

    @Override
    public void aIDeath(Pawn dead) {

    }

    @Override
    public void aIDeath(Pawn dead, int team) {

    }


    @Override
    public void init(Room room) {
        super.init(room);
        int teamCount = 2;
        teamScore = new float[teamCount];
        playerDeath= 0;
        canUseRobot = false;


    }

    @Override
    public void reload() {
        super.reload();
        ready = false;
        playerDeath= 0;
    }

    @Override
    public GameRuleModel getModel(){
        HoldPositionGameRuleModel model = new HoldPositionGameRuleModel();
        model.isGameEnded = isGameEnded;

        model.teamScore = new ArrayList<Integer>();
        for (float aTeamScore : teamScore) {

            model.teamScore.add((int)aTeamScore);

        }
        return model;
    }


    @Override
    public void addMasterInfo(ISFSObject res) {
        super.addMasterInfo(res);

    }

    @Override
    public void robotEnter(int team) {

    }

    @Override
    public void deadByAI(int team) {

    }

    public void nextWave() {
        teamScore[1]++;
        extension.updateGame();
    }
}
