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

	public SFSArray GetAllPalyerToSend() throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
        SFSArray sfsa = new SFSArray();
		for(Player player : allPlayer){
            sfsa.addClass(player.GetModel());

		}
		return  sfsa;
	}

    public void ClearScore(){
        for(Player player :allPlayer){
            player.ClearScore();
        }
    }

}
