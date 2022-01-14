/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 */

package Layouts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * Assuntos: ProgressBar, Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR),
 * Toolkit.getDefaultToolkit().beep();
 * 
 * NOTA: se o timer estiver a correr e fizermos close à aplicação, a janela
 * fecha-se mas a aplicação continua a correr por trás e vai fazer beep mais
 * tarde. Como fazer para terminar a aplicação naquele momento? (sem fazer exit)
 * 
 * @author ateofilo
 * 
 */

public class C05ProgressBarDemo extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final Random rg = new Random();

	// the ProgressBar
	private JProgressBar progressBar;

	private JButton startButton;
	private JTextArea taskOutput;

	private Timer timer;
	private int progress = 0;

	public C05ProgressBarDemo() {
		super(new BorderLayout());

		// Create the demo's UI.
		startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);

		// the progress Bar
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(200, 100, 100));

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);

		JPanel panel = new JPanel();
		panel.add(startButton);
		panel.add(progressBar);

		add(panel, BorderLayout.PAGE_START);
		add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// timer actionListener
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make some progress
				progress = Math.min(progress + 2 + rg.nextInt(8), 100);
				progressBar.setValue(progress);
				taskOutput.append("progress in " + progress + "%\n");
				if (progress == 100) {
					timer.stop();
					Toolkit.getDefaultToolkit().beep();
					startButton.setEnabled(true);
					// turn off the wait cursor
					setCursor(null);

				}
			}
		};

		timer = new Timer(300, al);
		timer.setRepeats(true);

	}

	/**
	 * Invoked when the user presses the start button.
	 */
	public void actionPerformed(ActionEvent evt) {
		startButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		progressBar.setValue(progress = 0);
		taskOutput.setText("progress in " + progress + "%\n");
		timer.start();
	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("ProgressBarDemo");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new C05ProgressBarDemo();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
