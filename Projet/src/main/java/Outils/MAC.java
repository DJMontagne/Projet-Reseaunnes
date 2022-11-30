<<<<<<< HEAD:Projet/src/main/java/Outils/MAC.java
package Outils;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;

/**
 * @author bpotetma
 */
=======
package réseaunnés;
>>>>>>> 888f124a7c17b51cbdb90b2f78801966cb06d8b0:Projet/src/MAC.java

public class MAC {

    //Attributs
<<<<<<< HEAD:Projet/src/main/java/Outils/MAC.java
    private String adresse;
    private static ArrayList<String> adresses = new ArrayList<String>();

    //Constructeur

    public MAC() {
        this.adresse = genererAdresseUnique();
    }

    //Geters
    public String getAdresse() {
        return this.adresse;
    }

    //Setters
    /**
     * @param mAdresse => nouvelle adresse MAC
     */
    public void setAdresse(String mAdresse) {
        this.adresse = mAdresse;
=======
    private String MAC;

    //Constructeurs
    public MAC() {
        String mac = genererMAC();
        this.MAC = mac;
    }
    public MAC(String MAC) {
        this.MAC = MAC;
    }

    //Geters
    public String getMAC() {
        return MAC;
    }

    //Setters
    public void setMAC(String mAC) {
        MAC = mAC;
>>>>>>> 888f124a7c17b51cbdb90b2f78801966cb06d8b0:Projet/src/MAC.java
    }

    //Méthodes

<<<<<<< HEAD:Projet/src/main/java/Outils/MAC.java
    /**
     * @return Une adresse MAC viable en String
     */
    public String genererAdresse() {
=======
    //toString
    @Override
    public String toString() {
        return "Infos de l'adresse MAC" + "\n Adresse MAC : " + this.getMAC();
    }
    
    /**
     * @return Une adresse MAC viable en String
     */
    public String genererMAC() {
>>>>>>> 888f124a7c17b51cbdb90b2f78801966cb06d8b0:Projet/src/MAC.java
        
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
<<<<<<< HEAD:Projet/src/main/java/Outils/MAC.java

    /**
     * @param mac => adresse MAC à vérifier l'existence
     * @return un booléen, VRAI si l'adresse MAC passée en paramètre 
     * a déjà était utilisé par une instance de MAC sinon FAUX 
     */
    public boolean existenceAdresse(String mac) {

        boolean existenceMAC = false;
        // Parcours du tableau dynamique static "adresses"
        for (int i = 0; i < MAC.adresses.size(); i++) {
            /* *
            * Si l'argument "mac" existe dans le tableau dynamique "adresses", on affecte
            * la valeur TRUE à la variable "existenceMAC"
            */
            if (MAC.adresses.get(i).equals(mac)) {
                existenceMAC = true;
            }
        }
        return existenceMAC;
    }

    /**
     * @return une adresse MAC non utilisée de type String 
     */
    public String genererAdresseUnique() {
        // On génère une adresse MAC sans vérifier sa potentielle existence
        String mac = genererAdresse(); 
        // Tant que l'adresse MAC générée existe on reaffecte une nouvelle adresse MAC à la variable "mac"
        while (existenceAdresse(mac)) {
            mac = genererAdresse();
        }
        /**
        * On ajoute l'adresse MAC unique au tableau dynamique "adresses" comportant ainsi 
        * toutes les adresses MAC uniques qui sont utilisés par les instances de la classe MAC
        */
        MAC.adresses.add(mac);
        return mac;
    } 

    //toString
    @Override
    public String toString() {
        return "Infos de l'adresse MAC" + "\n Adresse MAC : " + this.getAdresse();
    }
}
=======
}
>>>>>>> 888f124a7c17b51cbdb90b2f78801966cb06d8b0:Projet/src/MAC.java
