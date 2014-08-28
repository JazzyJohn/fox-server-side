package nstuff.juggerfall.extension.models;


/**
 * Created by Ivan.Ochincenko on 30.07.14.
 */
public class PawnModel extends NetViewModel {
	public int id;
	
    public String type;

    public int wallState;

    public int team;

    public int characterState;

    public boolean active;

    public boolean isDead;
    
    public float health;

    public boolean isAiming;

    public Vector3Model position;

    public Vector3Model aimRotation;

    public QuaternionModel rotation;
    

}
