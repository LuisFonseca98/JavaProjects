package aa;

import ca.Minefield;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class FlockOverGrid extends Flock
{
	public FlockOverGrid(PApplet p, int nboids, float radius, float hue)
	{
		super(p, nboids, radius, hue);
	}
	
	@Override
	protected void createFlock(PApplet p, int nboids)
	{
		p.colorMode(PConstants.HSB, 360, 100, 100);
		for(int i=0;i<nboids;i++) {
			int color = p.color(hue, p.random(50,100), p.random(70,100));
			Boid b = new BoidOverGrid(p, new PVector(p.random(p.width),
					p.random(p.height)), 1, color, radius);
			boids.add(b);
		}
		p.colorMode(PConstants.RGB, 255);
	}
	
	public void applyBehaviour(Minefield t)
	{	
		for(Boid b : boids) 
			applyBehaviour(b, t);
	}
	
	public void applyBehaviour(Boid b, Minefield t)
	{
		super.applyBehaviour(b);
		PVector fa = ((BoidOverGrid)b).avoidObstacle(t);
		b.applyForce(fa);
	}
}
