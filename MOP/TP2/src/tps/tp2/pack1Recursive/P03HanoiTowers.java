package tps.tp2.pack1Recursive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Hanoi Towers
 * 
 * Puzzle das Torres de Hanoi
 * 
 * Neste puzzle, existem N discos, com raios distintos, colocados numa vara e
 * pretende-se passar esses discos para um outra vara. O cenários tem 3 varas e
 * a condição de que um disco não pode ser colocado sobre um disco de raio maior
 * que ele. Este puzzle resolve-se encontrando uma sequência de movimentos de
 * forma a passar os N discos da vara de origem para a vara de destino.
 * 
 * Neste desafio também se quer uma indicação final de quantos movimentos de
 * discos foram executados.
 */
public class P03HanoiTowers {

	private JFrame frame;

	/** The scenario left rod */
	private Rod leftRod = null;

	/** The scenario center rod */
	private Rod centerRod = null;

	/** The scenario right rod */
	private Rod rightRod = null;

	private JPanel pcentral;

	private JSpinner jsNumberOfTowers;

	private JButton buttonStart;

	private JButton buttonCreate;

	private JSpinner jsMovementsDelay;

	private int movementsDelay;

	private JButton buttonCancel;

	private boolean isActive = true;

	/**
	 * Build the frame and starts the scenario
	 */
	public void init() {

		frame = new JFrame("...: Hanoi Towers :...");
		frame.setSize(650, 300);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		ProportionalLayout pl = new ProportionalLayout(0.1f, 0.2f, 0.1f, 0.1f);
		frame.setLayout(pl);

		// main panel
		pcentral = new JPanel(new GridLayout(1, 0));
		pcentral.setBackground(Color.green);
		pcentral.setOpaque(true);
		frame.add(pcentral, ProportionalLayout.CENTER);

		// panel buttons and buttons
		JPanel buttons = new JPanel();
		frame.add(buttons, ProportionalLayout.SOUTH);

		// spinner to control the number of discs
		SpinnerNumberModel snm = new SpinnerNumberModel(2, 1, 20, 1);
		jsNumberOfTowers = new JSpinner(snm);
		jsNumberOfTowers.setToolTipText("Number of discs");
		buttons.add(jsNumberOfTowers);

		// Create button
		buttonCreate = new JButton("Create");
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createScenario((int) (jsNumberOfTowers.getValue()));
				jsNumberOfTowers.setEnabled(false);
				buttonStart.setEnabled(true);
				buttonCreate.setEnabled(false);
				buttonCancel.setEnabled(true);
			}
		});
		buttons.add(buttonCreate);

		// Start button
		buttonStart = new JButton("Start");
		buttonStart.setEnabled(false);
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jsMovementsDelay.setEnabled(false);
				movementsDelay = (int) (jsMovementsDelay.getValue());
				startScenario();
				buttonStart.setEnabled(false);
			}
		});
		buttons.add(buttonStart);

		// Cancel button
		buttonCancel = new JButton("Cancel");
		buttonCancel.setEnabled(false);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jsMovementsDelay.setEnabled(true);
				jsNumberOfTowers.setEnabled(true);
				buttonCreate.setEnabled(true);
				buttonStart.setEnabled(false);
				buttonCancel.setEnabled(false);
				isActive = false;
			}
		});
		buttons.add(buttonCancel);

		// spinner to control the movements delay
		SpinnerNumberModel snm2 = new SpinnerNumberModel(500, 100, 2000, 100);
		jsMovementsDelay = new JSpinner(snm2);
		jsMovementsDelay.setToolTipText("Delay between movements, in milliseconds");
		buttons.add(jsMovementsDelay);

		frame.setVisible(true);

	}

	/**
	 * Do the initial actions
	 */
	private void createScenario(int nDiscs) {
		// clear old stuff
		pcentral.removeAll();

		// create new Rods
		leftRod = new Rod("left", nDiscs);
		centerRod = new Rod("center", nDiscs);
		rightRod = new Rod("right", nDiscs);

		// put them on frame
		pcentral.add(leftRod);
		pcentral.add(centerRod);
		pcentral.add(rightRod);

		// do update main panel with new components
		pcentral.doLayout();

		// build initial discs
		buildInitialDiscs((int) jsNumberOfTowers.getValue());
	}

	/**
	 * Creates the initial discs on the left Rod
	 */
	private void buildInitialDiscs(int nDiscs) {
		for (int i = 0; i < nDiscs; i++) {
			leftRod.addDisc(new Disc(nDiscs - i));
		}
	}

	/**
	 * Start action, create a Thread to do the animation
	 */
	private void startScenario() {
		isActive = true;

		Thread t = new Thread(new Runnable() {
			public void run() {
				// solve
				try {
					int nMoves = moveHanoiDiscs(leftRod.getNDiscs(), leftRod, centerRod, rightRod);
					System.out.println("Terminated with " + nMoves + " movements.");
				} catch (InterruptedException e) {
					// execution has canceled, nothing to do, just continue
				}

				// activate buttons
				doneActions();
			}
		});
		t.start();
	}

	/**
	 * Solves the Hanoi Towers in a recursive manner.
	 * 
	 * 
	 * @param nDiscs
	 * @param startRod tstart rod/pole for this movement
	 * @param endRod   final rod/pole for this movement
	 * @param auxRod   auxiliary rod/pole for this movement
	 * 
	 * @return the number of movements executed
	 * @throws InterruptedException
	 */
	private int moveHanoiDiscs(int nDiscs, Rod startRod, Rod auxRod, Rod endRod) throws InterruptedException {
		if (!isActive)
			cancelExecution();

		if (nDiscs == 0) return 0;
		
		moveHanoiDiscs(nDiscs - 1, startRod, endRod, auxRod);

		moveDisc(startRod, endRod);
				
		moveHanoiDiscs(nDiscs - 1, auxRod, startRod, endRod);

		int nMovements = (int) (Math.pow(2,nDiscs) -1) ;
		return nMovements;
	}

	/**
	 * Move disc from top of fromRod to top of toRod
	 */
	private void moveDisc(Rod fromRod, Rod toRod) throws InterruptedException {
		Disc p = fromRod.remDisc();
		toRod.addDisc(p);

		// delay between movements
		Thread.sleep(movementsDelay);
	}

	/**
	 * Cancel the current execution
	 */
	private void cancelExecution() {
		// terminate simulation
		Thread.currentThread().interrupt();
	}

	/**
	 * End execution actions - buttons actions
	 */
	private void doneActions() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					// activate buttons
					jsNumberOfTowers.setEnabled(true);
					buttonCreate.setEnabled(true);
					buttonCancel.setEnabled(false);
					jsMovementsDelay.setEnabled(true);

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método main
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				P03HanoiTowers bmapTrans = new P03HanoiTowers();
				bmapTrans.init();
			}
		});
	}
}

