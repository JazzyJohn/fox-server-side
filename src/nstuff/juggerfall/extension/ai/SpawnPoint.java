package nstuff.juggerfall.extension.ai;

import nstuff.juggerfall.extension.baseobject.Vector;
import nstuff.juggerfall.extension.models.Vector3Model;

import java.util.Date;

/**
 * Created by 804129 on 24.08.14.
 */
public class SpawnPoint  {

    protected Vector coords;

    protected int pawnId = -1;

    protected long timeDelay;

    protected long deadTime;

    protected boolean active= true;

    public SpawnPoint(Vector3Model vector3,long timeDelay) {
        coords = new Vector(vector3.x,vector3.y,vector3.z);
        this.timeDelay =timeDelay;
    }

    public boolean IsActive() {
        if(pawnId!=-1){
            return false;
        }else{
            if(active&&(deadTime+timeDelay<(new Date()).getTime())){
                active= false;
                return true;
            }else{
                return false;
            }

        }
    }
    public void  SetPawnId(int id){
        pawnId = id;
    }

    public void DeadPawn() {
        pawnId = -1;
        deadTime=(new Date()).getTime();
        active = true;
    }
}
