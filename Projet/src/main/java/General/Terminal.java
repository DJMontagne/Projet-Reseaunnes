package General;

import java.util.Scanner;
import Outils.*;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author victo
 */
public class Terminal {
    private boolean run; //Pour le While, pour savoir si le terminal doit être ouvert ou se fermer
    private Machine mach; //Machine sur laquelle le terminal va "s'afficher"
    
    public Terminal(Machine mach){
        this.run = true;
        this.mach = mach;
    }

    public void fermerTerminal(){ //retire le terminal de la liste des terminaux de la machine, le rendant inutilisable
        this.run = false;
        this.mach = null;
    }

    public String arp(){ //affichage de la table ARP de la machine
        return this.mach.getTableARP().toString();
    }

    public void ipRouteAdd(String strAddrReseau, int masqueDecimal, String passerelle) {

        if (this.mach instanceof Routeur) {
            
            Routeur routeur = (Routeur) this.mach;
            Octet[] masque = IPv4.genererMasque(passerelle);
            Octet[] addrReseau = IPv4.genererAdresseReseau(passerelle, masque);
            
            String nomInterface = null;
            TableRoutage tableRoutage = routeur.getTableRoutage();
            
            for (Map.Entry<Integer, String[]> table : tableRoutage.getTable().entrySet()) {
                if (table.getValue()[0].split("/")[0].equals(IPv4.getStrAdresse(addrReseau))) {
                    nomInterface = table.getValue()[2];
                }
            }

            String[] infosTableRoutage = {strAddrReseau + "/" + Integer.toString(masqueDecimal),
                passerelle,
                nomInterface};
            if (nomInterface != null) {
                tableRoutage.remplir(infosTableRoutage);
            }    
        }
    }
    
    public String ping(String addrIPDest, boolean verbose){

        final int NBR_PING = 4;
        boolean status;
        String addrIPSrc = null;
        // Empêche l'affichage de la commande "traceroute"
        ICMP.commande = "ping";

        if (this.mach.getCartesR().size() != 0) {
            if (mach instanceof Routeur) {
                Routeur routeur = (Routeur) mach;
                addrIPSrc = IPv4.getStrAdresse(routeur.getCarteRSelonRoute(addrIPDest).getIP().getAdresseIP());
            }
            else {
                addrIPSrc = IPv4.getStrAdresse(mach.getCartesR().get(0).getIP().getAdresseIP());
            }
        }
        else {
            return "Aucune carte réseau existante";
        }
        // Affichage
        String output = "PING " + addrIPDest + "\n";
        for (int i = 0; i < NBR_PING; i++) {
            // Requete ICMP
            if (verbose) {
                output += "\n";
            }
            status = ICMP.executerRequete(mach, addrIPSrc, addrIPDest, verbose);
            output += ICMP.pingVerboseOutput;
            if (status && ICMP.codeStatus == ICMP.FINISH) {
                if (verbose) {
                    output += "\nReply from ";
                }
                else {
                    output += "Reply from ";
                }
                output += addrIPDest + ": icmp_seq=" + (i + 1)
                    + " ttl=" + ICMP.ttl + " time";
                if (ICMP.tempsTotal < 1) {
                    output += "<1 ms";
                }
                else {
                    double temps = (double) Math.round(ICMP.tempsTotal * 100) / 100;
                    output += "=" + temps + " ms";
                }
            }
            else if (status && ICMP.codeStatus == ICMP.HOST_UNREACHABLE) {
            output += "Reply from " + addrIPDest + ": Destination host unreachable";
            }
            else {
                output += "Request timed out";
            }
            if (verbose && i != NBR_PING - 1) {
                output += "\n\n\n";
            }
            else if (i != NBR_PING - 1) {
                output += "\n";
            }
        }
        return output;
    }

    public String traceroute(String addrIPDest) {

        boolean status;
        String output = "";
        String addrIPSrc = null;
        ICMP.commande = "traceroute";
        if (this.mach.getCartesR().size() != 0) {
            if (mach instanceof Routeur) {
                Routeur routeur = (Routeur) mach;
                addrIPSrc = IPv4.getStrAdresse(routeur.getCarteRSelonRoute(addrIPDest).getIP().getAdresseIP());
            }
            else {
                addrIPSrc = IPv4.getStrAdresse(mach.getCartesR().get(0).getIP().getAdresseIP());
            }
        }
        else {
            return "Aucune carte réseau existante";
        }

        status = ICMP.executerRequete(mach, addrIPSrc, addrIPDest, false);
        output = ICMP.tracerouteOutput;

        return output;
    }
    
