package ecosystem;

import java.util.ArrayList;

import ca.GridConstants;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Fish extends Prey
{
	
	private PImage img, img_ori;
	boolean stochastic;
	
	public Fish(PApplet p, PVector pos, int color)
	{
		super(p, pos, color);
		type = "Fish";	
		img = WorldConstants.FISH_IMG;
		img_ori = img.copy();
	}
	
	public Fish(PApplet p, Fish fish) 
	{
		super(p, fish);
		img = WorldConstants.FISH_IMG;
		img_ori = img.copy();
	}


	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
		Patch patch = (Patch) terrain.getCell((int) pos.x, (int) pos.y);
		if (patch.getState() == GridConstants.State.SEAWEED.ordinal()) {
			energy += WorldConstants.ENERGY_FROM_PLANT;
			img_ori	= p.loadImage("images\\fish.png");
			patch.setFertileWater();
		}
	}
	
	
	@Override
	public Animal reproduce(float dt, boolean stochastic)
	{
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Fish(p, this);
		}
		return child;
	}
	
	@Override
	public void display() {
	
		radius = PApplet.map(energy, 0, 300, 3, 40);

		if (!stochastic) {
			img_ori.resize(2 * (int) radius, 2 * (int) radius);
		}

		p.image(img_ori, pos.x - img_ori.width / 2, pos.y - img_ori.height / 2);
	}
	
	@Override
	protected void activity(Terrain terrain) {
		super.applyBehaviour(terrain);
		PVector f = avoidObstacle(terrain);
		applyForce(f.mult(5));
	}
}