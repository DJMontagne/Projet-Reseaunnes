package GUI;

import javax.swing.JFrame;

public class Fenetre extends JFrame {

	public Fenetre(String nom, int largeur, int hauteur) {

		setTitle(nom);
	    setSize(largeur, hauteur);
	    setLocationRelativeTo(null);
	}
}