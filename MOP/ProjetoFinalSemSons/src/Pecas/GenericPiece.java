package Pecas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Layouts.ProportionalLayout;


public abstract  class GenericPiece extends Place{

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private String nome;
	private Direcoes entrada;
	private Direcoes saida;

	public GenericPiece(String nome,int x, int y, Direcoes entrada, Direcoes saida) {
		
		super(x, y);
		this.nome = nome;
		this.entrada = entrada;
		this.saida= saida;
		piecePicture(this,nome);
	}

	
	public void piecePicture(JLabel panel,String nome) {
		
		panel.setLayout(new ProportionalLayout(0,0,0,0));
		JLabel label = new JLabel(nome,SwingConstants.CENTER);
		label.setFont(new Font("OCR A Extended",Font.BOLD,22));
		label.setForeground(new Color(255,0,0));
		label.setOpaque(true);
		
		panel.add(label);	
	}

	
	public String getNome() {
		return this.nome;
	}	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public Direcoes getEntrada() {
		return this.entrada;
	}
	
	public void setEntrada(Direcoes entrada) {
		this.entrada = entrada;
	}
	
	public Direcoes getSaida() {
		return this.saida;
	}
	
	public void setSaida(Direcoes saida) {
		this.saida = saida;
	}
	
	
	@Override
	public boolean canMoveTo(int x, int y) {
		return false;
	}

	
	public boolean isConnected(GenericPiece pc) {
		
		boolean crossP = pc.getNome().equals("cross");
		
		int xi = this.getyBoard();
		int yi = this.getxBoard();
		int xp = pc.getyBoard();
		int yp = pc.getxBoard();

		int xcom = Math.abs(xi-xp);
		int ycom = Math.abs(yi-yp);
		
		if(pc.getNome().equals("empty"))return false;
		
		if(!(xcom == 0 && ycom <= 1 || ycom == 0 && xcom <= 1)) return false;

		Direcoes iEn = this.getEntrada();
		Direcoes iEx = this.getSaida();
		Direcoes pEn = pc.getEntrada();
		Direcoes pEx = pc.getSaida();
		
		if(crossP) return true;
		
		if(Math.abs(xi-xp) == 1) {
			if(xi-xp > 0) { //peça está à esquerda
				if(pEn == Direcoes.East || pEx == Direcoes.East) {
					if(iEn == Direcoes.West || iEx == Direcoes.West) return true;
				}
			}
			else { // peça está à direita
				if(pEn == Direcoes.West || pEx == Direcoes.West) {
					if(iEn == Direcoes.East || iEx == Direcoes.East) return true;
				}
			}
		}
		else {
			if(yi-yp > 0) { // peça está em cima
				if(pEn == Direcoes.South || pEx == Direcoes.South) {
					if(iEn == Direcoes.North || iEx == Direcoes.North) return true;
				}
			}
			else { // peça está em baixo
				if(pEn == Direcoes.North || pEx == Direcoes.North) {
					if(iEn == Direcoes.South || iEx == Direcoes.South) return true;
				}
			}
		}
		return false;
	}
	
	
	public void prettyPrint() {
		System.out.println(this.nome + " | " + this.getxBoard() + " " + this.getyBoard() + " | " + this.getEntrada() + " " + this.getSaida());
	}
	
	
	public abstract GenericPiece rotate();
	
	public abstract ImageIcon pickPic();

	public abstract boolean isFull();
	
	public abstract void setStartPaint(boolean startPaint);
	
	public abstract void setIsEntrada(boolean isEntrada);
	
	public abstract boolean getIsEntrada();
	
	@Override
	public abstract boolean isUsable();
	
	public abstract void setUsable(boolean usable);

	
}
