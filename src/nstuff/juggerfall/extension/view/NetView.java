package nstuff.juggerfall.extension.view;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import nstuff.juggerfall.extension.pawn.Pawn;

public abstract class NetView {
	public int id;

    public abstract void Update(NetView view);

    public abstract void Delete();

    public ViewManager manager;

    public abstract void ClearRef();
}
