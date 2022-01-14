package ca;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Cell
{
	protected CellularAutomata ca;
	protected int row, col;
	protected int state;
	protected Cell[] neighbors;
	protected int w, h;

	public Cell(CellularAutomata ca, int row, int col)
	{
		this.ca = ca;
		this.row = row;
		this.col = col;
		state = 0;
		neighbors = null;
		w = ca.getCellWidth();
		h = ca.getCellHeight(); 
	}

	public void setNeighbors(Cell[] neighbors)
	{
		this.neighbors = neighbors;
	}
	
	public Cell[] getNeighbors()
	{
		return neighbors;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public int getState()
	{
		return state;
	}

	public PVector getCenter()
	{
		float x = (col + 0.5f) * w;
		float y = (row + 0.5f) * h;
		return new PVector(x, y);
	}	

	public void display(PApplet p)
	{
		
		p.pushStyle();
		PImage img = ca.getStateImages()[state];
		img.resize(w, h);
		p.image(img, col * w, row * h);	
		p.popStyle();
	}
}