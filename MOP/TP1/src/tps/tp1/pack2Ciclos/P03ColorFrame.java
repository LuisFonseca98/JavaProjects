package tps.tp1.pack2Ciclos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Applicação que mostra uma variação de cores na parte central da sua janela.
 */
public class P03ColorFrame extends JFrame {
	private static final long serialVersionUID = -330888082383077655L;

	private MyLabel label1;
	private JButton bnColorStart;
	private JButton bnColorEnd;

	/**
	 * Create frame
	 */
	protected void init() {
		setTitle("...: ColorFrame :...");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// panel central
		JPanel jpCentral = new JPanel(new ProportionalLayout(0.1f));
		jpCentral.setBackground(Color.ORANGE);
		jpCentral.setOpaque(true);
		add(jpCentral, BorderLayout.CENTER);

		// label central
		label1 = new MyLabel();
		jpCentral.add(label1, ProportionalLayout.CENTER);

		// buttons panel
		JPanel jpButtons = new JPanel();
		add(jpButtons, BorderLayout.SOUTH);

		// button start color
		bnColorStart = new JButton("Start color");
		bnColorStart.setBackground(Color.GREEN);
		bnColorStart.setOpaque(true);
		bnColorStart.setHorizontalAlignment(SwingConstants.CENTER);
		jpButtons.add(bnColorStart);

		// button end color
		bnColorEnd = new JButton("End color");
		bnColorEnd.setBackground(Color.MAGENTA);
		bnColorEnd.setOpaque(true);
		bnColorEnd.setHorizontalAlignment(SwingConstants.CENTER);
		jpButtons.add(bnColorEnd);

		// listener start color button
		bnColorStart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(P03ColorFrame.this, "Choose Start Color",
						bnColorStart.getBackground());
				if (newColor != null) {
					bnColorStart.setBackground(newColor);
					label1.repaint();
				}
			}
		});

		// listener end color button
		bnColorEnd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(P03ColorFrame.this, "Choose End Color",
						bnColorEnd.getBackground());
				if (newColor != null) {
					bnColorEnd.setBackground(newColor);
					label1.repaint();
				}
			}
		});

		// set frame visible
		setVisible(true);
	}

	/**
	 * Main method - the execution starts here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				P03ColorFrame frame = new P03ColorFrame();
				frame.init();
			}
		});
	}

	/**
	 * Auxiliary class that extends JLabel
	 */
	class MyLabel extends JLabel {
		private static final long serialVersionUID = -4402584053051810107L;

		/**
		 * Method that paints the label
		 */
		public void paint(Graphics g) {
			super.paint(g);
			System.out.println("Paint...");

			drawColors(g, getWidth(), getHeight(), bnColorStart.getBackground(), bnColorEnd.getBackground());
		}

		/**
		 * Should draw all the drawing area with lines with color varying
		 * accordingly to the text.
		 * 
		 * To change the drawing color use: Graphics.setColor(Color newColor).
		 * To draw a line use: Graphics.drawLine(int x1, int y1, int x2, int y2)
		 * 
		 * @param g
		 *            the graphics where we should draw the lines
		 * @param dimX
		 *            x dimension of drawing area
		 * @param dimY
		 *            y dimension of drawing area
		 * @param startColor
		 *            start color
		 * @param endColor
		 *            end color
		 */
		private void drawColors(Graphics g, int dimX, int dimY, Color startColor, Color endColor) {
			// TODO Apenas fazer código aqui, dentro deste método

			// As cores devem ir de red, green e blue da cor inicial e terminar
			// com o red, green e blue da cor final. Portanto devem ir variando
			// de uma para a outra.

			Color auxColor = new Color(startColor.getRed(), startColor.getGreen(), startColor.getBlue());
			g.setColor(auxColor);
			double iNewRed = auxColor.getRed();
			double iNewGreen = auxColor.getGreen();
			double iNewBlue = auxColor.getBlue();
			double deltaR = endColor.getRed() - startColor.getRed();
			double deltaG = endColor.getGreen() - startColor.getGreen();
			double deltaB = endColor.getBlue() - startColor.getBlue();
			int dimT = (dimX + dimY)/2;
			
			for(int i = dimT; i > 0; i--) {
				int j = (i * dimY);
				Color cor_auxiliar = new Color((int) iNewRed, (int) iNewGreen, (int) iNewBlue);
				g.setColor(cor_auxiliar);
				int a = 4/3;
				int b1 = (dimX - i * a)/2;
				int c = (dimY - j * a)/2;
				int d = i * a;
				int e = j * a;
				g.drawRect(b1,c, d, e);
				iNewRed += deltaR/dimT;
				iNewGreen += deltaG/dimT;
				iNewBlue += deltaB/dimT;
			}
			System.out.println(iNewRed + " " + iNewGreen + " " + iNewBlue);

		}
	}

}
