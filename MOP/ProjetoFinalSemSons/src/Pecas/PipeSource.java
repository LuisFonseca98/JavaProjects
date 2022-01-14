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

public class PipeSource extends GenericPiece implements ActionListener{

	private Timer timer;
	private int index = 0;
	private boolean full, usable;
	private static final long serialVersionUID = 1L;

	
	public PipeSource(String nome, int x, int y, Direcoes entrada, Direcoes saida) {
		
		super(nome, x, y, entrada, saida);
		piecePicture(this, nome);
		drawPiece(this);
		this.full = false;
		this.usable = false;
		timer = new Timer(100, this);
		timer.start();
	}

	
	@Override
	public boolean canMoveTo(int x, int y) {
		return false;
	}

	
	public static GenericPiece createSource() {
		
		Random rn = new Random();
		int dir = rn.nextInt(3);
		Direcoes saida = Direcoes.values()[dir];
		Direcoes entrada = Direcoes.values()[dir];
		PipeSource Source = new PipeSource("source", 0, 0, entrada, saida);
		return Source;
	}
	

	public void drawPiece(JLabel panel) {

		panel.setLayout(new ProportionalLayout(0, 0, 0, 0));
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
		switch (this.getSaida()) {
		case North:
			return new ImageIcon("src\\images\\source2.png");
		case East:
			return new ImageIcon("src\\images\\source3.png");
		case South:
			return new ImageIcon("src\\images\\source4.png");
		case West:
			return new ImageIcon("src\\images\\source.png");
		default:
			return null;
		}
	}

	
	@Override
	public boolean isEmpty() {
		return false;
	}


	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Image image = this.pickPic().getImage();
		
		final int x = this.getxBoard();
		final int y = this.getyBoard();
		
		if (index < 110) {
			this.setUsable(false);
			g.setColor(new Color(45,125,216));

			switch (this.getSaida()) {
			case North:
				g.fillRect(0,0,110,110);
				g.setColor(Color.WHITE);
				g.fillRect(0,0,110,110-index);
				break;
			case East:
				g.fillRect(0,0,index,110);
				break;
			case South:
				g.fillRect(0,0,110,index);
				break;
			case West:
				g.fillRect(0,0,110,110);
				g.setColor(Color.WHITE);
				g.fillRect(0,0,110-index,110);
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


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==timer){	
			repaint();
		}
	}

	
	@Override
	public boolean isFull() {
		return this.full;
	}

	
	@Override
	public void setStartPaint(boolean startPaint) {
	}

	@Override
	public void setIsEntrada(boolean isEntrada) {
	}

	@Override
	public boolean getIsEntrada() {
		return false;
	}
	

	@Override
	public void setUsable(boolean usable) {
		this.usable = usable;
	}
}
