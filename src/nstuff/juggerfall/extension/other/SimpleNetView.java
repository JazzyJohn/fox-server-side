package nstuff.juggerfall.extension.other;

import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.models.SimpleNetModel;
import nstuff.juggerfall.extension.view.NetView;
import nstuff.juggerfall.extension.view.NetViewType;

/**
 * Created by 804129 on 02.08.14.
 */
public class SimpleNetView extends NetView {

    public String type;

    public SimpleNetModel model;

    public SimpleNetView(){

        viewType = NetViewType.NET_VIEW_TYPE_SIMPLE;
    }

    public SimpleNetView(SimpleNetModel model) {

        viewType = NetViewType.NET_VIEW_TYPE_SIMPLE;
        type = model.type;
        this.model = model;
        id =model.id;

    }

    @Override
    public void Update(NetViewModel view) {
        model = (SimpleNetModel) view;
    }

    @Override
    public void Delete() {

    }

    @Override
    public void DeleteLocal() {

    }

    @Override
    public void ClearRef() {

    }
}
