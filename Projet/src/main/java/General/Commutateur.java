
package General;

import Outils.*;

import java.util.HashMap;
/**
 * @author bpotetma
 */

public class Commutateur extends Machine {

    public static final int NBR_PORT_FAST = 8;
    public static final int NBR_CR = 1;
    private int id;
    private static int nbrCommutateur;
    private TableMAC tableMAC;

    //private TableMAC tableMac;

    public Commutateur(int x, int y) {
        
        super(x, y);
        nbrCommutateur++;
        this.id = nbrCommutateur;
        this.tableMAC = new TableMAC();
        this.nbrPortMax = NBR_PORT_FAST;
    }

    public TableMAC getTableMAC() {

        return this.tableMAC;
    }

    @Override
    // Permet d'ajouter une carte réseau à une machine
    public boolean ajouterInterface(CarteReseau cr) {
        
        boolean validite = false;
        if (super.cartesR.size() == 0) {
            super.cartesR.add(cr);
            cr.setMachine(this);
            validite = true;
        }
        return validite;
    }

    @Override
    public String toString() {

        return "Commutateur n°" + this.id;
    }

    public String tableMAC() {

        return this.tableMAC.toString();
    }

    public void afficherTableMAC() {

        System.out.println(this + "\n" + this.tableMAC);
    }
}