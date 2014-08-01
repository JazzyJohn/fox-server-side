package nstuff.juggerfall.extension.view;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;

import nstuff.juggerfall.extension.models.NetViewModel;
import nstuff.juggerfall.extension.pawn.Pawn;

public abstract class NetView {
	public int id;

    public abstract void Update(NetViewModel view);

    public abstract void Delete();

    public abstract void DeleteLocal();

    public transient  ViewManager manager;

    public abstract void ClearRef();
}
