
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GravarFormasGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public JButton playpauseBtn,reproduceBtn; 
	public JTextField textField;
	public boolean gravarBool = false;
	public boolean reproBool = false;
	public GravarFormas gravar;


	public GravarFormasGUI(GravarFormas gravar) {
		this.gravar = gravar;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Gravar Formas Geometricas");
	
		
		playpauseBtn = new JButton("Play/Pause");
		playpauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gravarBool) {
					gravarBool = false;
				}else {
					gravarBool = true;
				}
			}
		});
		playpauseBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		playpauseBtn.setBounds(149, 89, 242, 59);
		contentPane.add(playpauseBtn);
		
		reproduceBtn = new JButton("Reproduce");
		reproduceBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		reproduceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravar.estado = gravar.REPRODUZIR;
				if(reproBool) {
					reproBool = false;
				}else {
					reproBool = true;
				}
			}
		});
		reproduceBtn.setBounds(202, 158, 158, 59);
		contentPane.add(reproduceBtn);
		
		JLabel lblNewLabel = new JLabel("Path");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(111, 55, 28, 13);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textField.setBounds(149, 54, 242, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton setBtn = new JButton("Set");
		setBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravar.fileName = textField.getText();
				gravar.estado = gravar.SET_FICHEIRO;
				playpauseBtn.setEnabled(true);
				reproduceBtn.setEnabled(true);
			}
		});
		setBtn.setBounds(400, 48, 62, 31);
		contentPane.add(setBtn);
	}
}
