package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import nstuff.juggerfall.extension.gameplay.AssaultPoint;

/**
 * Created by Ivan.Ochincenko on 16.01.15.
 */
public class KingOfHillPointGameRule extends PointGameRule {

    float lastUpdateScore=0;

    protected void ruleLogic(long delta) {
        if(isGameEnded){
            return;
        }
     //   extension.trace(ExtensionLogLevel.WARN,"Delta " +delta);
        for(AssaultPoint assaultPoint : pointsDictionary.values()){
            if(assaultPoint.getModel().owner!=0) {
                teamScore[assaultPoint.getModel().owner - 1] += (float)delta/1000.f;
                if(lastUpdateScore+1f<=teamScore[assaultPoint.getModel().owner - 1]){
                    lastUpdateScore = teamScore[assaultPoint.getModel().owner - 1];
                    forcedUpdate = true;
                }
            }
        }
        if(pointsDictionary.size()==0){
            return;
        }
        boolean isWinner = false;

        for(int i=0;i<2;i++){
            if(teamScore[i]>=maxScore){
                isWinner= true;
            }
        }
        if(isWinner){
            gameFinish();
        }
    }

    @Override
    protected void pointsChange() {
        super.pointsChange();
        lastUpdateScore=0;
    }
}
