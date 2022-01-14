package Pecas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import Layouts.ProportionalLayout;

public class PipeSink extends GenericPiece implements ActionListener {

	/**
	 * 
	 */
	private boolean full, startPaint, isEntrada, usable;

	private int index = 0;
	private Timer timer;
	private static final long serialVersionUID = 1L;

	public PipeSink(String nome, int x, int y, Direcoes entrada, Direcoes saida) {
		super(nome, x, y, entrada, saida);
		piecePicture(this, nome);
		drawPiece(this);
		this.full = false;
		this.usable = false;
		timer = new Timer(100, this);
		timer.start();
		this.startPaint = false;
	}

	@Override
	public boolean canMoveTo(int x, int y) {
		return false;
	}


	public static GenericPiece createSink() {
		Random rn = new Random();
		int dir = rn.nextInt(3);
		Direcoes saida = Direcoes.values()[dir];
		Direcoes entrada = Direcoes.values()[dir];
		PipeSink Sink = new PipeSink("sink", 0, 0, entrada, saida);
		return Sink;
	}

	
	public void drawPiece(JLabel panel) {

		panel.setLayout(new ProportionalLayout(0,0,0,0));
		JLabel label = new JLabel();
		label.setOpaque(true);
		panel.add(label);
	}

	
	@Override
	public GenericPiece rotate() {
		return null;
	}

	
	@Override
	public boolean isUsable() {
		return usable;
	}
	

	@Override
	public ImageIcon pickPic() {
		switch(this.getEntrada()){
        case North:
            	return new ImageIcon("src\\images\\sink.png");
        case East:
            	return new ImageIcon("src\\images\\sink2.png");
        case South:
            	return new ImageIcon("src\\images\\sink3.png");
        case West:
            	return new ImageIcon("src\\images\\sink4.png");
        default:
            return null;
		}
	}
	

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Image image = this.pickPic().getImage();

		final int x = this.getxBoard();
		final int y = this.getyBoard();
		
		if (index < 110 && this.startPaint) {
			this.setUsable(false);
			g.setColor(new Color(45,125,216));

			switch (this.getEntrada()) {
			case North:
				g.fillRect(0,0,110,index);
				break;
			case East:
				g.fillRect(0,0,110,110);
				g.setColor(Color.WHITE);
				g.fillRect(0,0,110-index,110);
				break;
			case South:
				g.fillRect(0,0,110,110);
				g.setColor(Color.WHITE);
				g.fillRect(0,0,110,110-index);
				break;
			case West:
				g.fillRect(0,0,index,110);
				
				break;
			}
			index ++;
		}
		if(index >= 110) {
			full = true;
			g.setColor(new Color(45,125,216));
			g.fillRect(x,y,110,110);
			g.drawImage(image, 0, 0, null);
		}
		else {
			full = false;
			g.drawImage(image, 0, 0, null);
		}
	}	
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==timer){	
			repaint();
		}
	}


	@Override
	public boolean isEmpty() {
		return false;
	}

	
	@Override
	public boolean isFull() {
		return full;
	}

	
	@Override
	public void setStartPaint(boolean startPaint) {
		this.startPaint = startPaint;
	}

	
	@Override
	public void setIsEntrada(boolean isEntrada) {
		this.isEntrada = isEntrada;
	}

	
	@Override
	public boolean getIsEntrada() {
		return isEntrada;
	}

	
	@Override
	public void setUsable(boolean usable) {
		this.usable = usable;
	}
}
