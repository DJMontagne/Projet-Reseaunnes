
package Outils;
import General.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author victo
 */

public class TableARP {

    // Nombre de caractères d'une adresse MAC
    private final int LONG_ADRESSE_MAC = 17;
    // Nombre de caractères maximum d'une adresse IP
    private final int LONG_MAX_ADRESSE_IP = 15;
    // Nombre de caractère du mot "Interface"
    private final int LONG_NOM_INTERFACE = 9; 
    private final int LONG_TOTALE = LONG_ADRESSE_MAC + LONG_MAX_ADRESSE_IP + LONG_NOM_INTERFACE;
    private HashMap<Machine, String[]> tableARP;
    private String[] contenuEnTeteAffichage = {"Adresse IP", "Adresse MAC", "Interface"};

    // Constructeur 
    /**
    * @param machine
    */
    public TableARP(Machine machine) {
        
        this.tableARP = new HashMap<>();
        initialisationTableARP(machine);
    }

    // Getter
    public HashMap<Machine, String[]> getTableARP() {
        
        return this.tableARP;
    }

    /* 
    Attendre d'avoir effectuer la classe Réseau car la table ARP d'une machine se remplie 
    si cette dernière est connectée sur un réseau
    */
    public void initialisationTableARP(Machine machine) {
    }

    public String affichageSeparateurTableARP() {
        
        String separateurTableARP = "";
        for (int i = 0; i < LONG_TOTALE + 4; i++) {
            if (i == 0 || i == LONG_MAX_ADRESSE_IP + 1 || i == LONG_MAX_ADRESSE_IP + LONG_ADRESSE_MAC + 2 || i == LONG_TOTALE + 3) {
                separateurTableARP += "+";
            }
            else {
                separateurTableARP += "-";
            }
        }
        return separateurTableARP;
    }

    /**
    * @param contenus tableau de 3 données de type String que l'on veut afficher dans la table ARP 
    */
    public String affichageContenuTableARP(String[] contenus) {
        
        String contenuTableARP = "";
        for (int i = 0; i < LONG_TOTALE + 4; i++) {
            if (i == 0 || i == LONG_MAX_ADRESSE_IP + 1 || i == LONG_MAX_ADRESSE_IP + LONG_ADRESSE_MAC + 2 || i == LONG_TOTALE + 3) {
                contenuTableARP += "|";
            }
            else if (i == 1) {
                contenuTableARP += contenus[0];
                i += contenus[0].length() - 1;
            }
            else if (i == LONG_MAX_ADRESSE_IP + 2) {
                contenuTableARP += contenus[1];
                i += contenus[1].length() - 1;
            }
            else if (i == LONG_MAX_ADRESSE_IP + LONG_ADRESSE_MAC + 3) {
                contenuTableARP += contenus[2];
                i += contenus[2].length() - 1;
            }
            else {
                contenuTableARP += " ";
            }
        }

        return contenuTableARP;
    }

    // Permet d'afficher la table ARP
    public void afficher() {
        
        System.out.println(this.toString());
    }

    // Affichage d'une instance de classe TableARP
    @Override
    public String toString() {
        
        String enTete = "";
        enTete += this.affichageSeparateurTableARP() + "\n";
        
        enTete += this.affichageContenuTableARP(this.contenuEnTeteAffichage) + "\n";            
        enTete += this.affichageSeparateurTableARP() + "\n";

        String corps = "";
        int cpt = 0;
        for (Map.Entry<Machine, String[]> entree : this.tableARP.entrySet()) {
                
            String[] contenusCorps = {entree.getValue()[0], entree.getValue()[1], entree.getValue()[2]};
            corps += this.affichageContenuTableARP(contenusCorps) + "\n";
            
            if (cpt == this.tableARP.size() - 1) { 
                corps += this.affichageSeparateurTableARP();
            }
            else {
                corps += this.affichageSeparateurTableARP() + "\n";
            }
            cpt++;
        }

        String tableARP = "";
        tableARP += enTete + corps;

        return tableARP;
    }
}
