package ecosystem;

import java.util.ArrayList;
import ca.GridConstants;
import processing.core.PApplet;
import processing.core.PImage;

public class WorldConstants {
	// Terrain
	public final static float[] REGENERATION_TIME = {5.f, 15.f}; //seconds
	public final static float OBSTACLE_PENALTY_FACTOR = 25f;
	public static int[] TERRAIN_COLORS;
	
	// Global Population (all rates are per second)
	public final static float IMMIGRATION_FLOW = 0.f;
	public final static float EMIGRATION_FLOW = 0.f;
	
	// Prey Population (all rates are per second)
	public final static int INI_PREY_POPULATION = 1002;
	public final static float INI_PREY_ENERGY = 100f;
	public final static float ENERGY_FROM_PLANT = 25f;
	public final static float ENERGY_FROM_GRASS = 8f;
	
	public static int PREY_COLOR;
	public static int CLEVER_PREY_COLOR;
	public static int FLOCK_PREY_COLOR;
	
	// Deterministic version
	public final static float PREY_ENERGY_TO_REPRODUCE = 100f;
	
	// Stochastic version
	public final static float[] PREY_BIRTH_RATE = {0.02f, 0.5f};
	public final static float PREY_BIRTH_FACTOR = 0.03f;
	public final static float PREY_DEATH_RATE = 0.15f;
	
	// Predator Population (all rates are per second)
	public final static int INI_PREDATOR_POPULATION = 0;
	public final static float INI_PREDATOR_ENERGY = 100f;
	public final static float ENERGY_FROM_PREY = 10f;
	public static int PREDATOR_COLOR;
	
	// Deterministic version
	public final static float PREDATOR_ENERGY_TO_REPRODUCE = 200f;
	
	// Stochastic version
	public final static float[] PREDATOR_BIRTH_RATE = {0.02f, 0.5f};
	public final static float PREDATOR_BIRTH_FACTOR = 0.03f;
	public final static float PREDATOR_DEATH_RATE = 0.15f;
	
	// Simulation mode
	public final static boolean STOCHASTIC = false;
	
	//
	public static ArrayList<int[]> pontosAtracao = new ArrayList<int[]>();
	
	// 
	public static PImage CROC_BEAR_IMG;
	public static PImage PLANE_IMG;
	public static PImage CACADOR_IMG;
	public static PImage FISH_IMG;
	public static PImage CEGONHA_IMG;
	public static PImage MEAT_IMG;
	public static PImage SKIN_IMG;
	public static PImage GAZELA_IMG;
	public static PImage LION_IMG;

	
	
	public WorldConstants(PApplet p)
	{
		int[] ponto1 = {20, 8};
		pontosAtracao.add(ponto1);

		int[] ponto2 = {7, 23};
		pontosAtracao.add(ponto2);
		
		int[] ponto3 = {12, 31};
		pontosAtracao.add(ponto3);
		
		
		PREY_COLOR = p.color(0,0,255);
		CLEVER_PREY_COLOR = p.color(255);
		FLOCK_PREY_COLOR = p.color(255,255,0);
		PREDATOR_COLOR = p.color(255,0,0);
		
		TERRAIN_COLORS = new int[GridConstants.NSTATES];
		
		// ROCK
		TERRAIN_COLORS[1] = p.color(127, 127, 127);
		
		// WATER
		TERRAIN_COLORS[2] = p.color(117, 204, 250);
		
		// RURAL
		TERRAIN_COLORS[3] = p.color(222, 163, 87);
		
		
		CROC_BEAR_IMG = p.loadImage("images\\croc.png");
		PLANE_IMG = p.loadImage("images\\plane.png");
		CACADOR_IMG = p.loadImage("images\\cacador.png");
		FISH_IMG = p.loadImage("images\\fish.png");
		CEGONHA_IMG = p.loadImage("images\\cegonha.png");
		MEAT_IMG = p.loadImage("images\\meat.png");
		SKIN_IMG = p.loadImage("images\\skin.jpg");
		GAZELA_IMG = p.loadImage("images\\gazela.png");
		LION_IMG = p.loadImage("images\\lion.png");

	}
}
