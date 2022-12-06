package Outils;
import General.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author victo
 */

public class TableRoutage {

    // Nombre de caractères d'une passerelle
    private final int LONG_MAX_PASSERELLE = 15;
    // Nombre de caractères maximum d'une adresse IP
    private final int LONG_MAX_ADRESSE_IP = 15;
    // Nombre de caractère du mot "Interface"
    private final int LONG_NOM_INTERFACE = 9; 
    private final int LONG_TOTALE = LONG_MAX_PASSERELLE + LONG_MAX_ADRESSE_IP + LONG_NOM_INTERFACE;
    private HashMap<Machine, String[]> tableRoutage;
    private String[] contenuEnTeteAffichage = {"Adresse IP", "Passerelle", "Interface"};

    // Constructeur 
    /**
    * @param machine
    */
    public TableRoutage(Machine machine) {
        
        this.tableRoutage = new HashMap<>();
        initialisationTableRoutage(machine);
    }

    // Getter
    public HashMap<Machine, String[]> getTableRoutage() {
        
        return this.tableRoutage;
    }

    /* 
    Attendre d'avoir effectuer la classe Réseau car la table ARP d'une machine se remplie 
    si cette dernière est connectée sur un réseau
    */
    public void initialisationTableRoutage(Machine machine) {
    }

    public String affichageSeparateurTableRoutage() {
        
        String separateurTableRoutage = "";
        for (int i = 0; i < LONG_TOTALE + 4; i++) {
            if (i == 0 || i == LONG_MAX_ADRESSE_IP + 1 || i == LONG_MAX_ADRESSE_IP + LONG_MAX_PASSERELLE + 2 || i == LONG_TOTALE + 3) {
                separateurTableRoutage += "+";
            }
            else {
                separateurTableRoutage += "-";
            }
        }
        return separateurTableRoutage;
    }

    /**
    * @param contenus tableau de 3 données de type String que l'on veut afficher dans la table ARP 
    */
    public String affichageContenuTableRoutage(String[] contenus) {
        
        String contenuTableRoutage = "";
        for (int i = 0; i < LONG_TOTALE + 4; i++) {
            if (i == 0 || i == LONG_MAX_ADRESSE_IP + 1 || i == LONG_MAX_ADRESSE_IP + LONG_MAX_PASSERELLE + 2 || i == LONG_TOTALE + 3) {
                contenuTableRoutage += "|";
            }
            else if (i == 1) {
                contenuTableRoutage += contenus[0];
                i += contenus[0].length() - 1;
            }
            else if (i == LONG_MAX_ADRESSE_IP + 2) {
                contenuTableRoutage += contenus[1];
                i += contenus[1].length() - 1;
            }
            else if (i == LONG_MAX_ADRESSE_IP + LONG_MAX_PASSERELLE + 3) {
                contenuTableRoutage += contenus[2];
                i += contenus[2].length() - 1;
            }
            else {
                contenuTableRoutage += " ";
            }
        }

        return contenuTableRoutage;
    }

    // Permet d'afficher la table ARP
    public void afficher() {
        
        System.out.println(this.toString());
    }

    // Affichage d'une instance de classe TableARP
    @Override
    public String toString() {
        
        String enTete = "";
        enTete += this.affichageSeparateurTableRoutage() + "\n";
        
        enTete += this.affichageContenuTableRoutage(this.contenuEnTeteAffichage) + "\n";            
        enTete += this.affichageSeparateurTableRoutage() + "\n";

        String corps = "";
        int cpt = 0;
        for (Map.Entry<Machine, String[]> entree : this.tableRoutage.entrySet()) {
                
            String[] contenusCorps = {entree.getValue()[0], entree.getValue()[1], entree.getValue()[2]};
            corps += this.affichageContenuTableRoutage(contenusCorps) + "\n";
            
            if (cpt == this.tableRoutage.size() - 1) { 
                corps += this.affichageSeparateurTableRoutage();
            }
            else {
                corps += this.affichageSeparateurTableRoutage() + "\n";
            }
            cpt++;
        }

        String tableRoutage = "";
        tableRoutage += enTete + corps;

        return tableRoutage;
    }
}

