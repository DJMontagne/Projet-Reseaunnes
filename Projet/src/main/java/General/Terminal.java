package General;
import java.util.Map;
import java.util.Scanner;

import Outils.ICMP;
import Outils.IPv4;
import Outils.Octet;
import Outils.TableRoutage;


/**
 *
 * @author victo
 */
public class Terminal {
    private boolean on; //Pour le While, pour savoir si le terminal doit être ouvert ou se fermer
    private Machine mach; //Machine sur laquelle le terminal va "s'afficher"
    
    public Terminal(Machine mach){
        this.on = true;
        this.mach = mach;
    }
    public void fermerTerminal(){ //arrête le terminal
        this.on = false;
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
    
    public void ping(String addrIPDest, boolean verbose){

        final int NBR_PING = 4;
        boolean status;
        String addrIPSrc = null;
        // Empêche l'affichage de la commande "traceroute"
        ICMP.commande = "ping";

        if (mach instanceof Routeur) {
            Routeur routeur = (Routeur) mach;
            addrIPSrc = IPv4.getStrAdresse(routeur.getCarteRSelonRoute(addrIPDest).getIP().getAdresseIP());
        }
        else {
            addrIPSrc = IPv4.getStrAdresse(mach.getCartesR().get(0).getIP().getAdresseIP());
        }
        // Affichage
        System.out.println("\nPING " + addrIPDest + "\n");
        for (int i = 0; i < NBR_PING; i++) {
            // Requete ICMP
            String output = "";
            if (verbose) {
                output += "\n";
            }
            status = ICMP.executerRequete(mach, addrIPSrc, addrIPDest, verbose);
            if (status && ICMP.codeStatus == ICMP.FINISH) {
                output += "Reply from " + addrIPDest + ": icmp_seq=" + (i + 1)
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
            System.out.println(output);
        }
    }

    public void traceroute(String addrIPDest) {

        boolean status;
        String addrIPSrc = null;
        ICMP.commande = "traceroute";

        if (mach instanceof Routeur) {
            Routeur routeur = (Routeur) mach;
            addrIPSrc = IPv4.getStrAdresse(routeur.getCarteRSelonRoute(addrIPDest).getIP().getAdresseIP());
        }
        else {
            addrIPSrc = IPv4.getStrAdresse(mach.getCartesR().get(0).getIP().getAdresseIP());
        }

        status = ICMP.executerRequete(mach, addrIPSrc, addrIPDest, false);
    }
    
    public String ifconfig(){ //affichage de la config de la machine
        return this.mach.toString();
    }
    
    
    // MAIN DU PROGRAMME DU TERMINAL
    //Permet de lancer les méthodes correspondant à l'input de l'utilisateur
    public void run(){
        Scanner sc = new Scanner(System.in);
        while(this.on){
            System.out.println("\nEntrez une commande : ");
            String utilisation = sc.nextLine();
            utilisation = utilisation.toLowerCase();
            String input[] = utilisation.split(" ");
            switch (input[0]) {
                case "arp" -> {
                    System.out.println(arp());
                }
                case "ping" -> {
                    if(input.length == 2){
                        if(IPValide(input[1])){
                            //ping(input[1]);
                        }else{
                            System.out.println("adresse ip non valide ...");
                        }
                    }else{
                        System.out.println("Commande erronée ... (n'entrez qu'une adresse ip)");
                    }
                }
                case "ifconfig" -> {
                    System.out.println(this.ifconfig());
                }
                case "q"->{
                    this.fermerTerminal();
                }
                default -> {
                        System.out.println("commande non reconnue");
                }
            }
        }
    }
    
    //-----------Getters--------------
    public boolean isOn() {
        return on;
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
