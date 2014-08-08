package nstuff.juggerfall.extension.gameplay;

import nstuff.juggerfall.extension.models.BaseModel;

/**
 * Created by 804129 on 07.08.14.
 */
public class Base  {

    public int health;


    public int team;
    public Base(){

    }
    public Base(BaseModel baseModel){ 
        health = baseModel.health;
        team = baseModel.team;
    }


}
