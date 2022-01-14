package ca;


import ca.GridConstants.State;
import processing.core.PApplet;
import processing.core.PImage;
import tools.CustomRandomGenerator;

public class CellularAutomata
{
	protected int nrows, ncols;
	protected int w, h;
	protected Cell[][] cells;
	protected int radius;
	protected boolean moore;
	protected int numberOfStates;
	protected int[] colors;
	protected PApplet p;
	protected PImage[] images;
	protected int buildingCount;

	public CellularAutomata(PApplet p, int nrows, int ncols, 
			int radius, boolean moore, int numberOfStates)
	{
		this.p = p;
		this.nrows = nrows;
		this.ncols = ncols;
		this.radius = radius;
		this.moore = moore;
		this.numberOfStates = numberOfStates;		
		w = (p.width - 180)/ncols;
		h = p.height/nrows;
		cells = new Cell[nrows][ncols];
		createGrid();
		colors = new int[numberOfStates];
		setRandomStateColors();
		
		images = new PImage[numberOfStates];
		
		
		for(int i=0; i<GridConstants.imgPath.length; i++) {
			images[i] = p.loadImage(GridConstants.imgPath[i]);
		}
		
	}

	protected void createGrid()
	{
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j] = new Cell(this, i, j);
			}
		}
		if (moore) setNeighbors();
		else setNeighbors4();
	}

	public Cell getCellGrid(int row, int col)
	{
		return cells[row][col];	
	}
	
	public PImage[] getStateImages() {
		return images;
	}
	
	public void setNeighbors()
	{
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell[] neigh = new Cell[(int)Math.pow(2*radius+1,2)];
				int n = 0;
				for (int ii=-radius; ii<=radius; ii++) {
					for (int jj=-radius; jj<=radius; jj++) {
						int row = (i + ii + nrows) % nrows;
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbors(neigh);
			}
		}
	}

	public void setNeighbors4()
	{	
		int numberOfNeighbors = 2*(radius*radius+radius)+1;
		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				int n = 0;
				Cell[] neigh = new Cell[numberOfNeighbors];
				for (int ii=-radius; ii<=radius; ii++) {
					for (int jj=-radius+Math.abs(ii); jj<=radius-Math.abs(ii); jj++) {
						int row = (i + ii + nrows) % nrows;
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbors(neigh);
			}
		}
	}

	public void reset()
	{
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].setState(0);
			}
		}
	}

	public void setRandomStateColors()
	{
		for(int i=0;i<numberOfStates;i++) {
			colors[i] = p.color(p.random(255),p.random(255),p.random(255));
		}
	}

	public void setStateColors(int[] colors)
	{
		this.colors = colors;
	}

	public int[] getStateColors()
	{
		return colors;
	}

	public int getCellWidth()
	{
		return w;
	}

	public int getCellHeight()
	{
		return h;
	}

	public int getNumberOfStates()
	{
		return numberOfStates;
	}

	public Cell getCell(int x, int y)
	{
		int row = y/h;
		int col = x/w;
		if (row >= nrows) row = nrows - 1;
		if (col >= ncols) col = ncols - 1;
	
		return cells[row][col];
	}

	public void setRandomStates() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].setState((int) p.random(numberOfStates));
			}
		}
	}

	public void setRandomStatesCustom(double[] pmf) {
		CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].setState(crg.getRandomClass());
			}
		}
	}
	
	public void setRocksStates() {
		int[] nColunaX = {0, 1, 2, 4, 5, 6, 8, 9, 9, 10, 11, 12, 11, 11}; 
		int[] nColunaY = {13, 10, 12, 10, 11, 9, 8, 7, 5, 2, 0, 2, 4, 6};
		
		for(int i=0; i<nColunaX.length; i++) {
			int x = nColunaX[i];
			int y = nColunaY[i];
			cells[x][y].setState(State.ROCK.ordinal());
		}
	}
	
	public void setRuralStates() {
		for(int i = 22; i<25; i++) {
			for(int j = 5; j<29; j++) {
				cells[i][j].setState(State.RURAL.ordinal());
			}
		}
		
		for(int i = 25; i<30; i++) {
			for(int j = 5; j<12; j++) {
				cells[i][j].setState(State.RURAL.ordinal());
			}
		}
		
		for(int i = 15; i<25; i++) {
			for(int j = 29; j<32; j++) {
				cells[i][j].setState(State.RURAL.ordinal());
			}
		}
		
		for(int i = 7; i<15; i++) {
			for(int j = 29; j<35; j++) {
				cells[i][j].setState(State.RURAL.ordinal());
			}
		}
		
		for(int i = 7; i<11; i++) {
			for(int j = 35; j<40; j++) {
				cells[i][j].setState(State.RURAL.ordinal());
			}
		}
		
		for(int i = 5; i<10; i++) {
			for(int j = 21; j<29; j++) {
				cells[i][j].setState(State.RURAL.ordinal());
			}
		}
		
		for(int i = 10; i<12; i++) {
			for(int j = 14; j<29; j++) {
				cells[i][j].setState(State.RURAL.ordinal());
			}
		}
		
		for(int i = 5; i<10; i++) {
			for(int j = 14; j<21; j++) {	
				if(j<21 && i<10 && i>=((double)-5/7*j) +19) {
					cells[i][j].setState(State.RURAL.ordinal());		
				}
			}	
		}
		
		for(int i = 10; i<22; i++) {
			for(int j = 5; j<14; j++) {	
				if(j<14 && i<22 && i>=((double)-3/4*j) + 19) {
					cells[i][j].setState(State.RURAL.ordinal());		
				}
			}	
		}
		
		for(int i = 10; i<22; i++) {
			for(int j = 14; j<29; j++) {
				cells[i][j].setState(State.RURAL.ordinal());
			}
		}
		
		cells[9][13].setState(State.RURAL.ordinal());		
		
		
	}
	
	public int countMarketStates() {
		buildingCount=0;
		for (int i = 10; i < 22; i++) {
			for (int j = 14; j < 29; j++) {
				if(cells[i][j].getState()==State.MARKET.ordinal()) {
					buildingCount++;
				}

			}
		}
		return buildingCount;
	}
	
	public void setGrassStates() {
		for(int i = 11; i<30; i++) {
			for(int j = 35; j<40; j++) {
				cells[i][j].setState(State.GRASS.ordinal());
			}
		}	
		
		for(int i = 15; i<30; i++) {
			for(int j = 32; j<35; j++) {
				cells[i][j].setState(State.GRASS.ordinal());
			}
		}	
		
		for(int i = 25; i<30; i++) {
			for(int j = 11; j<32; j++) {
				cells[i][j].setState(State.GRASS.ordinal());
			}
		}	
			
	}
	
	public void setWaterStates() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].setState(State.WATER.ordinal());
			}
		}
	}
	public void setMarketStates() {
		cells[26][12].setState(State.GRASS.ordinal());
		cells[26][13].setState(State.GRASS.ordinal());
		cells[27][14].setState(State.GRASS.ordinal());
		
		cells[29][20].setState(State.GRASS.ordinal());
		cells[27][17].setState(State.GRASS.ordinal());
		cells[28][21].setState(State.GRASS.ordinal());
		
		
		cells[12][38].setState(State.GRASS.ordinal());
		cells[12][37].setState(State.GRASS.ordinal());
		cells[13][36].setState(State.GRASS.ordinal());
		
		
		cells[27][35].setState(State.GRASS.ordinal());
		cells[26][36].setState(State.GRASS.ordinal());
		cells[25][37].setState(State.GRASS.ordinal());
	}

	public void display()
	{
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].display(p);
			}
		}
	}
}