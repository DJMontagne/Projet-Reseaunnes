/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package réseaunnés;

import java.net.UnknownHostException;

/**
 *
 * @author colin
 */
public class CarteReseau {

    //Atributs
    private IP adresseIP;
    private MAC adresseMAC;
    private String nominterface;
    private String masque;
    private String passerelle;

    //Constructeurs
    public CarteReseau() throws UnknownHostException {
        this.adresseIP = new IP(); //Pas encore fait
        this.adresseMAC = new MAC();
        this.nominterface = "eth";
        this.masque = "";
        this.passerelle = "";
    }
    public CarteReseau(IP ip, MAC adresseMAC, String nominerface, String masque, String passerelle)  {
        this.adresseIP = ip;
        this.adresseMAC = adresseMAC; 
        this.nominterface = nominerface;
        this.masque = masque;
        this.passerelle = passerelle;
    }

    //Getters
    public MAC getAdresseMAC() {
        return adresseMAC;
    }
    public IP getIp() {
        return adresseIP;
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
    public void setIp(IP ip) {
        this.adresseIP = ip;
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
    
    //


}