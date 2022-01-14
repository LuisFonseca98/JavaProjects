import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ServidorDoRoboGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton limparTextAreaBTN;
	private ServidorDoRobo servidor;
	private JScrollPane scrollPane;
	private JButton btnNewButton;
	public JTextArea servidorMensagensTextArea;

	public ServidorDoRoboGUI(ServidorDoRobo servidor) throws IOException, InterruptedException {
		
		
		this.servidor = servidor;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 475);
		setTitle("GUI Servidor Robo");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JLabel MensagensRecebidasLabel = new JLabel("Mensagens Recebidas");
		MensagensRecebidasLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		MensagensRecebidasLabel.setBounds(10, 10, 193, 20);
		contentPane.add(MensagensRecebidasLabel);
		
		
		
		limparTextAreaBTN = new JButton("Limpar Area Texto");
		limparTextAreaBTN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		limparTextAreaBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servidorMensagensTextArea.setText("");
			}
		});
		limparTextAreaBTN.setBounds(10, 388, 692, 29);
		contentPane.add(limparTextAreaBTN);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 692, 338);
		contentPane.add(scrollPane);
		
		
		servidorMensagensTextArea = new JTextArea();
		scrollPane.setViewportView(servidorMensagensTextArea);
		servidorMensagensTextArea.setEditable(false);
		
		btnNewButton = new JButton("Gravar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servidor.robo.comportamento = servidor.robo.INICIAR_GRAVAR;
				servidor.robo.Desenhador();
				btnNewButton.setEnabled(false);
			}
		});
		btnNewButton.setBounds(617, 12, 85, 21);
		contentPane.add(btnNewButton);
		
		DefaultCaret caret = (DefaultCaret)servidorMensagensTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		setVisible(true);
		
	}
}
