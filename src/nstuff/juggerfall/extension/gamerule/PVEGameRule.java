package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.PVEGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PVEGameRule extends  GameRule {

    public int vipId;

    public void SetVipId(int vipId){
        this.vipId = vipId;
        ready = true;
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
            isGameEnded = true;
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


    }

    @Override
    public GameRuleModel GetModel(){
        PVEGameRuleModel model = new PVEGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.vipID = vipId;
        model.teamScore = new ArrayList<Integer>();
        for(int i =0; i <teamScore.length;i++){

            model.teamScore.add(teamScore[i]);

        }
        return model;
    }
}
