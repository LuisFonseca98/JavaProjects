
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GuiSpy extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -3154918662776834909L;
    private JPanel contentPane;
    private JTextField textFilePath;
    private JTextArea textArea;
    private JButton buttonReproduzir;
    private JButton btnRecord;
    private JLabel lblFicheiro;
    private JScrollPane scrollLog;
    private boolean pausa;

    /**
     * Create the frame.
     */
    public GuiSpy(SpyRobot spy) {
        pausa = false;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblFicheiro = new JLabel("Spy " + spy.getNome());
        lblFicheiro.setHorizontalAlignment(SwingConstants.LEFT);
        lblFicheiro.setBounds(29, 11, 70, 25);
        contentPane.add(lblFicheiro);

        textFilePath = new JTextField();
        textFilePath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String msg = textFilePath.getText();
                try {
                    spy.setFile(msg);
                    descongelarBotoes(true);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });
        lblFicheiro.setLabelFor(textFilePath);
        textFilePath.setBounds(109, 13, 183, 20);
        contentPane.add(textFilePath);
        textFilePath.setColumns(10);

        btnRecord = new JButton("Gravar");

        btnRecord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                spy.toggleGravacao();
            }
        });
        btnRecord.setBounds(50, 47, 150, 60);
        contentPane.add(btnRecord);

        buttonReproduzir = new JButton("Reproduzir");
        buttonReproduzir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spy.toggleReproducao();
                textFilePath.setEditable(false);
            }
        });
        buttonReproduzir.setBounds(237, 47, 150, 60);
        contentPane.add(buttonReproduzir);

        textArea = new JTextArea();
        textArea.setBounds(50, 118, 337, 132);
        textArea.setEditable(false);
        contentPane.add(textArea);
        scrollLog = new JScrollPane(textArea);
        scrollLog.setBounds(50, 118, 337, 132);
        contentPane.add(scrollLog);

        descongelarBotoes(false);


    }

    public void myPrint(String msg) {
        textArea.append(msg);
    }

    public void Gravar(boolean gravar) {
        buttonReproduzir.setEnabled(!gravar);
        textFilePath.setEditable(!gravar);
        if (gravar)
            btnRecord.setText("Stop");
        else
            btnRecord.setText("Gravar");
    }

    public void Reproduzir(boolean reproduzir) {
        btnRecord.setEnabled(!reproduzir);
        buttonReproduzir.setEnabled(!reproduzir);
        textFilePath.setEditable(!reproduzir);
        if (reproduzir) {

            buttonReproduzir.setText("A Reproduzir");
        } else
            buttonReproduzir.setText("Reproduzir");
    }

    public void descongelarBotoes(boolean b) {
        buttonReproduzir.setEnabled(b);
        btnRecord.setEnabled(b);
    }

    public void setLabel(String name) {
        lblFicheiro.setText(name);
    }


    public boolean isPausa() {
        return pausa;
    }
}
