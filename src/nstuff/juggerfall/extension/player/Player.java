package nstuff.juggerfall.extension.player;

import com.smartfoxserver.v2.entities.User;

import nstuff.juggerfall.extension.view.FoxView;



public class Player {
	public String Uid;
	
	public String Kill;
	
	public String Death;
	
	public String Assist;
	
	public String RobotKill;
	
	public User owner;
	
	public Player(User owner){
		this.owner = owner;
	}
	
}
