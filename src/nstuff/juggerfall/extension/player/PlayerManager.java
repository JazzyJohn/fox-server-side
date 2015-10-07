package nstuff.juggerfall.extension.player;


import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.SFSArray;

import java.util.ArrayList;
import java.util.List;


public class PlayerManager {

	public List<Player> allPlayer = new  ArrayList<Player> ();
	
	public  void AddPlayer(User user) {

			allPlayer.add(new Player(user));
	}

	public SFSArray getAllPlayerToSend() {
        SFSArray sfsa = new SFSArray();
		for(Player player : allPlayer){
            sfsa.addClass(player.getModel());

		}
		return  sfsa;
	}

    public void clearScore(){
        for(Player player :allPlayer){
            player.clearScore();
        }
    }

    public void DeletePlayer(User user) {
        allPlayer.remove(user.getProperty("player"));
    }
}
