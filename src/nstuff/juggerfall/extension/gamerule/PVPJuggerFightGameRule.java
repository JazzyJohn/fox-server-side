package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import nstuff.juggerfall.extension.gameplay.PVPBase;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.PVPJuggerFightGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 804129 on 07.08.14.
 */
public class PVPJuggerFightGameRule extends  GameRule {
    List<Pawn>  allJuggers = new ArrayList<Pawn>();

    PVPBase[] bases;

    @Override
    public void Kill(int team) {
        team =team-1;
        teamScore[team]+=1;
        extension.UpdateGame();
    }

    @Override
    public void Spawn(int team) {

    }

    @Override
    public void PlayerDeath(int team) {

    }

    @Override
    public void AIDeath(Pawn dead) {
        if(allJuggers.contains(dead)){
            teamScore[dead.team-1]+=10;
            allJuggers.remove(dead);
            extension.UpdateGame();
        }
    }

    @Override
    public GameRuleModel GetModel() {
        PVPJuggerFightGameRuleModel model = new PVPJuggerFightGameRuleModel();
        model.isGameEnded = isGameEnded;
        model.baseHealth = new ArrayList<Integer>();
        model.teamScore = new ArrayList<Integer>();
        for(int i =0; i <bases.length;i++){
            model.baseHealth.add(bases[i].health);
            model.teamScore.add(teamScore[i]);

        }
        return model;
    }

    public void SetJuggerPawn(Pawn pawn) {
        allJuggers.add(pawn);
    }

    public void BaseDamaged(int team,int dmg){
        team =team-1;
        bases[team].health-=dmg;
        if(bases[team].health<=0){
            GameFinish();
        }else{
            extension.UpdateGame();
        }
    }

    public void Init(Room room){
        teamScore = new int[2];
        bases = new PVPBase[2];
    }

    public void SetBase(PVPBase base) {
        bases[base.team-1] = base;

    }

}
