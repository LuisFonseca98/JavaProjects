package Game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameOver {
	public GameOver() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		createAndShowGui();
	}

	@SuppressWarnings("deprecation")
	public static void addComponentsPane(Container pane,JFrame frame) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

//		AudioInputStream stream;
//		AudioFormat format;
//		DataLine.Info info;
//		Clip clip;
//
//		File wavFile2 = new File("src\\images\\GameOver.wav");
//
//		stream = AudioSystem.getAudioInputStream(wavFile2);
//		format = stream.getFormat();
//		info = new DataLine.Info(Clip.class, format);
//		clip = (Clip) AudioSystem.getLine(info);
//		clip.open(stream);
//		clip.start();

		JButton newGame = new JButton("Retry");
		JButton quitGameButton = new JButton("Exit Game");
		
		pane.add(newGame);
		newGame.setPreferredSize(new Dimension(300,50));
		newGame.setFocusPainted(false);
		newGame.setBounds(250, 300, 300, 50);
		newGame.setForeground(new Color(162,161,159));
		newGame.setFont(new Font("Arial",Font.PLAIN,20));
		newGame.setBackground(new Color(105,101,97));
		
		pane.add(quitGameButton);

		quitGameButton.setPreferredSize(new Dimension(300,50));
		quitGameButton.setFocusPainted(false);
		quitGameButton.setBounds(250, 400, 300, 50);
		quitGameButton.setForeground(new Color(162,161,159));
		quitGameButton.setFont(new Font("Arial",Font.PLAIN,20));
		quitGameButton.setBackground(new Color(105,101,97));

		MouseAdapter ml = new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				((JComponent)e.getSource()).setBorder(BorderFactory.createMatteBorder(0,13,0,13, new Color(12,86,107)));
				((JComponent)e.getSource()).setForeground(new Color(255,255,255));
				//File soundEnter = new File("src\\images\\moveSound.wav");
				//AudioClip sound = null;
				try {
					//sound = Applet.newAudioClip(soundEnter.toURL());
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				//sound.play();
			}
			public void mouseExited(MouseEvent e) {
				((JComponent) e.getSource()).setForeground(new Color(255,255,255));
				((JComponent)e.getSource()).setBorder(BorderFactory.createMatteBorder(8, 0, 8, 0, new Color(59,69,75)));
			}
			
			public void mouseClicked(MouseEvent e) {
				//File pressSound = new File("src\\images\\pressingSound.wav");
				//AudioClip sound = null;
				try {
					//sound = Applet.newAudioClip(pressSound.toURL());
				}catch(Exception e2) {
					e2.printStackTrace();
				}
				//sound.play();
			}
		};

		newGame.addMouseListener(ml);
		quitGameButton.addMouseListener(ml);
		
		newGame.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				frame.dispose();
				new Game();
			}
		});

		quitGameButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.exit(0);
				//clip.stop();
			}
		});


	}

	private static void createAndShowGui() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		final JFrame frame = new JFrame();
		frame.setSize(800,600);
		frame.setUndecorated(true);
		String bgHelp = new String("src\\images\\GameOver.png");
		frame.setContentPane(new JLabel(new ImageIcon(bgHelp)));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		frame.setLocation((int)width/2-427, (int) height/2 - 375);
		frame.setVisible(true);
		addComponentsPane(frame.getContentPane(),frame);
		frame.setVisible(true);
	}

	public static void main(final String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new GameOver();
	}
}
