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
        this.adresseMAC = this.genererMAC(); //Besoin code Baptiste
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
    
    // Méthode permettant de générer une adresse MAC d'octet aléatoire
public String genererMAC() {
        
    // Nombre de caractère dans une @MAC
    final int NB_CARACTERE_MAC = 12;
    String adresseMAC = "";
    // Tableau des caractères possibles dans une @MAC
    String caracteres[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    // Compteur servant de repère pour placer un séparateur entre les caractères
    int cpt = 0;

    for (int i = 0; i < NB_CARACTERE_MAC; i++) {            
        // On place un séparateur tout les 2 caractères
        if (cpt == 2) {
            adresseMAC += ":";
            cpt = 0;
        }
        // Récupèration d'un caractère du tableau aléatoirement
        String caractereAlea = caracteres[(int) (Math.random() * caracteres.length)];
        // Concaténation du caractère aléatoire à la chaîne de caractère "adresseMAC"
        adresseMAC += caractereAlea;
        cpt++;
    }
    return adresseMAC;
}
    //
}
