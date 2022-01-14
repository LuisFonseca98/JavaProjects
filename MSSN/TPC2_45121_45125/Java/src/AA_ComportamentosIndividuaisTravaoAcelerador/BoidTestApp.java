package AA_ComportamentosIndividuaisTravaoAcelerador;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class BoidTestApp implements IProcessingApp 
{
	private double[] window = {-10, 10, -10, 10};
	private float[] viewport = {0f, 0f, 1f, 1f};
	private SubPlot plt;
	private Boid b;
	private BoidDNA dna;
	private float radius = 0.3f;
	private float mass = 0.2f;
	private PVector target, targetInPixels;
	private float speedUp = 1f;
	private boolean brake;

	@Override
	public void setup(PApplet p) 
	{
		plt = new SubPlot(window, viewport, p.width, p.height);
		b = new Boid(new PVector(), mass, radius, p.color(0), window);
		b.setShape(p, plt);
		dna = b.getDNA();
		target = new PVector();
		//targetInPixels = new PVector(p.width/2,p.height/2);
		targetInPixels = new PVector(p.width,p.height);
		brake = true;
	}

	@Override
	public void draw(PApplet p, float dt) 
	{
		p.background(255);
		PVector f;
		if (b.inSight(target, dna.visionDistance, dna.visionAngle) && brake)
			f = b.seek(target);
		else
			f = b.brake();
					
		b.applyForce(f);	
		b.move(speedUp*dt);
		
		p.circle(targetInPixels.x, targetInPixels.y, 10);
		b.display(p, plt);
	}

	@Override
	public void mousePressed(PApplet p) 
	{
		targetInPixels = new PVector(p.mouseX, p.mouseY);
		double[] pp = plt.getWorldCoord(p.mouseX, p.mouseY);
		target = new PVector((float)pp[0], (float)pp[1]);
	}

	@Override
	public void keyPressed(PApplet p) 
	{
		if(p.key == ' ') {
			brake = !brake;
		}
		if(p.keyCode == PConstants.UP) {
			dna.maxSpeed += 10;
			System.out.println("Max Speed UP " + dna.maxSpeed);
		}
		else if (p.keyCode == PConstants.DOWN) {
			dna.maxSpeed -= 10;
			System.out.println("Max Speed DOWN " + dna.maxSpeed);
		}
		else if(p.keyCode == PConstants.RIGHT) {
			dna.maxForce += 10;
			System.out.println("Max Force RIGHT " + dna.maxForce);
		}
		else if(p.keyCode == PConstants.LEFT) {
			dna.maxForce -= 10;
			System.out.println("Max Force LEFT " + dna.maxForce);
		}
		if (p.key == 'd') b.setDebug(true);
		else b.setDebug(false);
	}
}
