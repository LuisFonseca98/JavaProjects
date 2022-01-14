package ecosystem;

import aa.Flock;
import aa.FlockOverGrid;
import processing.core.PApplet;
import processing.core.PVector;

public class FlockPrey extends Prey 
{
	private Flock flock;
	public FlockPrey(PApplet p, PVector pos, int color, Flock flock)
	{
		super(p, pos, color);
		type = "FlockPrey";
		this.flock = flock;
	}
	
	public FlockPrey(PApplet p, FlockPrey flockPrey) 
	{
		super(p, flockPrey);
		this.flock = flockPrey.flock;
	}
	
	@Override
	public void applyBehaviour(Terrain t)
	{
		((FlockOverGrid)flock).applyBehaviour(this, t);
	}
	
	@Override
	public Animal reproduce(float dt, boolean stochastic)
	{
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new FlockPrey(p, this);
			flock.addBoid(child);
		}
		return child;
	}
	
	@Override
	protected boolean die(float dt, boolean stochastic)
	{
		boolean died = super.die(dt, stochastic);
		if (died) flock.delBoid(this);
		return died;
	}
}
