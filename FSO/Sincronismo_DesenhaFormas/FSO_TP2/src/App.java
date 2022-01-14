
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class App extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nomeRoboTextField;
	private JButton CriarServidorBTN;
	private JCheckBox inicarRoboCheckBox;
	
	public Quadrado quadrado;
	public Circulo circulo;
	public Espacar espacar;
	public JButton criarComportamentosBTN, btnDesenharQuadrado, btnDesenharCirculo;
	public ClienteDoRobo clienteRobo;
	public Semaphore semSinc;
	public int tipoMensagem, nComportamentos, raioDistAnterior;
	public JTextArea anguloText;


	public App() throws IOException, InterruptedException {

		setTitle("Trabalho 2 GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 759, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tipoMensagem = 0;
		nComportamentos = 0;
		raioDistAnterior = 0;
		clienteRobo = new ClienteDoRobo();
		circulo = new Circulo(clienteRobo,this);
		quadrado = new Quadrado(clienteRobo, this);
		espacar = new Espacar(clienteRobo, this);
		semSinc = new Semaphore(1);

		JLabel nomeRoboPanel = new JLabel("Nome do Robo");
		nomeRoboPanel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nomeRoboPanel.setBounds(10, 20, 112, 34);
		contentPane.add(nomeRoboPanel);

		nomeRoboTextField = new JTextField();
		nomeRoboTextField.setBounds(166, 25, 406, 29);
		contentPane.add(nomeRoboTextField);
		nomeRoboTextField.setColumns(10);

		inicarRoboCheckBox = new JCheckBox("Iniciar Robo");
		inicarRoboCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clienteRobo.server.robo.nomeRobot = nomeRoboTextField.getText();
				clienteRobo.server.robo.comportamento = clienteRobo.server.robo.OPEN_ROBOT;
				clienteRobo.server.robo.Desenhador();
			}
		});
		
		inicarRoboCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inicarRoboCheckBox.setBounds(609, 25, 130, 25);
		contentPane.add(inicarRoboCheckBox);

		JLabel ComportamentosLabel = new JLabel("Comportamentos");
		ComportamentosLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ComportamentosLabel.setBounds(291, 104, 145, 34);
		contentPane.add(ComportamentosLabel);

		criarComportamentosBTN = new JButton("Cria Comportamentos");
		criarComportamentosBTN.setFont(new Font("Tahoma", Font.PLAIN, 16));
		criarComportamentosBTN.setBounds(52, 148, 289, 34);
		contentPane.add(criarComportamentosBTN);

		criarComportamentosBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				quadrado.start();
				circulo.start();
				espacar.start();

				btnDesenharQuadrado.setEnabled(true);
				btnDesenharCirculo.setEnabled(true);
				criarComportamentosBTN.setEnabled(false);

			}
		});		

		CriarServidorBTN = new JButton("Criar Servidor");
		CriarServidorBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clienteRobo.iniciarTarefaServidor();
				CriarServidorBTN.setEnabled(false);
			}
		});
		CriarServidorBTN.setFont(new Font("Tahoma", Font.PLAIN, 16));
		CriarServidorBTN.setBounds(390, 148, 289, 34);
		contentPane.add(CriarServidorBTN);

		btnDesenharQuadrado = new JButton("Desenhar Quadrado");
		btnDesenharQuadrado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clienteRobo.server.robo.GUIopen) {
					if(!clienteRobo.server.robo.gravarGUI.reproBool) {
						tipoMensagem = 1;
						if(nComportamentos == 0) {
							raioDistAnterior = quadrado.gerarRandomQuadrado();
							quadrado.setDist(raioDistAnterior);
							quadrado.avancarQuadrado();
						}else {
							espacar.avancarEspacar();
						}
						nComportamentos++;
					}
				}else {
					tipoMensagem = 1;
					if(nComportamentos == 0) {
						raioDistAnterior = quadrado.gerarRandomQuadrado();
						quadrado.setDist(raioDistAnterior);
						quadrado.avancarQuadrado();
						
					}else {
						espacar.avancarEspacar();
					}
					nComportamentos++;
				}
			}
		});
		btnDesenharQuadrado.setBounds(128, 253, 161, 47);
		contentPane.add(btnDesenharQuadrado);
		btnDesenharQuadrado.setEnabled(false);

		btnDesenharCirculo = new JButton("Desenhar Circulo");
		btnDesenharCirculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clienteRobo.server.robo.GUIopen) {
					if(!clienteRobo.server.robo.gravarGUI.reproBool) {
						tipoMensagem = 2;
		
						if(nComportamentos == 0) {
							raioDistAnterior = circulo.gerarRandomCirculo();
							circulo.setRaio(raioDistAnterior);
							circulo.avancarCirculo();
						}else {
							espacar.avancarEspacar();
						}
		
						nComportamentos++;
					}
				}else {
					tipoMensagem = 2;
					
					if(nComportamentos == 0) {
						raioDistAnterior = circulo.gerarRandomCirculo();
						circulo.setRaio(raioDistAnterior);
						circulo.avancarCirculo();
					}else {
						espacar.avancarEspacar();
					}
	
					nComportamentos++;
				}
			}
		});
		btnDesenharCirculo.setBounds(437, 253, 161, 47);
		contentPane.add(btnDesenharCirculo);
		btnDesenharCirculo.setEnabled(false);

		anguloText = new JTextArea();
		anguloText.setBounds(447, 310, 145, 22);
		contentPane.add(anguloText);
		
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		App appGUI = new App();
		appGUI.setVisible(true);
	}
}


