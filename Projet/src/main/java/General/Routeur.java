package General;

import Outils.*;
import java.util.ArrayList;

public class Routeur extends Machine {

	public static final int NBR_PORT_GIGA = 2;
	private int id;
    private static int nbrRouteur;
    private static ArrayList<Routeur> allRouteurs = new ArrayList<Routeur>(); // ArrayList regroupant tous les routeurs

	public Routeur(int x, int y) {

		super(x, y);
		nbrRouteur++;
		this.id = nbrRouteur;
		allRouteurs.add(this);
	}

	@Override
	// Permet d'ajouter une carte réseau à une machine
    public void ajouterInterface(CarteReseau cr) {
        
    	boolean validiteCarteR = true;
	    for (int i = 0; i < super.cartesR.size(); i++) {
		    if ((IPv4.estEgale(cartesR.get(i).getIP().getReseau(), cr.getIP().getReseau())
		    && IPv4.estEgale(cartesR.get(i).getIP().getMasque(), cr.getIP().getMasque())
		    || cartesR.get(i).getNomInterface().equals(cr.getNomInterface()))) {
		    	validiteCarteR = false;
		    }
		}
        if (validiteCarteR && super.cartesR.size() < Routeur.NBR_PORT_GIGA) {
        	super.cartesR.add(cr);
        }
    }
	public void afficherTableRoutage() {

        System.out.println(this + "\n" + this.getTableRoutage());
    }

	public static ArrayList<Routeur> getAllRouteurs() {
		return allRouteurs;
	}

	public static Routeur getUnRouteur(int a) {
		return allRouteurs.get(a);
	}

    @Override
    public String toString() {

        return "Routeur n°" + this.id;
    }
}
