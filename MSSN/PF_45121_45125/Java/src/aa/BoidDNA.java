package aa;
import processing.core.PApplet;

public class BoidDNA 
{
	public float maxSpeed;
	public float maxForce;
	public float visionDistanceLarge;
	public float visionDistanceSmall;
	public float visionAngle;
	public float deltaTPursuit;
	public float deltaTWander;
	public float deltaPhiWander;
	public float radiusWander;
	
	public BoidDNA(PApplet p)
	{
		// physics
		maxSpeed = p.random(60,100);
		maxForce = p.random(200,400);
		// vision
		visionDistanceLarge = p.random(150,200);
		visionDistanceSmall = 0.8f*visionDistanceLarge;
		visionAngle = (float)Math.PI/6;
		// pursuit behavior
		deltaTPursuit = 0.8f;
		// wander behavior
		deltaTWander = 0.5f;
		deltaPhiWander = (float)(Math.PI/4);
		radiusWander = 150;
	}
}
