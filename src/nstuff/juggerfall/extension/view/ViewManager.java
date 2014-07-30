package nstuff.juggerfall.extension.view;

import java.util.HashMap;
import java.util.Map;


import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;

public class ViewManager {
	public Map<Integer ,FoxView> allView = new HashMap<Integer ,FoxView>();

	public FoxView CreateView(User user, ISFSObject data) {
		FoxView view  = new FoxView();
		view.owner = user;
		view.id = data.getInt("viewId");
		view.spawnData = data;
		allView.put(view.id, view);
		return view;
	}
	
	
}
