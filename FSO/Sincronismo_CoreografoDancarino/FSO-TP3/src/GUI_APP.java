import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GUI_APP extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel PanelConteudo, PanelCoreografo, PanelDancarino;
    private JTextArea logging;
    private JList<String> listDancarino;
    private JList<String> listCoreografo;
    private JButton buttonAddCoreografo, buttonAddDancarino, buttonRemoveCoreografo, buttonRemoveDancarino,
            buttonIniciarCoreografo, buttonIniciarDancarino, buttonLimpar;
    private JRadioButton radioAtivarTodosDanc, radioAtivarTodosCor;
    private Controlador controlador;
    private DefaultListModel<String> listaStringCoreo = new DefaultListModel<String>();
    private DefaultListModel<String> listaStringDanc = new DefaultListModel<String>();
    private int selectedIndex;
    private JScrollPane scrollLog;
    private JScrollPane scrollCor;
    private JScrollPane scrollDanc;

    private boolean isCoreosAtivos;
    private boolean isDancAtivos;

    /**
     * Create the frame.
     */
    public GUI_APP(Controlador control) {
        this.controlador = control;

        isCoreosAtivos = false;
        isDancAtivos = false;
        setTitle("Trabalho 2- ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 709, 715);
        PanelConteudo = new JPanel();
        PanelConteudo.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(PanelConteudo);
        PanelConteudo.setLayout(null);

        PanelCoreografo = new JPanel();
        PanelCoreografo.setBounds(41, 37, 283, 372);
        PanelConteudo.add(PanelCoreografo);
        PanelCoreografo.setBorder(new TitledBorder(new EtchedBorder(), "Coreografos"));
        PanelCoreografo.setLayout(null);

        listCoreografo = new JList<String>(listaStringCoreo);
        listCoreografo.setBorder(new LineBorder(new Color(0, 0, 0)));
        listCoreografo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                selectedIndex = listCoreografo.getSelectedIndex();
                if (evt.getClickCount() == 2) {
                    Coreografo c = controlador.getCoreografo(selectedIndex);
                    c.setVisible(!c.isVisible());
                    c.iniciar();
                }
            }
        });
        listCoreografo.setBounds(10, 21, 261, 130);
        PanelCoreografo.add(listCoreografo);

        scrollCor = new JScrollPane(listCoreografo);
        scrollCor.setBounds(10, 21, 261, 130);
        PanelCoreografo.add(scrollCor);

        buttonAddCoreografo = new JButton("Adicionar");
        buttonAddCoreografo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Coreografo c = controlador.addNovoCoreografo();
                listaStringCoreo.addElement(c.getName());
                myPrint(controlador.getCoreografoName(controlador.getCoreografos().size() - 1) + " Created");
            }
        });
        buttonAddCoreografo.setBounds(10, 162, 261, 41);
        PanelCoreografo.add(buttonAddCoreografo);

        buttonRemoveCoreografo = new JButton("Remover");
        buttonRemoveCoreografo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                selectedIndex = listCoreografo.getSelectedIndex();
                if (selectedIndex == -1) {
                    if (listaStringCoreo.size() > 0) {
                        myPrint(controlador.getCoreografoName(listaStringCoreo.getSize() - 1) + " Removed");
                        controlador.remCoreografo(listaStringCoreo.getSize() - 1);
                        listaStringCoreo.remove(listaStringCoreo.getSize() - 1);
                    }
                } else {
                    // Se houver um selecionado, é porque está guardado na lista, logo o tamanho é
                    // maior que 0
                    myPrint(controlador.getCoreografoName(selectedIndex) + " Removed");
                    controlador.remCoreografo(selectedIndex);
                    listaStringCoreo.remove(selectedIndex);
                }
            }
        });
        buttonRemoveCoreografo.setBounds(10, 214, 261, 41);
        PanelCoreografo.add(buttonRemoveCoreografo);

        buttonIniciarCoreografo = new JButton("Iniciar Todos");
        buttonIniciarCoreografo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.iniciarCoreografos();
                ArrayList<Coreografo> localCoreos = controlador.getCoreografos();
                for (Coreografo c : localCoreos) {
                    myPrint(c.getName() + "- Iniciado");
                }

                buttonAddCoreografo.setEnabled(false);
                buttonRemoveCoreografo.setEnabled(false);
                buttonIniciarCoreografo.setEnabled(false);
            }
        });

        buttonIniciarCoreografo.setBounds(10, 266, 261, 41);
        PanelCoreografo.add(buttonIniciarCoreografo);

        radioAtivarTodosCor = new JRadioButton("Ativa todos");
        radioAtivarTodosCor.setBounds(10, 327, 109, 23);
        radioAtivarTodosCor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Coreografo> localCoreos = controlador.getCoreografos();
                if (radioAtivarTodosCor.isSelected()) {
                    for (Coreografo coreografo : localCoreos) {
                        controlador.ativarCoreografo(coreografo);
                        myPrint(coreografo.getName() + " - Ativado");
                    }
                } else {
                    for (Coreografo coreografo : localCoreos) {
                        controlador.desativarCoreografo(coreografo);
                        myPrint(coreografo.getName() + " - Desativo");

                    }
                }
            }
        });

        PanelCoreografo.add(radioAtivarTodosCor);

        PanelDancarino = new JPanel();
        PanelDancarino.setBounds(367, 37, 283, 372);
        PanelConteudo.add(PanelDancarino);
        PanelDancarino.setBorder(new TitledBorder(new EtchedBorder(), "Dancarinos"));
        PanelDancarino.setLayout(null);

        listDancarino = new JList<String>(listaStringDanc);
        listDancarino.setBorder(new LineBorder(new Color(0, 0, 0)));
        listDancarino.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                selectedIndex = listDancarino.getSelectedIndex();
                if (evt.getClickCount() == 2) {
                    Dancarino d = controlador.getDancarino(selectedIndex);
                    d.setVisible(!d.isVisible());
                    d.iniciar();
                }
            }
        });
        listDancarino.setBounds(10, 21, 261, 130);
        PanelDancarino.add(listDancarino);

        scrollDanc = new JScrollPane(listDancarino);
        scrollDanc.setBounds(10, 21, 261, 130);
        PanelDancarino.add(scrollDanc);

        buttonAddDancarino = new JButton("Adicionar");
        buttonAddDancarino.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dancarino d = controlador.addNovoDancarino();
                listaStringDanc.addElement(d.getName());
                myPrint(controlador.getDancarinoName((controlador.getDancarinos().size() - 1)) + " Created");
            }
        });
        buttonAddDancarino.setBounds(10, 162, 261, 41);
        PanelDancarino.add(buttonAddDancarino);

        buttonRemoveDancarino = new JButton("Remover");
        buttonRemoveDancarino.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                selectedIndex = listDancarino.getSelectedIndex();

                if (selectedIndex != -1) {
                    myPrint(controlador.getDancarinoName(selectedIndex) + " Removed");
                    controlador.remDancarino(selectedIndex);
                    listaStringDanc.remove(selectedIndex);
                } else {
                    if (listaStringDanc.size() > 0) {
                        myPrint(controlador.getDancarinoName(listaStringDanc.getSize() - 1) + " Removed");
                        controlador.remDancarino(listaStringDanc.getSize() - 1);
                        listaStringDanc.remove(listaStringDanc.getSize() - 1);
                    }
                }
            }
        });
        buttonRemoveDancarino.setBounds(10, 214, 261, 41);
        PanelDancarino.add(buttonRemoveDancarino);

        buttonIniciarDancarino = new JButton("Iniciar Todos");
        buttonIniciarDancarino.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.iniciarDancarinos();

                ArrayList<Dancarino> a = controlador.getDancarinos();
                for (int i = 0; i < a.size(); i++) {
                    myPrint(a.get(i).getName() + "- Iniciado");
                }

                buttonAddDancarino.setEnabled(false);
                buttonRemoveDancarino.setEnabled(false);
                buttonIniciarDancarino.setEnabled(false);
            }
        });
        buttonIniciarDancarino.setBounds(10, 266, 261, 41);
        PanelDancarino.add(buttonIniciarDancarino);

        radioAtivarTodosDanc = new JRadioButton("Ativa todos");
        radioAtivarTodosDanc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                ArrayList<Dancarino> a = controlador.getDancarinos();

                for (int i = 0; i < a.size(); i++) {
                    if (controlador.ativarDancarino(a.get(i)))
                        myPrint(a.get(i).getName() + "- Ativado");
                    else
                        myPrint(a.get(i).getName() + "- Desativado");
                }
            }
        });
        radioAtivarTodosDanc.setBounds(10, 329, 109, 23);
        PanelDancarino.add(radioAtivarTodosDanc);

        buttonLimpar = new JButton("Limpar Logging");
        buttonLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                logging.setText("");
            }
        });
        buttonLimpar.setBounds(41, 420, 609, 47);
        PanelConteudo.add(buttonLimpar);

        logging = new JTextArea();
        logging.setEditable(false);
        logging.setBounds(41, 478, 609, 143);
        PanelConteudo.add(logging);

        scrollLog = new JScrollPane(logging);
        scrollLog.setBounds(41, 478, 609, 143);
        PanelConteudo.add(scrollLog);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {

            }
        });

        setVisible(true);
    }

    public void myPrint(String s) {
        controlador.setConsola(s);
        logging.append(controlador.getConsola() + '\n');
    }

    public boolean isCoreoAtivos() {
        return isCoreosAtivos;
    }

    public boolean isDancAtivos() {
        return isDancAtivos;
    }
}
