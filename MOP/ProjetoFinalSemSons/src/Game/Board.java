package Game;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Pecas.EmptyPiece;
import Pecas.GenericPiece;
import Pecas.Place;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Place[][] matriz; 
	

	public Board(int linhas, int colunas, Color pColor, Color bColor, Color bc) {

		this.matriz = new Place[linhas][colunas];
		createBoard(linhas, colunas, pColor, bColor, bc);
		fillEmpty(pColor, bColor);
	}

	
	private void createBoard(int linhas, int colunas, Color pColor, Color bColor, Color bc) {

		GridLayout gl = new GridLayout(linhas, colunas, 0, 0);
		setLayout(gl);
		setBackground(bc);
	}
	
	
	private void fillEmpty(Color pcolor, Color bcolor) {
		for(int l = 0; l < matriz.length; l++){
			for(int c = 0; c < matriz[l].length; c++){
				matriz[l][c] = new EmptyPiece(l,c, pcolor, bcolor);
				matriz[l][c].setBorder(new LineBorder(bcolor, 5));
				this.add(matriz[l][c]);	
			}
		}
	}

	
	Place getPiece(int x, int y) {
		return (Place)getComponent(8 * y + x);
	}
	
	
	void setPiece(GenericPiece piece) {
		
		int xp = piece.getyBoard();
		int yp = piece.getxBoard();

		remove(8 * yp + xp);
		add(piece, 8 * yp + xp);
		matriz[xp][yp] = piece;
		revalidate();
	}

	
	public Place getPlaceAt(int xBoard, int yBoard){
		
		Place p = matriz[xBoard][yBoard];
		return p;
	}
}
