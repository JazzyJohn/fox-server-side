package nstuff.juggerfall.extension.view;

import nstuff.juggerfall.extension.models.NetViewModel;

public abstract class NetView {
	public int id;

    public transient  NetViewType viewType;

    public abstract void Update(NetViewModel view);

    public abstract void Delete();

    public abstract void DeleteLocal();

    public transient  ViewManager manager;

    public abstract void ClearRef();
}
