package Outils;
import General.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author victo
 */

public class TableRoutage extends Table {

    // Nombre de caractères dans "Réseau Destination"
    private final int LONG_MAX_PASSERELLE = 18;
    // Nombre de caractères maximum d'une adresse IP
    private final int LONG_MAX_ADRESSE_IP = 15;
    // Nombre de caractère du mot "Interface"
    private final int LONG_NOM_INTERFACE = 9; 
    private String[] AFFICHAGE_EN_TETE = {"Destination Réseau", "Passerelle", "Interface"};

    // Constructeur 
    /**
    * @param machine
    */
    public TableRoutage() {
        
        super.longMaxChampGauche = this.LONG_MAX_PASSERELLE;
        super.longMaxChampMilieu = this.LONG_MAX_ADRESSE_IP;
        super.longMaxChampDroite = this.LONG_NOM_INTERFACE;
        super.affichageEnTete = this.AFFICHAGE_EN_TETE;
        super.table = new HashMap<Integer, String[]>();
        super.longTotale = this.LONG_MAX_PASSERELLE + this.LONG_MAX_ADRESSE_IP + this.LONG_NOM_INTERFACE;
        super.indice = 0;
    }

    public boolean existenceReseau(String addrReseau) {

        boolean existence = false;
        for (Map.Entry<Integer, String[]> tableRoutage : this.getTable().entrySet()) {
            if ((tableRoutage.getValue()[0].split("/")[0]).equals(addrReseau)) {
                existence = true;
            }
        }
        return existence;
    }

    public Integer getIndiceReseauMasque(String addrReseauMasque) {

        Integer indice = null;
        for (Map.Entry<Integer, String[]> tableRoutage : this.getTable().entrySet()) {
            if ((tableRoutage.getValue()[0]).equals(addrReseauMasque)) {
                indice = tableRoutage.getKey();
            }
        }
        return indice;
    }

    public boolean existencePasserelle(String addrReseau) {

        boolean existence = false;
        for (Map.Entry<Integer, String[]> tableRoutage : this.getTable().entrySet()) {
            if ((tableRoutage.getValue()[0].split("/")[0]).equals(addrReseau)
            && !tableRoutage.getValue()[1].equals("*")) {
                existence = true;
            }
        }
        return existence;
    }

    public String getPasserelle(String addrReseau) {

        String passerelle = null;
        for (Map.Entry<Integer, String[]> tableRoutage : this.getTable().entrySet()) {
            if ((tableRoutage.getValue()[0].split("/")[0]).equals(addrReseau) 
            && !tableRoutage.getValue()[1].equals("*")) {
                passerelle = tableRoutage.getValue()[1];
            }
        }
        return passerelle;
    }

    public String getInterface(String addrReseau) {

        String nomInterface = null;
        for (Map.Entry<Integer, String[]> tableRoutage : this.getTable().entrySet()) {
            if ((tableRoutage.getValue()[0].split("/")[0]).equals(addrReseau)) {
                nomInterface = tableRoutage.getValue()[2];
            }
        }
        return nomInterface;
    }
}

