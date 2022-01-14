package aa;
import java.util.ArrayList;

import physics.Mover;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Boid extends Mover 
{
	protected PShape s;
	protected BoidDNA dna;
	protected float phiWander;
	protected int color;
	protected PApplet p;

	public Boid(PApplet p, PVector pos, float mass, int color, float radius) 
	{
		super(pos, new PVector(), mass, radius);
		this.p = p;
		this.color = color;
		dna = new BoidDNA(p);
		setShape(color, radius);
	}

	public void setShape(int color, float radius)
	{
		s = p.createShape();
		s.beginShape();
		s.noStroke();
		s.fill(color);
		s.vertex(-radius, radius/2);
		s.vertex(radius, 0);
		s.vertex(-radius, -radius/2);
		s.vertex(-radius/2, 0);
		s.endShape(PConstants.CLOSE);
	}

	@Override
	public void applyForce(PVector f)
	{
		super.applyForce(f.limit(dna.maxForce));
	}

	@Override
	public void move(float dt)
	{
		
		int val = 190;
		
		super.move(dt);
		while (pos.x < 0) pos.x += (p.width - val);
		while (pos.y < 0) pos.y += p.height;
		
		if (pos.x > (p.width - 220) && pos.x < (p.width - (val-10))) pos.x = 0;
		if(pos.x < p.width && pos.x > (p.width - 10)) pos.x = (p.width-val);
		
		while (pos.y >= p.height) pos.y -= p.height;
		
	}

	public boolean inSight(PVector t, float visionDistance)
	{
		PVector r = PVector.sub(t, pos);
		float d = r.mag();
		float angle = PVector.angleBetween(vel,r);
		return ((d > 0) && (d < visionDistance) && (angle < dna.visionAngle));
	}

	public PVector brake()
	{
		PVector vd = new PVector();
		return PVector.sub(vd, vel);
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

	public PVector wander() {
		PVector center = pos.copy();
		center.add(PVector.mult(vel, dna.deltaTWander));
		PVector target = new PVector(dna.radiusWander * PApplet.cos(phiWander), 
				dna.radiusWander * PApplet.sin(phiWander));
		target.add(center);
		phiWander += p.random(-dna.deltaPhiWander, dna.deltaPhiWander);
		return seek(target);
	}

	public ArrayList<Boid> inCone(ArrayList<Boid> allBoids)
	{
		ArrayList<Boid> boidsInSight = new ArrayList<Boid>();
		for(Boid b : allBoids)
			if (inSight(b.pos, dna.visionDistanceLarge)) 
				boidsInSight.add(b);
		return boidsInSight;
	}

	public PVector separate(ArrayList<Boid> boids)
	{
		PVector vd = new PVector();
		for(Boid b : boids) {
			PVector r = PVector.sub(pos, b.pos);
			float d = r.mag();
			r.div(d*d);
			vd.add(r);
		}
		vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public PVector cohesion(ArrayList<Boid> boids)
	{
		PVector target = pos.copy();
		for(Boid b : boids) target.add(b.pos);
		target.div(boids.size()+1);
		return seek(target);
	}

	public PVector align(ArrayList<Boid> boids)
	{
		PVector vd = this.vel.copy();
		for(Boid b : boids) vd.add(b.vel);
		vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public BoidDNA getParam()
	{
		return dna;
	}
	
	public void setParam(BoidDNA param)
	{
		this.dna = param;
	}
	
	public void display()
	{
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		p.rotate(vel.heading());
		p.shape(s, 0, 0);
		p.popMatrix();
	}
}

