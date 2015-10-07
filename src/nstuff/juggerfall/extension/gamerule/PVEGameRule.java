package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.PVEGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PVEGameRule extends  GameRule {

    public int vipId;

    public List<Integer> route = new ArrayList<Integer>();

    public void SetVipId(int vipId){
        this.vipId = vipId;
        ready = true;
        if(state==GamerRuleState.AFTERLOAD){
            state= GamerRuleState.READY;
        }
    }

    @Override
    protected void checkGameEnd() {
        extension.updateGame();
    }

    @Override
    public void kill(int team) {

    }

    @Override
    public void spawn(int team) {

    }

    @Override
    public void playerDeath(Pawn dead) {

    }

    @Override
    public void playerJoin(User user) {

    }

    @Override
    public void aIDeath(Pawn dead) {
        if(dead.id==vipId){
            teamScore[0] = 0;
            teamScore[1]= 100;
            gameFinish();
        }
    }

    @Override
    public void aIDeath(Pawn dead, int team) {
        if(dead.id==vipId){
            teamScore[0] = 0;
            teamScore[1]= 100;
            gameFinish();
        }
    }


    @Override
    public void init(Room room) {
        super.init(room);
        int teamCount = 2;
        teamScore = new float[teamCount];
        canUseRobot = false;


    }

    @Override
    public void reload() {
        super.reload();
        ready = false;

    }

    @Override
    public GameRuleModel getModel(){
        PVEGameRuleModel model = new PVEGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.vipID = vipId;
        model.teamScore = new ArrayList<Integer>();
        for (float aTeamScore : teamScore) {

            model.teamScore.add((int)aTeamScore);

        }
        return model;
    }

    public  void addRoute(Integer nextRoute){
        route.add(nextRoute);
    }

    @Override
    public void addMasterInfo(ISFSObject res) {
        super.addMasterInfo(res);
        res.putIntArray("route",route);
    }

    @Override
    public void robotEnter(int team) {

    }

    @Override
    public void deadByAI(int team) {

    }

    public void arrived() {

        teamScore[0] = 100;
        teamScore[1]= 0;
        gameFinish();
    }
}
