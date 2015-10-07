package nstuff.juggerfall.extension.player;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;
import nstuff.juggerfall.extension.models.PlayerModel;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public class Player implements SerializableSFSType {
	public String uid;

    public String name;
	
	public int kill;

    public int aikill;
	
	public int death;
	
	public int assist;
	
	public int robotKill;

    public int rating;
	
	public transient User owner;
	
	public int userId;

	public int team;

    PlayerModel playerModel =  new PlayerModel();
	
	public Player(User owner){
		this.owner = owner;
        owner.setProperty("player",this);
        userId = owner.getId();
	}

	public PlayerModel getModel() {
		// TODO Auto-generated method stub


        playerModel.kill = kill;
        playerModel.aikill = aikill;
        playerModel.death = death;
        playerModel.assist = assist;
        playerModel.robotKill = robotKill;
        playerModel.team = team;
        playerModel.userId = userId;
        playerModel.uid = uid;
        playerModel.name = name;
        playerModel.rating = rating;
        return playerModel;
	}

    public void clearScore() {
        kill = 0;
        death =0;
        assist =0;
        robotKill =0;
        rating= 0;
    }

    public void updateModel(PlayerModel stats) {
        kill = stats.kill;
        aikill = stats.aikill;
        death =  stats.death;
        assist =  stats.assist;
        robotKill =  stats.robotKill;
        rating =  stats.rating;

    }
}
