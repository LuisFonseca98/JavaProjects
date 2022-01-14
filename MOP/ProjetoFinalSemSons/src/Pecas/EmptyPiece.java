package Pecas;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import Layouts.ProportionalLayout;



public class EmptyPiece extends GenericPiece{


	private static final long serialVersionUID = 1L;
	private boolean usable;

	public EmptyPiece(int x, int y,Color color1, Color color2) {
		super("empty", x, y, null, null);
		drawEmptyPlace(this,color1, color2);
		this.usable = true;
	}
	
	public void drawEmptyPlace(JLabel panel, Color color1, Color color2) {
		
		panel.setLayout(new ProportionalLayout(0,0,0,0));
		JLabel label = new JLabel();
		label.setBorder(new LineBorder(color2,3));
		label.setOpaque(true);
		label.setBackground(color1);
		panel.add(label);
	}




	@Override
	public boolean isUsable() {return usable;}
	
	@Override
	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	@Override
	public boolean canMoveTo(int x, int y) {
		return true;
	}
	

	@Override
	public boolean isEmpty() {
		return true;
	}

	
	@Override
	public GenericPiece rotate() {
		return null;
	}

	
	@Override
	public ImageIcon pickPic() {
		return null;
	}


	@Override
	public boolean isFull() {
		return false;
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
}
