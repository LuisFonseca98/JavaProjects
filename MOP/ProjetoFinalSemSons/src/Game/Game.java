package Game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Layouts.ProportionalLayout;
import Pecas.Direcoes;
import Pecas.GenericPiece;
import Pecas.PipeBallon;
import Pecas.PipeCorner;
import Pecas.PipeCross;
import Pecas.PipeLine;
import Pecas.PipeSink;
import Pecas.PipeSource;
import Pecas.Place;
import Pecas.Rock;

public class Game extends JPanel {

	private static Board board;
	private static Stack stack;
	private static JProgressBar progressBar;
	private static ArrayList<GenericPiece> deck;
	private static JFrame frame;
	private static Color bcColor, tableBC, fontColor, borderColor, titleBar, hooverC;
	private static PipeSink psi;
	private static PipeSource pso;
	private static boolean hasWon, hasLost;
	private static Thread t;
	private static final long serialVersionUID = 1L;


	public static void CreatePane(Container pane, float iTop, float iBottom, float iLeft, float iRigth, float zTop,
			float zBottom, float zLeft, float zRigth) {

		ProportionalLayout cl = new ProportionalLayout(zTop, zBottom, zLeft, zRigth);
		pane.setLayout(cl);

		cl.setInsets(iTop, iBottom, iLeft, iRigth);
		cl.setPGaps(0.00f);
		createColors();

		pane.setBackground(bcColor);

		// Norte
		JLabel label2 = new JLabel("  Pipe Game  ", SwingConstants.CENTER);
		label2.setForeground(fontColor);
		label2.setFont(new Font("OCR A Extended", Font.BOLD, 60));
		label2.setBackground(titleBar);
		label2.setOpaque(true);
		pane.add(label2, ProportionalLayout.NORTH);
		label2.setBorder(new LineBorder(borderColor));

		// Sul
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(100);
		//progressBar.setStringPainted(true);
		progressBar.setForeground(bcColor);
		progressBar.setPreferredSize(new Dimension(700, 50));

		JPanel panelS = new JPanel();
		panelS.setBackground(titleBar);
		panelS.setOpaque(true);
		panelS.add(progressBar);
		pane.add(panelS, ProportionalLayout.SOUTH);

		// Este
		stack = new Stack(5, tableBC, borderColor, bcColor);
		pane.add(stack, ProportionalLayout.EAST);

		// Oeste
		JLabel label5 = new JLabel("  Score   ", SwingConstants.CENTER);
		label5.setForeground(fontColor);
		label5.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		label5.setBackground(tableBC);
		label5.setOpaque(true);
		pane.add(label5, ProportionalLayout.WEST);
		label5.setBorder(new LineBorder(borderColor));

		// Center
		board = new Board(8, 8, tableBC, bcColor, borderColor);
		pane.add(board, ProportionalLayout.CENTER);
	}


	public void dispatchKeyEvent(KeyEvent e) {

		if (e.getID()==KeyEvent.KEY_TYPED && e.getKeyChar() == KeyEvent.VK_SPACE) {
			System.out.println("oiii");
		}
	}

	static MouseAdapter tile_ml = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {

			// mostra a quadricula com border se o rato passar por cima
			((JComponent)e.getSource()).setBorder(new LineBorder(hooverC,3));}
		public void mouseReleased(MouseEvent e) {
			Place place = (Place) e.getSource();
			int button = e.getButton();
			Place pa = null;

			switch (button) {
			case MouseEvent.BUTTON3:
				pa = stack.getPlaceAt(0);
				rotatePiece(pa);
			case MouseEvent.BUTTON1:
				if(pa == null) {
					pa = stack.getPlaceAt(0);
					GenericPiece gg = (GenericPiece)pa;
					GenericPiece ver = (GenericPiece)board.getPiece(place.getyBoard(), place.getxBoard());
					gg.setxBoard(place.getxBoard());
					gg.setyBoard(place.getyBoard());
					gg.addMouseListener(tile_ml);
					if(ver.isUsable()) {
						addToBoard(gg);
						moveToTop();
					}
				}
			}
		}

