package Menus;

import processing.core.PApplet;
import processing.core.PImage;
public class MainMenu {
	
	private PImage image;	
	
	public MainMenu(PImage image) {
		this.image = image;
		
	}
	
	public void display(PApplet p) {
		
		p.image(image, 0, 0, p.width, p.height);
		p.fill(255, 69, 0);
		p.textSize(115);
		String titleGame = new String("Savana");
		p.text(titleGame, p.width/2-145, p.height/2-340);

		p.fill(255, 69, 0);
		p.rect(p.width/2 - 110, p.height/2-200, 300, 100);
		p.fill(0,0,0);
		p.textSize(35);
		String jogar = new String("Jogar");
		p.text(jogar, p.width/2 - 10, p.height/2-135);

		p.fill(255, 69, 0);
		p.rect(p.width/2 - 110, p.height/2, 300, 100);
		p.fill(0,0,0);
		p.textSize(35);
		String instrucoes = new String("Opções");
		p.text(instrucoes, p.width/2 - 25, p.height/2 + 65);
		
		p.fill(255, 69, 0);
		p.rect(p.width/2 - 110, p.height/2+ 200, 300, 100);
		p.fill(0,0,0);
		p.textSize(35);
		String about = new String("About");
		p.text(about, p.width/2 - 20, p.height/2+ 265);
	}
	
}
