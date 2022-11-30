package Outils;


/**
 * Cette classe permet d'initialiser une adresse IP, si l'adresse IP est donné comme argument
 * du constructeur (type String ou Octet[]) un masque par défaut ainsi qu'une adresse réseau
 * et broadcast sont générées. Si cet object est instancié sans paramètre une adresse par défaut 
 * APIPA est généré automatiquement. Enfin si l'adresse IP, et un masque sont donnés en argument 
 * du constructeur alors, une adresse réseau et broadcast associées sont générées.
 * @author bpotetma
 */

public class IPv4 {

	// nombre d'octets d'une adresse IP
	static final int NBR_OCTET = 4;
	// adresses initialisées par 4 octets dont les bits sont nuls
	private Octet[] adresseIP = initAdresseVide();
	private Octet[] masque = initAdresseVide();
	private Octet[] adresseReseau = initAdresseVide();
	private Octet[] adresseBroadcast = initAdresseVide();
	private Octet[] adressePasserelle = initAdresseVide();
	// nombre de bit 
	private int masqueDecimal;

	public IPv4() {

		this.adresseIP = genererAdresseApipa();
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(String mAdresseIP) {

		IPv4.setAdresse(this.adresseIP, mAdresseIP);
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(Octet[] mAdresseIP) {

		IPv4.setAdresse(this.adresseIP, mAdresseIP);
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(Octet[] mAdresseIP, Octet[] masque) {

		IPv4.setAdresse(this.adresseIP, mAdresseIP);
		this.setMasque(masque);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(String mAdresseIP, String masque) {

		IPv4.setAdresse(this.adresseIP, mAdresseIP);
		this.setMasque(masque);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(Octet[] mAdresseIP, Octet[] masque, Octet[] mAdressePasserelle) {

		IPv4.setAdresse(this.adresseIP, mAdresseIP);
		this.setMasque(masque);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
		IPv4.setAdresse(this.adressePasserelle, mAdressePasserelle);
	}

	public IPv4(String mAdresseIP, String masque, String mAdressePasserelle) {

		IPv4.setAdresse(this.adresseIP, mAdresseIP);
		this.setMasque(masque);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
		IPv4.setAdresse(this.adressePasserelle, mAdressePasserelle);
	}

	public static Octet[] initAdresseVide() {
		
		Octet[] adresseVide = {new Octet(), new Octet(), new Octet(), new Octet()};
		return adresseVide;
	}

	// Génère une adresse APIPA, utilisé si aucune adresse IP est donné en argument du constructeur
	public Octet[] genererAdresseApipa() {
		
		Octet[] adresseAPIPA = initAdresseVide();
		
		for (int i = 0; i < adresseAPIPA.length; i++) {
			if (i == 0) {
				adresseAPIPA[i].setOctet(169);
			}
			else if (i == 1) {
				adresseAPIPA[i].setOctet(254);
			}
			else if (i == 2) {
				int min = 0;
				int max = 255;
				// nombre aléatoire généré entre min et max inclus
				int nbrAlea = min + (int) (Math.random() * ((max - min) + 1));
				adresseAPIPA[i].setOctet(nbrAlea);
			}
			else {
				int min = 1;
				int max = 254;
				int nbrAlea = min + (int) (Math.random() * ((max - min) + 1));
				adresseAPIPA[i].setOctet(nbrAlea);
			}
		}
		return adresseAPIPA;
	}

	// génère un masque par défaut en fonction l'adresse IP donné en argument de cette méthode
	public Octet[] genererMasque(Octet[] addrIP) {

		Octet[] masque = initAdresseVide();
		if (adresseIPValide(addrIP)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				if (addrIP[0].getDecimal() > 0 && addrIP[0].getDecimal() < 127) {
					masque[0].setOctet(255);
				}
				else if (addrIP[0].getDecimal() > 127 && addrIP[0].getDecimal() < 192) {
					masque[0].setOctet(255);
					masque[1].setOctet(255);
				}
				else if (addrIP[0].getDecimal() >= 192) {
					masque[0].setOctet(255);
					masque[1].setOctet(255);
					masque[2].setOctet(255);
				}
				else if (masque[i].getDecimal() != 255){
					masque[i].setOctet(0);
				}
			}
		}
		return masque;
	}

	// génère une adresse réseau en fonction de l'adresse IP et du masque donnés en argument de cette méthode
	public Octet[] genererAdresseReseau(Octet[] addrIP, Octet[] masque) {
		
		Octet[] addrReseau = initAdresseVide();
		for (int i = 0; i < NBR_OCTET; i++) {
			for (int j = 0; j < Octet.NBR_BIT; j++) {
				if (masque[i].getOctet()[j] == 1) {
					addrReseau[i].getOctet()[j] = addrIP[i].getOctet()[j];					
					this.masqueDecimal++;
				}
			}
		}
		return addrReseau;
	}

	// génère une adresse de diffusion en fonction de l'adresse Réseau et du masque donnés en argument de cette méthode
	public Octet[] genererAdresseBroadcast(Octet[] addrReseau, Octet[] masque) {

		Octet[] adresseBroadcast = initAdresseVide();
		for (int i = 0; i < NBR_OCTET; i++) {
			for (int j = 0; j < Octet.NBR_BIT; j++) {
				if (masque[i].getOctet()[j] == 1) {
					adresseBroadcast[i].getOctet()[j] = addrReseau[i].getOctet()[j];
				}
				else {
					adresseBroadcast[i].getOctet()[j] = 1;
				}
			}
		}
		return adresseBroadcast;
	}

	public Octet[] getAdresseIP() {
		
		return this.adresseIP;
	}
	
	public int getMasqueDecimal() {

		return this.masqueDecimal;
	}

	public Octet[] getMasque() {

		return this.masque;
	}

	public Octet[] getReseau() {

		return this.adresseReseau;
	}

	public Octet[] getBroadcast() {

		return this.adresseBroadcast;
	}

	public Octet[] getPasserelle() {

		return this.adressePasserelle;
	}

	/**
	 * @param addr
	 * @param nouvelleAddr
	 * Permet de remplacer l'addresse de paramètre "addr" avec celle de paramètre "nouvelleAddr",
	 * par exemple pour changer l'adresse IP la syntaxe sera this.ip.(this.ip.getAdresseIP(), foo) 
	 * depuis l'objet ip de classe IPv4 et this.ip(this.ip.getPasserelle(), foo)
	 */
	public static void setAdresse(Octet[] addr, Octet[] nouvelleAddr) {
		
		if (adresseIPValide(nouvelleAddr)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				addr[i].setOctet(nouvelleAddr[i].getDecimal());
			}
		}
	}

	static void setAdresse(Octet[] addr, String strNouvelleAddr) {

		String[] sectionAddr = strNouvelleAddr.split("\\.");
		Octet[] nouvelleAddr = initAdresseVide();
		for (int i = 0; i < NBR_OCTET; i++) {
			nouvelleAddr[i].setOctet(Integer.parseInt(sectionAddr[i]));
		}
		if (adresseIPValide(nouvelleAddr)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				addr[i].setOctet(nouvelleAddr[i].getDecimal());
			}
		}
	}

	public void setMasque(Octet[] masque) {
		
		if (masqueValide(masque)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				this.masque[i].setOctet(masque[i].getDecimal());
			}
		}
	}

	public void setMasque(String strMasque) {

		String[] sectionMasque = strMasque.split("\\.");
		Octet[] masque = initAdresseVide();
		for (int i = 0; i < NBR_OCTET; i++) {
			masque[i].setOctet(Integer.parseInt(sectionMasque[i]));
		}
		if (masqueValide(masque)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				this.masque[i].setOctet(masque[i].getDecimal());
			}
		}
	}

