package GUI;

import General.*;
import Outils.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Accueil implements ActionListener, MouseListener {

	private Fenetre fenetre;
	
	private JPanel contenuPane;
	private JPanel menuAjouter;

	private JButton btnOrdinateur;
	private JButton btnCommutateur;
	private JButton btnRouteur;
	private JButton btnLiaison;
	private JButton btnClique;
	
	private Repere repere;
	private HashMap<Machine, JLabel> machines;
	private HashMap<Machine, FenetreCarteR> fenetresCarteR;
	private	ArrayList<JLabel> labelsMachine;			


	public Accueil() {

		this.fenetre = new Fenetre("Réseaunnés", 1200, 700);
		this.fenetre.setExtendedState(Fenetre.MAXIMIZED_BOTH);
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.contenuPane = (JPanel) this.fenetre.getContentPane();
	   	this.menuAjouter = new JPanel();
	   	this.menuAjouter.setBackground(new Color(160, 160, 160));
	   	this.menuAjouter.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
	   	this.menuAjouter.setBorder(BorderFactory.createLineBorder(Color.RED));

	   	this.repere = new Repere();
	   	this.repere.addMouseListener(this);
	
	    this.btnOrdinateur = this.ajouterBouton("Ajouter un ordinateur");
	    this.btnCommutateur = this.ajouterBouton("Ajouter un commutateur");	   
	    this.btnRouteur = this.ajouterBouton("Ajouter un routeur");
	    this.btnLiaison = this.ajouterBouton("Ajouter une liaison");

	   	this.contenuPane.add(this.menuAjouter, BorderLayout.SOUTH);
	   	this.contenuPane.add(this.repere, BorderLayout.CENTER);

	   	this.machines = new HashMap<>();
	   	this.fenetresCarteR = new HashMap<>();
	   	this.labelsMachine = new ArrayList<>();
	}

	public Fenetre getFenetre() {

		return this.fenetre;
	}

	public JButton ajouterBouton(String texte) {
	
		JButton bouton = new JButton(texte);
		bouton.addActionListener(this);
		this.menuAjouter.add(bouton);

		return bouton;
    }

    @Override
	public void actionPerformed(ActionEvent e) {

	    if (e.getSource().equals(this.btnOrdinateur)) {
	    	btnClique = btnOrdinateur;
	    }
	    else if (e.getSource().equals(this.btnCommutateur)) {
	    	btnClique = btnCommutateur;
	    }
	    else if (e.getSource().equals(this.btnRouteur)) {
	    	btnClique = btnRouteur;
	    }
	    else if (e.getSource().equals(this.btnLiaison)) {
	    	btnClique = btnLiaison;
	    }
  	}

    private void creerOrdinateur(int x, int y) {

    	Ordinateur ordinateur = new Ordinateur(x, y);
		Image image = new ImageIcon("img\\ordinateur.png").getImage();
		int largeur = 50;
		int hauteur = 50;
		Image imageReduite = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(imageReduite);
		JLabel imgOrdinateur = new JLabel();
		imgOrdinateur.setName(ordinateur.toString());
		imgOrdinateur.setBorder(BorderFactory.createLineBorder(Color.RED));
		imgOrdinateur.setLayout(new BorderLayout());
		imgOrdinateur.setIcon(icon);
		JLabel texte = new JLabel(ordinateur.toString());
		texte.setBorder(BorderFactory.createLineBorder(Color.RED));
		imgOrdinateur.add(texte, BorderLayout.SOUTH);
		imgOrdinateur.setBounds(x - largeur/2, y - hauteur, largeur*2, hauteur*2);
		imgOrdinateur.repaint();
		this.machines.put(ordinateur, imgOrdinateur);
		this.repere.add(imgOrdinateur);
		this.repere.revalidate();
		this.repere.repaint();
    }

    private void creerCommutateur(int x, int y) {

    	Commutateur commutateur = new Commutateur(x, y);
		Image image = new ImageIcon("img\\commutateur.png").getImage();
		int largeur = 75;
		int hauteur = 75;
		Image imageReduite = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(imageReduite);
		JLabel imgCommutateur = new JLabel();
		imgCommutateur.setName(commutateur.toString());
		imgCommutateur.setBorder(BorderFactory.createLineBorder(Color.RED));
		imgCommutateur.setLayout(new BorderLayout());
		imgCommutateur.setIcon(icon);
		JLabel texte = new JLabel(commutateur.toString());
		texte.setBorder(BorderFactory.createLineBorder(Color.RED));
		imgCommutateur.add(texte, BorderLayout.SOUTH);
		imgCommutateur.setBounds(x - largeur/2, y - hauteur/2, largeur + 50, hauteur);
		imgCommutateur.repaint();
		this.machines.put(commutateur, imgCommutateur);
		this.repere.add(imgCommutateur);
		this.repere.revalidate();
		this.repere.repaint();
    }

    private void creerRouteur(int x, int y) {

    	Routeur routeur = new Routeur(x, y);
		Image image = new ImageIcon("img\\routeur.png").getImage();
		int largeur = 100;
		int hauteur = 100;
		Image imageReduite = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(imageReduite);
		JLabel imgRouteur = new JLabel();
		imgRouteur.setName(routeur.toString());
		imgRouteur.setBorder(BorderFactory.createLineBorder(Color.RED));
		imgRouteur.setLayout(new BorderLayout());
		imgRouteur.setIcon(icon);
		JLabel texte = new JLabel(routeur.toString());
		texte.setBorder(BorderFactory.createLineBorder(Color.RED));
		imgRouteur.add(texte, BorderLayout.SOUTH);
		imgRouteur.setBounds(x - largeur/2, y - hauteur/2, largeur, hauteur);
		imgRouteur.repaint();
		this.machines.put(routeur, imgRouteur);
		this.repere.add(imgRouteur);
		this.repere.revalidate();
		this.repere.repaint();
    }

    private void creerLiaison() {

    	Machine machineA = null;
    	Machine machineB = null;
    	for (Map.Entry<Machine, JLabel> machine : machines.entrySet()) {
    		if (this.labelsMachine.get(0).equals(machine.getValue())) {
    			machineA = machine.getKey();
    		}
    		else if (this.labelsMachine.get(1).equals(machine.getValue())) {
    			machineB = machine.getKey();
    		}
    	}    			
    	if (machineA != null && machineB != null) {
    		Liaison liaison = new Liaison();
    		liaison.lier(machineA, machineB);
    		if (liaison.getValidite()) {
    			this.repere.setLabelA(this.labelsMachine.get(0));
	    		this.repere.setLabelB(this.labelsMachine.get(1));
	    		this.repere.revalidate();
	    		this.repere.repaint();
	    		this.labelsMachine.clear();
	    		System.out.println(Reseau.getReseaux());
    		}
    		else {
    			if (machineA.taillePorts() == machineA.getNbrPortMax()) {
    				JOptionPane.showMessageDialog(new JFrame(), 
						machineA + " ne possède pas de port libre", "Liaison invalide", JOptionPane.INFORMATION_MESSAGE);
    			}
    			else if (machineB.taillePorts() == machineB.getNbrPortMax()) {
    				JOptionPane.showMessageDialog(new JFrame(), 
						machineB + " ne possède pas de port libre", "Liaison invalide", JOptionPane.INFORMATION_MESSAGE);
    			}
    			this.labelsMachine.clear();
    			liaison.delier();
    			liaison = null;
    		}
    	}
    }

    private void creerCarteReseau(JLabel label) {
    	Machine machineCherche = null;
    	for (Map.Entry<Machine, JLabel> machine : machines.entrySet()) {
    		if (label.equals(machine.getValue())) {
    			machineCherche = machine.getKey();
    		}
    	}
    	if (machineCherche != null) {
    		if (this.fenetresCarteR.containsKey(machineCherche)) {
    			this.fenetresCarteR.get(machineCherche).getFenetre().setVisible(true);
    		}
    		else {
    			FenetreCarteR fenetreCarteR = new FenetreCarteR(machineCherche);
    			fenetreCarteR.getFenetre().setVisible(true);
    			this.fenetresCarteR.put(machineCherche, fenetreCarteR);
    		}
    	}
    }

    private Machine getMachine(JLabel label) {

    	Machine machine = null;
    	for (Map.Entry<Machine, JLabel> entree : this.machines.entrySet()) {
    		if (entree.getValue().equals(label)) {
    			machine = entree.getKey();
    		}
    	}
    	return machine;
    }

     @Override
    public void mouseClicked(MouseEvent e) {

    	int x = e.getX();
		int y = e.getY();
		Component composant = repere.findComponentAt(x, y);
		if (btnClique != null && btnClique.equals(btnLiaison) && composant instanceof JLabel && composant.getName() != null) {
			JLabel label = (JLabel) composant;
			if (this.getMachine(label).getCartesR().size() == 0) {
				JOptionPane.showMessageDialog(new JFrame(), 
					this.getMachine(label) + " ne possède pas de carte réseau", "Carte réseau inexistante", JOptionPane.INFORMATION_MESSAGE);
				btnClique = null;
			}
			else if (!this.labelsMachine.contains(label)) {
				this.labelsMachine.add(label);
			}
			if (this.labelsMachine.size() == 2) {
				creerLiaison();
				btnClique = null;
			}
		}
		else if ( btnClique != null && btnClique.equals(btnLiaison) && composant instanceof JLabel) {
			JLabel label = (JLabel) composant;
			if (!label.getText().equals("")) {
				Container parent = label.getParent();
				if (parent instanceof JLabel) {
					JLabel labelParent = (JLabel) parent;
					if (this.getMachine(label).getCartesR().size() == 0) {
						JOptionPane.showMessageDialog(new JFrame(), 
							this.getMachine(label) + " ne possède pas de carte réseau", "Carte réseau inexistante", JOptionPane.INFORMATION_MESSAGE);
						btnClique = null;
					}
					else if (!this.labelsMachine.contains(labelParent)) {
						this.labelsMachine.add(labelParent);
					}
					if (this.labelsMachine.size() == 2) {
						creerLiaison();
						btnClique = null;
					}
				}
			}
		}
		else if (composant instanceof JLabel && composant.getName() != null) {
				
			JLabel label = (JLabel) composant;
			creerCarteReseau(label);
		}
		else if (composant instanceof JLabel) {
			JLabel label = (JLabel) composant;
			if (!label.getText().equals("")) {
				Container parent = label.getParent();
				if (parent instanceof JLabel) {
					JLabel labelParent = (JLabel) parent;
					creerCarteReseau(labelParent);
				}
			}
		}
		else if (btnClique.equals(btnOrdinateur)) {
			creerOrdinateur(x, y);
			btnClique = null;
		}
		else if (btnClique.equals(btnCommutateur)) {
			creerCommutateur(x, y);
			btnClique = null;
		}
		else if (btnClique.equals(btnRouteur)) {
			creerRouteur(x, y);
			btnClique = null;
		}
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}