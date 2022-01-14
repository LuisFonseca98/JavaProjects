package Menus;

import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;

public class Options {

	private PApplet p;
	private PImage image,back;
	private String turnON;
	private SoundFile sound;

	public Options(PApplet p, PImage image) {
		this.p = p;
		this.image = image;
		turnON = "Ligar Música!";
		back = p.loadImage("images//back.jpg");
		sound = new SoundFile(p,"images//ecosistema.wav");
	}


	public void display(PApplet p) {

		p.image(image, 0, 0, p.width, p.height);


		p.fill(255, 69, 0);
		p.rect(p.width / 2-300, p.height / 2 -100, 100, 100);

		p.fill(255,255,255);
		p.textSize(35);
		p.text(turnON, p.width / 2-180, p.height / 2 -47);

		Boolean isInsideButton = insideButton(p, p.width / 2-300, p.height / 2 -100, 100, 100);

		p.image(back, 50, 50);
		back.resize(50,50);

		if (isInsideButton) {
			sound.play();
			p.fill(255,255,0);
			p.rect(p.width / 2-300, p.height / 2 -100, 100, 100);
			p.fill(0,0,0);
		}
		
	}



	public boolean insideButton(PApplet p, int posx, int posy, int width, int height) {
		return (p.mouseX <= posx + width && p.mouseX > posx && p.mouseY <= posy + height&& p.mouseY > posy) ? true : false;
	}

}
