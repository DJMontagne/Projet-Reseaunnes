
package General;

import Outils.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bpotetma
 */

public class Liaison {

	private HashMap<Machine, Machine> liaison;
	private boolean validite;

	public Liaison() {
		
		this.liaison = new HashMap<>();
	}

	public boolean getValidite() {

		return this.validite;
	}

	public HashMap<Machine, Machine> getLiaison() {

		return this.liaison;
	}

	public Machine[] getMachines() {

		Machine[] machines = new Machine[2];
		for (Map.Entry<Machine, Machine> entree : this.liaison.entrySet()) {
			machines[0] = entree.getKey();
			machines[1] = entree.getValue();
		}
		return machines;
	}

	public boolean estEgale(Liaison cable) {

		boolean estEgale = false;
		int nbrMemeMachine = 0;
		for (int i = 0; i < this.getMachines().length; i++) {
			for (int j = 0; j < cable.getMachines().length; j++) {
				if (this.getMachines()[i].equals(cable.getMachines()[j])) {
					nbrMemeMachine++;
				}
			}
		}
		if (nbrMemeMachine == 2) {
			estEgale = true;
		}
		return estEgale;
	}

	/**
	 * @param machine1 
	 * @param machine2
	 */
	public void lier(Machine machine1, Machine machine2) {

		if (this.liaison.isEmpty()) {
			if (machine2.attribuerPort(machine1) && machine1.attribuerPort(machine2)) {
				this.liaison.put(machine1, machine2);
				this.validite = true;
			}
			else if (machine2.attribuerPort(machine1) && machine1.attribuerPort(machine2)) {
				this.liaison.put(machine1, machine2);
				this.validite = true;
			}
		}
		if (this.validite) {
			Reseau.ajouterAuReseau(this);
		}	
	}

	public void delier() {

		for (Machine machine : this.liaison.keySet()) {
			this.liaison.remove(machine);
		}
	}
}