	// retourne VRAI si l'adresse donné en argument est vide, elle est vide si au moins un octet est vide
	static boolean adresseVide(Octet[] addr) {
	
		boolean adresseVide = false;
		for (int i = 0; i < NBR_OCTET; i++) {
			if (addr[i].estVide()) {
				adresseVide = true;
			}
		}
		return adresseVide;
	}

	// retourne VRAI si l'adresse IP donné en argument est valide
	static boolean adresseIPValide(Octet[] addrIP) {

		boolean validite = true;
		if (addrIP[0].getDecimal() == 0 || addrIP[0].getDecimal() == 127 || addrIP[0].getDecimal() == 255) {
			validite = false;
		}
		return validite;
	}

	// retourne VRAI si le masque donné en argument est valide
	public boolean masqueValide(Octet[] masque) {

		boolean validite = true;
		for (int i = 0; i < NBR_OCTET; i++) {
			for (int j = 0; j < Octet.NBR_BIT - 1; j++) {
				if (masque[i].getOctet()[j] == 0 && masque[i].getOctet()[j + 1] == 1) {
					validite = false;
				}
			}
		}
		return validite;
	}
	
	// affichage d'une instance de cette classe
	@Override
	public String toString() {

		return "Adresse IP\n" + "    " + this.getStrAdresseIP() + "\n"
			+ "Masque de sous-réseau\n" + "    " + this.getStrMasque() + "\n"
			+ "Adresse Réseau\n" + "    " + this.getStrReseau() + "/" + this.getMasqueDecimal() + "\n"
			+ "Adresse Broadcast\n" + "    " + this.getStrBroadcast() + "\n"
			+ "Passerelle par défaut\n" + "    " + this.getStrPasserelle();
	}

