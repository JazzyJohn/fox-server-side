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
    protected void CheckGameEnd() {
        extension.UpdateGame();
    }

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
    public void PlayerJoin(User user) {

    }

    @Override
    public void AIDeath(Pawn dead) {
        if(dead.id==vipId){
            teamScore[0] = 0;
            teamScore[1]= 100;
            GameFinish();
        }
    }


    @Override
    public void Init(Room room) {
        super.Init(room);
        int teamCount = 2;
        teamScore = new int[teamCount];
        canUseRobot = false;
        extension.trace("ROOM START");

    }

    @Override
    public void Reload() {
        super.Reload();
        ready = false;

    }

    @Override
    public GameRuleModel GetModel(){
        PVEGameRuleModel model = new PVEGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.vipID = vipId;
        model.teamScore = new ArrayList<Integer>();
        for (int aTeamScore : teamScore) {

            model.teamScore.add(aTeamScore);

        }
        return model;
    }

    public  void AddRoute(Integer nextRoute){
        route.add(nextRoute);
    }

    @Override
    public void AddMasterInfo(ISFSObject res) {
        super.AddMasterInfo(res);
        res.putIntArray("route",route);
    }

    public void Arrived() {

        teamScore[0] = 100;
        teamScore[1]= 0;
        GameFinish();
    }
}