    public String ifconfig(){ //affichage de la config de la machine
        return this.mach.toString();
    }
    
    
    // MAIN DU PROGRAMME DU TERMINAL
    //Permet de lancer les méthodes correspondant à l'input de l'utilisateur
    public String executer(String utilisation){
        String input[] = utilisation.toLowerCase().split(" "); // séparer les mots entre les espaces dans un tableau de String
        String output = "La commande \"" + utilisation + "\" n'a pas été trouvée";
        switch (input[0]) {
            case "arp" -> {
                if (input.length == 1 && !(this.mach instanceof Commutateur)) {
                    output = this.mach.tableARP();
                }
                else if (input.length == 2 && input[1].equals("-d") && !(this.mach instanceof Commutateur)) {
                    this.mach.getTableARP().getTable().clear();
                    output = "Le cache ARP a été vidé";
                }
                else if (input.length == 3 && input[1].equals("-d") && IPValide(input[2]) && !(this.mach instanceof Commutateur)) {
                    boolean status = this.mach.getTableARP().getTable().containsKey(input[2]);
                    if (status) {
                        this.mach.getTableARP().getTable().remove(input[2]);
                        output = "L'entrée \"" + input[2] + "\" a été vidé du cache ARP"; 
                    }
                    else {
                        output = "Aucune entrée \"" + input[2] + "\" n'est renseignée dans la table ARP"; 
                    }
                }
                else {
                    output = "Aucune table ARP existante";
                }
            }
            case "show" -> {
                if (input.length == 2 && input[1].equals("mac-address-table")) {
                    if (this.mach instanceof Commutateur) {
                        Commutateur commutateur = (Commutateur) this.mach;
                        output = commutateur.tableMAC();
                    }
                    else {
                        output = "Aucune table MAC existante";
                    }
                }
            }
            case "clear" -> {
                if (input.length == 2 && input[1].equals("mac-address-table")) {
                    if (this.mach instanceof Commutateur) {
                        Commutateur commutateur = (Commutateur) this.mach;
                        output = commutateur.tableMAC();
                    }
                    else {
                        output = "Aucune table MAC existante";
                    }
                }
            } 
            case "ip" -> {
                if (input.length == 2 && input[1].equals("route") &&this.mach instanceof Routeur) {
                    Routeur routeur = (Routeur) this.mach;
                    output = routeur.tableRoutage();
                }
                else if (input.length == 4 && input[1].equals("route") && input[2].equals("delete") 
                && IPValide(input[3].split("/")[0]) && Integer.parseInt(input[3].split("/")[1]) >= 0 
                && Integer.parseInt(input[3].split("/")[1]) <= 32 && this.mach instanceof Routeur) {
                    Routeur routeur = (Routeur) this.mach;
                    Integer indice = routeur.getTableRoutage().getIndiceReseauMasque(input[3]);
                    if (indice != null) {
                        routeur.getTableRoutage().getTable().remove(indice);
                        output = "L'entrée \"" + input[3].split("/")[0] + "\" a été vidé";
                    }
                    else {
                        output = "Aucune entrée \"" + input[3].split("/")[0] + "\" n'est renseignée dans la table de routage"; 
                    }
                }
                else if (input.length == 6 && input[1].equals("route") && input[2].equals("add") 
                && IPValide(input[3].split("/")[0]) && Integer.parseInt(input[3].split("/")[1]) >= 0 
                && Integer.parseInt(input[3].split("/")[1]) <= 32 && input[4].equals("via") 
                && IPValide(input[5]) && this.mach instanceof Routeur) {
                    this.ipRouteAdd(input[3].split("/")[0], Integer.parseInt(input[3].split("/")[1]), input[5]);
                    output = "";
                }
                else if (this.mach instanceof Ordinateur || this.mach instanceof Commutateur) {
                    output = "Aucune table de routage existante";
                }
                else {
                    output = "Commande erronnée...";
                }
            } 
            case "ping" -> {
                if(input.length == 2) {
                    if (IPValide(input[1])) {
                        output = ping(input[1], false);
                    }
                    else{
                        output = "ping: " + input[1] + " : Nom ou service inconnu";
                    }
                }
                else if (input.length == 3 && input[1].equals("-v")) {
                    if (IPValide(input[2])) {
                        output = ping(input[2], true);
                    }
                    else {
                        output = "ping: " + input[1] + " : Nom ou service inconnu";
                    }
                }
                else{
                    output = "Commande erronée...";
                }
            }
        case "traceroute" -> {
            if(input.length == 2) {
                if (IPValide(input[1])) {
                    output = traceroute(input[1]);
                }
                else{
                    output = "traceroute: " + input[1] + " : Nom ou service inconnu";
                }
                }
                else{
                    output = "Commande erronée...";
                }
            }
            case "ifconfig" -> {
                if (input.length == 1) {
                    output = this.mach.config();
                }
            }
            case "" -> {
                output = "";
            }
        }
        return output;
    }
    
    //-----------Getters--------------
    public boolean isRun() {
        return run;
    }
    //---------fonctions privées------
    private boolean IPValide(String candidat){
        boolean validite = true;
        String util[] = candidat.split("\\.");
        if(!(util.length == 4)){
            validite = false;
        }
        if(validite){ // si les chaines de caractères sont pas des nombres
            int i = 0;
            while(validite && i < util.length){
                validite = util[i].matches("[+-]?\\d*(\\.\\d+)?");
                i++;
            }
        }
        
        if (validite){
            int premier = Integer.parseInt(util[0]);
            int deuxieme = Integer.parseInt(util[1]);
            int troisieme = Integer.parseInt(util[2]);
            int quatrieme = Integer.parseInt(util[3]);
            
            boolean boolpremier = ((premier > 0) && !(premier == 127) && (premier < 255));
            boolean booldeuxieme = ((deuxieme >= 0) && (deuxieme <= 255));
            boolean booltroisieme = ((troisieme >= 0) && (troisieme <= 255));
            boolean boolquatrieme = ((quatrieme >= 0) && (quatrieme <= 255));
            
            if(!boolpremier || !booldeuxieme || !booltroisieme || !boolquatrieme){
                validite = false;
            }
        }
        return validite;
    }
}