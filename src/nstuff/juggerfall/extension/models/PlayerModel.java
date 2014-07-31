package nstuff.juggerfall.extension.models;

import com.smartfoxserver.v2.entities.User;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;
import nstuff.juggerfall.extension.pawn.Pawn;

import java.util.ArrayList;
import java.util.List;


public class PlayerModel implements SerializableSFSType {
	public String uid;

    public String name;
	
	public int kill;
	
	public int death;
	
	public int assist;
	
	public int robotKill;
	

	
	public int userId;
	


    public int team;
	
	
}
