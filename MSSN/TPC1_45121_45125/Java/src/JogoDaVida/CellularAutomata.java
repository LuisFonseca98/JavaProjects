package JogoDaVida;

import processing.core.PApplet;

public class CellularAutomata {

	protected int nrows,ncols;
	protected int w,h;
	protected Cell[][] cells;
	protected int radius;
	protected boolean moore;
	protected int numberofStates;
	protected int[] colors;
	protected PApplet p;
	public CellularAutomata(PApplet p, int nrows, int ncols, int radius, boolean moore, int numberofStates) {
		this.p = p;
		this.nrows = nrows;
		this.ncols = ncols;
		this.radius = radius;
		this.moore = moore;
		this.numberofStates = numberofStates;
		w = p.width/ncols;
		h = p.height/nrows;
		cells = new Cell[nrows][ncols];
		createGrid();
		colors = new int[numberofStates];
	}

	protected void createGrid() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j <ncols ; j++) {
				cells[i][j] = new Cell(this, i ,j);
			}
		}
		if(moore) setNeighbors();
		else setNeighbors4();
	}

	public Cell getCellGrid(int row,int col) {
		return cells[row][col];
	}

	protected void setNeighbors4() {
		int numberofNeighbors = 2*(radius^2+radius)+1;
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j <ncols ; j++) {
				int n = 0;
				Cell[] neigh = new Cell[numberofNeighbors];
				for(int ii = -radius; ii <= radius ; ii++) {
					for(int jj =-radius+Math.abs(ii) ; jj <= radius-Math.abs(ii); jj++) {
						int row = (i + ii + nrows) % nrows;
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbors(neigh);
			}
		}
	}

	protected void setNeighbors() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j <ncols ; j++) {
				Cell[] neigh = new Cell[(int)Math.pow(2*radius+1, 2)];
				int n = 0;
				for(int ii = -radius; ii <= radius ; ii++) {
					for(int jj =-radius ; jj <= radius; jj++) {
						int row = (i + ii + nrows) % nrows;
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbors(neigh);
			}
		}
	}

	public void reset() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols ; j++) {
				cells[i][j].setState(0);
			}
		}
	}

	public int getCellWidth() {
		return w;
	}

	public int getCellHeight() {
		return h;
	}
	public void setStateColors(int[] colors) {
		this.colors = colors;
	}
	public int[] getStateColors() {
		return this.colors;
	}

	public int getNumberOfStates() {
		return numberofStates;
	}

	public Cell getCell(int x, int y) {
		int row = y/h;
		int col = x/w;
		if(row >= nrows) row = nrows-1;
		if(col >= ncols) col = col-1;
		return cells[row][col];
	}

	public void setRandomState() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols ; j++) {
				cells[i][j].setState((int) p.random(numberofStates));
			}
		}
	}
	public void setRandomStateCustom(double[] pmf) {
		CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols ; j++) {
				cells[i][j].setState(crg.getRandomClass());
			}
		}
	}

	public void display() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols ; j++) {
				cells[i][j].display(this.p);
			}
		}
	}
}