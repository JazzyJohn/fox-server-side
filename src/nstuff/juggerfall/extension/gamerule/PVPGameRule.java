package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.GameSettingModel;
import nstuff.juggerfall.extension.models.PVPGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;

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
    public void PlayerDeath(Pawn dead) {

    }

    @Override
    public void AIDeath(Pawn death) {

    }

    @Override
    public void AIDeath(Pawn dead, int team) {

    }


    @Override
    public void Init(Room room) {
        super.Init(room);
        ISFSObject object = room.getVariable("gameVar").getSFSObjectValue();
        GameSettingModel settings =(GameSettingModel) object.getClass("gameSetting");
        int teamCount = settings.teamCount;
        teamKill = new int[teamCount];
        teamScore = new int[teamCount];
        canUseRobot = true;
        extension.trace("ROOM START");

    }

    @Override
    public void Reload() {
        super.Reload();
        teamKill = new int[teamKill.length];

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

    @Override
    public void RobotEnter(int team) {

    }
}
