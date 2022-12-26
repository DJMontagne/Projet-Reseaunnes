package Outils;

import General.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bpotetma
 */

public class TableARP extends Table {

    // Nombre de caractères maximum d'une adresse IP
    private final int LONG_MAX_ADRESSE_IP = 15;
    // Nombre de caractères d'une adresse MAC
    private final int LONG_ADRESSE_MAC = 17;
    // Nombre de caractère du mot "Interface"
    private final int LONG_NOM_INTERFACE = 9; 
    private final String[] AFFICHAGE_EN_TETE = {"Adresse IP", "Adresse MAC", "Interface"};

    // Constructeur
    public TableARP() {
       
        super.longMaxChampGauche = this.LONG_MAX_ADRESSE_IP;
        super.longMaxChampMilieu = this.LONG_ADRESSE_MAC;
        super.longMaxChampDroite = this.LONG_NOM_INTERFACE;
        super.affichageEnTete = this.AFFICHAGE_EN_TETE;
        super.table = new HashMap<Integer, String[]>();
        super.longTotale = this.LONG_ADRESSE_MAC + this.LONG_MAX_ADRESSE_IP + this.LONG_NOM_INTERFACE;
        super.indice = 0;
    }

    public boolean existence(String addrIP) {

        boolean existence = false;
        for (Map.Entry<Integer, String[]> tableARP : this.getTable().entrySet()) {
            if (tableARP.getValue()[0].equals(addrIP)) {
                existence = true;
            }
        }
        return existence;
    }

    public String getMAC(String addrIP) {

        String macAddr = null;
        for (Map.Entry<Integer, String[]> tableARP : this.getTable().entrySet()) {
            if (tableARP.getValue()[0].equals(addrIP)) {
                macAddr = tableARP.getValue()[1];
            }
        }
        return macAddr;
    }

    public String getInterface(String addrIP) {

        String nomInterface = null;
        for (Map.Entry<Integer, String[]> tableARP : this.getTable().entrySet()) {
            if (tableARP.getValue()[0].equals(addrIP)) {
                nomInterface = tableARP.getValue()[2];
            }
        }
        return nomInterface;
    }
}