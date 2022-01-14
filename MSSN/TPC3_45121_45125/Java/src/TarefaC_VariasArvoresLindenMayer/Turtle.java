package TarefaC_VariasArvoresLindenMayer;

import processing.core.PApplet;

public class Turtle {
	
	private PApplet parent;
	private SubPlot plot;
	private float len;
	private float theta;
	
	public Turtle(PApplet parent,SubPlot plot,float step,float theta,boolean horizontal) {
		this.parent = parent;
		this.plot = plot;
		double[] window = plot.getWindow();
		float[] viewport = plot.getViewport();
		if(horizontal)
			len = step/(float)(window[1] - window[0]) * parent.width * viewport[2];
		else
			len = step/(float)(window[3]-window[2]) * parent.height * viewport[3];
		this.theta = theta;
	}
	
	public void setPose(double[] position,float orientation) {
		float[]c = plot.getPixelCoord(position);
		parent.translate(c[0], c[1]);
		parent.rotate(-orientation);
	}
	
	public void scaling(float s) {
		len *= s;
	}
	
	public void render(LSystem lsystem) {
		parent.stroke(1);
		for(int i = 0; i < lsystem.getSequence().length();i++) {
			char c = lsystem.getSequence().charAt(i);
			if(c == 'F' || c == 'G') {
				parent.line(0, 0, len, 0);
				parent.translate(len, 0);
			}
			else if(c == 'f')parent.translate(len, 0);
			else if(c == '+')parent.rotate(theta);
			else if(c == '-')parent.rotate(-theta);
			else if(c == '[')parent.pushMatrix();
			else if(c == ']')parent.popMatrix();

		}
	}

}
