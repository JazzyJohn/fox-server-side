package nstuff.juggerfall.extension.player;

import com.smartfoxserver.v2.entities.User;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.List;


public class Player implements SerializableSFSType {
	public String uid;

    public String name;
	
	public String kill;
	
	public String death;
	
	public String assist;
	
	public String robotKill;
	
	public transient User owner;

    public int team;
	
	public Player(User owner){
		this.owner = owner;
        owner.setProperty("player",this);
	}
    public Player(){

    }
}
