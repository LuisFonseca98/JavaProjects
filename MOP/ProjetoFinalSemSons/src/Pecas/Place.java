package Pecas;


import javax.swing.JLabel;

public abstract class Place extends JLabel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int xBoard; 
	private int yBoard;
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Place(int x, int y){
		
		this.xBoard = x;
		this.yBoard = y;
		
	}
	
	/**
	 * 
	 * @return int xBoard
	 */
	public int getxBoard() {
		return this.xBoard;
		
	}

	/**
	 * 
	 * @param xBoard
	 */
	public void setxBoard(int xBoard) {
		this.xBoard = xBoard;
	}

	/**
	 * 
	 * @return int yBoard
	 */
	public int getyBoard() {
		return yBoard;
	}

	/**
	 * @param yBoard
	 */
	public void setyBoard(int yBoard) {
		this.yBoard = yBoard;
	}
	
	/*
	 *  Método abstrato que determina se é ou não uma EmptyPlace
	 */
	public abstract boolean isEmpty();
	
	
	public abstract boolean isUsable();
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract boolean canMoveTo(int x, int y);
	

	
}
