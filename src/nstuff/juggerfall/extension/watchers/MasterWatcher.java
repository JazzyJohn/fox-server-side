package nstuff.juggerfall.extension.watchers;

import com.smartfoxserver.v2.entities.User;
import nstuff.juggerfall.extension.MainExtension;

/**
 * Created by vania_000 on 02.04.2015.
 */
public class MasterWatcher {

    private static long MAX_LATENCY_IN_MILLIS= 30000;

    private MainExtension extension;

    public MasterWatcher(MainExtension extension) {
        this.extension = extension;
        this.lastMasterUpdateTime =System.currentTimeMillis();
    }
    private volatile long lastMasterUpdateTime;

    public void updateMasterTime(){
        lastMasterUpdateTime = System.currentTimeMillis();
    }

    public boolean badMaster(){
        User currentMaster = extension.masterInfo;
        boolean ready = this.extension.isMasterInitialized();
        boolean connected = currentMaster==null||!currentMaster.isConnected()|| !currentMaster.isJoinedInRoom(extension.getParentRoom());

        boolean logic = (this.extension.getGameRule().isOnGoing()&&(System.currentTimeMillis()- lastMasterUpdateTime)>MAX_LATENCY_IN_MILLIS);
        return !extension.getParentRoom().isEmpty()&& ready&&( connected||logic);
    }

}
