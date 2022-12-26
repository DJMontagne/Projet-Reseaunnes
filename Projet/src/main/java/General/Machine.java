package General;

import Outils.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private ArrayList<Terminal> terminaux; //liste des terminaux de la machine(pour les commandes)
    private int x;
    private int y;
    private static ArrayList<Machine> machines = new ArrayList<>();
    private static int nbrMachine;
    private static int NBR_PORT;
    private HashMap<CarteReseau, ArrayList<CarteReseau>> ports;
    
    public Machine(int mX, int mY){
        
        Machine.nbrMachine++;
        this.id = nbrMachine;
        cartesR = new ArrayList<CarteReseau>(); //création d'une liste vide de carteReseau
        terminaux = new ArrayList<Terminal>();  // création d'un liste vide de Terminal
        terminaux.add(new Terminal(this)); //ajout d'un terminal dans la liste
        tableARP = new TableARP();         // création d'une tableARP vierge
        this.ports = new HashMap<>();
        this.x = mX;
        this.y = mY;
        machines.add(this);
    }
    
    
    public void addTerminal(Terminal term){ //ajout d'un terminal dans la liste de terminaux
        terminaux.add(term);
    }
    public void fermerTerminal(int index){ //retire le terminal de la liste des terminaux de la machine, le rendant inutilisable
        this.terminaux.get(index).fermerTerminal();
    }
    
    //----LISTE DE GETTERS ET SETTERS (peut-être à renommer pour facilité)-----
    protected abstract void ajouterInterface(CarteReseau cr);

    public int getX() {

        return this.x;
    }

    public int getY() {

        return this.y;
    }
    
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

    public CarteReseau getInterfaceParDefaut(Machine machine) {

        CarteReseau carteReseau = this.getInterfaceCompatible(machine);
        // Si il n'existe pas de carteReseau compatible, une carte réseau non utilisée est récupérée
        for (int i = 0; i < this.cartesR.size(); i++) {
            if (carteReseau == null) {
                if (!this.ports.containsKey(this.cartesR.get(i))) {
                    carteReseau = this.cartesR.get(i);
                }
                else {
                    for (int j = 0; j < machine.cartesR.size(); j++) {
                        for (Map.Entry<CarteReseau, ArrayList<CarteReseau>> port : this.ports.entrySet()) {
                            if (port.getValue().contains(machine.cartesR.get(j))) {
                                carteReseau = port.getKey();
                            }
                        }
                    }
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

    public ArrayList<Terminal> getTerminaux() {
        return terminaux;
    }
    
    public HashMap<CarteReseau, ArrayList<CarteReseau>> getPorts() {

        return this.ports;
    }

    public int taillePorts() {

        int taillePort = 0;
        for (Map.Entry<CarteReseau, ArrayList<CarteReseau>> port : this.ports.entrySet()) {
            for (int i = 0; i < port.getValue().size(); i++) {
                taillePort++;
            }
        }
        return taillePort;
    }

    public boolean attribuerPort(Machine machineDest) {

        boolean portLibre = false;
        if (this.cartesR.size() != 0) {
            // Récupération de la carte réseau de "this" ayant la même adresse Réseau et le même masque que la celle du paramètre "machineDest"
            CarteReseau carteRSrc = this.getInterfaceParDefaut(machineDest);
            CarteReseau carteRDest = machineDest.getInterfaceParDefaut(this);
            if (this instanceof Ordinateur) {
                Machine.NBR_PORT = Ordinateur.NBR_PORT_FAST;
            }
            else if (this instanceof Routeur) {
                Machine.NBR_PORT = Routeur.NBR_PORT_GIGA;
            }
            else if (this instanceof Commutateur) {
                Machine.NBR_PORT = Commutateur.NBR_PORT_FAST;
            }
            if (this.ports.containsKey(carteRSrc) && this.taillePorts() < Machine.NBR_PORT) {
                this.ports.get(carteRSrc).add(carteRDest);
                portLibre = true;
            }
            else if (this.taillePorts() < Machine.NBR_PORT) {
                ArrayList<CarteReseau> cartesRDest = new ArrayList<>();
                cartesRDest.add(carteRDest);
                this.ports.put(carteRSrc, cartesRDest);
                portLibre = true;
            }    
        }
        return portLibre;
    }

    public void afficherPorts() {

        String ports = "[";
        if (this instanceof Ordinateur) {
            Machine.NBR_PORT = Ordinateur.NBR_PORT_FAST;
        }
        else if (this instanceof Routeur) {
            Machine.NBR_PORT = Routeur.NBR_PORT_GIGA;
        }
        else if (this instanceof Commutateur) {
            Machine.NBR_PORT = Commutateur.NBR_PORT_FAST;
        }
        int nbrPort = 0;
        int i = 0;
        for (Map.Entry<CarteReseau, ArrayList<CarteReseau>> cr : this.ports.entrySet()) {
            for (int j = 0; j < cr.getValue().size(); j++) {
                ports += cr.getKey().getNomInterface() + " => ";
                ports += IPv4.getStrAdresse(cr.getValue().get(j).getIP().getAdresseIP());
                if (j != (cr.getValue().size() - 1)) {
                    ports += "    ";
                }
                nbrPort++;
            }
            if (i != this.taillePorts() - 1) {
                ports += "    ";
            }
            i++;
        }
        for (int k = nbrPort; k < Machine.NBR_PORT; k++) {
            ports += "    " + (k + 1) + " => DOWN";
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