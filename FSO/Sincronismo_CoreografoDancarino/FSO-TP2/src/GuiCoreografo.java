import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class GuiCoreografo extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea ComandosRegistadosText;

    private boolean infinito, coreoAtivo;
    private int numOrdensAEnviar;
    private ArrayList<MyMensagem> ultimosComandos;

    private JRadioButton AtivarRadioButton;
    private JButton GerarComandosIlimitadosButton;

    private JButton Gerar1ComandoButton;
    private JButton Gerar16ComandosButton;
    private JButton Gerar32ComandosButton;
    private JButton PararComandoButton;

    /**
     * Create the frame.
     */
    public GuiCoreografo(final Coreografo p) {
        numOrdensAEnviar = 0;
        infinito = false;
        coreoAtivo = false;

        setTitle("Grupo 2- " + p.getName());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 729, 504);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel UltimosComandosLabel = new JLabel("Ultimos 10 Comandos");
        UltimosComandosLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        UltimosComandosLabel.setBounds(76, 10, 167, 20);
        contentPane.add(UltimosComandosLabel);

        ComandosRegistadosText = new JTextArea();
        ComandosRegistadosText.setFont(new Font("Monospaced", Font.PLAIN, 18));
        ComandosRegistadosText.setBounds(10, 40, 324, 417);
        contentPane.add(ComandosRegistadosText);
        ComandosRegistadosText.setColumns(20);

        AtivarRadioButton = new JRadioButton("Ativar");
        AtivarRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        AtivarRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                AtivarRadioButton.setSelected(isCoreoAtivo());
                updateButtons(AtivarRadioButton.isSelected());
                if (!isCoreoAtivo())
                    p.enviarParar();

            }
        });
        AtivarRadioButton.setBounds(375, -2, 167, 44);
        contentPane.add(AtivarRadioButton);

        Gerar1ComandoButton = new JButton("Gerar 1 Comando");
        Gerar1ComandoButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Gerar1ComandoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                p.adicionaOrdensAEnviar(1);
            }
        });
        Gerar1ComandoButton.setBounds(375, 48, 259, 40);
        contentPane.add(Gerar1ComandoButton);

        Gerar16ComandosButton = new JButton("Gerar 16 Comando");
        Gerar16ComandosButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Gerar16ComandosButton.setBounds(375, 122, 259, 40);
        Gerar16ComandosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                p.adicionaOrdensAEnviar(15);
            }

        });
        contentPane.add(Gerar16ComandosButton);

        Gerar32ComandosButton = new JButton("Gerar 32 Comando");
        Gerar32ComandosButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Gerar32ComandosButton.setBounds(375, 207, 259, 40);
        Gerar32ComandosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                p.adicionaOrdensAEnviar(31);
            }
        });
        contentPane.add(Gerar32ComandosButton);

        GerarComandosIlimitadosButton = new JButton("Gerar Comandos Ilimitados");
        GerarComandosIlimitadosButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        GerarComandosIlimitadosButton.setBounds(375, 292, 259, 40);
        GerarComandosIlimitadosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                infinito = true;
            }
        });
        contentPane.add(GerarComandosIlimitadosButton);

        PararComandoButton = new JButton("Parar Comandos");
        PararComandoButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        PararComandoButton.setBounds(375, 370, 259, 40);
        PararComandoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                infinito = false;
            }
        });
        contentPane.add(PararComandoButton);

        updateButtons(false);
        setVisible(false);

    }

    public boolean isCoreoAtivo() {
        return AtivarRadioButton.isSelected();
    }

    public void setCoreoAtivo(boolean coreoAtivo) {
        this.coreoAtivo = coreoAtivo;
    }

    public void display(boolean b) {
        setVisible(b);
    }

    public int getNumOrdensAEnviar() {
        return numOrdensAEnviar;
    }

    public void setNumOrdensAEnviar(int i) {
        numOrdensAEnviar = i;
    }

    public void setUltimosComandos(ArrayList<MyMensagem> ultimosComandos) {
        this.ultimosComandos = ultimosComandos;
        String s = Arrays.toString(ultimosComandos.toArray()).replaceAll(", ", "\n").replaceAll("\\[|\\]", "");
        ComandosRegistadosText.setText(s);
    }

    public void updateButtons(boolean estados) {
        PararComandoButton.setEnabled(estados);
        GerarComandosIlimitadosButton.setEnabled(estados);
        Gerar32ComandosButton.setEnabled(estados);
        Gerar16ComandosButton.setEnabled(estados);
        Gerar1ComandoButton.setEnabled(estados);
    }

    public boolean isInfinito() {
        return infinito;
    }

    public void setInfinito(boolean b) {

        infinito = b;
    }

    public void Ativar() {
        AtivarRadioButton.setSelected(!isCoreoAtivo());
        updateButtons(AtivarRadioButton.isSelected());

    }

    public void setAtivar() {
        Ativar();
    }
}
