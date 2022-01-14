package Pecas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import Layouts.ProportionalLayout;

public class PipeCross extends GenericPiece implements ActionListener {
	
	private boolean horizontal, full, startPaint, isEntrada, usable;
	private int index = 0;
	private Timer timer;
	private static final long serialVersionUID = 1L;
	
	public PipeCross(String nome, int x, int y, Direcoes entrada, Direcoes saida) {
		super(nome, x, y, entrada, saida);
		piecePicture(this,nome);
		drawPiece(this);
		this.horizontal = true;
		this.full = false;
		this.usable = true;
		timer = new Timer(100, this);
		timer.start();
		this.startPaint = false;	
	}

	
	@Override
	public boolean canMoveTo(int x, int y) {
		return true;
	}


	public static GenericPiece[] createPieces() {
		int nPieces = 21;
		GenericPiece[] PipeCrossX =new GenericPiece[nPieces];
		for(int i = 0; i < nPieces; i++) {
			PipeCrossX[i] = new PipeCross("cross",0,0,Direcoes.East,Direcoes.West);
		}
		
		GenericPiece[] Pieces = new GenericPiece[nPieces];
		for(int i = 0; i < nPieces; i++) {
			Pieces[i] = PipeCrossX[i];
		}
		return Pieces;
	}

	
	public void drawPiece(JLabel panel) {

		panel.setLayout(new ProportionalLayout(0,0,0,0));
		JLabel label = new JLabel();
		label.setOpaque(true);
		panel.add(label);
	}
	
	
	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Image image = this.pickPic().getImage();

		final int x = this.getxBoard();
		final int y = this.getyBoard();
		
		Direcoes saida;
		saida = (getIsEntrada())?this.getSaida():this.getEntrada();

		if (index < 110 && this.startPaint) {
			this.setUsable(false);
			g.setColor(new Color(45,125,216));

			switch (saida) {
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
			g.drawImage(image,0, 0, 100,90, null);
		}
		else {
			full = false;
			g.drawImage(image, 0, 0,100,90, null);
		}
	}	
	
	//Cross tem de ser considerada horizontal ou vertical devido às conexões
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
		if(horizontal) {
			this.setEntrada(Direcoes.East);
			this.setSaida(Direcoes.West);
		}
		else {
			this.setEntrada(Direcoes.North);
			this.setSaida(Direcoes.South);
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==timer){	
			repaint();
		}
	}


	@Override
	public GenericPiece rotate() {
		return this;
	}


	@Override
	public boolean isUsable() {
		return usable;
	}

	
	@Override
	public ImageIcon pickPic() {
		return new ImageIcon("src\\images\\cross.png");
	}
	

	@Override
	public boolean isEmpty() {
		return false;
	}


	@Override
	public boolean isFull() {
		return this.full;
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
	
	
	public boolean isHorizontal() {
		return horizontal;
	}
}
