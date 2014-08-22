package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.ai.AIDirector;
import nstuff.juggerfall.extension.baseobject.TimeUpdateEntity;
import nstuff.juggerfall.extension.models.GameRuleModel;
import nstuff.juggerfall.extension.models.GameSettingModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.Date;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 *
 */
public abstract class GameRule implements TimeUpdateEntity {
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

    public AIDirector director = new AIDirector();

    public abstract void Kill(int team);

    public abstract void Spawn(int team);

    public abstract void PlayerDeath(Pawn dead);

    public abstract void AIDeath(Pawn dead);

    public abstract void AIDeath(Pawn dead,int team);

    public transient MainExtension extension;

    public transient GamerRuleState state = GamerRuleState.AFTERLOAD;

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
        ISFSObject object = room.getVariable("gameVar").getSFSObjectValue();
        GameSettingModel settings =(GameSettingModel) object.getClass("gameSetting");
        maxScore=settings.maxScore;
        gameTime =settings.maxTime*1000;
        Date date = new Date();
        gameStart  = date.getTime();
    }

    public  void StartGame()
    {
        state =GamerRuleState.GOING;
        extension.StartGameEvent();
    }
    public void GameFinish(){
        Date date = new Date();
        isGameEnded= true;
        state =GamerRuleState.FINISH;
        gameEnd=date.getTime();
        extension.UpdateGame();
    }

    protected void  CheckGameEnd(){
        for(int score: teamScore){

            if(score>=maxScore){

                GameFinish();

                break;
            }
        }
        extension.UpdateGame();
    }

    @Override
    public void Update(long delta){
        if(gameTime!=0){
            Date date = new Date();
            if(date.getTime()>gameStart+gameTime){
                GameFinish();
                extension.UpdateGame();
                return;
            }
        }
        if(state==GamerRuleState.READY){
            StartGame();

        }
        if(ready&&state==GamerRuleState.AFTERRELOAD){
            StartGame();
        }
        if(isGameEnded){
            Date date = new Date();
            if(date.getTime()>gameEnd+afterMathTime){
                extension.ReloadMap();
                Reload();
            }
        }

    }

    public void Reload(){
        Date date = new Date();
        gameStart  = date.getTime();
        isGameEnded= false;
        state= GamerRuleState.AFTERRELOAD;
        teamScore = new int[teamScore.length];
        extension.playerManager.ClearScore();
    }

    public void PlayerJoin(User user){
        if(state==GamerRuleState.AFTERLOAD){
            state= GamerRuleState.READY;
        }
    }

    public abstract GameRuleModel GetModel();


    public void AddMasterInfo(ISFSObject res){

    }

    public void AddSpawnInfo(ISFSObject res) {
        if(director.HasList()){
            res.putIntArray("swarmIds",director.GetList());
        }
    }


    public abstract void RobotEnter(int team);
}
