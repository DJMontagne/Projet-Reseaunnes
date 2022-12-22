package General;
import java.util.Scanner;
import Outils.*;
import java.util.ArrayList;

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
    
    public void ping(IPv4 adrr){
        // à faire
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
                            ping(new IPv4(input[1]));
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
