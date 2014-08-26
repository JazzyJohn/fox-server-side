package nstuff.juggerfall.extension.handlermanagers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import nstuff.juggerfall.extension.handlers.player.PlayerInitHandler;
import nstuff.juggerfall.extension.handlers.player.PlayerSetNameUIDHandler;
import nstuff.juggerfall.extension.handlers.player.PlayerSetTeamHandler;
import nstuff.juggerfall.extension.player.Player;

import java.util.List;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PlayerHandlerManager extends AbstractHandlerManager  {

    public static final String RequestName_InitPlayer = "playerSpawm";

    public static final String RequestName_InitPlayers = "playersSpawm";

    public static final String RequestName_SetNameUID = "setNameUID";

    public static final String RequestName_SetTeam = "setTeam";

    public static final String RequestName_UpdatePlayerInfo= "updatePlayerInfo";
    @Override
    public void init() {
        extension.addClientHandler(RequestName_InitPlayer, PlayerInitHandler.class);

        extension.addClientHandler(RequestName_SetNameUID, PlayerSetNameUIDHandler.class);

        extension.addClientHandler(RequestName_SetTeam, PlayerSetTeamHandler.class);
    }

    public void updatePlayerInfo(User user) {
        List<User> targets = extension.getParentRoom().getUserList();
        ISFSObject res = new SFSObject();
        Player player =(Player)user.getProperty("player");
        try {
			res.putClass("player", player.getModel());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			extension.trace(e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			extension.trace(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			extension.trace(e);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			extension.trace(e);
		}
        extension.send(PlayerHandlerManager.RequestName_UpdatePlayerInfo,res,targets);
    }
}
