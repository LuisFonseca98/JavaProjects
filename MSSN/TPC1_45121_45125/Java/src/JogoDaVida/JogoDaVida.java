package JogoDaVida;

import processing.core.PApplet;

public class JogoDaVida extends CellularAutomata{
	public JogoDaVida(PApplet p, int nrows, int ncols) {
		super(p,nrows,ncols,1,true,2);

	}

	public void initRandom(float prob) {
		double[] pmf = new double[2];
		pmf[0] = 1-prob;
		pmf[1] = prob;
		setRandomStateCustom(pmf);
	}


	protected void createGrid() {
		for(int i = 0; i<nrows;i++) {
			for(int j = 0; j<ncols;j++) {
				cells[i][j]=new LifeCell(this,i,j);
			}
		}
		setNeighbors();
	}
	
	public void run() {
		LifeCell c;
		for(int i = 0; i<nrows;i++) {
			for(int j = 0; j<ncols;j++) {
				c = (LifeCell) cells[i][j];
				c.countAlives();
			}
		}
		for(int i = 0; i<nrows;i++) {
			for(int j = 0; j<ncols;j++) {
				c = (LifeCell) cells[i][j];
				c.applyRule();
			}
		}
	}
}

