package Outils;

/**
 * @author bpotetma
 */

public class Octet {

	static final int NBR_BIT = 8;
	private int[] octet = {-1, -1, -1, -1, -1, -1, -1, -1};

	public Octet(int mOctet) {

		this.setOctet(mOctet);
	}

	public Octet() {

	}

	public int[] getOctet() {
		
		return this.octet;
	}

	public int getDecimal() {
		
		int decimal = 0;
		for (int i = 0; i < NBR_BIT; i++) {
			if (this.octet[i] == 1) {
				decimal += Math.pow(2, NBR_BIT - 1 - i);
			}
		}
		return decimal;
	}

	public void setOctet(int decimal) {
		
		for (int i = NBR_BIT - 1; i >= 0; i--) {
			if (decimal % 2 == 1) {
				this.octet[i] = decimal % 2;
			}
			else {
				this.octet[i] = 0;
			}
			decimal /= 2;
		} 
	}

	public boolean estEgale(Octet mOctet) {

		boolean estEgale = false;
		int nbrBitEgale = 0;
		for (int i = 0; i < NBR_BIT; i++) {
			if (this.octet[i] == mOctet.octet[i]) {
				nbrBitEgale++;
			}
		}
		if (nbrBitEgale == NBR_BIT) {
			estEgale = true;
		}
		return estEgale;
	}

	public boolean estVide() {

		boolean estVide = false;
		for (int i = 0; i < NBR_BIT; i++) {
			if (this.octet[i] == -1) {
				estVide = true;
			}
		}
		return estVide;
	}

	public String toString() {

		String octetToString = "";
		for (int i = 0; i < NBR_BIT; i++) {
			octetToString += String.valueOf(this.octet[i]);
		}
		return octetToString;
	}
}