
import java.util.HashMap;
import java.util.Map;

/**
 * @author bpotetma
 */

public class Liaison {

	private HashMap<Machine, Machine> liaison;
	private String typeCable;

	// Constructeurs
	public Liaison() {

	}
	/**
	 * @param typeCable => 2 valeurs possibles de type String cable "droit" ou cable "croisé"
	 */
	public Liaison(String mTypeCable) {
		if (mTypeCable.equals("droit") || mTypeCable.equals("croisé")) {
			this.typeCable = mTypeCable;
		}
		this.liaison = new HashMap<>();
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

		boolean liaisonValide = false;	
		if (this.liaison.isEmpty()) {

			if (this.typeCable.equals("droit") && ((!(machine1 instanceof Commutateur) && (machine2 instanceof Commutateur))
			|| (!(machine2 instanceof Commutateur) && (machine1 instanceof Commutateur)))) {
				if (machine2.attribuerPort(machine1) && machine1.attribuerPort(machine2)) {
					this.liaison.put(machine1, machine2);
					liaisonValide = true;
				}
			}
			else if (this.typeCable.equals("croisé") && ((!(machine1 instanceof Commutateur)  && !(machine2 instanceof Commutateur))
			|| ((machine1 instanceof Commutateur) && (machine2 instanceof Commutateur)))) {
				if (machine2.attribuerPort(machine1) && machine1.attribuerPort(machine2)) {
					this.liaison.put(machine1, machine2);
					liaisonValide = true;
				}
			}
		}
		if (liaisonValide) {
			Reseau.ajouterAuReseau(this);
		}	
	}

	public void delier() {

		for (Machine machine : this.liaison.keySet()) {
			this.liaison.remove(machine);
		}
	}
}