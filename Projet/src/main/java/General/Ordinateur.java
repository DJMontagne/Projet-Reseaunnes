package General;

import Outils.*;
import java.util.ArrayList;

public class Ordinateur extends Machine {

	public static final int NBR_PORT_FAST = 1;

	public Ordinateur(int x, int y) {

		super(x, y);
	}

	@Override
	// Permet d'ajouter une carte réseau à une machine
    public void ajouterInterface(CarteReseau cr) {
        
        if (super.cartesR.size() == 0) {
            super.cartesR.add(cr);
        }
    }
}