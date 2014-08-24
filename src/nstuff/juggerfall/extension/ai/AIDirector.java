package nstuff.juggerfall.extension.ai;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.gameplay.QueenEgg;
import nstuff.juggerfall.extension.gamerule.GameRule;
import nstuff.juggerfall.extension.gamerule.HuntGameRule;
import nstuff.juggerfall.extension.gamerule.PVPGameRule;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.Collection;
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

    public void ReadFromSFSObject(ISFSObject dt){
        if(loaded){
            return;
        }
        loaded= true;
        ISFSArray swarms = dt.getSFSArray("swarms");
        for(int i=0; i<swarms.size();i++){
            ISFSObject swarm = swarms.getSFSObject(i);
            AISwarm aiSwarm = GetSwarmByName(swarm.getUtfString("class"));
            aiSwarm.Init(this,i);
            aiSwarm.LoadFromSFSObject(swarm);
            allSwarm.add(aiSwarm);
        }
        for(Integer val :dt.getIntArray("chain")){

                swarmChain.add(val);

        }
        for(Integer val :dt.getIntArray("active")){

            allSwarm.get(val).Activate();

        }
    }

    private AISwarm GetSwarmByName(String aClass) {
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
                 ((HuntGameRule)(extension).gameRule).LastWave();
            }
        }else{
            allSwarm.get(nextSwarm)  .Activate();
        }

    }

    public void AddPawn(Pawn pawn) {
        allSwarm.get(pawn.aiSwarmId).AgentSpawn(pawn);
    }

    public void DeadPawn(Pawn pawn) {
        allSwarm.get(pawn.aiSwarmId).AgentKill(pawn);
    }

    public void AddInfo(ISFSObject res) {
        if(!loaded){
            return;
        }
        ISFSArray swarms = new SFSArray();
        for(AISwarm swarm : allSwarm){
            swarms.addSFSObject(swarm.WriteToSFSObject());
        }
        res.putSFSArray("swarms",swarms);
    }

    public void Reload() {
       allSwarm.clear();
    }

    public void AddEgg(QueenEgg queenEgg) {
        ((AISwarm_QueenSwarm)allSwarm.get(queenEgg.spawnId)).AddEgg(queenEgg);
    }
    public void RemoveEgg(QueenEgg queenEgg) {
        ((AISwarm_QueenSwarm)allSwarm.get(queenEgg.spawnId)).RemoveEgg(queenEgg);
    }
}
