import java.util.ArrayList;

public class MAC {

    //Attributs
    private String adresseMAC;
    private static ArrayList<String> adressesMAC = new ArrayList<String>();

    //Constructeurs

    public MAC() {
        this.adresseMAC = genererMacUnique();
    }

    //Geters
    public String getMAC() {
        return this.adresseMAC;
    }

    //Setters
    public void setMAC(String mac) {
        this.adresseMAC = mac;
    }

    //Méthodes

    /**
     * @return Une adresse MAC viable en String
     */
    public String genererMac() {
        
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

    /**
     * @return un booléen, VRAI si l'adresse MAC passée en paramètre 
     * a déjà était utilisé par une instance de MAC sinon FAUX 
     */
    public boolean existenceMac(String mac) {

        boolean existenceMAC = false;
        // Parcours du tableau dynamique static "adressesMAC"
        for (int i = 0; i < MAC.adressesMAC.size(); i++) {
            /* *
            * Si l'argument "mac" existe dans le tableau dynamique "adressesMAC", on affecte
            * la valeur TRUE à la variable "existenceMAC"
            */
            if (MAC.adressesMAC.get(i).equals(mac)) {
                existenceMAC = true;
            }
        }
        return existenceMAC;
    }

    /**
     * @return une adresse MAC non utilisée de type String 
     */
    public String genererMacUnique() {
        // On génère une adresse MAC sans vérifier son existence potentielle
        String mac = genererMac(); 
        // Tant que l'adresse MAC générée existe on reaffecte une nouvelle adresse MAC à la variable "mac"
        while (existenceMac(mac)) {
            mac = genererMac();
        }
        /**
        * On ajoute l'adresse MAC unique au tableau dynamique "adressesMAC" comportant ainsi 
        * toutes les adresses MAC uniques qui sont utilisés par les instances de la classe MAC
        */
        MAC.adressesMAC.add(mac);
        return mac;
    } 

    //Affichage pour une instance de MAC
    @Override
    public String toString() {
        return "Infos de l'adresse MAC" + "\n Adresse MAC : " + this.getMAC();
    }
}
