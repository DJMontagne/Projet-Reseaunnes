package réseaunnés;

public class MAC {

    //Attributs
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
    }

    //Méthodes

    //toString
    @Override
    public String toString() {
        return "Infos de l'adresse MAC" + "\n Adresse MAC : " + this.getMAC();
    }
    
    /**
     * @return Une adresse MAC viable en String
     */
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
}