/**
 * Class that supports a Disc
 *
 */
class Disc {
	private int size;

	/**
	 * Constructor
	 */
	public Disc(int size) {
		this.size = size;
	}

	/**
	 * Get the size of the disc
	 */
	public int getSize() {
		return size;
	}
}

/**
 * Class that supports a Rod - uma haste
 *
 */
class Rod extends JLabel {
	private static final long serialVersionUID = 1L;

	String name;
	Disc[] discs;
	int nDiscsOnRod = 0;

	/**
	 * Constructor
	 */
	public Rod(String rodName, int maxSize) {
		discs = new Disc[maxSize];
		name = rodName;
	}

	/**
	 * Get the number of discs
	 */
	public int getNDiscs() {
		return nDiscsOnRod;
	}

	/**
	 * Get the rod name
	 */
	public String getRodName() {
		return name;
	}

	/**
	 * Add a disc, following the rule that it should not be placed on top of a
	 * bigger disc
	 */
	public void addDisc(Disc p) {
		if (nDiscsOnRod > 0 && discs[nDiscsOnRod - 1].getSize() < p.getSize())
			throw new IllegalStateException("We cannot put a disc of size " + p.getSize() + " on top of a disc of size "
					+ discs[nDiscsOnRod - 1].getSize());

		discs[nDiscsOnRod++] = p;
		repaint();
	}

	/**
	 * Removes the top disc, if any
	 */
	public Disc remDisc() {
		if (nDiscsOnRod == 0)
			throw new IllegalStateException("We cannot remove a disc from an empty rod");
		Disc p = discs[nDiscsOnRod - 1];
		nDiscsOnRod--;
		repaint();
		return p;
	}

	/**
	 * Update the graphical aspect of the Rod
	 */
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.MAGENTA);
		g.fillRect(getWidth() / 2 - 2, getHeight() / 10, 4, (int) (getHeight() * 0.9));

		// draw discs
		for (int i = 0; i < nDiscsOnRod; i++) {
			int discSize = discs[i].getSize();
			drawDisc(discSize, i, discs.length, g);
		}
	}

	/**
	 * Draw a disc
	 */
	private void drawDisc(int size, int discNumber, int nDiscs, Graphics g) {
		int discSize = (int) ((getHeight() - (double) getHeight() / 5) / nDiscs);
		discSize = discSize > 20 ? 20 : discSize;

		int discWidth = (int) ((getWidth() - (double) getWidth() / 10) * size / nDiscs);

		g.setColor(Color.ORANGE);
		g.fillRect((getWidth() - discWidth) / 2, getHeight() - discSize * (1 + discNumber), discWidth, discSize);
		g.setColor(Color.RED);
		g.drawRect((getWidth() - discWidth) / 2, getHeight() - discSize * (1 + discNumber), discWidth, discSize);
	}

	/**
	 * Get a String description of the Rod
	 */
	@Override
	public String toString() {
		return getRodName() + " " + getNDiscs();
	}
}
