package nstuff.juggerfall.extension.gameplay;

import nstuff.juggerfall.extension.gamerule.PointGameRule;
import nstuff.juggerfall.extension.models.AssaultPointModel;

import java.util.Hashtable;

import static nstuff.juggerfall.extension.gamerule.PointGameRule.ADD_PLAYER_SCORE;

/**
 * Created by Ivan.Ochincenko on 16.01.15.
 */
public class AssaultPoint {

    AssaultPointModel model;
    PointGameRule gameRule;

    float scorePoint=0;

    boolean send= false;

    public AssaultPoint(AssaultPointModel model,PointGameRule gameRule){
        this.model = model;
        this.gameRule = gameRule;
    }

    public void readFromModel(AssaultPointModel model){
        this.model=model;
    }
    public void update(float time){

        if(model.teamConquering!=0){
            if(model.owner==0){
                if(model.needPoint>scorePoint){
                   changeValue(time);
                }else {
                    model.owner=model.teamConquering;
                    send = true;

                }
            }else{
                if(0<scorePoint){
                    changeValue(-time);
                }else {
                    model.owner=0;
                    send = true;
                }
            }
        }
    }

    private void changeValue(float time) {
        boolean open =true;
        for(int i : model.lockedBy){
            if(gameRule.getPointsDictionary().containsKey(i)&&gameRule.getPointsDictionary().get(i).model.owner!=model.teamConquering){
                open= false;
            }
        }
        if(open){
            scorePoint += time*getCoef();
            model.scorePoint = scorePoint;
            send = true;
        }
    }

    private float getCoef() {
        return 1+(model.people-1)* ADD_PLAYER_SCORE/100f;
    }

    public boolean isSend(){
        return send;
    }
    public AssaultPointModel getModel(){
        return model;
    }
}
