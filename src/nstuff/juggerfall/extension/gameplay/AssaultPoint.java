package nstuff.juggerfall.extension.gameplay;

import nstuff.juggerfall.extension.gamerule.PointGameRule;
import nstuff.juggerfall.extension.models.AssaultPointModel;

import java.util.Hashtable;
import java.util.List;

import static nstuff.juggerfall.extension.gamerule.PointGameRule.ADD_PLAYER_SCORE;

/**
 * Created by Ivan.Ochincenko on 16.01.15.
 */
public class AssaultPoint {

    AssaultPointModel model;
    PointGameRule gameRule;

    float scorePoint=0;

    boolean send= false;

    public List<Integer> lockedByOneTeam;

    public List<Integer> lockedBySecondTeam;

    public AssaultPoint(AssaultPointModel model,PointGameRule gameRule){
        this.model = model;
        this.gameRule = gameRule;
        lockedByOneTeam = model.lockedByOneTeam;
        lockedBySecondTeam = model.lockedBySecondTeam;
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
        List<Integer>  curLocked;
        if(model.teamConquering==1){
            curLocked = lockedByOneTeam;
        }else{
            curLocked = lockedBySecondTeam;
        }
        for(int i :curLocked){
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
