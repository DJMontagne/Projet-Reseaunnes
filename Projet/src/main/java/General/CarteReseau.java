package General;
import Outils.*;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Set;

public class CarteReseau {
    //Atributs
    private IPv4 ip;
    private MAC adresseMAC;
    private String nominterface;
    private String masque;
    private String passerelle;

    //Constructeurs
    public CarteReseau() {
        this.ip = new IPv4(); 
        this.adresseMAC = new MAC(); 
        this.nominterface = "eth";
        this.masque = "";
        this.passerelle = "";
    }
    public CarteReseau(IPv4 ip, MAC adresseMAC, String nominerface, String masque, String passerelle)  {
        this.ip = ip;
        this.adresseMAC = adresseMAC; 
        this.nominterface = nominerface;
        this.masque = masque;
        this.passerelle = passerelle;
    }

    //Getters
    public MAC getAdresseMAC() {
        return this.adresseMAC;
    }
    public IPv4 getIp() {
        return this.ip;
    }
    public String getMasque() {
        return this.masque;
    }
    public String getNominterface() {
        return nominterface;
    }
    public String getPasserelle(){
        return this.passerelle;
    }
    
    public void setPasserelle(String passerelle) {
        this.passerelle = passerelle;
    }

    //Affichage

    @Override
    public String toString() {
        return " Nom de l'interface : " + this.getNominterface() +
        "\n \t Addresse MAC : " + this.getAdresseMAC().getAdresse()  +
        "\n \t Adresse IP : " + this.getIp().getStrAdresseIP() + 
        "\n \t Masque : " + this.getIp().getStrMasque() + 
        "\n \t Passerelle : " + this.getPasserelle();
    }
    
}
