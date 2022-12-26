package General;

import Outils.*;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author bpotetma
 */

public class Commutateur extends Machine {

    public static final int NBR_PORT_FAST = 8;
    private int id;
    private static int nbrCommutateur;
    private TableMAC tableMAC;
    private static ArrayList<Commutateur> allCommutateurs = new ArrayList<Commutateur>(); // ArrayList regroupant tous les commutateurs
    //private TableMAC tableMac;

    public Commutateur(int x, int y) {
        
        super(x, y);
        nbrCommutateur++;
        this.id = nbrCommutateur;
        this.tableMAC = new TableMAC();
        allCommutateurs.add(this);
    }

    public TableMAC getTableMAC() {

        return this.tableMAC;
    }

    @Override
    // Permet d'ajouter une carte réseau à une machine
    public void ajouterInterface(CarteReseau cr) {
        
        if (super.cartesR.size() == 0) {
            super.cartesR.add(cr);
            cr.setMachine(this);
        }
    }

    public static ArrayList<Commutateur> getAllCommutateurs() {
		return allCommutateurs;
	}

	public static Commutateur getUnCommutateur(int a) {
		return allCommutateurs.get(a);
	}

    @Override
    public String toString() {

        return "Commutateur n°" + this.id;
    }

    public void afficherTableMAC() {

        System.out.println(this + "\n" + this.tableMAC);
    }
}