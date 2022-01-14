package AA_ComportamentosIndividuiasArrive;

public class BoidDNA 
{
	public float maxSpeed;
	public float maxForce;
	public float visionDistance;
	public float visionSafeDistance;
	public float visionAngle;
	public float deltaTPursuit;
	public float deltaTWander;
	public float deltaPhiWander;
	public float radiusWander;
	
	public BoidDNA()
	{
		// physics
		maxSpeed = random(3,5);
		maxForce = random(4,7);
		// vision
		visionDistance = random(2,4);
		visionSafeDistance = 0.25f*visionDistance;
		visionAngle = (float)Math.PI;
		// pursuit behavior
		deltaTPursuit = 0.8f;
		// wander behavior
		deltaTWander = 0.5f;
		deltaPhiWander = (float)(Math.PI/4);
		radiusWander = 150;
	}
	
	public static float random(float min, float max)
	{
		return (float)(min + (max - min) * Math.random());
	}
}
