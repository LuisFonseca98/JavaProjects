package AA_ComportamentosIndividuiasArrive;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class BoidTestApp implements IProcessingApp 
{
	private double[] window = {-10, 10, -10, 10};
	private float[] viewport = {0f, 0f, 1f, 1f};
	private SubPlot plt;
	private Boid b;
	private float radius = 0.3f;
	private float mass = 0.2f;
	private float speedUp = 1f;
	private float timer;
	private PVector v;
	private boolean flag;
	@Override
	public void setup(PApplet p) 
	{
		plt = new SubPlot(window, viewport, p.width, p.height);
		b = new Boid(new PVector(), mass, radius, p.color(0), window);
		b.setShape(p, plt);
		timer = 0;
		v = new PVector();
		flag = true;
		
	}
	@Override
	public void draw(PApplet p, float dt) 
	{
		p.background(255);
		PVector f;	
		if(timer % 50 == 0) {
			f = b.wander();
			b.applyForce(f);
			timer = 0;
			v = f;
			flag = true;
		}else {
			if(!flag) {
				float value = p.random(-10,10);
				v = new PVector(value,value);
				f = b.seek(v);
				b.applyForce(f);
			}else {
				f = b.seek(v);
				b.applyForce(f);
			}
		}
		b.wander();
		b.move(dt*speedUp);
		b.display(p, plt);
		timer++;
		
	}

	@Override
	public void mousePressed(PApplet p) {}

	@Override
	public void keyPressed(PApplet p) {
		if (p.key == 'd') b.setDebug(true);
		else b.setDebug(false);
	}
}
