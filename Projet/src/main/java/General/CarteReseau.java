/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package General;
import Outils.*;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Set;
/**
 *
 * @author victo
 */
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
        return adresseMAC;
    }
    public IPv4 getIp() {
        return ip;
    }
    public String getMasque() {
        return masque;
    }
    public String getNominterface() {
        return nominterface;
    }
    public String getPasserelle() {
        return passerelle;
    }
    
    //Setters
    public void setAdresseMAC(MAC adresseMAC) {
        this.adresseMAC = adresseMAC;
    }
    public void setIp(IPv4 ip) {
        this.ip = ip;
    }
    public void setMasque(String masque) {
        this.masque = masque;
    }
    public void setNominterface(String nominterface) {
        this.nominterface = nominterface;
    }
    public void setPasserelle(String passerelle) {
        this.passerelle = passerelle;
    }
    
   
}
