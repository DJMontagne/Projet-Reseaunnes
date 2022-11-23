

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    
    public Machine(){
        cartesR = new ArrayList<CarteReseau>(); //création d'une liste vide de carteReseau
        terminaux = new ArrayList<Terminal>();  // création d'un liste vide de Terminal
        terminaux.add(new Terminal(this)); //ajout d'un terminal dans la liste
        cartesR.add(new CarteReseau());   // ajout d'une carte réseau(à voir avec le constructeur) car une machine a au moins une carte Réseau
        tableARP = new TableARP();         // création d'une tableARP vierge
        tableRoutage = new TableRoutage(); //création d'une tableRoutage vierge(à voir avec le constructeur pour le 0.0.0.0/0)
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
    
    
    
}
