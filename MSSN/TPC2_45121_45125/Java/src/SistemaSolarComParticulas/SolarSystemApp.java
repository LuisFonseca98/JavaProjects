package SistemaSolarComParticulas;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class SolarSystemApp implements IProcessingApp  {

	private ArrayList<CelestialBody> planets;
	private ArrayList<ParticleSystem> ps;
	private CelestialBody sun;
	private double[] window = {-10,10,-10,10};
	private float[] viewport = {0f,0f,1f,1f};
	private SubPlot plt;
	private float speedUp = 5f;
	private PImage bg;
	private float timer;
	private float timeInterval = 1.0f;

	@Override
	public void setup(PApplet parent) {
		plt = new SubPlot(window,viewport,parent.width,parent.height);
		sun = new CelestialBody(new PVector(), new PVector(), 300000,1,parent.color(255,128,0));
		planets = new ArrayList<CelestialBody>();
		//bg = parent.loadImage("src/data/galaxy.jpg");
		ps = new ArrayList<ParticleSystem>();
		timer = 0;
	}

	@Override
	public void draw(PApplet parent, float dt) {
		//parent.image(bg, 0, 0);
		timer += dt;
		System.out.println("tempo:" + timer);

		
		float[]bb = plt.getBoundingBox();
		parent.fill(205,32);
		parent.rect (bb[0],bb[1],bb[2],bb[3]);
		sun.display(parent, plt);
		for(CelestialBody planet : planets) {
			PVector f = sun.attraction(planet);
			planet.applyForce(f);
			planet.move(dt*speedUp);
			planet.display(parent, plt);
		}

		if(timer < timeInterval) {
			timer = 0;
			ps.add(new ParticleSystem(parent, 
					new PVector(0,parent.random(0,300)),
					new PVector(parent.random(100,200),200), 
					parent.random(0,5), 
					new PVector(80,30), 
					parent.color(parent.color(255),0,parent.random(128)), 
					12, 
					2));

		}
		for(ParticleSystem pS : ps) {
			PVector f2 = sun.attraction(pS);
			pS.applyForce(f2);
			pS.move(dt);
			pS.display(parent,plt);
		}
	}

	@Override
	public void keyPressed(PApplet parent) {

	}

	@Override
	public void mousePressed(PApplet parent) {
		if(plt.isInside(parent.mouseX, parent.mouseY)) {
			double[] pp = plt.getWorldCoord(parent.mouseX, parent.mouseY);
			float velx = parent.random(2,4);
			float radius = parent.random(0.1f, 0.5f);
			float mass = parent.random(0.5f, 0.5f);
			float green = parent.random(100,255);
			float blue = parent.random(100,255);
			CelestialBody planet = new CelestialBody(new PVector((float)pp[0],(float)pp[1]),
					new PVector(velx,0), mass, radius,parent.color(0,green,blue));
			planets.add(planet);
		}

	}

}
