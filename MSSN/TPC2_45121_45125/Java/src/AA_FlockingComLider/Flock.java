package AA_FlockingComLider;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Flock 
{
	protected ArrayList<Boid> boids;
	protected float radius;
	protected float hue;

	public Flock(PApplet p, int nboids, float radius, float hue)
	{
		this.radius = radius;
		this.hue = hue;
		
		boids = new ArrayList<Boid>();
		createFlock(p, nboids);
	}

	protected void createFlock(PApplet p, int nboids)
	{
		p.colorMode(PConstants.HSB, 360, 100, 100);
		for(int i=0;i<nboids;i++) {
			int color = p.color(hue, p.random(50,100), p.random(70,100));
			Boid b = new Boid(p, new PVector(p.random(p.width),
					p.random(p.height)), 1, color, radius);
			boids.add(b);
		}
		p.colorMode(PConstants.RGB, 255);
	}
	
	public void addBoid(Boid b)
	{
		boids.add(b);
	}
	
	public Boid getBoid(int n)
	{
		return boids.get(n);
	}
	
	public void delBoid(Boid b)
	{
		boids.remove(b);
	}

	public void move(float dt)
	{
		for(Boid b : boids) b.move(dt);
	}

	public void applyBehaviour()
	{
		for(Boid b : boids) 
			applyBehaviour(b);
	}
	
	public void applyBehaviour(Boid b)
	{
		ArrayList<Boid> boidsInSight = b.inCone(boids);
		PVector fs = b.separate(boidsInSight);
		PVector fa = b.align(boidsInSight);
		PVector fc = b.cohesion(boidsInSight);
		b.applyForce(fs.add(fa).add(fc));
	}
		
	public void setBoids(ArrayList<Boid> boids)
	{
		this.boids = boids;
	}
	
	public void display()
	{
		for(Boid b : boids) b.display();
	}
}
