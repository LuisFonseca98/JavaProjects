package aa;
import ca.Cell;
import ca.Minefield;
import processing.core.PApplet;
import processing.core.PVector;

public class BoidOverGrid extends Boid 
{
	private boolean debug = false;
	
	public BoidOverGrid(PApplet p, PVector pos, float mass, int color, float radius) 
	{
		super(p, pos, mass, color, radius);
	}

	private float hasObstacle_1(Minefield t)
	{
		for(Cell c : t.getObstacles()) {
			PVector obstacle = c.getCenter();
			if (inSight(obstacle, dna.visionDistanceSmall)) {
				PVector r = PVector.sub(obstacle, pos);
				PVector vd = new PVector(vel.y, -vel.x);
				return PVector.dot(vd,r);
			}
		}
		return 0;
	}

	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}
	
	private float hasObstacle_2(Minefield t)
	{
		PVector cm = new PVector();
		int count = 0;
		for(Cell c : t.getObstacles()) {
			if (inSight(c.getCenter(), dna.visionDistanceSmall)) {
				cm.add(c.getCenter());
				count++;
			}
		}
		if (count == 0) return 0;
		cm.div(count);
		PVector r = PVector.sub(cm, pos);
		PVector vd = new PVector(vel.y, -vel.x);
		return PVector.dot(vd,r);
	}

	public PVector avoidObstacle(Minefield t)
	{
		float s = hasObstacle_1(t);
		if (s == 0) return new PVector();
		PVector vd = new PVector(vel.y, -vel.x);
		if (s > 0) vd.mult(-1);
		return PVector.sub(vd,vel);
	}
	
	@Override 
	public void display()
	{
		super.display();
		if (debug)
		{
			p.pushMatrix();
			p.pushStyle();
			p.stroke(255,255,0);
			p.strokeWeight(3);
			p.translate(pos.x, pos.y);
			p.rotate(vel.heading());
			p.rotate(dna.visionAngle);
			p.line(0, 0, dna.visionDistanceSmall, 0);
			p.rotate(-2*dna.visionAngle);
			p.line(0, 0, dna.visionDistanceSmall, 0);
			p.popStyle();
			p.popMatrix();
		}
	}
}
