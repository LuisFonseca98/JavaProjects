package ecosystem;

import java.util.ArrayList;

import ca.GridConstants;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Cegonha extends Animal{
	
	private int glacierX;
	private int glacierY;
	
	private PImage img, img_ori;
	boolean stochastic;
	
	public Cegonha(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.PREDATOR_DEATH_RATE;
		minBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[1];
		birthFactor = WorldConstants.PREDATOR_BIRTH_FACTOR;
		energy = WorldConstants.INI_PREDATOR_ENERGY;
		energyToReproduce = 100f;
		type = "Cegonha";
		
		glacierX = 4;
		glacierY = 4;
		
		img = WorldConstants.CEGONHA_IMG;
		img_ori = img.copy();
	}

	public Cegonha(PApplet p, Cegonha cegonha)
	{
		super(p, cegonha);
		stochastic = WorldConstants.STOCHASTIC;
		img = WorldConstants.CEGONHA_IMG;
		img_ori = img.copy();
	}
	
	@Override
	public Animal reproduce(float dt, boolean stochastic)
	{
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Cegonha(p, this);
		}
		return child;
	}
	
	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
		Patch patch = (Patch) terrain.getCell((int) pos.x, (int) pos.y);
		if (patch.getState() == GridConstants.State.SEAWEED.ordinal()) {
			energy += WorldConstants.ENERGY_FROM_PLANT;
			img_ori	= p.loadImage("images\\cegonha.png");
			patch.setFertileWater();
		}
	}

	@Override
	public void display() {
		
		radius = PApplet.map(energy, 0, 200, 3, 30);

		if (radius <= 0)
			radius = 1;

		if (!stochastic) {
			img_ori.resize(2 * (int) radius, 2 * (int) radius);
		}

		p.image(img_ori, pos.x - img_ori.width / 2, pos.y - img_ori.height / 2);
	}

	@Override
	protected void activity(Terrain terrain) {
		if(PApplet.dist(this.getPos().x, this.getPos().y, terrain.getCellGrid(4, 4).getCenter().x, terrain.getCellGrid(4, 4).getCenter().y) >  180) {
			PVector f = this.seek(new PVector(terrain.getCellGrid(4, 4).getCenter().x, terrain.getCellGrid(4, 4).getCenter().y)); 
			this.applyForce(f);
		}
		
		else {
			this.applyBehaviour(terrain);
		}
	}
	
	
}
