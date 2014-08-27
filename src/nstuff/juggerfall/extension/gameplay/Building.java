package nstuff.juggerfall.extension.gameplay;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.other.PREFABTYPE;
import nstuff.juggerfall.extension.other.SimpleNetView;

/**
 * Created by 804129 on 26.08.14.
 */
public class Building extends SimpleNetView {
    public User owner;

    public Building(){
        super();
    }
    public Building(SimpleNetModel model, PREFABTYPE type) {
        super(model,type);
    }

    @Override
    public void addData(ISFSObject res) {
        super.addData(res);
        res.putInt("ownerId",owner.getId());
    }

    @Override
    public boolean needDelete(int ownerId) {
        return owner.getId()==ownerId;
    }
}
