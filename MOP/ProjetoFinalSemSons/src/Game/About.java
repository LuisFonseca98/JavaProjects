package Game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class About {
	public About() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		createAndShowGui();
	}

	@SuppressWarnings("deprecation")
	public static void addComponentsPane(Container pane,JFrame frame) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));

//		AudioInputStream stream;
//		AudioFormat format;
//		DataLine.Info info;
//		Clip clip;
//
//		File wavFile2 = new File("src\\images\\helpMenuMusic.wav");
//
//		stream = AudioSystem.getAudioInputStream(wavFile2);
//		format = stream.getFormat();
//		info = new DataLine.Info(Clip.class, format);
//		clip = (Clip) AudioSystem.getLine(info);
//		clip.open(stream);
//		clip.start();

		JTextArea texto = new JTextArea(0,0);
		String conteudo = null;

		try {
			Scanner sc = new Scanner(new File("src\\images\\info.txt"));
			conteudo = sc.useDelimiter("\\Z").next();
			sc.close();
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		texto.setText(conteudo);
		texto.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(128,128,128)));
		texto.setForeground(new Color(162,161,159));
		texto.setFont(new Font("Arial",Font.PLAIN,18));
		texto.setBackground(new Color(8,77,110));
		
		JButton menuBackButton = new JButton("Back to the Main Menu");
		menuBackButton.setPreferredSize(new Dimension(300,50));
		menuBackButton.setFocusPainted(false);
		menuBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuBackButton.setForeground(new Color(255,255,255));
		menuBackButton.setFont(new Font("Arial",Font.PLAIN,20));
		menuBackButton.setBackground(new Color(105,101,97));

		pane.add(Box.createRigidArea(new Dimension(0,100)));
		pane.add(texto);
		pane.add(Box.createRigidArea(new Dimension(0,30)));
		pane.add(menuBackButton);
		pane.add(Box.createRigidArea(new Dimension(0,20)));

		
		
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
		
		menuBackButton.addMouseListener(ml);
		
		menuBackButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				frame.dispose();
				//clip.stop();
				try {
					new MainMenu();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
            }
        });
	
	}

	private static void createAndShowGui() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		final JFrame frame = new JFrame();
		frame.setSize(800,600);
		frame.setUndecorated(true);
		String bgHelp = new String("src\\images\\bg.png");
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
		new About();
	}
}
