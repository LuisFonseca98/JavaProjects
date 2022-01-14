package AA_ComportamentosIndividuiasArrive;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Boid extends Mover 
{
	private int color;
	private PShape shape;
	private BoidDNA dna;
	private double[] window;
	private boolean debug;
	public float phiWander;
	private PApplet p;

	protected Boid(PVector pos, float mass, float radius, 
			int color, double[] window) 
	{
		super(pos, new PVector(), mass, radius);
		this.dna = new BoidDNA();
		this.color = color;
		this.window = window;
		this.debug = false;
	}

	public BoidDNA getDNA()
	{
		return dna;
	}

	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}

	public void setShape(PApplet p, SubPlot plt)
	{
		float[] rr = plt.getPixelVector(radius, radius);
		PShape s = p.createShape();
		s.beginShape();
		s.noStroke();
		s.fill(color);
		s.vertex(-rr[0], rr[0]/2);
		s.vertex(rr[0], 0);
		s.vertex(-rr[0], -rr[0]/2);
		s.vertex(-rr[0]/2, 0);	
		s.endShape(PConstants.CLOSE);	
		this.shape = s;
	}

	public void setShape(PApplet p, SubPlot plt, float radius, int color)
	{
		this.color = color;
		this.radius = radius;
		setShape(p, plt);
	}

	@Override
	public void applyForce(PVector f)
	{
		super.applyForce(f.limit(dna.maxForce));
	}

	@Override
	public void move(float dt)
	{
		super.move(dt);
		while (pos.x < window[0]) pos.x += (window[1] - window[0]);
		while (pos.y < window[2]) pos.y += (window[3] - window[2]);
		while (pos.x >= window[1]) pos.x -= (window[1] - window[0]);
		while (pos.y >= window[3]) pos.y -= (window[3] - window[2]);
	}

	public ArrayList<Boid> inCone(ArrayList<Boid> allBoids)
	{
		ArrayList<Boid> boidsInSight = new ArrayList<Boid>();
		for(Boid b : allBoids)
			if (inSight(b.pos, dna.visionDistance, dna.visionAngle)) 
				boidsInSight.add(b);
		return boidsInSight;
	}

	public boolean inSight(PVector t, float maxDistance, float maxAngle)
	{
		PVector r = PVector.sub(t, pos);
		float d = r.mag();
		float angle = PVector.angleBetween(vel, r);
		return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
	}

	public PVector seek(PVector target)
	{
		PVector vd = PVector.sub(target, pos);
		vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public PVector flee(PVector target)
	{
		return seek(target).mult(-1);
	}

	public PVector pursuit(Boid b)
	{
		PVector d = PVector.mult(b.vel, dna.deltaTPursuit);
		PVector target = PVector.add(b.pos, d);
		return seek(target);
	}

	public PVector evade(Boid b)
	{
		return pursuit(b).mult(-1);
	}

	public PVector brake()
	{
		PVector vd = new PVector();
		return PVector.sub(vd, vel);
	}

	public PVector separate(ArrayList<Boid> boids)
	{
		PVector vd = this.vel.copy();
		float d = dna.visionSafeDistance;
		for (Boid b : boids)
		{
			if (inSight(b.pos, d, (float)Math.PI))
			{
				PVector r = PVector.sub(pos, b.pos);
				d = r.mag();
				vd = PVector.div(r, d*d);
			}
		}
		//vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public PVector align(ArrayList<Boid> boids)
	{
		PVector vd = this.vel.copy();
		for(Boid b : boids) vd.add(b.vel);
		vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public PVector cohesion(ArrayList<Boid> boids)
	{
		PVector target = this.pos.copy();
		for(Boid b : boids) target.add(b.pos);
		target.div(boids.size()+1);
		return seek(target);
	}

	public PVector wander() {
		PVector center = pos.copy();
		center.add(PVector.mult(vel, dna.deltaTWander));
		PVector target = new PVector(dna.radiusWander * PApplet.cos(phiWander), dna.radiusWander
				* PApplet.sin(phiWander));
		target.add(center);
		if(phiWander != 0)
			phiWander += p.random(-dna.deltaPhiWander, dna.deltaPhiWander);
		return seek(target);
	}
	
	
	public void display(PApplet p, SubPlot plt)
	{
		p.pushStyle();
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] rr = plt.getPixelVector(radius, radius);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		if (debug)
		{
			p.pushStyle();
			p.stroke(255, 255, 0);
			p.strokeWeight(3);
			float[] dd = plt.getPixelVector(dna.visionDistance, dna.visionSafeDistance);
			p.rotate(dna.visionAngle);
			p.line(0,0,dd[0],0);
			p.rotate(-2*dna.visionAngle);
			p.line(0,0,dd[0],0);
			p.rotate(dna.visionAngle);
			p.arc(0, 0, 2*dd[0], 2*dd[0], -dna.visionAngle, dna.visionAngle);
			p.arc(0, 0, 2*dd[1], 2*dd[1], 0, 2*(float)Math.PI);
			p.popStyle();
		}	
		if (shape != null) 
			p.shape(shape, 0, 0);
		else 
			p.circle(0, 0, 4*rr[0]);
		p.popMatrix();	
		p.popStyle();
	}
}
