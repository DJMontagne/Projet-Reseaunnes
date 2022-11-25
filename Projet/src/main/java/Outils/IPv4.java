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
	private Octet[] adresseIP = {new Octet(), new Octet(), new Octet(), new Octet()};
	private Octet[] masque = {new Octet(), new Octet(), new Octet(), new Octet()};
	private Octet[] adresseReseau = {new Octet(), new Octet(), new Octet(), new Octet()};
	private Octet[] adresseBroadcast = {new Octet(), new Octet(), new Octet(), new Octet()};
	// nombre de bit 
	private int masqueDecimal;

	public IPv4() {

		this.adresseIP = genererAdresseApipa();
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(String mAdresseIP) {

		this.setAdresse(mAdresseIP);
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(Octet[] mAdresseIP) {

		this.setAdresse(mAdresseIP);
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(Octet[] mAdresseIP, Octet[] masque) {

		this.setAdresse(mAdresseIP);
		this.setMasque(masque);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	public IPv4(String mAdresseIP, String masque) {

		this.setAdresse(mAdresseIP);
		this.setMasque(masque);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
		this.adresseBroadcast = genererAdresseBroadcast(this.adresseReseau, this.masque);
	}

	// Génère une adresse APIPA, utilisé si aucune adresse IP est donné en argument du constructeur
	public Octet[] genererAdresseApipa() {
		
		Octet[] adresseAPIPA = {new Octet(), new Octet(), new Octet(), new Octet()};
		
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

		Octet[] masque = {new Octet(), new Octet(), new Octet(), new Octet()};
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
		
		Octet[] addrReseau = {new Octet(), new Octet(), new Octet(), new Octet()};
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

		Octet[] adresseBroadcast = {new Octet(), new Octet(), new Octet(), new Octet()};
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

	public Octet[] getAdresse() {
		
		return this.adresseIP;
	}
	
	public int getMasqueDecimal() {

		return this.masqueDecimal;
	}

	public Octet[] getMasque() {

		return this.masque;
	}

	public Octet[] getAdresseReseau() {

		return this.adresseReseau;
	}

	public void setAdresse(Octet[] addrIP) {
		
		if (adresseIPValide(addrIP)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				this.adresseIP[i].setOctet(addrIP[i].getDecimal());
			}
		}
	}

	public void setAdresse(String strAddrIP) {

		String[] sectionAddrIP = strAddrIP.split("\\.");
		Octet[] addrIP = {new Octet(), new Octet(), new Octet(), new Octet()};
		for (int i = 0; i < NBR_OCTET; i++) {
			addrIP[i].setOctet(Integer.parseInt(sectionAddrIP[i]));
		}
		if (adresseIPValide(addrIP)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				this.adresseIP[i].setOctet(addrIP[i].getDecimal());
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
		Octet[] masque = {new Octet(), new Octet(), new Octet(), new Octet()};
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
	public boolean adresseVide(Octet[] addr) {
	
		boolean adresseVide = false;
		for (int i = 0; i < NBR_OCTET; i++) {
			if (addr[i].estVide()) {
				adresseVide = true;
			}
		}
		return adresseVide;
	}

	// retourne VRAI si l'adresse IP donné en argument est valide
	public boolean adresseIPValide(Octet[] addrIP) {

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
			+ "Masque\n" + "    " + this.getStrMasque() + "\n"
			+ "Adresse Réseau\n" + "    " + this.getStrAdresseReseau() + "/" + this.getMasqueDecimal() + "\n"
			+ "Adresse Broadcast\n" + "    " + this.getStrAdresseBroadcast();
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

	public String getStrAdresseReseau() {
		
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

	public String getStrAdresseReseauBinaire() {
		
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

	public String getStrAdresseBroadcast() {
		
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

	public String getStrAdresseBroadcastBinaire() {
		
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
}