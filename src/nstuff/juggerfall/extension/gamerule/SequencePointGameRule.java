package nstuff.juggerfall.extension.gamerule;

import nstuff.juggerfall.extension.gameplay.AssaultPoint;

/**
 * Created by Ivan.Ochincenko on 16.01.15.
 */
public class SequencePointGameRule extends PointGameRule {

    protected void ruleLogic(long delta) {
        if(isGameEnded){
            return;
        }
        for(int i =0;i<2;i++){
            teamScore[i] = 0;
        }
        for(AssaultPoint assaultPoint : pointsDictionary.values()){
            if(assaultPoint.getModel().owner!=0){
                teamScore[assaultPoint.getModel().owner-1]++;
            }
        }
        boolean isWinner = false;

        for(int i=0;i<2;i++){
            if(teamScore[i]==pointsDictionary.size()){
                isWinner= true;
            }
        }
        if(isWinner){
            gameFinish();
        }
    }

}
