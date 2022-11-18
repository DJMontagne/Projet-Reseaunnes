package réseaunnés;

import java.net.UnknownHostException;
import java.net.Inet4Address;

public class IP {
    
    //Attributs
    private Inet4Address IP;
    private String addresseIP;

    //Constructeurs
    public IP() throws UnknownHostException {
        String ip = generateIP();
        this.addresseIP = ip;
        this.IP = (Inet4Address) Inet4Address.getByName(this.addresseIP);
    }
    public IP(Inet4Address ip,String adresse) {
        this.addresseIP = adresse;
        this.IP = ip;
    }
    public IP(String adresse) throws UnknownHostException {
        this.addresseIP = adresse;
        this.IP = (Inet4Address) Inet4Address.getByName(adresse);
    }
    public IP(Inet4Address ip) {
        this.addresseIP = ip.getHostAddress();
        this.IP = ip; 
    }

    //Getters
    public String getAdresse() {
        return addresseIP;
    }
    public Inet4Address getIP() {
        return IP;
    }

    //Setters
    public void setAdresse(String adresse) {
        this.addresseIP = adresse;
    }
    public void setIP(Inet4Address iP) {
        IP = iP;
    }

    //Méthodes

    //Genération d'IP
    public String generateIP() {
        String addresseIP = "";
        // Tableau des caractères possibles dans une @IP
        String caracteres1[] = {"172", "192"}; //Premier nombre de l'adresse

        String carac1 = caracteres1[(int) (Math.random() * caracteres1.length)];
        addresseIP += carac1; 
        addresseIP += ".";

        int min = 0; int max = 0; int range = max - min + 1;
        int rand = 0;
        switch(carac1) {
            case "172":
                min = 16; max = 31; range = max - min + 1;
                rand = (int)(Math.random() * range) + min;
                addresseIP += String.valueOf(rand);
                addresseIP += ".0.0";
                break;
            case "192":
                addresseIP += "168.";
                min = 0; max = 255; range = max - min + 1;
                rand = (int)(Math.random() * range) + min;
                addresseIP += String.valueOf(rand);
                addresseIP += ".0";
                break;
        }
        return addresseIP;
    }

    //Verifier si une IP est valide
    public boolean estValide() {
        boolean valide = true;
        return valide;
    }

    //Equals pour vérifier si deux IP sont identiques
    public boolean equals(Inet4Address ip) {
        return this.IP.equals(ip);
    }

    //toString
    @Override
    public String toString() {
        return "Infos de l'ip" + "\n IP en Inet4Address : " + this.getIP() 
                + " \n IP en String : " + this.getAdresse();
    }

}
