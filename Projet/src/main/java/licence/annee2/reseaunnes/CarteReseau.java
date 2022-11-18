/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licence.annee2.reseaunnes;

/**
 *
 * @author victo
 */
public class CarteReseau {
    //Atributs
    private IP ip;
    private String adresseMAC;
    private String nominterface;
    private String masque;
    private String passerelle;

    //Constructeurs
    public CarteReseau() {
        this.ip = new IP(); //Besoin code Baptiste
        this.adresseMAC = new adresseMAC(); //Besoin code Baptiste
        this.nominterface = "eth";
        this.masque = "";
        this.passerelle = "";
    }
    public CarteReseau(IP ip, String adresseMAC, String nominerface, String masque, String passerelle)  {
        this.ip = ip;
        this.adresseMAC = adresseMAC; 
        this.nominterface = nominerface;
        this.masque = masque;
        this.passerelle = passerelle;
    }

    //Getters
    public String getAdresseMAC() {
        return adresseMAC;
    }
    public IP getIp() {
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
    public void setAdresseMAC(String adresseMAC) {
        this.adresseMAC = adresseMAC;
    }
    public void setIp(IP ip) {
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
    
    //
}
