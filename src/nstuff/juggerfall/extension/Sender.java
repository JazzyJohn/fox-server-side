package nstuff.juggerfall.extension;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import nstuff.juggerfall.extension.baseobject.Vector;
import nstuff.juggerfall.extension.models.Vector3Model;

/**
 * Created by 804129 on 24.08.14.
 */
public class Sender {

    public static final String RequestName_AISwarmUpdate ="AISwarmUpdate";

    public static final  String RequestName_AISpawnBot = "AISpawnBot";

    public static final  String RequestName_AINextWave = "AINextWave";

    protected MainExtension extension;

    public Sender(MainExtension mainExtension) {
        extension =mainExtension;
    }

    public void sendSwarmChange(int swarmId, boolean isActive) {
        ISFSObject res = new SFSObject();
        res.putInt("swarmId", swarmId);
        res.putBool("isActive", isActive);
        extension.send(RequestName_AISwarmUpdate, res, extension.getParentRoom().getUserList());
    }

    public void spawnOnPoint(String bot, int swarmId, int id, Vector coords) {
        ISFSObject res = new SFSObject();
        res.putUtfString("prefabName", bot);
        res.putInt("swarmId",swarmId);
        res.putInt("id", id);
        res.putClass("position",new Vector3Model(coords.getX(),coords.getY(),coords.getZ()));
        extension.send(RequestName_AISpawnBot, res, extension.masterInfo);

    }

    public void sendNextWave(int swarmId) {
        ISFSObject res = new SFSObject();
        res.putInt("swarmId", swarmId);

        extension.send(RequestName_AINextWave, res, extension.getParentRoom().getUserList());

    }
}
