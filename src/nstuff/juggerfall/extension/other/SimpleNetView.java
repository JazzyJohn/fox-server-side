package nstuff.juggerfall.extension.other;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.view.NetView;
import nstuff.juggerfall.extension.view.NetViewType;


public class SimpleNetView extends NetView {

    public String type;

    public PREFABTYPE prefType;

    public SimpleNetModel model;

    public SimpleNetView(){

        viewType = NetViewType.NET_VIEW_TYPE_SIMPLE;
    }

    public SimpleNetView(SimpleNetModel model,PREFABTYPE prefType) {
        this();
        this.type = model.type;
        id =model.id;
        this.model = model;
        this.prefType = prefType;
    }

    @Override
    public void update(NetViewModel view) {
        model = (SimpleNetModel) view;
    }

    @Override
    public void delete() {

    }

    @Override
    public void deleteLocal() {

    }

    @Override
    public void clearRef() {

    }

    @Override
    public boolean needDelete(int ownerId) {
        return false;
    }

    public void addData(ISFSObject res) {
        res.putInt("preftype",prefType.getValue());
    }
}
