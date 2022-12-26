package Outils;

import General.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * @author bpotetma
 */

public class ARP {
	
        private static int nbrEnvoieRequete = 0;

	public static String requete(Machine machineSrc, String strAddrIP) {

		ICMP.verbose = true;

		if (nbrEnvoieRequete == 0) {
			System.out.print(ICMP.verbose ? "\nEnvoie d'une requête ARP...\n\n" : "");
		}
		else if (nbrEnvoieRequete == 1) {
			System.out.print(ICMP.verbose ? "\nRéponse de la requête ARP...\n\n" : "");
		}

		CarteReseau crSrc = null;
		if (machineSrc instanceof Routeur) {
			Routeur routeur = (Routeur) machineSrc;
			crSrc = routeur.getCarteRSelonRoute(strAddrIP);
		}
		else {
			crSrc = machineSrc.getCartesR().get(0);
		}

		CarteReseau crCourante = crSrc;
		Machine machineCourante = machineSrc;
		Octet[] addrIP = IPv4.initAdresseVide();
		IPv4.setAdresse(addrIP, strAddrIP);
		String adresseMAC = null;
		ArrayList<Commutateur> fileCommutateur = new ArrayList<>();
		Machine commutateurAtteint = null;

		Iterator<Map.Entry<CarteReseau, ArrayList<CarteReseau>>> iterateur = machineCourante.getPorts().entrySet().iterator();
		while (iterateur.hasNext()) {
			Map.Entry<CarteReseau, ArrayList<CarteReseau>> cr = iterateur.next();	
			if (crCourante.equals(cr.getKey())) {
				for (int i = 0; i < cr.getValue().size(); i++) {
					CarteReseau crDest = cr.getValue().get(i);
					Machine machineDest = crDest.getMachine();
					if (!machineDest.equals(commutateurAtteint) && !machineDest.equals(machineSrc)) {
						System.out.print(ICMP.verbose ? machineCourante + " ---> " : "");
						if (IPv4.estEgale(crDest.getIP().getAdresseIP(), addrIP)) {
							adresseMAC = crDest.getMAC().getAdresse();
							System.out.print(ICMP.verbose ? machineDest + "\n" : "");
							// Appel méthode de préparation et d'ajout de contenus pour la table ARP
							ARP.remplissageTableARP(machineSrc, machineDest);
							// Reponse de la requete
							nbrEnvoieRequete++;
							if (nbrEnvoieRequete == 1) {
								String iPMachineSrc = IPv4.getStrAdresse(machineSrc.getInterfaceCompatible(machineDest).getIP().getAdresseIP());
								ARP.requete(machineDest, iPMachineSrc);
							}
							ARP.nbrEnvoieRequete = 0; 
							// On sort de la boucle itérant les liaisons du réseau
							i = cr.getValue().size();
						}
						else {
							System.out.print(ICMP.verbose ? machineDest + "   " : "");
							if (machineDest instanceof Commutateur) {
								Commutateur commutateur = (Commutateur) machineDest;
								ARP.remplissageTableMAC(crSrc.getMAC().getAdresse(), machineCourante, commutateur);
								fileCommutateur.add(commutateur);
								if (machineCourante instanceof Commutateur) {
									commutateurAtteint = machineCourante;
								}	
							}
							if (i == cr.getValue().size() - 1 && !fileCommutateur.isEmpty()) {
								machineCourante = fileCommutateur.get(0);
								iterateur = machineCourante.getPorts().entrySet().iterator();
								fileCommutateur.remove(0);
								crCourante = machineCourante.getCartesR().get(0);
							}
						}
					}
					else if (i == cr.getValue().size() - 1 && !fileCommutateur.isEmpty()) {
						machineCourante = fileCommutateur.get(0);
						iterateur = machineCourante.getPorts().entrySet().iterator();
						fileCommutateur.remove(0);
						crCourante = machineCourante.getCartesR().get(0);
					}
				}
			}
		}
		if (adresseMAC == null) {
				System.out.print(ICMP.verbose ? "\n\nL'hôte de destination n'a pas été trouvé, le paquet ARP se détruit...\n" : "");
		}
		return adresseMAC;
	}

	private static void remplissageTableARP(Machine machineSrc, Machine machineDest) {

		// Remplissage de la table ARP de la machine destination
		CarteReseau crCompatibleMachineSrc = machineSrc.getInterfaceCompatible(machineDest);
		CarteReseau crCompatibleMachineDest = machineDest.getInterfaceCompatible(machineSrc);
		
		if (crCompatibleMachineSrc != null && crCompatibleMachineDest != null) {
			String[] infosMachineSrc = {IPv4.getStrAdresse(crCompatibleMachineSrc.getIP().getAdresseIP()), 
									crCompatibleMachineSrc.getMAC().getAdresse(),
									crCompatibleMachineDest.getNomInterface()};
			machineDest.getTableARP().remplir(infosMachineSrc);
			// Remplissage de la table ARP de la machine source
			String[] infosMachineDest = {IPv4.getStrAdresse(crCompatibleMachineDest.getIP().getAdresseIP()), 
										crCompatibleMachineDest.getMAC().getAdresse(),
										crCompatibleMachineSrc.getNomInterface()};
			machineSrc.getTableARP().remplir(infosMachineDest);
		}
	}

	private static void remplissageTableMAC(String addrMacSrc, Machine machineCourante, Commutateur commutateur) {


		// Attribution d'un numéro de port pour la machine enregistrée dans la table MAC
		int numPort = 0;
		int i = 0;
		for (Map.Entry<CarteReseau, ArrayList<CarteReseau>> cr : commutateur.getPorts().entrySet()) {
			for (int j = 0; j < cr.getValue().size(); j++) {
				if (cr.getValue().get(j).getMachine().equals(machineCourante)) {
					numPort = i + 1;
				}
				i++;
			}
		}
		String[] infosMachineSrc = {addrMacSrc, 
									"DYNAMIC",
									"Fa0/" + numPort};
		if (numPort != 0) {	
			commutateur.getTableMAC().remplir(infosMachineSrc);
		}
	}
}