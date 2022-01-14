package Menus;

import processing.core.PApplet;
import processing.core.PImage;

public class Informa��es {
	
	private PApplet p;
	private PImage image,back;

	public Informa��es(PApplet p, PImage image) {
		this.p = p;
		this.image = image;
		back = p.loadImage("images//back.jpg");
	}


	public void display(PApplet p) {
		
		p.image(image, 0, 0, p.width, p.height);
		p.fill(0,0,0);
		p.text("Sobre n�s:", 150, 80);
		p.text("+ Paulo Jorge, A45121 aluno no curso de LEIM.", 200, 110);
		p.text("+ Luis Fonseca, A45125 aluno no curso de LEIM.", 200, 155);
		
		p.text("Sobre o Jogo:", 50, 220);
		p.text("- Ecossitema terrestre, e uma por��o aqu�tica, constituida por diferentes especi�s de presas e predadores.", 50, 260);
		p.text("- Se o predador colidir com a presa esta �ltima � consumida.", 50, 300);
		p.text("- Para acrescentar um nivel de complexidade foram tamb�m adicionados obst�culos, \n e intera��o com o utilizador.", 50, 350);
		
		p.image(back, 50, 50);
		back.resize(50, 50);

	}

}
