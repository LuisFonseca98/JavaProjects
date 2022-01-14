package ecosystem;

import java.util.ArrayList;
import java.util.Random;

import ca.GridConstants;
import ca.GridConstants.State; 

public class RuralManager {
	
	private int population;

	private int lastPopulation;
	public int nMarkets;

	public ArrayList<Integer> ruralI, ruralJ, marketI,marketJ;
	
	
	public RuralManager() {
		
		ruralI = new ArrayList<Integer>();
		ruralJ = new ArrayList<Integer>();
		marketI = new ArrayList<Integer>();
		marketJ = new ArrayList<Integer>();
		
		this.population = 0;
		this.lastPopulation = 0;
		this.nMarkets = 0;
	}

	/*public void fillRural(Terrain terrain) {
		for (int i = 0; i < GridConstants.NROWS; i++) {
			for (int j = 0; j < GridConstants.NCOLS; j++) {
				if(terrain.getCellGrid(i, j).getState() == State.RURAL.ordinal()) {
					ruralI.add(i);
					ruralJ.add(j);
				}
			}
		}	
		
	}*/
	
	/*public void fillMarket(Terrain terrain) {
		for (int i = 0; i < GridConstants.NROWS; i++) {
			for (int j = 0; j < GridConstants.NCOLS; j++) {
				if(terrain.getCellGrid(i, j).getState() == State.MARKET.ordinal()) {
					marketI.add(i);
					marketJ.add(j);
				}
			}
		}	
	}*/
	
	public void buildMarket(Terrain terrain) {
		
		nMarkets++;
		
		if(ruralI.size() > 0) {
			Random r = new Random();
			int rdm = r.nextInt(( (ruralI.size()-1) - 0) + 1) + 0;
			
			int i = ruralI.get(rdm);
			int j = ruralJ.get(rdm);
			
			if(terrain.getCellGrid(i, j).getState() == State.RURAL.ordinal()) {
					terrain.getCellGrid(i, j).setState(State.MARKET.ordinal());
			}	
			
			ruralI.remove(rdm);
			ruralJ.remove(rdm);
			marketI.add(i);
			marketJ.add(j);
		}	
	}
	
	public void update(float dt, Terrain terrain) {		
		
		
		if(population > lastPopulation && population % 5 == 0 && ruralI.size() > 0) {	
			
			Random r = new Random();
			int rdm = r.nextInt(( (ruralI.size()-1) - 0) + 1) + 0;
			
			int i = ruralI.get(rdm);
			int j = ruralJ.get(rdm);
			
			if(terrain.getCellGrid(i, j).getState() == State.RURAL.ordinal()) {
					terrain.getCellGrid(i, j).setState(State.MARKET.ordinal());
			}	
			
			ruralI.remove(rdm);
			ruralJ.remove(rdm);
			marketI.add(i);
			marketJ.add(j);
			
		}
		
		
		if(population < lastPopulation && population % 5 == 0 && marketI.size()>0) {	
			
			Random r = new Random();
			int rdm = r.nextInt(( (marketI.size()-1) - 0) + 1) + 0;
			
			int i = marketI.get(rdm);
			int j = marketJ.get(rdm);
			
			
			if(terrain.getCellGrid(i, j).getState() == State.RURAL.ordinal()) {
					terrain.getCellGrid(i, j).setState(State.MARKET.ordinal());
			}	
			
			
			marketI.remove(rdm);
			marketJ.remove(rdm);
			ruralI.add(i);
			ruralJ.add(j);
			
			
		}
	
		lastPopulation = population;
		
	}
	
	
	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
	
	public int getnFarms() {
		return nMarkets;
	}

	public void setnFarms(int nFarms) {
		this.nMarkets = nFarms;
	}
	

}
