package nstuff.juggerfall.extension.view;

import nstuff.juggerfall.extension.models.NetViewModel;

public abstract class NetView {
	public int id;

    public transient  NetViewType viewType;

    public abstract void update(NetViewModel view);

    public abstract void delete();

    public abstract void deleteLocal();

    public transient  ViewManager manager;

    public abstract void clearRef();
}
