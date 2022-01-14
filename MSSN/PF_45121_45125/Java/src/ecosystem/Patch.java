package ecosystem;

import java.util.ArrayList;

import ca.GridConstants.State;
import ca.MajorityCell;

public class Patch extends MajorityCell 
{
	private long eatenTime;
	private int timeToGrow;
	private ArrayList<Animal> animals;

	public Patch(Terrain terrain, int row, int col, int timeToGrow) {
		super(terrain, row, col);
		this.timeToGrow = timeToGrow;
		eatenTime = System.currentTimeMillis();
		animals = new ArrayList<Animal>();
	}
	
	public void setFertileWater()
	{
		state = State.WATER.ordinal(); 
		eatenTime = System.currentTimeMillis();
	}
	
	public void setFertileRural() {
		state = State.RURAL.ordinal(); 
		eatenTime = System.currentTimeMillis();
		
	}
	
	public void soldMarket() {
		state=State.SOLD.ordinal();
		eatenTime = System.currentTimeMillis();
	}
	
	public void regenerateFood()
	{
		if (state == State.WATER.ordinal()
				&& System.currentTimeMillis() > (eatenTime + 2*timeToGrow))
			state = State.SEAWEED.ordinal();
		
		if(state == State.GRASS.ordinal()
				&& System.currentTimeMillis() > (eatenTime + 3*timeToGrow))
			state = State.MARKET.ordinal();
		
		if(state == State.SOLD.ordinal()
				&& System.currentTimeMillis() > (eatenTime + 2*timeToGrow))
			state = State.GRASS.ordinal();
		
		if (state == State.RURAL.ordinal()
				&& System.currentTimeMillis() > (eatenTime + timeToGrow))
			state = State.GRASSFOOD.ordinal();
	}
	
	
	public ArrayList<Animal> getAnimals()
	{
		return animals;
	}
	
	public void clearAnimalsList()
	{
		animals.clear();
	}
	
	public void addAnimal(Animal a)
	{
		animals.add(a);
	}
	
	public void delAnimal(Animal a)
	{
		animals.remove(a);
	}
}
