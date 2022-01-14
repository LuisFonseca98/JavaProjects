package TarefaB_JogoCaosRestricoes;

import processing.core.PApplet;
import processing.core.PVector;

public class ChaosGameApp implements IProcessingApp {

	private double[] window = {-1,1,0,1};
	private float[] viewport = {0.1f,0.1f,0.8f,0.8f};
	private SubPlot plot;
	private PVector[] points;
	private PVector current;
	private PVector previous;

	@Override
	public void setup(PApplet parent) {
		parent.background(0);
		plot = new SubPlot(window,viewport,parent.width,parent.height);
		int n = 4;
		points = new PVector[n];
		for (int i = 0; i < n; i++) {
			float angle = i * parent.TWO_PI/n;
			PVector v = PVector.fromAngle(angle);
			v.mult(parent.width/2);
			v.add(parent.width/2,parent.height/2);
			points[i] = v;
		}
		reset(parent);
	}

	void reset(PApplet parent) {
		current = new PVector(parent.random(parent.width), parent.random(parent.height));
		parent.background(0);
		parent.stroke(255);
		parent.strokeWeight(8);
		for (PVector p : points) {
			parent.point(p.x, p.y);
		}    
	}

	@Override
	public void draw(PApplet parent, float dt) {
		if(parent.frameCount % 100 == 0) {
			reset(parent);
		}
		for (int i = 0; i <= 1000; i++) {
			parent.strokeWeight(1);
			PVector next = points[PApplet.floor(parent.random(points.length))];
			if(next != previous) {
				current.x = PApplet.lerp(current.x, next.x, 0.5f);
				current.y = PApplet.lerp(current.y, next.y, 0.5f);
				parent.stroke(255,0,0);
				parent.point(current.x, current.y);
			}
			previous = next;
		}
	}
	@Override
	public void keyPressed(PApplet parent) {}
	@Override
	public void mousePressed(PApplet parent) {}
}