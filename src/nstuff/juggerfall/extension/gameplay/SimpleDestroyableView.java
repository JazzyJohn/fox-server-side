package nstuff.juggerfall.extension.gameplay;

import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.models.SimpleDestroyableModel;
import nstuff.juggerfall.extension.view.NetView;
import nstuff.juggerfall.extension.view.NetViewType;

/**
 * Created by 804129 on 07.08.14.
 */
public class SimpleDestroyableView extends NetView {
    public float health;
    public SimpleDestroyableView(){

        viewType = NetViewType.NET_VIEW_TYPE_SIMPLE;
    }
    public SimpleDestroyableView(SimpleDestroyableModel model) {

        viewType = NetViewType.NET_VIEW_TYPE_SIMPLE;
        health = model.health;
        id =model.id;

    }
    @Override
    public void Update(NetViewModel view) {
        SimpleDestroyableModel model = (SimpleDestroyableModel)view;
        health =model.health;
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
