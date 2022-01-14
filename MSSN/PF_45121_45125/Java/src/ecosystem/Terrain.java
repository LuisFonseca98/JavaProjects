package ecosystem;

import java.util.ArrayList;

import ca.GridConstants;
import ca.Minefield;
import ca.GridConstants.State;
import processing.core.PApplet;
import processing.core.PVector;

public class Terrain extends Minefield {
	
	private int mouseX;
	private int tamanho_colunas;
	public int nMarketsToBuild;
	public int leather,meat; 

	public Terrain(PApplet p) {
		super(p, GridConstants.NROWS, GridConstants.NCOLS, 1, true, GridConstants.NSTATES);
		tamanho_colunas= p.width-180;
	}

	@Override
	protected void createGrid() {
		int minRT = (int) (WorldConstants.REGENERATION_TIME[0] * 1000);
		int maxRT = (int) (WorldConstants.REGENERATION_TIME[1] * 5000); // 1000 EM AMBOS
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				int timeToGrow = (int) (minRT + (maxRT - minRT) * Math.random());
				cells[i][j] = new Patch(this, i, j, timeToGrow);
			}
		}
		setNeighbors();
	}

	public void regenerateCellWithFood() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				((Patch) cells[i][j]).regenerateFood();
			}
		}
	}

	public void clickMarket() {

		if (p.mousePressed && p.mouseX <= tamanho_colunas) {
			mouseX = p.mouseX;
			if (((Patch) cells[(int) (nrows * p.mouseY / p.height)][(int) (ncols * mouseX / tamanho_colunas)])
					.getState() == State.MARKET.ordinal()) {
				((Patch) cells[(int) (nrows * p.mouseY / p.height)][(int) (ncols * mouseX / tamanho_colunas)])
						.soldMarket();
				leather += 100;
				meat += 50;
				
				if(leather == 300 && meat == 150) {
					leather -= 300;
					meat -= 150;
					nMarketsToBuild += 1;
				}
			}
		}
	}

	public void setAnimalLists(ArrayList<Animal> animals) {
		for (Animal a : animals) {
			PVector pos = a.getPos();
			Patch patch = (Patch) getCell((int) pos.x, (int) pos.y);
			patch.addAnimal(a);
		}
	}

	void clearAnimalLists() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				((Patch) cells[i][j]).clearAnimalsList();
			}
		}
	}

	public int getnMarketsToBuild() {
		return nMarketsToBuild;
	}

	public void setnMarketsToBuild(int nMarketsToBuild) {
		this.nMarketsToBuild = nMarketsToBuild;
	}
	
	public int getLeather() {
		return leather;
	}

	public void setLeather(int leather) {
		this.leather = leather;
	}
	
	public int getMeat() {
		return meat;
	}
	
	public void setMeat(int meat) {
		this.meat = meat;
	}
	
	
	
}
