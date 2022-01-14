package TP1;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

public class GuiChat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea mensagensRecebidasTextArea,enviarTextArea,fileNameTextArea;
	private JOptionPane messageWarning;
	private JRadioButton javaRadioButton, todasRadioButton, FSOradioButton, RobotsRadioButton;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton abrirBTN,enviarBTN,browseBTN, terminarBTN;
	private MaquinaEstados states;
	private int guiID;
	public String FilePath;

	/**
	 * Create the application.
	 */
	public GuiChat(MaquinaEstados states) {
		this.states = states;
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		guiID+=1;
		getContentPane().setForeground(Color.BLUE);
		setBounds(100, 100, 750, 482);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("My Chat "+guiID);		
		
		browseBTN = new JButton("Browse");
		browseBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkFileCC();
				abrirBTN.setEnabled(true);
			}
		});

		browseBTN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		browseBTN.setBounds(376, 21, 146, 33);
		getContentPane().add(browseBTN);

		abrirBTN = new JButton("Abrir");
		abrirBTN.setEnabled(false);
		abrirBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createCC();
				terminarBTN.setEnabled(true);
			}
		});
		abrirBTN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		abrirBTN.setBounds(551, 21, 146, 33);
		getContentPane().add(abrirBTN);

		fileNameTextArea = new JTextArea();
		fileNameTextArea.setBackground(Color.WHITE);
		fileNameTextArea.setForeground(Color.BLACK);
		fileNameTextArea.setBounds(189, 27, 172, 27);
		fileNameTextArea.setEditable(false);
		getContentPane().add(fileNameTextArea);

		JLabel caixaCorreioLabel = new JLabel("Caixa de Correio");
		caixaCorreioLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		caixaCorreioLabel.setBounds(10, 24, 121, 27);
		getContentPane().add(caixaCorreioLabel);

		JLabel mensagemEnviarLabel = new JLabel("Mensagem A Enviar");
		mensagemEnviarLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mensagemEnviarLabel.setBounds(189, 58, 172, 27);
		getContentPane().add(mensagemEnviarLabel);

		enviarBTN = new JButton("Enviar");
		enviarBTN.setEnabled(false);
		enviarBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				states.numState = states.WRITE_MESSAGE;
			}
		});

		enviarBTN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		enviarBTN.setBounds(551, 92, 146, 33);
		getContentPane().add(enviarBTN);

		JButton limparBTN = new JButton("Limpar");
		limparBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mensagensRecebidasTextArea.setText("");
			}
		});
		limparBTN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		limparBTN.setBounds(551, 135, 146, 33);
		getContentPane().add(limparBTN);

		enviarTextArea = new JTextArea();
		enviarTextArea.setForeground(Color.BLACK);
		enviarTextArea.setBackground(Color.WHITE);
		enviarTextArea.setBounds(191, 95, 331, 27);
		getContentPane().add(enviarTextArea);

		JLabel mensagemRecebidaTextArea = new JLabel("Mensagens Recebidas");
		mensagemRecebidaTextArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mensagemRecebidaTextArea.setBounds(189, 138, 172, 27);
		getContentPane().add(mensagemRecebidaTextArea);
		
		JScrollPane scrollPaneRecebidas = new JScrollPane();
		scrollPaneRecebidas.setBounds(189, 178, 508, 169);
		getContentPane().add(scrollPaneRecebidas);

		mensagensRecebidasTextArea = new JTextArea();
		scrollPaneRecebidas.setViewportView(mensagensRecebidasTextArea);
		mensagensRecebidasTextArea.setForeground(Color.BLACK);
		mensagensRecebidasTextArea.setBackground(Color.WHITE);
		mensagensRecebidasTextArea.setEditable(false);
		
		JLabel tipoMensagemLabel = new JLabel("Tipo de Mensagem");
		tipoMensagemLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tipoMensagemLabel.setBounds(20, 80, 122, 27);
		getContentPane().add(tipoMensagemLabel);

		todasRadioButton = new JRadioButton("Todas");
		todasRadioButton.setSelected(true);
		buttonGroup.add(todasRadioButton);
		todasRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		todasRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		todasRadioButton.setBounds(20, 124, 135, 21);
		getContentPane().add(todasRadioButton);

		FSOradioButton = new JRadioButton("FSO");
		buttonGroup.add(FSOradioButton);
		FSOradioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		FSOradioButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		FSOradioButton.setBounds(20, 184, 135, 21);
		getContentPane().add(FSOradioButton);

		RobotsRadioButton = new JRadioButton("Robots");
		buttonGroup.add(RobotsRadioButton);
		RobotsRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		RobotsRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		RobotsRadioButton.setBounds(20, 254, 135, 21);
		getContentPane().add(RobotsRadioButton);
		
		javaRadioButton = new JRadioButton("Java");
		buttonGroup.add(javaRadioButton);
		javaRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		javaRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		javaRadioButton.setBounds(20, 326, 135, 21);
		getContentPane().add(javaRadioButton);

		terminarBTN = new JButton("Terminar");
		terminarBTN.setEnabled(false);
		terminarBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				states.numState = states.TERMINATE_APP;
			}
		});
		terminarBTN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		terminarBTN.setBounds(387, 136, 154, 30);
		getContentPane().add(terminarBTN);
		
		JScrollPane pane = new JScrollPane ();
		pane.getViewport ().setView ( mensagemRecebidaTextArea );
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		setVisible(true);
	}

	/**
	 * Metodo que verifica o ficheiro do canal de comunicacao
	 */
	private void checkFileCC() {
		String filename = File.separator+"tmp";
        JFileChooser chooser = new JFileChooser(new File(filename));
        JFrame frame = null;
        int result = chooser.showSaveDialog(frame );
        if(result == JFileChooser.CANCEL_OPTION) {
            messageWarning.showMessageDialog(null, "A opção de cancel foi selecionada!","Warning!",JOptionPane.ERROR_MESSAGE);
        }else {
            File file = chooser.getSelectedFile();
            System.out.println(file);
            FilePath=file.getAbsolutePath();
            fileNameTextArea.append(file.getAbsolutePath());
        }
	}
	
	/**
	 * Metodo que permite abrir e criar um canal de comunicacao
	 */
	private void createCC() {
		enviarBTN.setEnabled(true);
		messageWarning.showMessageDialog(null, "Foi criado um canal de comunicação!","Warning!",JOptionPane.ERROR_MESSAGE);
	}

	public boolean getAbrirButton() {
		return(this.abrirBTN.getModel().isArmed() ? true:false);
	}
	
	public boolean isJavaRadio() {
		return javaRadioButton.isSelected();
	}
	
	public boolean isTodasRadio() {
		return todasRadioButton.isSelected();
	}
	
	public boolean isFSORadio() {
		return FSOradioButton.isSelected();
	}
	
	public boolean isRobotsRadio() {
		return RobotsRadioButton.isSelected();
	}

	public JTextArea getMensagensRecebidasTextArea() {
		return mensagensRecebidasTextArea;
	}

	/**
	 * Método que permite criar uma scrollbar quando sao enviadas muitas mensagens em simultaneio
	 * @param mensagensRecebidasTextArea
	 */
	public void setMensagensRecebidasTextArea(String mensagensRecebidasTextArea) {
		this.mensagensRecebidasTextArea.append(mensagensRecebidasTextArea);
		this.mensagensRecebidasTextArea.setCaretPosition(this.mensagensRecebidasTextArea.getDocument().getLength());
	}

	public JTextArea getEnviarTextArea() {
		return enviarTextArea;
	}

	public void setEnviarTextArea(JTextArea enviarTextArea) {
		this.enviarTextArea = enviarTextArea;
	}
	
}
