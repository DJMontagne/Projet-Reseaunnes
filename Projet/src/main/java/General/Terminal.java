package General;
import java.util.Scanner;

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
        this.mach.getTerminaux().remove(this);
        this.run = false;
        this.mach = null;
    }

    public String arp(){ //affichage de la table ARP de la machine
        return this.mach.getTableARP().toString();
    }
    
    public void ping(){
        // à faire
    }
    
    public String ifconfig(){ //affichage de la config de la machine
        return this.mach.toString();
    }
    
    
    // MAIN DU PROGRAMME DU TERMINAL
    //Permet de lancer les méthodes correspondant à l'input de l'utilisateur
    public void on(){
        Scanner sc = new Scanner(System.in);
        while(this.run){
            System.out.println("\nEntrez une commande : ");
            String input = sc.nextLine();
            System.out.println("");
            switch (input) {
                case "arp" -> System.out.println(arp());
                case "ping" -> ping();
                case "ifconfig" -> System.out.println(this.ifconfig());
                default -> {
                        System.out.println("commande non reconnue");
                        this.fermerTerminal(); // pour le test
                }
            }
        }
    }
    
    //-----------Getters--------------
    public boolean isRun() {
        return run;
    }
    
}