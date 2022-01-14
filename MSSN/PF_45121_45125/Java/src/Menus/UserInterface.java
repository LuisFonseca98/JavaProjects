package Menus;

import ecosystem.World;
import ecosystem.WorldConstants;
import processing.core.PApplet;

public class UserInterface {
	
	private World world;
	public UserInterface(PApplet p) {
		world = new World(p);
		
	}
	
	
	public void display(PApplet p,float dt) {
		
		p.background(184, 182, 176);
		p.textSize(26);
		p.text("User Interface", p.width - 188, 45);
		
		p.textSize(16);
		p.text("Tipo", p.width - 180, 110);
		
		p.image(WorldConstants.FISH_IMG, p.width - 180, 140, 40, 40);
		p.image(WorldConstants.PLANE_IMG, p.width - 195, 190, 80, 80);
		p.image(WorldConstants.CROC_BEAR_IMG, p.width - 180, 300, 40, 40);
		p.image(WorldConstants.CACADOR_IMG, p.width - 170, 375, 40, 40);
		p.image(WorldConstants.GAZELA_IMG, p.width - 178, 435, 40, 40);
		p.image(WorldConstants.LION_IMG, p.width - 178, 500, 40, 40);
		p.image(WorldConstants.CEGONHA_IMG, p.width - 178, 550, 40, 40);

		p.image(WorldConstants.MEAT_IMG, p.width - 180, 640, 40, 40);
		p.image(WorldConstants.SKIN_IMG, p.width - 180, 700, 40, 40);
		
		p.text("Quantidade", p.width - 110, 110);
		
		p.text("" + world.population.getNumberOfFishes(), p.width - 80, 165);
		p.text("" + world.population.getNumberOfPlanes(), p.width - 80, 235);
		p.text("" + world.population.getNumberOfCrocs(), p.width - 80, 325);
		p.text("" + world.population.getNumberOfCacadores(), p.width - 80, 400);
		p.text("" + world.population.getNumberOfGazelas(), p.width - 80, 465);
		p.text("" + world.population.getNumberOfLions(), p.width - 80, 525);
		p.text("" + world.population.getNumberOfCegonhas(), p.width - 80, 575);

		p.textSize(26);
		p.text("Recursos", p.width - 167, 620);
				
		p.textSize(16);
		p.text("" + world.getTerrain().getMeat(), p.width - 80, 665);
		p.text("" + world.getTerrain().getLeather(), p.width - 80, 720);
		
		p.textSize(24);
		p.text("Salvar Espécies", p.width - 188, 790);
		p.image(WorldConstants.FISH_IMG, p.width - 120, 800, 40, 40);
		p.image(WorldConstants.CEGONHA_IMG, p.width - 120, 860, 40, 40);
		p.image(WorldConstants.CROC_BEAR_IMG, p.width - 120, 930, 40, 40);
		p.image(WorldConstants.GAZELA_IMG, p.width - 120, 980, 40, 40);
		p.image(WorldConstants.LION_IMG, p.width - 120, 1040, 40, 40);
		
		world.update(dt);
		world.display();
		
		
		if (p.mousePressed && p.mouseX > p.width - 120 && p.mouseX < p.width - 80 && 
			      p.mouseY > 810 && p.mouseY < 860) {
			world.population.saveFish();
		} 
		
		if (p.mousePressed && p.mouseX > p.width - 120 && p.mouseX < p.width - 80 && 
			      p.mouseY > 870 && p.mouseY < 930) {
			world.population.saveCegonha();
		} 
		
		if (p.mousePressed && p.mouseX > p.width - 120 && p.mouseX < p.width - 80 && 
			      p.mouseY > 940 && p.mouseY < 990) {
			world.population.saveCrocodilo();
		} 
		
		if (p.mousePressed && p.mouseX > p.width - 120 && p.mouseX < p.width - 80 && 
			      p.mouseY > 990 && p.mouseY < 1040) {
			world.population.saveGazela();
		} 
		
		if (p.mousePressed && p.mouseX > p.width - 120 && p.mouseX < p.width - 80 && 
			      p.mouseY > 1050 && p.mouseY < 1100) {
			world.population.saveLion();
		} 
	}

}
