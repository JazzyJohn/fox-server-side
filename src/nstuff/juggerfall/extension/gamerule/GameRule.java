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

    public boolean isWithPractice = true;

    public boolean canUseRobot = false;

    public int curStage = 0;

    public boolean isPractice;

    public boolean start = false;

    public float deathY= -50f;

    public boolean ready = false;

    public int[] teamScore;

    public AIDirector director;

    public abstract void kill(int team);

    public abstract void spawn(int team);

    public abstract void playerDeath(Pawn dead);

    public abstract void aIDeath(Pawn dead);

    public abstract void aIDeath(Pawn dead, int team);

    public abstract void robotEnter(int team);

    public abstract void deadByAI(int team);

    public transient MainExtension extension;

    public transient GamerRuleState state = GamerRuleState.AFTERLOAD;

    public int winner() {
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

    public void init(Room room){
        ISFSObject object = room.getVariable("gameVar").getSFSObjectValue();
        GameSettingModel settings =(GameSettingModel) object.getClass("gameSetting");
        maxScore=settings.maxScore;
        gameTime =settings.maxTime*1000;
        gameStart  = System.currentTimeMillis();
        director  = new AIDirector(extension);
        isWithPractice  = settings.isWithPractice;
        extension.trace("ROOM START GameMode:" + this.getClass().toString());
    }

    public  void startGame()
    {
        state =GamerRuleState.GOING;
        extension.startGameEvent();
        ready = true;
    }
    public void gameFinish(){

        isGameEnded= true;
        state =GamerRuleState.FINISH;
        gameEnd=System.currentTimeMillis();
        extension.updateGame();
    }

    protected void checkGameEnd(){
        for(int score: teamScore){

            if(score>=maxScore){

                gameFinish();

                break;
            }
        }
        extension.updateGame();
    }

    @Override
    public void update(long delta){
        if(gameTime!=0){

            if(System.currentTimeMillis()>gameStart+gameTime){
                gameFinish();
                extension.updateGame();
                return;
            }
        }
        if(state==GamerRuleState.READY){
            startGame();

        }
        if(ready&&state==GamerRuleState.AFTERRELOAD){
            startGame();
        }
        if(isGameEnded){

            if(System.currentTimeMillis()>gameEnd+afterMathTime){
                extension.reloadMap();
                reload();
            }
        }
        if(isWithPractice&&extension.getParentRoom().getPlayersList().size()<=1){
            if(!isPractice){
                isPractice= true;

            }

        }else{
            if(isPractice){
                isPractice= false;

            }
        }

    }

    public void reload(){
        gameStart  = System.currentTimeMillis();
        isGameEnded= false;
        state= GamerRuleState.AFTERRELOAD;
        teamScore = new int[teamScore.length];
        extension.playerManager.clearScore();
        director.Reload();

    }

    public void playerJoin(User user){
        if(state==GamerRuleState.AFTERLOAD){
            state= GamerRuleState.READY;
        }
    }

    public abstract GameRuleModel getModel();


    public void addMasterInfo(ISFSObject res){

    }



}