		public void mouseExited(MouseEvent e){
			((JComponent)e.getSource()).setBorder(new LineBorder(borderColor,3));
		}
	};

	private static void hover() {

		for (int l = 0; l < 8; l++) {
			for (int c = 0; c < 8; c++) {
				board.getPlaceAt(l,c).addMouseListener(tile_ml);
			}
		}
	}


	//Adiciona Piece ao Board
	public static void addToBoard(Place gp) {

		GenericPiece gg = (GenericPiece)gp;
		board.setPiece(gg);
	}


	//Cria, Adiciona e Baralha as peças num deck
	public static void addToDeck() {

		deck = new ArrayList<GenericPiece>();
		deck.addAll(Arrays.asList(PipeLine.createPieces()));
		deck.addAll(Arrays.asList(PipeCorner.createPieces()));
		deck.addAll(Arrays.asList(PipeCross.createPieces()));
		deck.addAll(Arrays.asList(PipeBallon.createPieces()));
		Collections.shuffle(deck, new Random());		
	}


	//Passa as Peças do Deck para o Stack
	public static void addToStack() {

		GenericPiece gp = deck.get(0);
		stack.add(gp);
		deck.remove(0);
	}


	//Preenche a Stack inicialmente
	public static void fillStack() {

		for(int i = 3; i>=0; i--) {
			for(GenericPiece GP : deck) {
				stack.setPiece(GP,i);
			}
		}
	}


	//Move as peças do stack para o topo e adiciona uma peça
	//Na última posição
	public static void moveToTop() {

		for(int idx = 1; idx >= 3; idx++) {
			Place pa = stack.getPlaceAt(idx);
			stack.setPiece((GenericPiece)pa, idx-1);
		}
		addToStack();
	}


	//Roda a primeira peça do Stack
	public static void rotatePiece(Place pa) {

		GenericPiece gp = (GenericPiece)pa;
		Place pl = (Place)gp.rotate();
		stack.setPiece((GenericPiece)pl, 0);
		// Este add salvou tudo :D
		stack.add((GenericPiece)pl,0);
	}


	//Adicionar o Source dados todos os condicionalismos
	public static PipeSource addSource() {

		Random random = new Random();
		int rx = random.nextInt(7);
		int ry = random.nextInt(7);
		int dir = random.nextInt(3);

		while(rx == 0 && dir == 3 || rx == 7 && dir == 1 || ry == 0 && dir == 0 ||ry == 7 && dir == 2) {
			rx = random.nextInt(7);
			ry = random.nextInt(7);
			dir = random.nextInt(3);
		}

		Direcoes saida = Direcoes.values()[dir];
		pso = new PipeSource("source", ry, rx, null, saida);

		pso.setxBoard(ry);
		pso.setyBoard(rx);
		board.setPiece(pso);

		return pso;
	}


	//Adiciona o Sink dados todos os condicionalismos
	public static PipeSink addSink() {

		Random random = new Random();
		int rx = random.nextInt(7);
		int ry = random.nextInt(7);
		int dir = random.nextInt(3);

		while(rx == 0 && dir == 3 || rx == 7 && dir == 1 || ry == 0 && dir == 0 ||ry == 7 && dir == 2) {
			rx = random.nextInt(7);
			ry = random.nextInt(7);
			dir = random.nextInt(3);
		}
		Direcoes entrada = Direcoes.values()[dir];

		psi = new PipeSink("sink", rx, ry, entrada, null);
		psi.setxBoard(ry);
		psi.setyBoard(rx);
		board.setPiece(psi);


		return psi;
	}


	//Adiciona a pedra
	public static void addRock() {

		Random random = new Random();
		int rx = random.nextInt(7);
		int ry = random.nextInt(7);

		while(rx == 0 || rx == 7 || ry == 0 ||ry == 7) {
			rx = random.nextInt(7);
			ry = random.nextInt(7);
		}

		Rock rock = new Rock("rock",rx, ry, null, null);
		rock.setxBoard(ry);
		rock.setyBoard(rx);

		board.setPiece(rock);
	}


	//Criação das cores utilizadas no jogo
	public static void createColors() {
		bcColor = new Color(63,64,69);
		tableBC = new Color(64,121,140);
		fontColor = new Color(27,153,139);
		borderColor = new Color(63,64,69); // Color.RED;//new Color(51,50,50); 
		titleBar = new Color(23);
		hooverC = new Color(155,29,32);
	}


	//Método que verifica se existe caminho entre Source e Sink
	//Chamado uma vez para Source
	public static boolean hasPath(GenericPiece gp) {

		GenericPiece ngp = null; 
		Direcoes nE;
		boolean entrada = false;
		switch (gp.getSaida()) {
		case North:
			if(gp.getxBoard()-1 > 7 || gp.getxBoard()-1 < 0)break;
			ngp = (GenericPiece)board.getPiece(gp.getyBoard(), gp.getxBoard()-1);
			if(ngp.getNome().equals("cross")) ((PipeCross) ngp).setHorizontal(false);
			nE = ngp.getEntrada();
			entrada = (nE == Direcoes.South)?true:false;
			break;
		case East:
			if(gp.getyBoard()+1> 7 || gp.getyBoard()+1 < 0)break;
			ngp = (GenericPiece)board.getPiece(gp.getyBoard()+1, gp.getxBoard());
			if(ngp.getNome().equals("cross")) ((PipeCross) ngp).setHorizontal(true);
			nE = ngp.getEntrada();
			entrada = (nE == Direcoes.West)?true:false;
			break;
		case South:
			if(gp.getxBoard()+1 > 7 || gp.getxBoard()+1 < 0)break;
			ngp = (GenericPiece)board.getPiece(gp.getyBoard(), gp.getxBoard()+1);
			if(ngp.getNome().equals("cross")) ((PipeCross) ngp).setHorizontal(false);
			nE = ngp.getEntrada();
			entrada = (nE == Direcoes.North)?true:false;
			break;
		case West:
			if(gp.getyBoard()-1 > 7 || gp.getyBoard()-1 < 0)break;
			ngp = (GenericPiece)board.getPiece(gp.getyBoard()-1, gp.getxBoard());
			if(ngp.getNome().equals("cross")) ((PipeCross) ngp).setHorizontal(true);
			nE = ngp.getEntrada();
			entrada = (nE == Direcoes.East)?true:false;
			break;
		default:
			break;
		}


		if(ngp != null) {
			if(gp.isConnected(ngp)) {
				if(gp.isFull()) {
					if(ngp.getNome().equals("sink")) {
						ngp.setStartPaint(true);

						ngp.repaint();
						return gp.isConnected(ngp);
					}
					ngp.setStartPaint(true);
					ngp.setIsEntrada(entrada);
					ngp.repaint();
				}
				return hasPath(ngp, entrada);
			}
		}
		return false;
	}


	//Método Que verifica se existe caminho entre Source e Sink
	//Recursivo, chamado para todas as genericPieces
	public static boolean hasPath(GenericPiece gp, boolean entrada) {

		Direcoes saida;
		saida = (entrada)?gp.getSaida():gp.getEntrada();
		GenericPiece ngp = null; 
		Direcoes nE;
		if(!gp.getNome().equals("sink")) {

			switch (saida) {
			case North:
				if(gp.getxBoard()-1 > 7 || gp.getxBoard()-1 < 0)break;
				ngp = (GenericPiece)board.getPiece(gp.getyBoard(), gp.getxBoard()-1);
				if(ngp.getNome().equals("cross")) ((PipeCross) ngp).setHorizontal(false);
				nE = ngp.getEntrada();
				entrada = (nE == Direcoes.South)?true:false;
				break;
			case East:
				if(gp.getyBoard()+1> 7 || gp.getyBoard()+1 < 0)break;
				ngp = (GenericPiece)board.getPiece(gp.getyBoard()+1, gp.getxBoard());
				if(ngp.getNome().equals("cross")) ((PipeCross) ngp).setHorizontal(true);
				nE = ngp.getEntrada();
				entrada = (nE == Direcoes.West)?true:false;
				break;
			case South:
				if(gp.getxBoard()+1 > 7 || gp.getxBoard()+1 < 0)break;
				ngp = (GenericPiece)board.getPiece(gp.getyBoard(), gp.getxBoard()+1);
				if(ngp.getNome().equals("cross")) ((PipeCross) ngp).setHorizontal(false);
				nE = ngp.getEntrada();
				entrada = (nE == Direcoes.North)?true:false;
				break;
			case West:
				if(gp.getyBoard()-1 > 7 || gp.getyBoard()-1 < 0)break;
				ngp = (GenericPiece)board.getPiece(gp.getyBoard()-1, gp.getxBoard());
				if(ngp.getNome().equals("cross")) ((PipeCross) ngp).setHorizontal(true);
				nE = ngp.getEntrada();
				entrada = (nE == Direcoes.East)?true:false;
				break;
			default:
				break;
			}
		}

		if(ngp != null) {
			if(gp.isConnected(ngp)) {
				if(gp.isFull()) {
					if(ngp.getNome().equals("sink")) {
						ngp.setStartPaint(true);
						ngp.setIsEntrada(entrada);
						ngp.repaint();
						if(ngp.isFull()) {
							hasWon = true;
							return true;	
						}
					}
					ngp.setStartPaint(true);
					ngp.setIsEntrada(entrada);
					ngp.repaint();
				}
				return hasPath(ngp, entrada);

			}
			else if (hasLost(gp, ngp)) hasLost = true;
		}
		return false;
	}


	//Verifica se o Sink está coberto por uma peça imovível
	public static boolean isOnExit(PipeSink ps) {

		GenericPiece ngp = null; 
		switch (ps.getEntrada()) {
		case North:
			ngp = (GenericPiece)board.getPiece(ps.getyBoard(), ps.getxBoard()-1);
			break;
		case East:
			ngp = (GenericPiece)board.getPiece(ps.getyBoard()+1, ps.getxBoard());
			break;
		case South:
			ngp = (GenericPiece)board.getPiece(ps.getyBoard(), ps.getxBoard()+1);
			break;
		case West:
			ngp = (GenericPiece)board.getPiece(ps.getyBoard()-1, ps.getxBoard());
			break;

		}

		if(ps.getEntrada() == ngp.getEntrada() || ps.getEntrada() == ngp.getSaida()) {
			return true;
		}

		else return false;

	}


	//Verifica se o jogador perdeu o jogo
	public static boolean hasLost(GenericPiece gp1, GenericPiece gp2) {
		if(gp1.isFull()) {
			if(!gp1.isConnected(gp2)) return true;
			if(!isOnExit(psi)) return true;
		}
		return false;
	}


	//Seleciona o menu a apresentar em caso de derrota ou vitória
	public static void changeMenu() {

		if(hasWon == true) {
			try {
				new Victory();
				frame.dispose();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}

		if(hasLost == true) {
			try {
				new GameOver();
				frame.dispose();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}	
	}


	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	private static JFrame createAndShowGUI(float iTop, float iBottom, float iLeft, float iRigth, float zTop,
			float zbottom, float zLeft, float zRigth) {
		// Create and set up the window.
		frame = new JFrame("Pipe Game");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Set up the content pane.
		CreatePane(frame.getContentPane(), iTop, iBottom, iLeft, iRigth, zTop, zbottom, zLeft, zRigth);

		// Display the window.
		// frame.pack();
		frame.setSize(900, 800);

		// to center a frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


		return frame;
	}


	public Game() {
		createAndShowGUI(0.0f, 0.0f, 0.005f, 0.0f, 0.1f, 0.1f, 0f,0.2f);
		hasLost = false;
		hasWon = false;
		hover();
		addSource();
		addSink();
		addRock();
		addToDeck();
		fillStack();
		frame.setVisible(true);
		t = new Thread(new Runnable() {
			public void run() {
				int contador = 100;
				while(contador >= 0) {
					progressBar.setValue(contador);
					contador -= 2;
					try {
						Thread.sleep(2000);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		
		t = new Thread(new Runnable() {
			public void run() {
				while(!hasLost && !hasWon) {
					hasPath(pso);
					changeMenu();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	public static void main(String[] args) {
		new Game();
	}
}