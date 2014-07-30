package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.baseobject.TimeUpdateEntity;

import java.util.Date;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public abstract class GameRule implements SerializableSFSType,TimeUpdateEntity {
    private static final long afterMathTime = 10000;

    protected int maxScore;

    public long gameTime;

    public long gameStart;

    public long gameEnd;

    public boolean isGameEnded = false;

    public boolean canUseRobot = false;

    public int curStage = 0;

    public boolean start = false;

    public float deathY= -50f;

    public boolean ready = false;

    public int[] teamScore;

    public abstract void Kill(int team);

    public abstract void Spawn(int team);

    public abstract void PlayerDeath(int team);

    public transient MainExtension extension;

    public int Winner() {
        int winner =-1;
        int lastScore =0;
        for(int i=0; i<teamScore.length;i++){
           if(lastScore<teamScore[i]){
               lastScore =teamScore[i];
               winner=i;
           }
        }
        return winner;
    }

    public void Init(Room room){
        maxScore=room.getVariable("maxScore").getIntValue();
        gameTime =room.getVariable("maxTime").getIntValue()*1000;
    }

    public  void StartGame()
    {
        start = true;
    }
    protected void  CheckGameEnd(){
        for(int score: teamScore){
            if(score>maxScore){
                isGameEnded= true;
                return;
            }
        }
        extension.UpdateGame();
    }

    @Override
    public void Update(long delta){
        if(gameTime!=0){
            Date date = new Date();
            if(date.getTime()>gameStart+gameTime){
                gameEnd=date.getTime();
                isGameEnded= true;
                extension.UpdateGame();
                return;
            }
        }
        if(ready){
            StartGame();

        }
        if(isGameEnded){
            Date date = new Date();
            if(date.getTime()>gameEnd+afterMathTime){
                extension.ReloadMap();
            }
        }

    }

    public void PlayerJoin(User user){
        if(!ready){
            ready= true;
        }
    }
}
