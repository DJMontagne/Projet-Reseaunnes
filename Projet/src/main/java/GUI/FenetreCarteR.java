package GUI;

import General.*;
import Outils.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class FenetreCarteR implements KeyListener {

	private Fenetre fenetre;
	private Repere repere;
	private Machine machine;

	private JPanel contenuPane;
    private JPanel contenuConfigPane;
	private JPanel config;
	private JPanel cartesR;
	private JPanel imgCartesR;
	private JPanel contenantAjoutSuppr;
	
	private String[] nomChamps = {"Interface", "Adresse IP", "Masque", "Passerelle"};
	private ArrayList<JTextField> champsTexte;
	private HashMap<JLabel, CarteReseau> labelCr;
	private CarteReseau crModifier;
	private ArrayList<JLabel> textes;

	public FenetreCarteR(Machine mMachine, Repere mRepere, Fenetre fenetreMenu) {
		
		this.machine = mMachine;

		this.repere = mRepere;
		this.fenetre = fenetreMenu;
		
		this.contenuPane = (JPanel) this.fenetre.getContentPane();
		
		this.contenuConfigPane = new JPanel();
		this.contenuConfigPane.setLayout(new BoxLayout(this.contenuConfigPane, BoxLayout.Y_AXIS));
		this.contenuPane.add(contenuConfigPane);

		this.config = new JPanel(new GridLayout(4, 1));
		
		this.cartesR = new JPanel(new GridLayout(1, 2));
		this.cartesR.setPreferredSize(new Dimension(900, 10));

		this.imgCartesR = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.contenantAjoutSuppr = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		this.cartesR.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 210, 210)));
		this.cartesR.add(imgCartesR);
		this.cartesR.add(contenantAjoutSuppr);
		
		this.contenuConfigPane.add(config);
		this.contenuConfigPane.add(cartesR);
		
		this.labelCr = new HashMap<>();
		this.textes = new ArrayList<>();
		this.champsTexte = new ArrayList<>();

		ajouterIconeAjout();
		ajouterIconeSuppr();

		for (String nomChamp : this.nomChamps) {
			creerChampTexte(nomChamp);
		}
	}

	public Fenetre getFenetre() {

		return this.fenetre;
	}

	public JPanel getContenuPane() {

		return this.contenuConfigPane;
	}

	private void creerChampTexte(String nom) {
	
		JPanel panel = new JPanel(new BorderLayout());
		JLabel nomChamp = new JLabel(nom);
		nomChamp.setFont(nomChamp.getFont().deriveFont(14.0f));
		JTextField champTexte = new JTextField();
		JPanel contenantTexte = new JPanel(new FlowLayout(FlowLayout.CENTER));
		champTexte.setName(nom);
		this.champsTexte.add(champTexte);
		champTexte.addKeyListener(this);
		champTexte.setPreferredSize(new Dimension(500, 25));
		contenantTexte.setBorder(new EmptyBorder(30, 0, 0, 0));
		nomChamp.setBorder(new EmptyBorder(0, 50, 0, 0));
		contenantTexte.add(champTexte);
		panel.add(nomChamp, BorderLayout.WEST);
		panel.add(contenantTexte, BorderLayout.EAST);
		this.config.add(panel);
	}

	private void ajouterIconeAjout() {

		Icon icon = new ImageIcon("./img/ajout.png");
		JLabel imgAjout = new JLabel();
		imgAjout.setBorder(new EmptyBorder(0, 0, 0, 15));
		imgAjout.setIcon(icon);
		this.contenantAjoutSuppr.add(imgAjout);
		this.cartesR.revalidate();
		
		imgAjout.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String nomInterface = champsTexte.get(0).getText();
				String strAddrIP = champsTexte.get(1).getText();
				String strMasque = champsTexte.get(2).getText();
				String strPasserelle = champsTexte.get(3).getText();
				Octet[] addrIP = IPv4.initAdresseVide();
				IPv4.setAdresse(addrIP, strAddrIP);
				Octet[] masque = IPv4.initAdresseVide();
				IPv4.setMasque(masque, strMasque);
				Octet[] passerelle = IPv4.initAdresseVide();
				IPv4.setAdresse(passerelle, strPasserelle);

				if (machine instanceof Ordinateur && machine.getNbrCarteR() == Ordinateur.NBR_CR) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le nombre maximum de carte réseau a été atteint", "Maximum atteint", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (machine instanceof Commutateur && machine.getNbrCarteR() == Commutateur.NBR_CR) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le nombre maximum de carte réseau a été atteint", "Maximum atteint", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (machine instanceof Routeur && machine.getNbrCarteR() == Routeur.NBR_CR) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le nombre maximum de carte réseau a été atteint", "Maximum atteint", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (nomInterface.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le champ nom interface est vide", "Champ vide", JOptionPane.WARNING_MESSAGE);
				}
				else if (addrIP.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le champ adresse IP est vide", "Champ vide", JOptionPane.WARNING_MESSAGE);
				}
				else if (masque.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le champ masque est vide", "Champ vide", JOptionPane.WARNING_MESSAGE);
				}
				else if (!IPv4.adresseIPValide(addrIP) || IPv4.adresseVide(addrIP)) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"L'adresse IP est invalide", "Adresse IP invalide", JOptionPane.WARNING_MESSAGE);
				}
				else if (!IPv4.masqueValide(masque) || IPv4.adresseVide(masque)) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le masque est invalide", "Masque invalide", JOptionPane.WARNING_MESSAGE);
				}
				else if ((!IPv4.adresseIPValide(passerelle) || IPv4.adresseVide(passerelle)) && !strPasserelle.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"L'adresse IP de la passerelle est invalide", "Passerelle invalide", JOptionPane.WARNING_MESSAGE);
				}
				else if (machine.ipExistant(strAddrIP)) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"L'adresse IP est déjà utilisée", "Addresse IP invalide", JOptionPane.WARNING_MESSAGE);
				}
				else if (machine.nomCarteRUtilise(champsTexte.get(0).getText())) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le nom de la carte réseau est déjà utilisé", "Nom utilisé", JOptionPane.WARNING_MESSAGE);
				}
				else {
					creerCarteReseau();
				}
			}
		});
	}

	private void ajouterIconeSuppr() {

		Icon icon = new ImageIcon("./img/supprimer.png");
		JLabel imgSuppr = new JLabel();
		imgSuppr.setBorder(new EmptyBorder(0, 0, 0, 15));
		imgSuppr.setIcon(icon);
		this.contenantAjoutSuppr.add(imgSuppr);
		this.cartesR.revalidate();

		imgSuppr.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				imgCartesR.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						
						int x = e.getX();
						int y = e.getY();
						Component composant = imgCartesR.findComponentAt(x, y);
						if (composant instanceof JLabel) {
							JLabel label = (JLabel) composant;
							Container labelSuppr = null;
							if (!label.getText().equals("")) {
								labelSuppr = label.getParent();
							}
							else {
								labelSuppr = label;
							}
							imgCartesR.remove(labelSuppr);
							imgCartesR.invalidate();			
							imgCartesR.repaint();
							CarteReseau crSuppr = labelCr.get(labelSuppr);
							ArrayList<Liaison> liaisonsSuppr = machine.getLiaisons(crSuppr);
							for (int i = 0; i < liaisonsSuppr.size(); i++) {
								Liaison liaison = liaisonsSuppr.get(i);
								liaison.delier();
							}
							repere.invalidate();
							repere.repaint();
							machine.supprimerCarteR(crSuppr);
							imgCartesR.removeMouseListener(this);
						}
					}
				});
			}
		});
	}

	public void creerCarteReseau() {
		
		String nomInterface = champsTexte.get(0).getText();
		String adresseIP = champsTexte.get(1).getText();
		String masque = champsTexte.get(2).getText();
		String passerelle = champsTexte.get(3).getText();
		CarteReseau carteR = new CarteReseau(nomInterface, adresseIP, masque, passerelle);
		boolean validiteCarteR = false;
		if (this.machine instanceof Ordinateur) {
			Ordinateur ordinateur = (Ordinateur) this.machine;
			validiteCarteR = ordinateur.ajouterInterface(carteR);
		}
		else if (this.machine instanceof Commutateur) {
			Commutateur commutateur = (Commutateur) this.machine;
			validiteCarteR = commutateur.ajouterInterface(carteR);
		}
		else if (this.machine instanceof Routeur) {
			Routeur routeur = (Routeur) this.machine;
			validiteCarteR = routeur.ajouterInterface(carteR);
		}
		if (validiteCarteR) {
			afficherCarteReseau(carteR);
		}
		else {
			if (this.machine instanceof Routeur) {
				JOptionPane.showMessageDialog(new JFrame(), 
					IPv4.getStrAdresse(carteR.getIP().getReseau()) + " " 
					+ IPv4.getStrAdresse(carteR.getIP().getMasque()) + " est déjà utilisée"
					, "Adresse réseau existant", JOptionPane.WARNING_MESSAGE);
			}
			carteR = null;
		}

		if (this.machine.getNbrCarteR() == 1) {
			ecouterCartesR();
		}
	}

	private void afficherCarteReseau(CarteReseau carteR) {

		Image image = new ImageIcon("./img/carte_reseau.png").getImage();
		int largeur = 75;
		int hauteur = 75;
		Image imageReduite = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(imageReduite);
		JLabel imgCarteR = new JLabel();
		imgCarteR.setName(carteR.getNomInterface());
		imgCarteR.setLayout(new BorderLayout());
		imgCarteR.setIcon(icon);
		JLabel texte = new JLabel(carteR.getNomInterface());
		this.textes.add(texte);
		texte.setFont(texte.getFont().deriveFont(14.0f));
		texte.setBorder(new EmptyBorder(0, 25, 0, 0));
		imgCarteR.setBorder(new EmptyBorder(0, 35, 0, 0));
		imgCarteR.add(texte, BorderLayout.SOUTH);
		this.imgCartesR.add(imgCarteR, BorderLayout.WEST);
		this.cartesR.revalidate();
		this.labelCr.put(imgCarteR, carteR);
		champBlanc();
	}

	private void champBlanc() {

		for (int i = 0; i < this.champsTexte.size(); i++) {
			this.champsTexte.get(i).setText("");
		}
	}

	private void ecouterCartesR() {

		imgCartesR.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int x = e.getX();
				int y = e.getY();
				Component composant = imgCartesR.findComponentAt(x, y);
				if (composant instanceof JLabel) {
					JLabel label = (JLabel) composant;
					Container contenant = null;
					if (!label.getText().equals("")) {
						contenant = label.getParent();
					}
					else {
						contenant = label;
					}
					if (labelCr.containsKey(contenant)) {
						CarteReseau cr = labelCr.get(contenant);
						modifierDonnees(cr);
					}
				}
			}
		});	
	}

	private void modifierDonnees(CarteReseau cr) {

		this.crModifier = cr;

		this.champsTexte.get(0).setText(this.crModifier.getNomInterface());
		this.champsTexte.get(1).setText(IPv4.getStrAdresse(this.crModifier.getIP().getAdresseIP()));
		this.champsTexte.get(2).setText(IPv4.getStrAdresse(this.crModifier.getIP().getMasque()));
		this.champsTexte.get(3).setText(IPv4.getStrAdresse(this.crModifier.getIP().getPasserelle()));
	}

	private void modifierNomInterface(String nom) {

		for (int i = 0; i < this.textes.size(); i++) {
			if (this.textes.get(i).getText().equals(this.crModifier.getNomInterface())) {
				this.textes.get(i).setText(nom);
			}
		}
	}

	private void modificationTablesRouteur() {

		if (this.machine instanceof Routeur) {
			Routeur routeur  = (Routeur) this.machine;

			Integer indice = null;
			for (Map.Entry<Integer, String[]> entree : routeur.getTableARP().getTable().entrySet()) {
				if (entree.getValue()[1].equals(this.crModifier.getMAC().getAdresse())) {
					indice = entree.getKey();
				}
			}
			if(indice != null) {
				String[] infosARP = {IPv4.getStrAdresse(this.crModifier.getIP().getAdresseIP()), 
					this.crModifier.getMAC().getAdresse(), 
					this.crModifier.getNomInterface()};
				routeur.getTableARP().getTable().put(indice, infosARP);
			}
			else {
				String[] infosARP = {IPv4.getStrAdresse(this.crModifier.getIP().getAdresseIP()), 
					this.crModifier.getMAC().getAdresse(), 
					this.crModifier.getNomInterface()};
				routeur.getTableARP().remplir(infosARP);
			}
			String[] infosRoutage = {IPv4.getStrAdresse(this.crModifier.getIP().getReseau()) + "/" 
				+ IPv4.getMasqueDecimal(this.crModifier.getIP().getMasque()), 
				"*", 
				this.crModifier.getNomInterface()};
			routeur.getTableRoutage().remplir(infosRoutage);
		}
	}

	public void keyPressed(KeyEvent e) {
		
		Octet[] adresse = IPv4.initAdresseVide();
		boolean nomInterfaceValide = false;
		boolean ipValide = false;
		boolean masqueValide = false;
		boolean passerelleValide = false;
		if (e.getSource().equals(this.champsTexte.get(0))) {
			if ((e.getKeyCode() == KeyEvent.VK_ENTER)) {
				if (machine.nomCarteRUtilise(this.champsTexte.get(0).getText())) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le nom de la carte réseau est déjà utilisé", "Nom utilisé", JOptionPane.WARNING_MESSAGE);
				}
				else if (this.crModifier != null) {
					modifierNomInterface(this.champsTexte.get(0).getText());
					this.crModifier.setNominterface(this.champsTexte.get(0).getText());
					modificationTablesRouteur();
				}
			}
		}
		else if (e.getSource().equals(this.champsTexte.get(1))) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				IPv4.setAdresse(adresse, this.champsTexte.get(1).getText());
				
				if (!IPv4.adresseIPValide(adresse) || IPv4.adresseVide(adresse)) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"L'adresse IP est invalide", "Adresse IP invalide", JOptionPane.WARNING_MESSAGE);
				}
				else if (machine.ipExistant(IPv4.getStrAdresse(adresse))) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"L'adresse IP est déjà utilisée", "Addresse IP invalide", JOptionPane.WARNING_MESSAGE);
				}
				else if (this.crModifier != null) {
					this.crModifier.setAdresseIP(this.champsTexte.get(1).getText());
					modificationTablesRouteur();
				}
				if (IPv4.adresseIPValide(adresse) && !IPv4.adresseVide(adresse) 
				&& this.champsTexte.get(2).getText().equals("")) {
					this.champsTexte.get(2).setText(IPv4.getStrAdresse(IPv4.genererMasque(adresse)));
				} 
			}
		}
		else if (e.getSource().equals(this.champsTexte.get(2))) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				IPv4.setMasque(adresse, this.champsTexte.get(2).getText());
				
				if (!IPv4.masqueValide(adresse) || IPv4.adresseVide(adresse)) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Le masque est invalide", "Masque invalide", JOptionPane.WARNING_MESSAGE);
				}
				else if (this.crModifier != null) {
					this.crModifier.setMasque(this.champsTexte.get(2).getText());
					modificationTablesRouteur();
				}
			}
		}
		else if (e.getSource().equals(this.champsTexte.get(3))) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				IPv4.setAdresse(adresse, this.champsTexte.get(3).getText());
				
				if (!IPv4.adresseIPValide(adresse) || IPv4.adresseVide(adresse)) {
					JOptionPane.showMessageDialog(new JFrame(), 
						"L'adresse IP de la passerelle est invalide", "Passerelle invalide", JOptionPane.WARNING_MESSAGE);
				}
				else if (this.crModifier != null) {
					this.crModifier.setPasserelle(this.champsTexte.get(3).getText());
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}