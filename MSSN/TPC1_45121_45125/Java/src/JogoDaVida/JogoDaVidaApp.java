package JogoDaVida;

import processing.core.PApplet;

public class JogoDaVidaApp implements IProcessingApp {

	private JogoDaVida jdv;
	private int nrows = 90;
	private int ncols = 120;
	private boolean paused;

	public void setup(PApplet p) {
		jdv = new JogoDaVida(p,nrows,ncols);
		jdv.initRandom(0.5f);
		p.frameRate(10);
		int[] colors = new int[2];
		colors[0] = p.color(255);
		jdv.setStateColors(colors);
	}

	@Override
	public void draw(PApplet parent, float dt) {
		if (!paused) {
			jdv.run();
		}
		jdv.display();
	}

	@Override
	public void keyPressed(PApplet p) {
		if(p.key== 'B'|| p.key == 'b') {
			jdv.reset();
			paused = true;
			PApplet.println("RESET");
		}
		if(p.key== 'P'|| p.key == 'p') {
			paused = !paused;
			if(paused) PApplet.println("PAUSED");
		}
	}

	@Override
	public void mousePressed(PApplet p) {
		((LifeCell)(jdv.getCell(p.mouseX, p.mouseY))).flipState();
	}
}
