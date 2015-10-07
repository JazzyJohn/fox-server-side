package nstuff.juggerfall.extension.handlermanagers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import nstuff.juggerfall.extension.handlers.player.PlayerInitHandler;
import nstuff.juggerfall.extension.handlers.player.PlayerSetNameUIDHandler;
import nstuff.juggerfall.extension.handlers.player.PlayerSetStatsHandler;
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

    public static final String RequestName_SendStats = "sendStats";


    public static final String RequestName_UpdatePlayerInfo= "updatePlayerInfo";
    @Override
    public void init() {
        extension.addClientHandler(RequestName_InitPlayer, PlayerInitHandler.class);

        extension.addClientHandler(RequestName_SetNameUID, PlayerSetNameUIDHandler.class);

        extension.addClientHandler(RequestName_SetTeam, PlayerSetTeamHandler.class);

        extension.addClientHandler(RequestName_SendStats, PlayerSetStatsHandler.class);
    }

    public void updatePlayerInfo(User user) {
        List<User> targets = extension.getParentRoom().getUserList();
        ISFSObject res = new SFSObject();
        Player player =(Player)user.getProperty("player");

			res.putClass("player", player.getModel());

        extension.send(PlayerHandlerManager.RequestName_UpdatePlayerInfo,res,targets);
    }
}
