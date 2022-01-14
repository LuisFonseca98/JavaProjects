package AA_FlockingComPresaPredador;
import processing.core.PApplet;
import processing.core.PVector;

public class FlockTestApp implements IProcessingApp {
	private int nBoids = 25,radius = 30;
	private Flock presas;
	private Boid specialOne,chaser;
	private PVector target;
	private boolean changePos;

	@Override
	public void setup(PApplet p) 
	{
		presas = new Flock(p,nBoids,25,90);
		specialOne = presas.getBoid((int)p.random(nBoids));
		specialOne.setShape(p.color(255,0,0), radius);
		chaser = new Boid(p, new PVector(p.random(p.width),p.random(p.height)), 1f, 
				p.color(255,255,0), 30);
		target = new PVector();
		changePos = false;
	}

	@Override
	public void draw(PApplet parent, float dt) 
	{
		parent.background(255);
		PVector pursuitF,fleeF,wanderF;

		pursuitF = chaser.pursuit(specialOne);
		fleeF = specialOne.flee(pursuitF);

		if(chaser.inSight(specialOne.getPos(), 100)) {
			chaser.applyForce(pursuitF);
		}else {
			if(changePos) {
				wanderF = chaser.seek(target);
				chaser.applyForce(wanderF);
			}else {
				wanderF = chaser.wander();
				chaser.applyForce(wanderF);
			}
		}

		if(specialOne.inSight(chaser.getPos(),200))specialOne.applyForce(fleeF);

		presas.applyBehaviour();
		presas.move(dt);
		presas.display();

		chaser.move(dt);
		chaser.display();
	}


	@Override
	public void keyPressed(PApplet parent) 
	{
	}

	@Override
	public void mousePressed(PApplet parent) 
	{
		changePos = !changePos;
		target.x = parent.mouseX;
		target.y = parent.mouseY;
	}
}
