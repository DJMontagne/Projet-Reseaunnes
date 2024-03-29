package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import General.Machine;

public class Menu {

	private Fenetre fenetre;
	private FenetreCarteR carteRConfig;
	private FenetreTerminal terminal;
	private Machine machine;
	private Repere repere;

	private JPanel contenuPane;
	private JPanel contenuMenuPane;
	private JPanel panelConfig;
	private JPanel panelTerminal;


	public Menu(Machine mMachine, Repere mRepere) {

		this.machine = mMachine;
		this.repere = mRepere;

		this.fenetre = new Fenetre(this.machine.toString(), 900, 500);

		this.contenuPane = (JPanel) this.fenetre.getContentPane();
		
		this.contenuMenuPane = new JPanel(new GridLayout(1, 2));
		this.contenuPane.add(contenuMenuPane);

		this.panelConfig = new JPanel();
		this.panelTerminal = new JPanel();

		this.contenuMenuPane.add(panelConfig);
		this.contenuMenuPane.add(panelTerminal);

		creerMenuConfig();
		creerMenuTerminal();
	}

	public Fenetre getFenetre() {

		return this.fenetre;
	}

	public JPanel getContenuPane() {

		return this.contenuMenuPane;
	}

	public void creerMenuConfig() {
		
		JPanel contenant = new JPanel(new BorderLayout());
		contenant.setBorder(new EmptyBorder(60, 0, 0, 0));
		Image image = new ImageIcon("./img/config.png").getImage();
		int largeur = 200;
		int hauteur = 200;
		Image imageReduite = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(imageReduite);
		JButton btnConfig = new JButton();
		btnConfig.setIcon(icon);
		btnConfig.setFocusPainted(false);
		btnConfig.setBackground(new Color(210, 210, 210));  
		btnConfig.setBorder(new RoundBorder(30, new Color(200, 200, 200)));
		btnConfig.setPreferredSize(new Dimension(300, 300));
		btnConfig.setLayout(new BorderLayout());
		JLabel texteConfig = new JLabel("Configuration");
		texteConfig.setFont(texteConfig.getFont().deriveFont(30.0f));
		texteConfig.setBorder(new EmptyBorder(10, 50, 0, 0));
		contenant.add(btnConfig, BorderLayout.NORTH);
		contenant.add(texteConfig, BorderLayout.SOUTH);
		this.panelConfig.add(contenant);
		
		btnConfig.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		           creerFenetreCarteR();
			}
		});

		btnConfig.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		        
		    	btnConfig.setBackground(new Color(180, 180, 180));
		    	btnConfig.setBorder(new RoundBorder(30, new Color(180, 180, 180)));    	
		    }
		    public void mouseExited(MouseEvent e) {
		    	
		    	btnConfig.setBackground(new Color(210, 210, 210));		
		    	btnConfig.setBorder(new RoundBorder(30, new Color(200, 200, 200)));    	
    		}
		});
	}

	private void creerFenetreCarteR() {
    	
		if (this.carteRConfig != null) {
			this.carteRConfig.getFenetre().setContentPane(this.carteRConfig.getContenuPane());
			this.carteRConfig.getFenetre().revalidate();
		}
		else {
			this.carteRConfig = new FenetreCarteR(this.machine, this.repere, this.fenetre);
			this.carteRConfig.getFenetre().setContentPane(this.carteRConfig.getContenuPane());
			this.carteRConfig.getFenetre().revalidate();
		}
    }

	public void creerMenuTerminal() {

		JPanel contenant = new JPanel(new BorderLayout());
		contenant.setBorder(new EmptyBorder(60, 0, 0, 0));
		Image image = new ImageIcon("./img/terminal.png").getImage();
		int largeur = 200;
		int hauteur = 200;
		Image imageReduite = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(imageReduite);
		JButton btnTerminal = new JButton();
		btnTerminal.setIcon(icon);
		btnTerminal.setFocusPainted(false);
		btnTerminal.setBackground(new Color(210, 210, 210));  
		btnTerminal.setBorder(new RoundBorder(30, new Color(200, 200, 200)));
		btnTerminal.setPreferredSize(new Dimension(300, 300));
		btnTerminal.setLayout(new BorderLayout());
		JLabel texteTerminal = new JLabel("Terminal");
		texteTerminal.setFont(texteTerminal.getFont().deriveFont(30.0f));
		texteTerminal.setBorder(new EmptyBorder(10, 80, 0, 0));
		contenant.add(btnTerminal, BorderLayout.NORTH);
		contenant.add(texteTerminal, BorderLayout.SOUTH);
		this.panelTerminal.add(contenant);
		
		btnTerminal.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		    	creerFenetreTerminal();
		    }
		});

		btnTerminal.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		        
		    	btnTerminal.setBackground(new Color(180, 180, 180));
		    	btnTerminal.setBorder(new RoundBorder(30, new Color(180, 180, 180)));    	
		    }
		    public void mouseExited(MouseEvent e) {
		    	
		    	btnTerminal.setBackground(new Color(210, 210, 210));		
		    	btnTerminal.setBorder(new RoundBorder(30, new Color(200, 200, 200)));    	
    		}
		});
	}

	private void creerFenetreTerminal() {

		if (this.terminal != null) {
			this.terminal.getFenetre().setContentPane(this.terminal.getContenuPane());
			this.terminal.getFenetre().revalidate();
		}
		else {
			this.terminal = new FenetreTerminal(this.machine, this.fenetre);
			this.terminal.getFenetre().setContentPane(this.terminal.getContenuPane());
			this.terminal.getFenetre().revalidate();
		}	           
	}
}