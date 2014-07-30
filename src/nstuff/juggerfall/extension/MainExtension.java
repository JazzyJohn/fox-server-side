package nstuff.juggerfall.extension;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

public class MainExtension extends SFSExtension {

    public User masterInfo;

    public void ChoiceMaster(User exMaster) throws SFSVariableException {

        List<User> list = getParentRoom().getUserList();
        if (list.size()!= 0){

            for (int i=0; i<list.size(); i++){
                User user = list.get(i);
                if (user !=exMaster ){
                    masterInfo = user;
                    user.setVariable(new SFSUserVariable("Master",true ));
                    return;
                }


            }

        }
    }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		trace(ExtensionLogLevel.INFO,"MainExtension is Up");
	}

}
