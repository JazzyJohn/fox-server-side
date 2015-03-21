package nstuff.juggerfall.extension.models;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

/**
 * Created by vania_000 on 13.09.2014.
 */
public class BaseDamageModel implements SerializableSFSType {

    public float damage;

    public float pushForce;

    public Vector3Model hitPosition;

    public int weaponId;

    public int damageType;

    public float vsArmor;
}
