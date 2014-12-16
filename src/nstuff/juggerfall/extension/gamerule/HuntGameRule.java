package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.GameSettingModel;
import nstuff.juggerfall.extension.models.PVPHuntGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class HuntGameRule extends  GameRule {

    Map<String, Object> scoreTable;

    private static final String BOSS_DAMAGE = "boss_damage";

    private static final String ROBOT_ENTER = "robot_enter";

    @Override
    public void kill(int team) {

    }

    @Override
    public void spawn(int team) {

    }

    @Override
    public void playerDeath(Pawn dead) {
        int scoreTeam = 1-(dead.team-1);
        if(scoreTable.containsKey(dead.type)){
            teamScore[scoreTeam]+= (Integer)scoreTable.get(dead.type);
        }
    }

    @Override
    public void aIDeath(Pawn death) {

    }

    @Override
    public void aIDeath(Pawn dead, int team) {
        team =team-1;
        if(scoreTable.containsKey(dead.type)){
            teamScore[team]+= (Integer)scoreTable.get(dead.type);
        }else{
            teamScore[team]++;
        }

        checkGameEnd();
    }


    @Override
    public void init(Room room) {

        super.init(room);
        ISFSObject object = room.getVariable("gameVar").getSFSObjectValue();
        GameSettingModel settings =(GameSettingModel) object.getClass("gameSetting");
        int teamCount = settings.teamCount;
        teamScore = new int[teamCount];
        canUseRobot = true;



        scoreTable = settings.huntTable;
        if(scoreTable==null){
            scoreTable = new HashMap<String, Object>();
        }
    }

    @Override
    public void reload() {
        super.reload();


    }
    @Override
    protected void checkGameEnd(){

        extension.updateGame();
    }
    @Override
    public GameRuleModel getModel(){
        PVPHuntGameRuleModel model = new PVPHuntGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.teamScore = new ArrayList<Integer>();
        for (int aTeamScore : teamScore) {
            model.teamScore.add(aTeamScore);
        }
        return model;
    }

    @Override
    public void robotEnter(int team) {
        team--;
        if(scoreTable.containsKey(ROBOT_ENTER)){
            teamScore[team]+= (Integer)scoreTable.get(ROBOT_ENTER);
        }else{
            teamScore[team]++;
        }
        checkGameEnd();
    }

    @Override
    public void deadByAI(int team) {

    }

    public void bossDamage(int team) {
        team =team-1;
        if(scoreTable.containsKey(BOSS_DAMAGE)){
            teamScore[team]+= (Integer)scoreTable.get(BOSS_DAMAGE);
        }else{
            teamScore[team]++;
        }
        checkGameEnd();
    }

    public void lastWave() {
        gameFinish();
    }
}
