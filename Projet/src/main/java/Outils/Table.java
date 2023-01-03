package Outils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bpotetma
 */

public class Table {

	protected int longMaxChampGauche;
    protected int longMaxChampMilieu;
    protected int longMaxChampDroite; 
    protected int longTotale;
    protected HashMap<Integer, String[]> table;
    protected String[] affichageEnTete;
    protected int indice;

    // Constructeur 
    /**
    * @param machine
    */
    public Table() {
        
    }

    // Getter
    public Map<Integer, String[]> getTable() {
        
        return this.table;
    }

    public void remplir(String[] contenus) {

        boolean existence = false;
        for (Map.Entry<Integer, String[]> entree : this.table.entrySet()) {
            if (contenus[0].equals(entree.getValue()[0])) {
                existence = true;
                this.table.put(entree.getKey(), contenus);
            }
        }
        if (!existence) {
            this.indice++;
            this.table.put(this.indice, contenus);
        }
    }

    private String affichageSeparateurTable() {
        
        String separateurtable = "";
        for (int i = 0; i < longTotale + 4; i++) {
            if (i == 0 || i == longMaxChampGauche + 1 || i == longMaxChampGauche + longMaxChampMilieu + 2 || i == longTotale + 3) {
                separateurtable += "+";
            }
            else {
                separateurtable += "-";
            }
        }
        return separateurtable;
    }

    /**
    * @param contenus tableau de 3 données de type String que l'on veut afficher dans la table 
    */
    private String affichageContenuTable(String[] contenus) {
        
        String contenutable = "";
        for (int i = 0; i < longTotale + 4; i++) {
            if (i == 0 || i == longMaxChampGauche + 1 || i == longMaxChampGauche + longMaxChampMilieu + 2 || i == longTotale + 3) {
                contenutable += "|";
            }
            else if (i == 1) {
                contenutable += contenus[0];
                i += contenus[0].length() - 1;
            }
            else if (i == longMaxChampGauche + 2) {
                contenutable += contenus[1];
                i += contenus[1].length() - 1;
            }
            else if (i == longMaxChampGauche + longMaxChampMilieu + 3) {
                contenutable += contenus[2];
                i += contenus[2].length() - 1;
            }
            else {
                contenutable += " ";
            }
        }

        return contenutable;
    }

    // Permet d'afficher la table
    public void afficher() {
        
        System.out.println(this.toString());
    }

    // Affichage d'une instance de classe table
    @Override
    public String toString() {
        
        String enTete = "";
        enTete += this.affichageSeparateurTable() + "\n";
        
        enTete += this.affichageContenuTable(affichageEnTete) + "\n";            
        enTete += this.affichageSeparateurTable() + "\n";

        String corps = "";
        int cpt = 0;
        for (Map.Entry<Integer, String[]> entree : this.table.entrySet()) {
                
            String[] contenusCorps = {entree.getValue()[0], entree.getValue()[1], entree.getValue()[2]};
            corps += this.affichageContenuTable(contenusCorps) + "\n";
            
            if (cpt == this.table.size() - 1) { 
                corps += this.affichageSeparateurTable();
            }
            else {
                corps += this.affichageSeparateurTable() + "\n";
            }
            cpt++;
        }

        String table = "";
        table += enTete + corps;

        return table;
    }
}