package General;

import Outils.*;
import java.util.ArrayList;

/**
 *
 * @author victo
 */
public abstract class Machine {
    //-----attributs
    private int id;
    protected ArrayList<CarteReseau> cartesR; //Liste de cartes réseau
    private TableARP tableARP; //table ARP de la machine
    private TableRoutage tableRoutage; //table de routage de la machine
    private Terminal terminal; //terminal de la machine
    private int x;
    private int y;
    private static int nbrMachine;
    public static final Object PORT_DOWN = null;
    private CarteReseau[] ports;
    
    public Machine(int mX, int mY){
        
        Machine.nbrMachine++;
        this.id = nbrMachine;
        cartesR = new ArrayList<CarteReseau>(); //création d'une liste vide de carteReseau
        terminal = new Terminal(this);  //ajout d'un terminal
        tableARP = new TableARP();         // création d'une tableARP vierge
        tableRoutage = new TableRoutage(); //création d'une tableRoutage vierge(à voir avec le constructeur pour le 0.0.0.0/0)
        this.ports = this.initPorts();
        this.x = mX;
        this.y = mY;
    }
    
    
    
    public void fermerTerminal(){ //retire le terminal de la liste des terminaux de la machine, le rendant inutilisable
        this.terminal.fermerTerminal();
    }
    
    //----LISTE DE GETTERS ET SETTERS (peut-être à renommer pour facilité)-----
     protected abstract void ajouterInterface(CarteReseau cr);

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

    public CarteReseau getInterfaceCompatible(Machine machine) {

        CarteReseau carteReseau = null;
        for (int i = 0; i < this.getCartesR().size(); i++) {
            for (int j = 0; j < machine.getCartesR().size(); j++) {
                if (IPv4.estEgale(this.getCartesR().get(i).getIP().getReseau(), machine.getCartesR().get(j).getIP().getReseau())
                && IPv4.estEgale(this.getCartesR().get(i).getIP().getMasque(), machine.getCartesR().get(j).getIP().getMasque())) {
                    carteReseau = this.getCartesR().get(i);
                }
            }
        }
        return carteReseau;
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

    public Terminal getTerminal() {
        return terminal;
    }
    
    public CarteReseau[] getPorts() {

        return this.ports;
    }

    public boolean carteRAttribue(CarteReseau cr) {
        
        boolean estAttribue = false;
        for (int i = 0; i < this.ports.length; i++) {
            if (this.ports[i] != PORT_DOWN && this.ports[i].equals(cr)) {
                estAttribue = true;
            }
        }
        return estAttribue;
    }

    private CarteReseau[] initPorts() {
        
        CarteReseau[] initPorts = null;
        if (this instanceof Ordinateur) {
            initPorts = new CarteReseau[Ordinateur.NBR_PORT_FAST];
        }
        else if (this instanceof Routeur) {
            initPorts = new CarteReseau[Routeur.NBR_PORT_GIGA];
        }
        else if (this instanceof Commutateur) {
            initPorts = new CarteReseau[Commutateur.NBR_PORT_FAST];
        }

        return initPorts;
    }

    public boolean attribuerPort(Machine machine) {

        boolean portLibre = false;
        if (this.cartesR.size() != 0) {
            if (this instanceof Ordinateur) {
                for (int i = 0; i < this.ports.length; i++) {
                    if (this.ports[i] == PORT_DOWN) {
                        this.ports[i] = this.cartesR.get(0);
                        portLibre = true;
                        i = Ordinateur.NBR_PORT_FAST;
                    }
                }
            }
            else if (this instanceof Routeur) {
                // Récupération d'une carte réseau ayant la même adresse Réseau et le même masque que la celle du paramètre "machine"
                CarteReseau carteR = this.getInterfaceCompatible(machine);
                if (carteR == null) {
                    for (int i = 0; i < this.cartesR.size(); i++) {
                        if (!this.carteRAttribue(this.cartesR.get(i))) {
                            carteR = this.cartesR.get(i);
                        }
                    }
                }
                for (int i = 0; i < this.ports.length; i++) {
                    if (this.ports[i] == PORT_DOWN) {
                        this.ports[i] = carteR;
                        portLibre = true;
                        i = Routeur.NBR_PORT_GIGA;
                    }
                }
            }
            else if (this instanceof Commutateur) {
                for (int i = 0; i < this.ports.length; i++) {
                    if (this.ports[i] == PORT_DOWN) {
                        this.ports[i] = this.cartesR.get(0);
                        portLibre = true;
                        i = Commutateur.NBR_PORT_FAST;
                    }
                }
            }
        }
        return portLibre;
    }

    public void afficherPorts() {

        String ports = "[";
        for (int i = 0; i < this.ports.length; i++) {
            ports += (i + 1) + " => ";
            if (this.ports[i] != PORT_DOWN) {
                ports += IPv4.getStrAdresse(this.ports[i].getIP().getAdresseIP());
            }
            else {
                ports += "DOWN";
            }
            if (i != (this.ports.length - 1)) {
                ports += "\t";
            }
        }
        ports += "]";
        System.out.println(ports);
    }
    
    @Override
    public String toString() {
        
        return "Machine n°" + this.id;
    }

    public void afficherConfig() {

        String config = this.toString() + "\n";
        for (int i = 0; i < this.cartesR.size(); i++) {
            config += i + 1 + ": ";
            if (i == this.cartesR.size() - 1) {
                config += this.cartesR.get(i).toString();
            }
            else {
                config += this.cartesR.get(i).toString() + "\n\n";
            }
        }
        System.out.println(config);
    }

    public void afficherTableARP() {

        System.out.println(this + "\n" + this.tableARP);
    }
}
