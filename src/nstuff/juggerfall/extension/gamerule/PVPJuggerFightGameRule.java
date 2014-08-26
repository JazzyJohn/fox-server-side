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
    public void kill(int team) {
        team =team-1;
        teamScore[team]+=1;
        extension.updateGame();
    }

    @Override
    public void spawn(int team) {

    }

    @Override
    public void playerDeath(Pawn dead) {

    }

    @Override
    public void aIDeath(Pawn dead) {
        if(allJuggers.contains(dead)){
            int scoreTeam = 1-(dead.team-1);
            teamScore[scoreTeam]+=10;
            allJuggers.remove(dead);
            extension.updateGame();
        }
    }

    @Override
    public void aIDeath(Pawn dead, int team) {
        if(allJuggers.contains(dead)){
            int scoreTeam = 1-(dead.team-1);
            teamScore[scoreTeam]+=10;
            allJuggers.remove(dead);
            extension.updateGame();
        }
    }

    @Override
    public GameRuleModel getModel() {
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

    @Override
    public void robotEnter(int team) {

    }

    public void setJuggerPawn(Pawn pawn) {
        allJuggers.add(pawn);
    }

    public void baseDamaged(int team, int dmg){
        team =team-1;
        bases[team].health-=dmg;
        if(bases[team].health<=0){
            gameFinish();
        }else{
            extension.updateGame();
        }
    }

    public void init(Room room){
        teamScore = new int[2];
        bases = new PVPBase[2];
    }

    public void setBase(PVPBase base) {
        bases[base.team-1] = base;

    }

}
