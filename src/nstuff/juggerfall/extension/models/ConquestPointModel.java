package nstuff.juggerfall.extension.models;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

/**
 * Created by Ivan.Ochincenko on 08.08.14.
 */
public class ConquestPointModel implements SerializableSFSType {
    public int id;

    public int scorePoint;

    public int owner;

}
