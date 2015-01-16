package nstuff.juggerfall.extension.models;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

import java.util.List;

/**
 * Created by Ivan.Ochincenko on 16.01.15.
 */
public class AssaultPointModel implements SerializableSFSType {

    public float scorePoint;

    public float needPoint;

    public int owner;

    public int id;

    public int people;

    public int teamConquering;

    public List<Integer> lockedBy;

}
