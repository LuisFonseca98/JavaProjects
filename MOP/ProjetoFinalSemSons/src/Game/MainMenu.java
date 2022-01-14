package Game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenu {

	public MainMenu() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		createAndShowGui();
	}

	public static void addComponentsPane(Container pane,JFrame frame) throws UnsupportedAudioFileException, IOException, LineUnavailableException {


//		AudioInputStream stream;
//		AudioFormat format;
//		DataLine.Info info;
//		Clip clip;
//
//		File wavFile2 = new File("src\\images\\MMmusic.wav");
//
//		stream = AudioSystem.getAudioInputStream(wavFile2);
//		format = stream.getFormat();
//		info = new DataLine.Info(Clip.class, format);
//		clip = (Clip) AudioSystem.getLine(info);
//		clip.open(stream);
//		clip.start();

		JButton newGame = new JButton("Start a New Game"),about = new JButton("About"),
				help = new JButton("Help"), quitGameButton = new JButton("Exit Game"); 

		pane.add(newGame);
		newGame.setBounds(250, 550, 300, 50);
		newGame.setForeground(new Color(162,161,159));
		newGame.setFont(new Font("Arial",Font.PLAIN,20));
		newGame.setBackground(new Color(105,101,97));

		pane.add(help);
		help.setBounds(250, 650, 300, 50);
		help.setForeground(new Color(162,161,159));
		help.setFont(new Font("Arial",Font.PLAIN,20));
		help.setBackground(new Color(105,101,97));

		pane.add(about);
		about.setBounds(600, 550, 300, 50);
		about.setForeground(new Color(162,161,159));
		about.setFont(new Font("Arial",Font.PLAIN,20));
		about.setBackground(new Color(105,101,97));

		pane.add(quitGameButton);
		quitGameButton.setBounds(600, 650, 300, 50);
		quitGameButton.setForeground(new Color(162,161,159));
		quitGameButton.setFont(new Font("Arial",Font.PLAIN,20));
		quitGameButton.setBackground(new Color(105,101,97));

		MouseAdapter ml = new MouseAdapter() {

			@SuppressWarnings("deprecation")
			public void mouseEntered(MouseEvent e) {
				((JComponent)e.getSource()).setBorder(BorderFactory.createMatteBorder(0,13,0,13, new Color(35,142,177)));
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

			@SuppressWarnings("deprecation")
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
		help.addMouseListener(ml);
		about.addMouseListener(ml);
		quitGameButton.addMouseListener(ml);

		newGame.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				frame.dispose();
				new Game();
			}
		});

		help.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				frame.dispose();
				try {
					new Help();
					//clip.stop();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		});

		about.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				frame.dispose();
				try {
					new About();
					//clip.stop();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
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
		frame.setSize(1200,800);
		frame.setUndecorated(false);
		String bgHelp = new String("src\\images\\MMpic.png");
		frame.setContentPane(new JLabel(new ImageIcon(bgHelp)));
		frame.setVisible(true);
		addComponentsPane(frame.getContentPane(),frame);
		frame.setVisible(true);
	}

	public static void main(final String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new MainMenu();
	}
}
