package Outils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author bpotetma
 */

public class IPv4 {

	private final int NBR_OCTET = 4;
	private Octet[] adresseIP = {new Octet(), new Octet(), new Octet(), new Octet()};
	private Octet[] masque = {new Octet(), new Octet(), new Octet(), new Octet()};
	private int masqueDecimal;
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

	public Octet[] genererAdresseReseau(Octet[] addrIP, Octet[] masque) {
		
		Octet[] addrReseau = {new Octet(), new Octet(), new Octet(), new Octet()};
		for (int i = 0; i < NBR_OCTET; i++) {
			for (int j = 0; j < Octet.NBR_BIT; j++) {
				if (masque[i].getOctet()[j] == 1) {
					this.masqueDecimal++;
					addrReseau[i].getOctet()[j] = addrIP[i].getOctet()[j];
				}
			}
		}
		return addrReseau;
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

	public boolean adresseVide(Octet[] addr) {
	
		boolean existence = true;
		for (int i = 0; i < NBR_OCTET; i++) {
			if (addr[i].estVide()) {
				existence = false;
			}
		}
		return existence;
	}

	public boolean adresseIPValide(Octet[] addrIP) {

		boolean validite = true;
		if (addrIP[0].getDecimal() == 0 && addrIP[0].getDecimal() == 127 && addrIP[0].getDecimal() == 255) {
			validite = false;
		}
		return validite;
	}

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

		return "Adresse IP\n" + "    " + this.affichageAdresseIP() + "\n"
			+ "Masque\n" + "    " + this.affichageMasque() + "\n"
			+ "Adresse Réseau\n" + "    " + this.affichageAdresseReseau() + "/" + this.getMasqueDecimal();
	}

	public String affichageAdresseIP() {
		
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

	public String affichageAdresseIPBinaire() {
		
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

	public String affichageMasque() {
		
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

	public String affichageMasqueBinaire() {
		
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

	public String affichageAdresseReseau() {
		
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

	public String affichageAdresseReseauBinaire() {
		
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
}