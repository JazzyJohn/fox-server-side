package nstuff.juggerfall.extension.gameplay;

import nstuff.juggerfall.extension.baseobject.Vector;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.other.PREFABTYPE;
import nstuff.juggerfall.extension.other.SimpleNetView;
import nstuff.juggerfall.extension.view.NetViewType;


import java.util.Date;

/**
 * Created by 804129 on 24.08.14.
 */
public class QueenEgg extends SimpleNetView {

    public Vector position;

    public long readyDelay;

    public long timeSpawned;
    public int spawnId;

    public QueenEgg(){
        timeSpawned= (new Date()).getTime();
        viewType = NetViewType.NET_VIEW_TYPE_SIMPLE;
    }

    public QueenEgg(SimpleNetModel model, PREFABTYPE type) {
        viewType = NetViewType.NET_VIEW_TYPE_SIMPLE;
        this.type = model.type;
        id =model.id;
        this.model = model;
        this.prefType = type;
        position =new Vector( model.position.x,model.position.y,model.position.z);
        timeSpawned= (new Date()).getTime();
    }

    public boolean IsReady(){
      return  timeSpawned+readyDelay< (new Date()).getTime();
    }
}