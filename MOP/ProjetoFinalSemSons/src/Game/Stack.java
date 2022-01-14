package Game;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Pecas.EmptyPiece;
import Pecas.GenericPiece;
import Pecas.Place;


public class Stack extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private Place[][] matriz; 


	public Stack(int linhas, Color pColor, Color bColor, Color bc) {

		this.matriz = new Place[linhas][1];
		createBoard(linhas, pColor, bColor, bc);
		fillEmpty(pColor, bColor);
	}

	
	private void createBoard(int linhas, Color pColor, Color bColor, Color bc) {

		GridLayout gl = new GridLayout(linhas, 1, 0, 0);
		setLayout(gl);
		setBackground(bc);
	}

	
	private void fillEmpty(Color pcolor, Color bcolor) {
		for(int l = 0; l < matriz.length; l++){
			matriz[l][0] = new EmptyPiece(l,0, pcolor, bcolor);
			matriz[l][0].setBorder(new LineBorder(bcolor, 10));
			this.add(matriz[l][0]);
		}
	}
	
	
	Place setPiece(GenericPiece piece, int x) {
		Place place = getPiece(piece);
		place.setxBoard(x);
		place.setyBoard(0);
		revalidate();
		return place;
	}
	
	
	public Place getPiece(GenericPiece gp) {

		Place pc = (Place)getComponent(0);
		remove(0);
		add(gp);
		return pc;
	}
	

	public Place getPlaceAt(int xBoard){

		Place pc = (Place)getComponent(xBoard);
		return pc;
	}
}
