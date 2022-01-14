import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class GuiDancarino extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -2243053666059074666L;
    private JPanel contentPane;
    private JTextField robotTextField;
    private JTextField textField_Consola;
    private JTextField textField_Raio;
    private JTextField textField_Angulo;
    private JTextField textField_Distancia;
    private JRadioButton onOffRadioButton;
    private JLabel lblRobot;
    private JButton btnFrente, btnParar, btnRetaguarda, btnDireita, btnEsquerda;
    private JCheckBox chckbxAtivar;
    private BD bd;
    private Semaphore s;

    /**
     * Create the frame.
     */
    public GuiDancarino(Dancarino d, BD bd) {

        s = bd.getSem();
        this.bd = bd;
        setTitle("Grupo 2- " + d.getName());
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBackground(Color.WHITE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblRobot = new JLabel("ROBOT");
        lblRobot.setHorizontalAlignment(SwingConstants.LEFT);
        lblRobot.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblRobot.setBounds(10, 11, 48, 30);
        contentPane.add(lblRobot);

        robotTextField = new JTextField("" + bd.getNomeRobot());
        robotTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                bd.setNomeRobot(robotTextField.getText());
                myPrint(bd.getNomeRobot());
            }
        });
        robotTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        robotTextField.setBounds(68, 12, 96, 20);
        contentPane.add(robotTextField);
        robotTextField.setColumns(10);

        onOffRadioButton = new JRadioButton("On/Off");
        onOffRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (bd.isOnOff()) {
                    bd.CloseEV3();
                    bd.setOnOff(false);
                } else {
                    bd.setOnOff(bd.OpenEV3(bd.getNomeRobot()));
                }
                updateButtons(isAtivar());
                onOffRadioButton.setSelected(bd.isOnOff());
            }
        });
        onOffRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        onOffRadioButton.setBounds(319, 11, 109, 20);
        contentPane.add(onOffRadioButton);

        JLabel lblRadio = new JLabel("Raio");
        lblRadio.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblRadio.setBounds(10, 52, 37, 22);
        contentPane.add(lblRadio);

        JLabel lblAngulo = new JLabel("Angulo");
        lblAngulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAngulo.setBounds(146, 53, 48, 20);
        contentPane.add(lblAngulo);

        JLabel lblDistancia = new JLabel("Distancia");
        lblDistancia.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDistancia.setBounds(278, 53, 66, 20);
        contentPane.add(lblDistancia);

        JCheckBox chckbxDebug = new JCheckBox("Debug", true);
        chckbxDebug.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                bd.setDebug(!bd.isDebug());
                updateButtons(bd.isDebug() || chckbxAtivar.isSelected());
                myPrint("" + bd.isDebug());
            }
        });
        chckbxDebug.setBounds(10, 206, 97, 23);
        contentPane.add(chckbxDebug);

        chckbxAtivar = new JCheckBox("Ativar");
        chckbxAtivar.setBounds(360, 206, 131, 23);
        chckbxAtivar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateButtons(isAtivar());
            }
        });
        contentPane.add(chckbxAtivar);

        textField_Consola = new JTextField("" + bd.getConsola());
        textField_Consola.setBounds(10, 236, 418, 20);
        contentPane.add(textField_Consola);
        textField_Consola.setColumns(10);

        textField_Raio = new JTextField("" + bd.getRaio());
        textField_Raio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bd.setRaio(Integer.parseInt(textField_Raio.getText()));
                myPrint("Raio " + bd.getRaio());
            }
        });
        textField_Raio.setBounds(52, 55, 55, 20);
        contentPane.add(textField_Raio);
        textField_Raio.setColumns(10);

        textField_Angulo = new JTextField("" + bd.getAngulo());
        textField_Angulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bd.setAngulo(Integer.parseInt(textField_Angulo.getText()));
                myPrint("Angulo " + bd.getAngulo());
            }
        });
        textField_Angulo.setColumns(10);
        textField_Angulo.setBounds(204, 55, 55, 20);
        contentPane.add(textField_Angulo);

        textField_Distancia = new JTextField("" + bd.getDistancia());
        textField_Distancia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bd.setDistancia(Integer.parseInt(textField_Distancia.getText()));
                myPrint("Distancia " + bd.getDistancia());
            }
        });
        textField_Distancia.setColumns(10);
        textField_Distancia.setBounds(354, 55, 55, 20);
        contentPane.add(textField_Distancia);

        btnFrente = new JButton("FRENTE");
        btnFrente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    s.acquire();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                frente(bd.getDistancia());
                parar(false);
                s.release();
            }
        });
        btnFrente.setBackground(Color.GREEN);
        btnFrente.setForeground(Color.BLACK);
        btnFrente.setBounds(146, 98, 113, 30);
        contentPane.add(btnFrente);

        btnParar = new JButton("PARAR");
        btnParar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    s.acquire();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                d.Evitar();
                parar(true);
                s.release();
            }
        });
        btnParar.setForeground(Color.BLACK);
        btnParar.setBackground(Color.RED);
        btnParar.setBounds(146, 126, 113, 30);
        contentPane.add(btnParar);

        btnRetaguarda = new JButton("RETAGUARDA");
        btnRetaguarda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    s.acquire();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                retaguarda(bd.getDistancia());
                parar(false);
                s.release();
            }
        });
        btnRetaguarda.setForeground(Color.BLACK);
        btnRetaguarda.setBackground(Color.GREEN);
        btnRetaguarda.setBounds(146, 153, 113, 30);
        contentPane.add(btnRetaguarda);

        btnDireita = new JButton("DIREITA");
        btnDireita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    s.acquire();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                CurvarDireita(bd.getRaio(), bd.getAngulo());
                parar(false);
                s.release();
            }
        });
        btnDireita.setForeground(Color.BLACK);
        btnDireita.setBackground(Color.YELLOW);
        btnDireita.setBounds(257, 126, 113, 30);
        contentPane.add(btnDireita);

        btnEsquerda = new JButton("ESQUERDA");
        btnEsquerda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    s.acquire();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                CurvarEsquerda(bd.getRaio(), bd.getAngulo());
                parar(false);
                s.release();
            }
        });
        btnEsquerda.setForeground(Color.BLACK);
        btnEsquerda.setBackground(Color.YELLOW);
        btnEsquerda.setBounds(35, 126, 113, 30);
        contentPane.add(btnEsquerda);

        updateButtons(bd.isOnOff());

        setVisible(false);
    }

    /**
     * Faz print no textField de debug da GUI e guarda na BD o log
     *
     * @param s
     */
    public void myPrint(String s) {
        if (bd.isDebug()) {
            bd.setConsola(s);
            textField_Consola.setText(bd.getConsola());
        }
    }

    public boolean isOnOff() {
        return bd.isOnOff() && bd.OpenEV3(bd.getNomeRobot());
    }

    public void frente(int cm) {
        myPrint("Andar para a frente " + cm + "cm");
        bd.frente(cm);
    }

    public void CurvarDireita(int raio, int angulo) {
        myPrint("Curvar Direita " + raio + " " + angulo);
        bd.CurvarDireita(raio, angulo);

    }

    public void CurvarEsquerda(int raio, int angulo) {
        myPrint("Curvar Esquerda " + raio + " " + angulo);
        bd.CurvarEsquerda(raio, angulo);
    }

    public void retaguarda(int cm) {
        myPrint("Andar para tras " + cm + "cm");
        frente(-1 * cm);
    }

    public void parar(boolean imediato) {
        myPrint("Parar (" + imediato + ")");
        bd.parar(imediato);
    }

    public void updateValores() {
        bd.setDistancia(Integer.parseInt(textField_Distancia.getText()));
        bd.setAngulo(Integer.parseInt(textField_Angulo.getText()));
        bd.setRaio(Integer.parseInt(textField_Raio.getText()));

        textField_Consola.setText(bd.getConsola());
        textField_Angulo.setText("" + bd.getAngulo());
        textField_Distancia.setText("" + bd.getDistancia());
        textField_Raio.setText("" + bd.getRaio());
    }

    public void updateButtons(boolean estados) {
        btnFrente.setEnabled(estados);
        btnRetaguarda.setEnabled(estados);
        btnEsquerda.setEnabled(estados);
        btnDireita.setEnabled(estados);
        btnParar.setEnabled(estados);
    }

    public boolean isAtivar() {
        return chckbxAtivar.isSelected();
    }

    public void toggleAtivar() {
        if (!isAtivar()) {
            ativar();
        } else {
            desativar();
        }
        updateButtons(isAtivar());
    }

    public void desativar() {
        chckbxAtivar.setSelected(false);
        updateButtons(isAtivar());
    }

    public void ativar() {
        chckbxAtivar.setSelected(true);
        updateButtons(isAtivar());
    }
}
