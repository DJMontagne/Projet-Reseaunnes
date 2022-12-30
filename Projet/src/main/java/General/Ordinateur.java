package General;

import Outils.*;
import GUI.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Ordinateur extends Machine {

	public static final int NBR_PORT_FAST = 1;
	public static final int NBR_CR = 1;
	private int id;
	private static int nbrOrdinateur;

	public Ordinateur(int x, int y) {
		
		super(x, y);
		nbrOrdinateur++;
		this.id = nbrOrdinateur;
		this.nbrPortMax = NBR_PORT_FAST;
	}

	@Override
	// Permet d'ajouter une carte réseau à une machine
    public void ajouterInterface(CarteReseau cr) {
        
        if (super.cartesR.size() == 0) {
            super.cartesR.add(cr);
            cr.setMachine(this);
        }
    }

    @Override
    public String toString() {

    	return "Ordinateur n°" + this.id;
    }
}