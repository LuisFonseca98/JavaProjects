package ecosystem;

import java.util.ArrayList;

import ca.GridConstants;
import processing.core.PApplet;
import processing.core.PVector;

public class Prey extends Animal
{	
	public Prey(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.PREY_DEATH_RATE;
		minBirthRate = WorldConstants.PREY_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.PREY_BIRTH_RATE[1];
		birthFactor = WorldConstants.PREY_BIRTH_FACTOR;
		energy = WorldConstants.INI_PREY_ENERGY;
		energyToReproduce = WorldConstants.PREY_ENERGY_TO_REPRODUCE;
		type = "Prey";
	}
	
	public Prey(PApplet p, Prey prey) {
		super(p, prey);
	}

	@Override
	public Animal reproduce(float dt, boolean stochastic)
	{
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Prey(p, this);
		}
		return child;
	}

	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals)
	{
		Patch patch = (Patch)terrain.getCell((int)pos.x, (int)pos.y);
//		if (patch.getState() == GridConstants.State.FOOD.ordinal()) {
//			energy += WorldConstants.ENERGY_FROM_PLANT;
//			patch.setFertile();
//		}
	}

	@Override
	protected void activity(Terrain terrain) {
		// TODO Auto-generated method stub
		
	}
}
