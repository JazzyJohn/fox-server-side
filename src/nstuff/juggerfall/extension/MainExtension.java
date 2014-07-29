package nstuff.juggerfall.extension;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import nstuff.juggerfall.extension.rpc.TransitRPCHandler;
import nstuff.juggerfall.extension.utils.ServerTimeHandler;

public class MainExtension extends SFSExtension {

    public static final String RequestName_GetTime ="getTime";

    public static final String RequestName_TransitRPC="transitRPC";

	@Override
	public void init() {
		// TODO Auto-generated method stub
		trace(ExtensionLogLevel.INFO,"MainExtension is Up");


        addRequestHandler(RequestName_GetTime,ServerTimeHandler.class);

        addRequestHandler(RequestName_TransitRPC,TransitRPCHandler.class);


	}

}
