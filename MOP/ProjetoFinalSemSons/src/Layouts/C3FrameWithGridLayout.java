package Layouts;


import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * Demonstra a utilização de GridLayout. Este layout manager (gestor espacial)
 * posiciona os seus elementos em grelha. Se o nº de componentes a mostrar
 * exceder o número de celulas o Gridlayout cria mais colunas.
 * 
 * The number of rows have priority over number of Columns
 * 
 * Assuntos: GridLayout, hgap, vgap
 * 
 * @author António Teófilo
 * 
 */

public class C3FrameWithGridLayout {

	/**
	 * Main
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		System.out.println("End of main...");
	}

	/**
	 * Create and Show GUI
	 */
	public static void createAndShowGUI() {
		// create a JFrame
		JFrame frame = new JFrame();
		// set title
		frame.setTitle("...: My grid layout frame :...");
		// set size and location
		frame.setSize(800, 600);
		// to center a frame
		frame.setLocationRelativeTo(null);
		// set default close operation
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// build the gridLayout
		int nRows = 8; // 4;
		int nCols = 6; // 2;
		GridLayout gl = new GridLayout(nRows, nCols, 0, 5);
		frame.setLayout(gl);

		// set content pane background color
		frame.getContentPane().setBackground(new Color(255, 0, 0));

		Random rg = new Random();
		JLabel[][] labels = new JLabel[nRows][nCols];
		// criar JLabels
		for (int y = 0; y < nRows; ++y) {
			for (int x = 0; x < nCols; ++x) {
				JLabel label = labels[y][x] = new JLabel(
						"x: " + x + ", y:" + y);
				label.setBorder(new LineBorder(Color.yellow, 1));
				label.setOpaque(true);
				label.setBackground(new Color(rg.nextInt(256), rg.nextInt(256),
						rg.nextInt(256)));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				frame.add(label);
			}
		}

		System.out.println("Component count -> "
				+ frame.getContentPane().getComponentCount());

		/**
		 * Teste 1
		 */
		frame.getContentPane()
				.remove(rg.nextInt(gl.getRows() * gl.getColumns()));

		/**
		 * teste 2
		 */
		// Trocar um componente por outro
		frame.getContentPane().remove(4);
		JButton jb = new JButton("Novo componente");
		frame.getContentPane().add(jb, 4);


		frame.setVisible(true);
		// life goes on
		System.out.println("Frame created...");

	}
}
