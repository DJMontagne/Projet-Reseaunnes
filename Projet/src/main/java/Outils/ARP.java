package Outils;

import General.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author bpotetma
 */

public class ARP {
	
	private static int nbrEnvoieRequete = 0;

	public static String requete(Machine machineSrc, String strAddrIP) {

		Machine cpMachineSrc = machineSrc;
		Octet[] addrIP = IPv4.initAdresseVide();
		IPv4.setAdresse(addrIP, strAddrIP);
		Reseau reseau = Reseau.getReseauSelonMachine(machineSrc);
		String adresseMAC = null;
		int ttl = 600;

		while (adresseMAC == null && ttl > 0) {
			for (int i = 0; i < reseau.getLiaisons().size(); i++) {	
				for (Map.Entry<Machine, Machine> machine : reseau.getLiaisons().get(i).getLiaison().entrySet()) {
					ttl--;
					if (machine.getKey().equals(cpMachineSrc)) {
						System.out.print(cpMachineSrc + " ---> ");
						CarteReseau crMachineDestCompatible = machine.getValue().getInterfaceCompatible(machineSrc);
						if (crMachineDestCompatible != null && IPv4.estEgale(crMachineDestCompatible.getIP().getAdresseIP(), addrIP)) {
							adresseMAC = machine.getValue().getInterfaceCompatible(cpMachineSrc).getMAC().getAdresse();
							System.out.print(machine.getValue() + "\n");
							// Appel méthode de préparation et d'ajout de contenus pour la table ARP
							ARP.remplissageTableARP(machineSrc, machine.getValue());
							// Reponse de la requete
							nbrEnvoieRequete++;
							if (nbrEnvoieRequete == 1) {
								String iPMachineSrc = IPv4.getStrAdresse(machineSrc.getInterfaceCompatible(machine.getValue()).getIP().getAdresseIP());
								ARP.requete(machine.getValue(), iPMachineSrc);
							}
							ARP.nbrEnvoieRequete = 0; 
							// On sort de la boucle itérant les liaisons du réseau
							i = reseau.getLiaisons().size();
						}
						else {
							if (machine.getValue() instanceof Commutateur) {
								Commutateur commutateur = (Commutateur) machine.getValue();
								ARP.remplissageTableMAC(machineSrc, commutateur);
							}
							cpMachineSrc = machine.getValue();
						}
					}
					else if (machine.getValue().equals(cpMachineSrc)) {
						System.out.print(cpMachineSrc + " ---> ");
						CarteReseau crMachineDestCompatible = machine.getKey().getInterfaceCompatible(machineSrc);
						if (crMachineDestCompatible != null && IPv4.estEgale(crMachineDestCompatible.getIP().getAdresseIP(), addrIP)) {
							adresseMAC = machine.getKey().getInterfaceCompatible(cpMachineSrc).getMAC().getAdresse();
							System.out.print(machine.getKey() + "\n");
							// Appel méthode de préparation et d'ajout de contenus pour la table ARP
							ARP.remplissageTableARP(machineSrc, machine.getKey());
							// Reponse de la requete
							nbrEnvoieRequete++;
							if (nbrEnvoieRequete == 1) {
								String iPMachineSrc = IPv4.getStrAdresse(machineSrc.getInterfaceCompatible(machine.getKey()).getIP().getAdresseIP());
								ARP.requete(machine.getKey(), iPMachineSrc);
							}
							ARP.nbrEnvoieRequete = 0; 
							// On sort de la boucle itérant les liaisons du réseaux
							i = reseau.getLiaisons().size();
						}
						else {
							if (machine.getKey() instanceof Commutateur) {
								Commutateur commutateur = (Commutateur) machine.getKey();
								ARP.remplissageTableMAC(machineSrc, commutateur);
							}
							cpMachineSrc = machine.getKey();
						}
					}
				}
			}
		}
		return adresseMAC;
	}

	private static void remplissageTableARP(Machine machineSrc, Machine machineDest) {

		// Remplissage de la table ARP de la machine destination
		CarteReseau crCompatibleMachine = machineSrc.getInterfaceCompatible(machineDest);
		String[] infosMachineSrc = {IPv4.getStrAdresse(crCompatibleMachine.getIP().getAdresseIP()), 
									crCompatibleMachine.getMAC().getAdresse(),
									crCompatibleMachine.getNomInterface()};
		machineDest.getTableARP().remplir(machineSrc, infosMachineSrc);
		// Remplissage de la table ARP de la machine source
		crCompatibleMachine = machineDest.getInterfaceCompatible(machineSrc);
		String[] infosMachineDest = {IPv4.getStrAdresse(crCompatibleMachine.getIP().getAdresseIP()), 
									crCompatibleMachine.getMAC().getAdresse(),
									crCompatibleMachine.getNomInterface()};
		machineSrc.getTableARP().remplir(machineDest, infosMachineDest);
	}

	private static void remplissageTableMAC(Machine machineSrc, Commutateur commutateur) {

		// Attribution d'un numéro de port pour la machine enregistrée dans la table MAC
		ArrayList<Integer> numsPortUtilise = new ArrayList<>();
		for (Map.Entry<Machine, String[]> entree : commutateur.getTableMAC().getTable().entrySet()) {
			int num = Integer.parseInt(entree.getValue()[2].split("/")[1]);
			numsPortUtilise.add(num);
		}
		int numPort = 0;
		for (int i = 0; i < commutateur.getPorts().length; i++) {
			if (commutateur.getPorts()[i] != Machine.PORT_DOWN && !numsPortUtilise.contains(i)) {
				numPort = i;
			}
		}
		// Remplissage de la table MAC de la machine destination
		CarteReseau crCompatibleMachine = machineSrc.getInterfaceCompatible(commutateur);
		String[] infosMachineSrc = {crCompatibleMachine.getMAC().getAdresse(), 
									"DYNAMIC",
									"Fa0/" + numPort};
		commutateur.getTableMAC().remplir(machineSrc, infosMachineSrc);
	}
}