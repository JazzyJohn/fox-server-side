package nstuff.juggerfall.extension.player;

import com.smartfoxserver.v2.entities.User;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

import nstuff.juggerfall.extension.models.PlayerModel;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


public class Player implements SerializableSFSType {
	public String uid;

    public String name;
	
	public int kill;
	
	public int death;
	
	public int assist;
	
	public int robotKill;
	
	public transient User owner;
	
	public int userId;

	public int team;
	
	public Player(User owner){
		this.owner = owner;
        owner.setProperty("player",this);
        userId = owner.getId();
	}
    public Player(){

    }
	public PlayerModel GetModel() throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
		// TODO Auto-generated method stub
		PlayerModel playerModel =  new PlayerModel();
		
			
			Field[] allfiled = this.getClass().getFields();
			for(Field field :allfiled){
				int modifier =field.getModifiers();
				if(!Modifier.isTransient(modifier)&&Modifier.isPublic(modifier)){
					
					playerModel.getClass().getField(field.getName()).set(playerModel, field.get(this));
				
				}
				
			}
	
	
		return playerModel;
	}
}
