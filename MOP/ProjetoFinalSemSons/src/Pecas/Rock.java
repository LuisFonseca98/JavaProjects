package Pecas;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Layouts.ProportionalLayout;

public class Rock extends GenericPiece{

	/**
	 * 
	 */
	
	private boolean full, usable;
	private static final long serialVersionUID = 1L;

	
	public Rock(String nome, int x, int y, Direcoes entrada, Direcoes saida) {
		super(nome, x, y, entrada, saida);
		piecePicture(this, nome);
		drawPiece(this);
		this.full = false;
		this.usable = false;

	}
	
	
	public void drawPiece(JLabel panel) {

		panel.setLayout(new ProportionalLayout(0,0,0,0));
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setIcon(this.pickPic());
		panel.add(label);
	}
	

	@Override
	public GenericPiece rotate() {
		return null;
	}

	
	@Override
	public ImageIcon pickPic() {
		return new ImageIcon("src\\images\\rock.png");
	}

	
	@Override
	public boolean isFull() {
		return full;
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
	public boolean isUsable() {
		return usable;
	}

	
	@Override
	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	
	@Override
	public boolean isEmpty() {
		return false;
	}
}
