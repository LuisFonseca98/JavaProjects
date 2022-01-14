package ecosystem;

import java.util.ArrayList;

import aa.BoidOverGrid;
import ca.GridConstants.State;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Animal extends BoidOverGrid
{
	protected float energy;
	protected String type = "";
	protected int birthday;
	protected float deathRate;
	protected float minBirthRate;
	protected float maxBirthRate;
	protected float birthFactor;
	protected float energyToReproduce;
	protected int value;

	protected Animal(PApplet p, PVector pos, int color) {
		super(p, pos, 1f, color, 25);
		birthday = p.millis();
	}

	protected Animal(PApplet p, Animal a)
	{
		super(p, a.pos, 1f, a.color, a.radius);
		this.energy = a.energy;
		this.type = a.type;
		this.birthday = p.millis();
		this.deathRate = a.deathRate;
		this.minBirthRate = a.minBirthRate;
		this.maxBirthRate = a.maxBirthRate;
		this.birthFactor = a.birthFactor;
		this.energyToReproduce = a.energyToReproduce;
		this.value = a.value;
	}

	protected double birthRate()
	{
		return maxBirthRate * minBirthRate /
				(minBirthRate+(maxBirthRate-minBirthRate)*
						Math.exp(-birthFactor*energy));
	}

	protected boolean die(float dt, boolean stochastic)
	{
		if (stochastic && p.random(1) < deathRate*dt) 
			return true;
		if(!stochastic && energy < 0)
			return true;	
			
		return false;
	}

	@Override
	public void move(float dt)
	{
		super.move(dt);
		energy -= dt;
	}

	protected void obstaclesPenalty(Terrain terrain, float dt)
	{
		Patch patch = (Patch) terrain.getCell((int)pos.x, (int)pos.y);

		if(this.getType() == "Plane" && patch.getState() == State.ROCK.ordinal()) {
			energy = 0;
		}


		if(this.getType() == "Cacador" && patch.getState() == State.WATER.ordinal()
				|| this.getType() == "Gazela" && patch.getState() == State.WATER.ordinal()
					|| this.getType() == "Leao" && patch.getState() == State.WATER.ordinal())
		{ 
			energy -= 50f * dt;
		}


		if(this.getType() == "Gazela" && patch.getState() == State.GRASS.ordinal()) {
			energy -= 20f * dt;
		}
		
		if(this.getType() == "Fish" && patch.getState() == State.MARKET.ordinal() ||
				this.getType() == "Fish" && patch.getState() == State.GRASS.ordinal()) {
			energy = -1;
		}
	}

	protected void setEnergy(float energy)
	{
		this.energy = energy;
	}

	protected String getType()
	{
		return type;
	}

	protected void setType(String type)
	{
		this.type = type;
	}

	public boolean isItTimeToReproduce(float dt, boolean stochastic)
	{
		boolean reproduce = false;
		if (stochastic) {
			double br = birthRate()*dt;
			if (p.random(1) < br) reproduce = true;
		} 
		else if (energy > energyToReproduce)
			reproduce = true;
		return reproduce;
	}

	public void applyBehaviour(Terrain t)
	{
		PVector f = wander();
		applyForce(f);
	}

	@Override
	public void display()
	{
		radius = PApplet.map(energy, 0, 200, 3, 20);
		setShape(color, radius);
		super.display();
	}

	protected abstract Animal reproduce(float dt, boolean stochastic);

	protected abstract void eat(Terrain terrain, ArrayList<Animal> allAnimals);

	protected abstract void activity(Terrain terrain);
}
