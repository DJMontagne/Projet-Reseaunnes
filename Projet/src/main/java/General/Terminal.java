package General;

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
        this.mach = null;
    }

    public void arp(){ //affichage de la table ARP de la machine
        this.mach.getTableARP().affichage();
    }
    
    public void ping(){
        // à faire
    }
    
    public void ifconfig(){ //affichage de la config de la machine
        //this.mach.config();
    }
    
    //-----------Getters--------------
    public boolean isRun() {
        return run;
    }
    
}
