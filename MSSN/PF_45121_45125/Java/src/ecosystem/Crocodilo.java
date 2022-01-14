package ecosystem;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Crocodilo extends Animal{
	
	private int glacierX,glacierY;
	
	private PImage img, img_ori;
	boolean stochastic;
	
	public Crocodilo(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.PREDATOR_DEATH_RATE;
		minBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[1];
		birthFactor = WorldConstants.PREDATOR_BIRTH_FACTOR;
		energy = WorldConstants.INI_PREDATOR_ENERGY;
		energyToReproduce = WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE;
		type = "Crocodilo";
		
		glacierX = 4;
		glacierY = 4;
		
		img = WorldConstants.CROC_BEAR_IMG;
		img_ori = img.copy();
	}

	public Crocodilo(PApplet p, Crocodilo croc)
	{
		super(p, croc);
		stochastic = WorldConstants.STOCHASTIC;
		img = WorldConstants.CROC_BEAR_IMG;
		img_ori = img.copy();
	}
	
	@Override
	public Animal reproduce(float dt, boolean stochastic)
	{
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Crocodilo(p, this);
		}
		return child;
	}
	
	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals)
	{
		Patch patch = (Patch) terrain.getCell((int)pos.x, (int)pos.y);
		ArrayList<Animal> patchAnimals = patch.getAnimals();
		for(int i=0;i<patchAnimals.size();i++) {
			Animal a = patchAnimals.get(i);
			if (a.type == "Prey" || a.type == "FlockPrey" || a.type == "Fish"
					|| a.type == "Cegonha") {
				patchAnimals.remove(a);
				allAnimals.remove(a);
				energy += WorldConstants.ENERGY_FROM_PREY;
				img_ori	= p.loadImage("images\\croc.png");
				break;
			}
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
		if(PApplet.dist(this.getPos().x, this.getPos().y, terrain.getCellGrid(4, 4).getCenter().x, terrain.getCellGrid(4, 4).getCenter().y) >  120) {
			PVector f = this.seek(new PVector(terrain.getCellGrid(4, 4).getCenter().x, terrain.getCellGrid(4, 4).getCenter().y)); 
			this.applyForce(f);
			energy-=1f;
		}
		
		else {
			this.applyBehaviour(terrain);
		}
	}
	
	
}
