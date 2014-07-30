package nstuff.juggerfall.extension.gamerule;

import com.smartfoxserver.v2.entities.Room;

import java.util.Date;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PVPGameRule extends  GameRule {

    public int[] teamKill;


    @Override
    public void Kill(int team) {
        teamKill[team]++;
        teamScore[team]++;
        CheckGameEnd();
    }

    @Override
    public void Spawn(int team) {

    }

    @Override
    public void PlayerDeath(int team) {

    }



    @Override
    public void Init(Room room) {
        super.Init(room);
        int teamCount = room.getVariable("teamCount").getIntValue();
        teamKill = new int[teamCount];
        teamScore = new int[teamCount];
        canUseRobot = true;
        Date date = new Date();
        gameStart  = date.getTime();
    }
}
