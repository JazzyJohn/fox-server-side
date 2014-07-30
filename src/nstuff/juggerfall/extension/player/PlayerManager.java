package nstuff.juggerfall.extension.player;


import java.util.ArrayList;
import java.util.List;

import com.smartfoxserver.v2.entities.data.SFSArray;
import nstuff.juggerfall.extension.view.ViewManager;

import com.smartfoxserver.v2.entities.User;


public class PlayerManager {

	public List<Player> allPlayer = new  ArrayList<Player> ();
	
	public  void AddPlayer(User user) {

			allPlayer.add(new Player(user));
	}

	public SFSArray GetAllPalyerToSend() {
        SFSArray sfsa = new SFSArray();
		for(Player player : allPlayer){
            sfsa.addClass(player);

		}
		return  sfsa;
	}


}
