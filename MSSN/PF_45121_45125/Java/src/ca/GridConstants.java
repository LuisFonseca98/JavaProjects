package ca;

public class GridConstants {
	public final static int NROWS = 30;
	public final static int NCOLS = 40;
	public static enum State {ROCK, WATER, RURAL,GRASS, SEAWEED,MARKET,SOLD,GRASSFOOD}
	
	public final static String[] imgPath = {
								"images\\rock.png",
								"images\\water.png",
								"images\\rural.png",
								"images\\grass.jpg",
								"images\\seaweed.png",
								"images\\mercado.png",
								"images\\sold.png",
								"images\\grassFood.png"
								};
	
	public final static int NSTATES = State.values().length;
}