package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import General.Machine;


public class FenetreTerminal {

	private Fenetre fenetre;
	private Machine machine;
	private JTextArea terminal;

	private JPanel contenuPane;
    private JPanel contenuTerminalPane;

	public FenetreTerminal(Machine mMachine, Fenetre fenetreMenu) {
		
		this.machine = mMachine;
		this.fenetre = fenetreMenu;

		this.contenuPane = (JPanel) this.fenetre.getContentPane();

		this.contenuTerminalPane = new JPanel(new BorderLayout());
		this.contenuTerminalPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contenuPane.add(contenuTerminalPane);

		creerTerminal();
	}

	public Fenetre getFenetre() {

		return this.fenetre;
	}

	public JPanel getContenuPane() {

		return this.contenuTerminalPane;
	}

	private void creerTerminal() {

		this.terminal = new JTextArea();
		this.terminal.setCaretColor(new Color(255, 255, 255));
		this.terminal.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 5));
		this.terminal.setFont(new Font("Monospaced", Font.PLAIN, 14));
		this.terminal.setBackground(new Color(0, 0, 0));
		this.terminal.setForeground(new Color(255, 255, 255));
		JScrollPane scrollPane = new JScrollPane(this.terminal);
		this.terminal.setText(this.machine + ":~$ ");
		this.contenuTerminalPane.add(scrollPane, BorderLayout.CENTER);

		terminal.addKeyListener(new KeyListener() {

			private int minPositionCurseur = terminal.getCaretPosition();
			private boolean backspaceDesactive;
			private int nbrEntree;
			private final String actiontParDefaut = (String) terminal.getInputMap().get(KeyStroke.getKeyStroke("BACK_SPACE"));

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            		
            		nbrEntree++;
                	String regex = machine + ":~$ ";
                	String texteTerminal = terminal.getText();
                	String[] lignes = texteTerminal.split(machine + ":~\\$ ");

                	String commande = "";
                	if (lignes.length != 0) {
                		commande = lignes[lignes.length - 1].replace("\n", "");
                	}

                	String output = machine.getTerminal().executer(commande);
                	if (nbrEntree != 1) {
						if (!commande.equals("") && !output.equals("")) {
                			terminal.setText(terminal.getText() + output + "\n");
                		}
                		if (commande.equals("clear")) {
                			terminal.setText(machine + ":~$ ");
							nbrEntree = 0;
                		}
                		else {
                			terminal.setText(terminal.getText() + regex);
                		}
                	}
                	else {
                		if (!commande.equals("") && !output.equals("")) {
							terminal.setText(terminal.getText() + "\n" + output);
                		}
                		if (commande.equals("clear")) {
                			terminal.setText(machine + ":~$ ");
							nbrEntree = 0;
                		}
                		else {
                			terminal.setText(terminal.getText() + "\n" + regex);
                		}
                	}
                }
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                	
                	int positionCurseur = terminal.getCaretPosition();
                	if (positionCurseur <= minPositionCurseur && !this.backspaceDesactive) {
                		this.backspaceDesactive = true;
		            	terminal.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "none");
		            }
					else if (positionCurseur > minPositionCurseur && this.backspaceDesactive) {
						this.backspaceDesactive = false;
						terminal.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), actiontParDefaut);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

	            if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
	                
	                terminal.setCaretPosition(terminal.getCaretPosition() - 1);
            		this.minPositionCurseur = terminal.getCaretPosition(); 
            	}
            }

             @Override
            public void keyTyped(KeyEvent e) {
            }
		});
	}
}