package nstuff.juggerfall.extension;

import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class MainExtension extends SFSExtension {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		trace(ExtensionLogLevel.INFO,"MainExtension is Up");
	}

}
