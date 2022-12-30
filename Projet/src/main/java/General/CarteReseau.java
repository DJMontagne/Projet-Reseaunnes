package General;

import Outils.*;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Set;

public class CarteReseau {
    //Atributs
    private IPv4 ip;
    private MAC mac;
    private String nominterface;
    private Machine machine;

    //Constructeurs
    public CarteReseau() {
        this.ip = new IPv4(); 
        this.mac = new MAC(); 
        this.nominterface = "eth";
    }

    public CarteReseau(String nominterface) {
        this.ip = new IPv4(); 
        this.mac = new MAC(); 
        this.nominterface = nominterface;
    }

    public CarteReseau(String nominerface, String ip)  {
        this.ip = new IPv4(ip);
        this.mac = new MAC();
        this.nominterface = nominerface;
    }

    public CarteReseau(String nominerface, String ip, String masque)  {
        this.ip = new IPv4(ip, masque);
        this.mac = new MAC();
        this.nominterface = nominerface;
    }

    public CarteReseau(String nominerface, String ip, String masque, String passerelle)  {
        this.ip = new IPv4(ip, masque);
        this.mac = new MAC();
        this.nominterface = nominerface;
        this.setPasserelle(passerelle);
    }

    public CarteReseau(String nominerface, String ip, String masque, String adresseMAC, String passerelle)  {
        this.ip = new IPv4(ip, masque);
        this.mac = new MAC();
        this.mac.setAdresse(adresseMAC);
        this.nominterface = nominerface;
        this.setPasserelle(passerelle);
    }

    //Getters
    public MAC getMAC() {
        return this.mac;
    }

    public IPv4 getIP() {
        return this.ip;
    }

    public String getNomInterface() {

        return this.nominterface;
    }

    public Machine getMachine() {

        return this.machine;
    }
    
    //Setters
    public void setAdresseMAC(String adresseMAC) {
        this.mac.setAdresse(adresseMAC);
    }

    public void setIP(IPv4 ip) {
        this.ip = ip;
    }

    public void setNominterface(String nominterface) {
        this.nominterface = nominterface;
    }

    public void setAdresseIP(String addrIP) {

        this.ip = new IPv4(addrIP, IPv4.getStrAdresse(this.ip.getMasque()));
    }

    public void setMachine(Machine machine) {

        this.machine = machine;
    }
    
    /**
     *  Lors du changement du masque, l'adresse réseau doit être recalculée
     *  on créé alors un nouvel objet IPv4 avec l'adresse IP de la carte réseau
     *  et le masque spécifié en paramètre
     */
    public void setMasque(String masque) {

        this.ip = new IPv4(IPv4.getStrAdresse(this.ip.getAdresseIP()), masque);
    }

    public void setPasserelle(String passerelle) {
        
        IPv4.setAdresse(this.ip.getPasserelle(), passerelle);
    }

    //Affichage

    @Override
    public String toString() {

        return this.nominterface + ":\n" + "Adresse MAC\n" + "    " + this.mac.getAdresse() + "\n" 
        + this.ip.toString(); 
    }

    public void afficher() {

        System.out.println(this.toString());
    }   
}