	public void afficher() {
		
		System.out.println(this.toString());
	}

	/**
	 * Récupération d'une adresse sous la forme X.X.X.X, de type String X représentant un octet dont les valeurs sont
	 * représentées soit en binaire, soti en décimal selon la méthode.  
	 */
	public String getStrAdresseIP() {
		
		String strAddrIP = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strAddrIP += this.adresseIP[i].getDecimal() + ".";
			}
			else {
				strAddrIP += this.adresseIP[i].getDecimal();
			}
		}
		return strAddrIP;
	}

	public String getStrAdresseIPBinaire() {
		
		String strAddrIPBinaire = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strAddrIPBinaire += this.adresseIP[i].toString() + ".";
			}
			else {
				strAddrIPBinaire += this.adresseIP[i].toString();
			}
		}
		return strAddrIPBinaire;
	}

	public String getStrMasque() {
		
		String strMasque = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strMasque += this.masque[i].getDecimal() + ".";
			}
			else {
				strMasque += this.masque[i].getDecimal();
			}
		}
		return strMasque;
	}

	public String getStrMasqueBinaire() {
		
		String strMasqueBinaire = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strMasqueBinaire += this.masque[i].toString() + ".";
			}
			else {
				strMasqueBinaire += this.masque[i].toString();
			}
		}
		return strMasqueBinaire;
	}

	public String getStrReseau() {
		
		String strAddrReseau = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strAddrReseau += this.adresseReseau[i].getDecimal() + ".";
			}
			else {
				strAddrReseau += this.adresseReseau[i].getDecimal();
			}
		}
		return strAddrReseau;
	}

	public String getStrReseauBinaire() {
		
		String strAddrReseauBinaire = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strAddrReseauBinaire += this.masque[i].toString() + ".";
			}
			else {
				strAddrReseauBinaire += this.masque[i].toString();
			}
		}
		return strAddrReseauBinaire;
	}

	public String getStrBroadcast() {
		
		String strAddrBroadcast = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strAddrBroadcast += this.adresseBroadcast[i].getDecimal() + ".";
			}
			else {
				strAddrBroadcast += this.adresseBroadcast[i].getDecimal();
			}
		}
		return strAddrBroadcast;
	}

	public String getStrBroadcastBinaire() {
		
		String strAddrBroadcastBinaire = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strAddrBroadcastBinaire += this.adresseBroadcast[i].toString() + ".";
			}
			else {
				strAddrBroadcastBinaire += this.adresseBroadcast[i].toString();
			}
		}
		return strAddrBroadcastBinaire;
	}

	public String getStrPasserelle() {
		
		String strAdressePasserelle = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strAdressePasserelle += this.adressePasserelle[i].getDecimal() + ".";
			}
			else {
				strAdressePasserelle += this.adressePasserelle[i].getDecimal();
			}
		}
		return strAdressePasserelle;
	}

	public String getStrPasserelleBinaire() {
		
		String strAdressePasserelle = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strAdressePasserelle += this.adressePasserelle[i].toString() + ".";
			}
			else {
				strAdressePasserelle += this.adressePasserelle[i].toString();
			}
		}
		return strAdressePasserelle;
	}
}