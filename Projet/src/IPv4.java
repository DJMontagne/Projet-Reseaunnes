
public class IPv4 {

	private final int NBR_OCTET = 4;
	private Octet[] adresseIP = {new Octet(), new Octet(), new Octet(), new Octet()};
	private Octet[] masque = {new Octet(), new Octet(), new Octet(), new Octet()};

	public IPv4(String mAdresseIP) {
		this.setAdresse(mAdresseIP);
	}

	public IPv4(Octet[] mAdresseIP) {
		this.setAdresse(mAdresseIP);
	}

	public IPv4() {
		this.adresseIP = genererAdresseApipa();
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

	public Octet[] getAdresse() {
		
		return this.adresseIP;
	}

	public Octet[] getMasque() {

		return this.masque;
	}

	/*#################################
	  #	10.X ==> 255.0.0.0
	  # 255.0.0.0 ==> 172.X
	  # 172.X ==> 255.255.0.0
	  # 255.255.0.0 ==> 192.X
	  # 192.X ==> 255.255.255.0
	  # 255.255.255.0 ==> 255.X ???
	  #################################
	 */

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
			System.out.println(sectionAddrIP[0]);
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
		if (addrIP[0].getDecimal() != 0) {
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
		for (int i = 0; i < NBR_OCTET; i++) {
			if (i != NBR_OCTET - 1) {
				adresseIPtoString += this.adresseIP[i].getDecimal() + ".";
			}
			else {
				adresseIPtoString += this.adresseIP[i].getDecimal();
			}
		}
		return adresseIPtoString;
	}

	public String affichageBinaire() {
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