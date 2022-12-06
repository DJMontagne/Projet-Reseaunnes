package Outils;

import General.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author bpotetma
 */

public class ARP {
	

	public ARP() {
		
	}

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
							// Remplissage de la table ARP de la machine destination
							CarteReseau crCompatibleMachine = machineSrc.getInterfaceCompatible(machine.getValue());
							String[] infosMachineSrc = {IPv4.getStrAdresse(crCompatibleMachine.getIP().getAdresseIP()), 
														crCompatibleMachine.getMAC().getAdresse(),
														crCompatibleMachine.getNomInterface()};
							machine.getValue().getTableARP().associerAdresses(machineSrc, infosMachineSrc);
							// Remplissage de la table ARP de la machine source
							crCompatibleMachine = machine.getValue().getInterfaceCompatible(machineSrc);
							String[] infosMachineDest = {IPv4.getStrAdresse(crCompatibleMachine.getIP().getAdresseIP()), 
														crCompatibleMachine.getMAC().getAdresse(),
														crCompatibleMachine.getNomInterface()};
							machineSrc.getTableARP().associerAdresses(machine.getValue(), infosMachineDest);
							// On sort de la boucle itérant les liaisons du réseau
							i = reseau.getLiaisons().size();
						}
						else {
							cpMachineSrc = machine.getValue();
						}
					}
					else if (machine.getValue().equals(cpMachineSrc)) {
						System.out.print(cpMachineSrc + " ---> ");
						CarteReseau crMachineDestCompatible = machine.getKey().getInterfaceCompatible(machineSrc);
						if (crMachineDestCompatible != null && IPv4.estEgale(crMachineDestCompatible.getIP().getAdresseIP(), addrIP)) {
							adresseMAC = machine.getValue().getInterfaceCompatible(cpMachineSrc).getMAC().getAdresse();
							System.out.print(machine.getKey() + "\n");
							// Remplissage de la table ARP de la machine destination
							CarteReseau crCompatibleMachine = machineSrc.getInterfaceCompatible(machine.getKey());
							String[] infosMachineSrc = {IPv4.getStrAdresse(crCompatibleMachine.getIP().getAdresseIP()), 
														crCompatibleMachine.getMAC().getAdresse(),
														crCompatibleMachine.getNomInterface()};
							machine.getKey().getTableARP().associerAdresses(machineSrc, infosMachineSrc);
							// Remplissage de la table ARP de la machine source
							crCompatibleMachine = machine.getValue().getInterfaceCompatible(machineSrc);
							String[] infosMachineDest = {IPv4.getStrAdresse(crCompatibleMachine.getIP().getAdresseIP()), 
														crCompatibleMachine.getMAC().getAdresse(),
														crCompatibleMachine.getNomInterface()};
							machineSrc.getTableARP().associerAdresses(machine.getKey(), infosMachineDest);
							i = reseau.getLiaisons().size();
						}
						else {
							cpMachineSrc = machine.getKey();
						}
					}
				}
			}
		}
		return adresseMAC;
	}
}