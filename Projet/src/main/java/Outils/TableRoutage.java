package Outils;
import General.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author victo
 */

public class TableRoutage extends Table {

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
    public TableRoutage() {
        super.longMaxChampGauche = this.LONG_MAX_PASSERELLE;
        super.longMaxChampMilieu = this.LONG_MAX_ADRESSE_IP;
        super.longMaxChampDroite = this.LONG_NOM_INTERFACE;
        super.affichageEnTete = this.contenuEnTeteAffichage;
        super.table = new HashMap<Machine, String[]>();
        super.longTotale = this.LONG_MAX_PASSERELLE + this.LONG_MAX_ADRESSE_IP + this.LONG_NOM_INTERFACE;
       
    }

   
}

