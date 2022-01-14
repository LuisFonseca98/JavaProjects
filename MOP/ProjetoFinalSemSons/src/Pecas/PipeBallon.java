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

public class PipeBallon extends GenericPiece implements ActionListener{
	
	private boolean full, startPaint, isEntrada, usable;
	private int index = 0;
	private Timer timer;
	private static final long serialVersionUID = 1L;

	public PipeBallon(String nome, int x, int y, Direcoes entrada, Direcoes saida) {
		super(nome, x, y, entrada, saida);
		piecePicture(this, nome);
		drawPiece(this);
		this.full = false;
		this.usable = true;
		timer = new Timer(2000, this);
		timer.start();
		this.startPaint = false;
	}

	@Override
	public boolean canMoveTo(int x, int y) {
		boolean b = true;

		return b;
	}

	public static GenericPiece[] createPieces() {
		Random rn = new Random();
		int nPieces = 21;
		GenericPiece[] PipeBallonX =new GenericPiece[nPieces];
		for(int i = 0; i < nPieces; i++) {
			int dir = rn.nextInt(3);
			Direcoes entrada = Direcoes.values()[dir];
			Direcoes saida;
			if(dir<2)saida = Direcoes.values()[dir+2];
			else saida = Direcoes.values()[dir-2];
			PipeBallonX[i] = new PipeBallon("ballon",0,0,entrada,saida);
		}
	
		GenericPiece[] Pieces = new GenericPiece[nPieces];
		for(int i = 0; i < nPieces; i++) {
			Pieces[i] = PipeBallonX[i];
		}
		return Pieces;
	}

	
	@Override
	public GenericPiece rotate() {

		// Obter Entrada e Saída
		Direcoes oEntrada = this.getEntrada();
		Direcoes oSaida = this.getSaida();

		// Obter indice da entrada e saida
		int vE = Direcoes.valueOf(oEntrada.name()).ordinal();
		int vS = Direcoes.valueOf(oSaida.name()).ordinal();

		// Novas entradas e saidas
		Direcoes nEntrada;
		Direcoes nSaida;
		
		if(vE < 3 && vS < 3) {
			nEntrada = Direcoes.values()[vE+1];
			nSaida = Direcoes.values()[vS+1];
		}
		else {
			if(vE >= 3) {
				nEntrada = Direcoes.values()[0];
				nSaida = Direcoes.values()[vS+1];
			}
			else {
				nEntrada = Direcoes.values()[vE+1];
				nSaida = Direcoes.values()[0];
			}
		}
		return new PipeBallon("ballon", 0,0, nEntrada, nSaida);
	} 

	
	public void drawPiece(JLabel panel) {

		panel.setLayout(new ProportionalLayout(0,0,0,0));
		JLabel label = new JLabel();
		label.setOpaque(true);
		panel.add(label);
	}
	

	@Override
	public ImageIcon pickPic() {
		switch(this.getEntrada()){
        case North:
            	return new ImageIcon("src\\images\\pipeBallon2.png");
        case East:
            	return new ImageIcon("src\\images\\pipeBallon.png");
        case South:
            	return new ImageIcon("src\\images\\pipeBallon2.png");
        case West:
            	return new ImageIcon("src\\images\\pipeBallon.png");
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
		
		return this.full;
	}
	
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

	@Override
	public boolean isUsable() {
		return usable;
	}
}
