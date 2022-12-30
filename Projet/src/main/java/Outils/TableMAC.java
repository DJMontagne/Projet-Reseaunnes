package Outils;

import General.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bpotetma
 */

public class TableMAC extends Table {
    
    // Nombre de caractères d'une adresse MAC
    private final int LONG_ADRESSE_MAC = 17;
    // Nombre de caractères maximum dans de la valeur "dynamic" du champ type
    private final int LONG_MAX_TYPE = 7;
    // Nombre de caractère maxium pourt le nom d'un port "FaX/X"
    private final int LONG_MAX_NOM_PORT = 5; 
    private final String[] AFFICHAGE_EN_TETE = {"Adresse MAC", "Type", "Ports"};

    // Constructeur 
    public TableMAC() {

        super.longMaxChampGauche = this.LONG_ADRESSE_MAC;
        super.longMaxChampMilieu = this.LONG_MAX_TYPE;
        super.longMaxChampDroite = this.LONG_MAX_NOM_PORT;
        super.affichageEnTete = this.AFFICHAGE_EN_TETE;
        super.table = new HashMap<Integer, String[]>();
        super.longTotale = this.LONG_ADRESSE_MAC + this.LONG_MAX_TYPE + this.LONG_MAX_NOM_PORT;
        super.indice = 0;
    }
}