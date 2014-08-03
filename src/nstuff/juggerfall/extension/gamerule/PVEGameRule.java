package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.PVEGameRuleModel;
import nstuff.juggerfall.extension.models.PVPGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PVEGameRule extends  GameRule {

    public int[] teamKill;

    public int vipId;

    public void SetVipId(int vipId){
        this.vipId = vipId;

    }


    @Override
    public void Kill(int team) {
        team =team-1;

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
        teamKill = new int[teamKill.length];
        teamScore = new int[teamKill.length];
    }

    @Override
    public GameRuleModel GetModel(){
        PVEGameRuleModel model = new PVEGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.vipID = vipId;
        model.teamScore = new ArrayList<Integer>();
        for(int i =0; i <teamKill.length;i++){

            model.teamScore.add(teamScore[i]);

        }
        return model;
    }
}
