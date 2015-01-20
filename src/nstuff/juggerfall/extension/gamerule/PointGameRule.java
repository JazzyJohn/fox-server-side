package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.SFSArray;
import nstuff.juggerfall.extension.gameplay.AssaultPoint;
import nstuff.juggerfall.extension.models.AssaultPointModel;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.PVPGameRuleModel;
import nstuff.juggerfall.extension.models.PointGameRuleModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Ivan.Ochincenko on 16.01.15.
 */
public class PointGameRule  extends  GameRule{

    public static final float ADD_PLAYER_SCORE =50.f;

    protected Hashtable<Integer, AssaultPoint> pointsDictionary = new Hashtable<Integer, AssaultPoint>();
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
    public void aIDeath(Pawn dead) {

    }

    @Override
    public void aIDeath(Pawn dead, int team) {

    }

    @Override
    public void robotEnter(int team) {

    }

    @Override
    public void deadByAI(int team) {

    }

    @Override
    public GameRuleModel getModel() {
        PointGameRuleModel model = new PointGameRuleModel();
        model.isGameEnded = isGameEnded;

        model.teamScore = new ArrayList<Integer>();
        for(int i =0; i <teamScore.length;i++){

            model.teamScore.add(teamScore[i]);

        }

        return model;
    }


    @Override
    public void update(long delta) {
        super.update(delta);
        ruleLogic(delta);
        ISFSArray array  = new SFSArray();
        for (AssaultPoint point : pointsDictionary.values()){


            point.update(((float)delta)/1000.f);
            if(point.isSend()){
                array.addClass(point.getModel());
            }
        }
        if(array.size()>0){
            extension.sender.sendGamePoint(array);
        }



    }

    protected void ruleLogic(long delta) {

    }

    public void readPoint(AssaultPointModel assaultPointModel) {
        if(pointsDictionary.containsKey(assaultPointModel.id)){
            pointsDictionary.get(assaultPointModel.id).readFromModel(assaultPointModel);
        }else{
            pointsDictionary.put(assaultPointModel.id,new AssaultPoint(assaultPointModel,this));
        }
    }

    public Hashtable<Integer, AssaultPoint> getPointsDictionary() {
        return pointsDictionary;
    }
}
