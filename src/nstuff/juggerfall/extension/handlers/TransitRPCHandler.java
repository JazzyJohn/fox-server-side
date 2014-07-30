package nstuff.juggerfall.extension.handlers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;

import java.util.ArrayList;

import java.util.List;


public class TransitRPCHandler extends BaseClientRequestHandler
{

    @Override
    public void handleClientRequest(User sender, ISFSObject params)
    {

        RPCTransitTargetType targetType = RPCTransitTargetType.values()[params.getInt("TargetType")];
        List<User> targets;
        switch(targetType){
            case OTHER:
                targets  = getParentExtension().getParentRoom().getUserList();
                targets.remove(sender);
                break;
            default:
                targets = new ArrayList<User>();
                break;

        }
        if(params.containsKey("UDP")&&params.getBool("UDP")){
            send(MainExtension.RequestName_TransitRPC,params,targets,true);
        }else{
            send(MainExtension.RequestName_TransitRPC,params,targets);
        }
    }
}