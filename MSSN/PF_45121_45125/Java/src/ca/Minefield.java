package ca;

import java.util.ArrayList;

import ca.GridConstants.State;
import processing.core.PApplet;

public class Minefield extends MajorityCA
{
	private ArrayList<Cell> obstacles;
	//private ArrayList<Cell> rocks;

	public Minefield(PApplet p, int nrows, int ncols, int radius, 
			boolean moore, int numberOfStates)
	{
		super(p, nrows, ncols, radius, moore, numberOfStates);
		obstacles = new ArrayList<Cell>();
		//rocks = new ArrayList<Cell>();
	}

	public void setObstacles()
	{
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell c = cells[i][j];
				if (c.getState() == State.ROCK.ordinal() || c.getState() == State.RURAL.ordinal() ||
						c.getState() == State.GRASS.ordinal()) {
					obstacles.add(c);	
				}
			}
		}
	}
	
	/*public void setRocks()
	{
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell c = cells[i][j];
				if (c.getState() == State.ROCK.ordinal())
					rocks.add(c);
			}
		}
	}*/
	
	public ArrayList<Cell> getObstacles()
	{
		return obstacles;
	}
	
}