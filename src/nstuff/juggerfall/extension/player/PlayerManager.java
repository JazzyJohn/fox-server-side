package nstuff.juggerfall.extension.player;


import java.util.ArrayList;
import java.util.List;

import nstuff.juggerfall.extension.view.ViewManager;

import com.smartfoxserver.v2.entities.User;


public class PlayerManager {

	public ViewManager viewManager;
	
	public List<Player> allPlayer = new  ArrayList<Player> ();
	
	public  void AddPlayer(User user) {
		
			allPlayer.add(new Player(user));
	}

	public List<Integer> GetAllPalyerToSend() {
		List<Integer> answer = new ArrayList<Integer>();
		for(Player player : allPlayer){
			answer.add(player.owner.getId());			
		}
		
		return answer;
	}

}
