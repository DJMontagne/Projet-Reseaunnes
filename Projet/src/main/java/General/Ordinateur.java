package General;

import Outils.*;
import java.util.ArrayList;

public class Ordinateur extends Machine {

	public static final int NBR_PORT_FAST = 1;
	private int id;
	private static int nbrOrdinateur;

	public Ordinateur(int x, int y) {
		
		super(x, y);
		nbrOrdinateur++;
		this. id = nbrOrdinateur;
	}

	@Override
	// Permet d'ajouter une carte réseau à une machine
    public void ajouterInterface(CarteReseau cr) {
        
        if (super.cartesR.size() == 0) {
            super.cartesR.add(cr);
        }
    }

    @Override
    public String toString() {

    	return "Ordinateur n°" + this.id;
    }
}