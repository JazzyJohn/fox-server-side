package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gameplay.QueenEgg;
import nstuff.juggerfall.extension.gamerule.HuntGameRule;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 18.08.14.
 */
public class AIDirector {

    private static final String PACK_NAME ="nstuff.juggerfall.extension.ai.";

    protected List<AISwarm> allSwarm = new ArrayList<AISwarm>();

    protected List<Integer> swarmChain = new ArrayList<Integer>();

    public MainExtension extension;

    public  boolean loaded = false;

    public AIDirector(MainExtension extension){
        this.extension =extension;
    }

    public void readFromSFSObject(ISFSObject dt){
        if(loaded){
            return;
        }
        loaded= true;
        ISFSArray swarms = dt.getSFSArray("swarms");
        for(int i=0; i<swarms.size();i++){
            ISFSObject swarm = swarms.getSFSObject(i);
            AISwarm aiSwarm = getSwarmByName(swarm.getUtfString("class"));
            aiSwarm.init(this, i);
            aiSwarm.loadFromSFSObject(swarm);
            allSwarm.add(aiSwarm);
        }
        for(Integer val :dt.getIntArray("chain")){

                swarmChain.add(val);

        }
        for(Integer val :dt.getIntArray("active")){

            allSwarm.get(val).activate();

        }
    }

    private AISwarm getSwarmByName(String aClass) {
        try {
            Class theClass =  Class.forName(PACK_NAME+aClass);
            return (AISwarm)theClass.newInstance();
        } catch (ClassNotFoundException e) {
            return new AISwarm();
           // trace(ExtensionLogLevel.INFO,"Game Rule not found  "+e.getStackTrace().toString());
        } catch (InstantiationException e) {
            return new AISwarm();
            //trace(ExtensionLogLevel.INFO, "Game Rule InstantiationException   " + e.getStackTrace().toString());
        } catch (IllegalAccessException e) {
            return new AISwarm();
           // trace(ExtensionLogLevel.INFO, "Game Rule IllegalAccessException   " + e.getStackTrace().toString());

        }
    }


    public void SwarmEnd(int swarmId) {

       int nextSwarm = swarmChain.get(swarmId);
        if(nextSwarm==-1){
            if((extension).gameRule instanceof HuntGameRule){
                 ((HuntGameRule)(extension).gameRule).lastWave();
            }
        }else{
            allSwarm.get(nextSwarm)  .activate();
        }

    }

    public void AddPawn(Pawn pawn) {
        allSwarm.get(pawn.aiSwarmId).agentSpawn(pawn);
    }

    public void DeadPawn(Pawn pawn) {
        allSwarm.get(pawn.aiSwarmId).agentKill(pawn);
    }

    public void AddInfo(ISFSObject res) {
        if(!loaded){
            return;
        }
        ISFSArray swarms = new SFSArray();
        for(AISwarm swarm : allSwarm){
            swarms.addSFSObject(swarm.writeToSFSObject());
        }
        res.putSFSArray("swarms",swarms);
    }

    public void Reload() {
       allSwarm.clear();
       loaded = false;
    }

    public void AddEgg(QueenEgg queenEgg) {
        ((AISwarm_QueenSwarm)allSwarm.get(queenEgg.spawnId)).addEgg(queenEgg);
    }
    public void RemoveEgg(QueenEgg queenEgg) {
        ((AISwarm_QueenSwarm)allSwarm.get(queenEgg.spawnId)).removeEgg(queenEgg);
    }
}
