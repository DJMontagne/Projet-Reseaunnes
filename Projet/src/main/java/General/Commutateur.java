
package General;

import Outils.*;

import java.util.HashMap;
/**
 * @author bpotetma
 */

public class Commutateur extends Machine {

    public static final int NBR_PORT_FAST = 8;
    private int id;
    private static int nbrCommutateur;
    private TableMAC tableMAC;
    //private TableMAC tableMac;

    public Commutateur(int x, int y) {
        
        super(x, y);
        nbrCommutateur++;
        this.id = nbrCommutateur;
        this.tableMAC = new TableMAC();
    }

    public TableMAC getTableMAC() {

        return this.tableMAC;
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

        return "Commutateur n°" + this.id;
    }

    public void afficherTableMAC() {

        System.out.println(this.tableMAC);
    }
}