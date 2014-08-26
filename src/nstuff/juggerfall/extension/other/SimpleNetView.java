package nstuff.juggerfall.extension.other;

import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.view.NetView;
import nstuff.juggerfall.extension.view.NetViewType;

;
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
}
