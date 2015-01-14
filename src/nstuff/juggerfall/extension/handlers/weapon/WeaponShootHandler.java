package nstuff.juggerfall.extension.handlers.weapon;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.*;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import nstuff.juggerfall.extension.MainExtension;
import nstuff.juggerfall.extension.handlermanagers.WeaponHandlerManager;
import nstuff.juggerfall.extension.pawn.Pawn;
import nstuff.juggerfall.extension.weapon.Weapon;

import java.util.Iterator;

/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class WeaponShootHandler  extends BaseClientRequestHandler {
    @Override
    public void handleClientRequest(User user, ISFSObject alldata) {
        ISFSArray shoots = alldata.getSFSArray("shoots");
        ISFSArray verifyShoots = new SFSArray();
        MainExtension extension = (MainExtension)getParentExtension();
        for(Iterator<SFSDataWrapper> iterator =shoots.iterator();iterator.hasNext();){
            ISFSObject data = (ISFSObject) iterator.next().getObject();

            Weapon weapon = (Weapon)extension.viewManager.getView(data.getInt("id"));
            if(weapon==null){
                return;
            }
            if(weapon.owner==null){
                Pawn owner  =(Pawn)extension.viewManager.getView(weapon.lateId);
                if(owner!=null){

                    extension.SendWeaponRequest(user,owner,weapon);
                }
            }else{
                if(!weapon.owner.isOwner(user)){
                    continue;
                }
            }
            verifyShoots.addSFSObject(data);

        }
        ISFSObject verifyData = new SFSObject();
        verifyData.putSFSArray("shoots",verifyShoots);
        send(WeaponHandlerManager.RequestName_WeaponShoot,verifyData,extension.getOther(user));
    }
}
