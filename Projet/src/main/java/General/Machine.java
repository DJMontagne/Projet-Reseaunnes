
package General;

import Outils.*;
import java.util.ArrayList;

/**
 *
 * @author victo
 */
public class Machine {
    //-----attributs
    private ArrayList<CarteReseau> cartesR; //Liste de cartes réseau
    private TableARP tableARP; //table ARP de la machine
    private TableRoutage tableRoutage; //table de routage de la machine
    private ArrayList<Terminal> terminaux; //liste des terminaux de la machine(pour les commandes)
    private int x;
    private int y;
    
    public Machine(int mX, int mY){
        cartesR = new ArrayList<CarteReseau>(); //création d'une liste vide de carteReseau
        terminaux = new ArrayList<Terminal>();  // création d'un liste vide de Terminal
        terminaux.add(new Terminal(this)); //ajout d'un terminal dans la liste
        tableARP = new TableARP(this);         // création d'une tableARP vierge
        tableRoutage = new TableRoutage(); //création d'une tableRoutage vierge(à voir avec le constructeur pour le 0.0.0.0/0)
        this.x = mX;
        this.y = mY;
    }
    
    
    public void addTerminal(Terminal term){ //ajout d'un terminal dans la liste de terminaux
        terminaux.add(term);
    }
    public void fermerTerminal(int index){ //retire le terminal de la liste des terminaux de la machine, le rendant inutilisable
        this.terminaux.get(index).fermerTerminal();
    }
    
    //----LISTE DE GETTERS ET SETTERS (peut-être à renommer pour facilité)-----
    public ArrayList<CarteReseau> getCartesR() {
        return cartesR;
    }

    public CarteReseau getCarteR(CarteReseau cr) {

        CarteReseau crCherche = null;
        for (int i = 0; i < this.cartesR.size(); i++) {
            if (this.cartesR.get(i).equals(cr)) {
                crCherche = this.cartesR.get(i);
            }
        }
        return crCherche;
    }

    public TableARP getTableARP() {
        return tableARP;
    }

    protected void setTableARP(TableARP tableARP) {
        this.tableARP = tableARP;
    }

    public TableRoutage getTableRoutage() {
        return tableRoutage;
    }

    protected void setTableRoutage(TableRoutage tableRoutage) {
        this.tableRoutage = tableRoutage;
    }

    public ArrayList<Terminal> getTerminaux() {
        return terminaux;
    }
    
    // Permet d'ajouter une carte réseau à une machine
    public void ajouterInterface(CarteReseau cr) {
        
        ArrayList<String> interfaces = new ArrayList<String>();
        for (int i = 0; i < this.cartesR.size(); i++) {
            interfaces.add(this.cartesR.get(i).getNomInterface());
        }
        if (!interfaces.contains(cr.getNomInterface()) && !this.cartesR.contains(cr)) {
            this.cartesR.add(cr);
        }
    }
    
    @Override
    public String toString() {
        String config = "";
        for (int i = 0; i < this.cartesR.size(); i++) {
            config += i + 1 + ": ";
            if (i == this.cartesR.size() - 1) {
                config += this.cartesR.get(i).toString();
            }
            else {
                config += this.cartesR.get(i).toString() + "\n\n";
            }
        }
        return config;
    }

    public void afficherConfig() {

        System.out.println(this.toString());
    }
    
}