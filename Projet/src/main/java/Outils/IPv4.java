package Outils;

public class IPv4 {

	private final int NBR_OCTET = 4;
	private Octet[] adresseIP = {new Octet(), new Octet(), new Octet(), new Octet()};
	private Octet[] masque = {new Octet(), new Octet(), new Octet(), new Octet()};
	private Octet[] adresseReseau = {new Octet(), new Octet(), new Octet(), new Octet()};

	public IPv4() {
		this.adresseIP = genererAdresseApipa();
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
	}

	public IPv4(String mAdresseIP) {
		this.setAdresse(mAdresseIP);
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
	}

	public IPv4(Octet[] mAdresseIP) {
		this.setAdresse(mAdresseIP);
		this.masque = genererMasque(this.adresseIP);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
	}

	public IPv4(Octet[] mAdresseIP, Octet[] masque) {
		this.setAdresse(mAdresseIP);
		this.setMasque(masque);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
	}

	public IPv4(String mAdresseIP, String masque) {
		this.setAdresse(mAdresseIP);
		this.setMasque(masque);
		this.adresseReseau = genererAdresseReseau(this.adresseIP, this.masque);
	}

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

	public Octet[] genererMasque(Octet[] addrIP) {

		Octet[] masque = {new Octet(), new Octet(), new Octet(), new Octet()};
		if (adresseValide(addrIP)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				System.out.println(addrIP[i].getDecimal());
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

	public Octet[] genererAdresseReseau(Octet[] addrIP, Octet[] masque) {
		
		Octet[] addrReseau = {new Octet(), new Octet(), new Octet(), new Octet()};
		for (int i = 0; i < NBR_OCTET; i++) {
			for (int j = 0; j < Octet.NBR_BIT; j++) {
				if (masque[i].getOctet()[j] == 1) {
					addrReseau[i].getOctet()[j] = addrIP[i].getOctet()[j];  
				}
			}
		}
		return addrReseau;
	}

	public Octet[] getAdresse() {
		
		return this.adresseIP;
	}

	public Octet[] getMasque() {

		return this.masque;
	}

	public Octet[] getAdresseReseau() {

		return this.adresseReseau;
	}

	public void setAdresse(Octet[] addrIP) {
		
		if (adresseValide(addrIP)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				this.adresseIP[i].setOctet(addrIP[i].getDecimal());
			}
		}
	}

	public void setAdresse(String addrIP) {

		String[] sectionAddrIP = addrIP.split("\\.");
		for (int i = 0; i < NBR_OCTET; i++) {
			if (!sectionAddrIP[0].equals("0")) {
				this.adresseIP[i].setOctet(Integer.parseInt(sectionAddrIP[i]));
			}
		}
	}

	public void setMasque(Octet[] masque) {
		
		if (adresseValide(masque)) {
			for (int i = 0; i < NBR_OCTET; i++) {
				this.masque[i].setOctet(masque[i].getDecimal());
			}
		}
	}

	public void setMasque(String masque) {

		String[] sectionMasque = masque.split("\\.");
		for (int i = 0; i < NBR_OCTET; i++) {
			if (!sectionMasque[0].equals("0")) {
				this.masque[i].setOctet(Integer.parseInt(sectionMasque[i]));
			}
		}
	}

	public boolean adresseVide(Octet[] addr) {
	
		boolean existence = true;
		for (int i = 0; i < NBR_OCTET; i++) {
			if (addr[i].estVide()) {
				existence = false;
			}
		}
		return existence;
	}

	public boolean adresseValide(Octet[] addrIP) {

		boolean validite = false;
		if (addrIP[0].getDecimal() != 0 && addrIP[0].getDecimal() != 127 && addrIP[0].getDecimal() != 255) {
			validite = true;
		}
		return validite;
	}

	/*	
	public int[] getAdresseBroadcast() {

		if (existenceAdresseIP()) {

			int[] getAdresseBroadcast = this.adresseIP;
			for (int i = 0; i < NBR_OCTET; i++) {

			}
		}
	}
	*/
	

	@Override
	public String toString() {

		String adresseIPtoString = "";
		String masqueToString = "";
		String adresseReseauToString = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				adresseIPtoString += this.adresseIP[i].getDecimal() + ".";
				masqueToString += this.masque[i].getDecimal() + ".";
				adresseReseauToString += this.adresseReseau[i].getDecimal() + ".";
			}
			else {
				adresseIPtoString += this.adresseIP[i].getDecimal();
				masqueToString += this.masque[i].getDecimal();
				adresseReseauToString += this.adresseReseau[i].getDecimal();
			}
		}
		return "Adresse IP\n" + "    " + adresseIPtoString + "\n"
			+ "Masque\n" + "    " + masqueToString + "\n"
			+ "Adresse Réseau\n" + "    " + adresseReseauToString;
	}

	public String affichageAdresseIPBinaire() {
		
		String strBinaire = "";
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				strBinaire += this.adresseIP[i].toString() + ".";
			}
			else {
				strBinaire += this.adresseIP[i].toString();
			}
		}
		return strBinaire;
	}
}