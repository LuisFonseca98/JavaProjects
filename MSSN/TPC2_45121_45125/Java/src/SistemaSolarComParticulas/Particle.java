package SistemaSolarComParticulas;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle extends Mover {
	private float lifespan;
	private int color;
	private float timer;
	private int particleFormat;

	public Particle(PVector pos, PVector vel, int color, 
			float radius, float lifespan, int particleFormat) {
		super(pos, vel, 0f, radius);
		this.color = color;
		this.lifespan = lifespan;
		this.timer = 0;
		this.particleFormat = particleFormat;
	}

	@Override
	public void move(float dt)
	{
		super.move(dt);
		timer += dt;
	}

	public boolean isDead() {
		return (timer > lifespan);
	}

	public void display(PApplet parent, SubPlot plt) {
		parent.pushStyle();
		float alpha = PApplet.map(timer, 0, lifespan, 255, 0);
		parent.fill(color, alpha);
		parent.noStroke();
		switch(particleFormat) {
		case 0:
			parent.stroke(parent.random(255),parent.random(255),parent.random(255));
			parent.point(pos.x,pos.y);
			break;
		case 1:
			parent.ellipse(pos.x,pos.y,radius,radius);
			break;
		case 2:
			parent.rect(pos.x,pos.y,radius,radius);
			break;
		case 3:
			parent.triangle(pos.x, pos.y, pos.x +radius, pos.y+radius, pos.x - radius, pos.y - radius);
			break;
		}
		parent.popStyle();
	}
}
