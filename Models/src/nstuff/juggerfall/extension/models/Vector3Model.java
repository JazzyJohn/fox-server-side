package nstuff.juggerfall.extension.models;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

/**
 * Created by Ivan.Ochincenko on 01.08.14.
 */
public class Vector3Model  implements SerializableSFSType {

    public float x;

    public float y;

    public float z;

    public Vector3Model(){

    }
    public Vector3Model(double x, double y, double z){
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;

    }
}
