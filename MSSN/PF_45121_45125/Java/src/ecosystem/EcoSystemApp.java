package ecosystem;

import Menus.Informações;
import Menus.MainMenu;
import Menus.Options;
import Menus.UserInterface;
import processing.IProcessingApp;
import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;


public class EcoSystemApp implements IProcessingApp
{
	private World world;
	private int alpha;
	private MainMenu mainMenu;
	private Options options;
	private Informações informacoes;
	private UserInterface userInterface;

	private int gameState = 0;
	PImage imgMM, imgOptions, imgInfo, img3, img4, img5, img6, img7;
		
	@Override
	public void setup(PApplet p) 
	{
		new WorldConstants(p);
		world = new World(p);
		alpha= world.terrain.countMarketStates();

		imgMM = p.loadImage("images//savana.png");
		imgOptions = p.loadImage("images//options.jpg");
		imgInfo = p.loadImage("images//infos.jpg");
		
		mainMenu = new MainMenu(imgMM);
		options = new Options(p,imgOptions);
		informacoes = new Informações(p, imgInfo);
		userInterface = new UserInterface(p);
		
	}

	@Override
	public void draw(PApplet p, float dt) 
	{
		if (gameState == 0) {
			mainMenu.display(p);
		} else if (gameState == 1) {
			userInterface.display(p, dt);
		} else if (gameState == 2) {
			options.display(p);
		}else if(gameState == 3) {
			informacoes.display(p);
		}
	
	}
	
	@Override
	public void keyPressed(PApplet p) {}

	@Override
	public void mousePressed(PApplet p)
	{	
		if (gameState == 0 && (p.mouseX > p.width/2 - 110 && p.mouseX < p.width/2 - 110 + 300) && (p.mouseY > p.height/2-200 && p.mouseY < p.height/2-200 + 100)) {
			gameState = 1;
		}

		if (gameState == 0 && (p.mouseX > p.width/2 - 110 && p.mouseX < p.width/2 - 110 + 300) && (p.mouseY > p.height/2 && p.mouseY < p.height/2 + 100)) {
			gameState = 2;
		}

		if (gameState == 0 && (p.mouseX > p.width/2 - 110 && p.mouseX < p.width/2 - 110 + 300) && (p.mouseY > p.height/2+ 200 && p.mouseY < p.height/2+ 200 + 100)) {
			gameState = 3;
		}

		if ((gameState == 2 || gameState == 3) && (p.mouseX > 50 && p.mouseX < 50 + 100)
				&& (p.mouseY > 50 && p.mouseY < 50 + 100)) {
			gameState = 0;
		}
		
	}
	
}
