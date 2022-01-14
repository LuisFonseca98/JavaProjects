package AA_FlockingComLider;

import processing.core.PApplet;
import processing.core.PVector;

public class TesteFlockComLider implements IProcessingApp {
	
	private Flock flock;
	private Boid boid,y;
	private int nBoids = 110, hue = 255,radius = 10;
	
	
	@Override
	public void setup(PApplet parent) {
		flock = new Flock(parent,nBoids,radius,hue);
		boid = new Boid(parent, new PVector(512,512),20,parent.color(255,128,64),20);
		
	}
	@Override
	public void draw(PApplet parent, float dt) {
		parent.background(0);
		y = flock.getBoid(nBoids - 1);
		y.setShape(parent.color(0,255,0), 10);
		if(boid.inSight(y.getPos(), 500)) {
			boid.dna.velMax = parent.random(250,500);
			PVector f = boid.seek(y.getPos());
			boid.applyForce(f);
		}else {
			PVector wander = boid.wander();
			boid.applyForce(wander);
		}
		
		flock.applyBehaviour();
		flock.move(dt);
		//boid.move(dt);
		flock.display();
		//boid.display();
		
	}
	@Override
	public void keyPressed(PApplet parent) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(PApplet parent) {
		// TODO Auto-generated method stub
		
	}
	
}
