package Outils;

import General.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author bpotetma
 */

public class ICMP {
	
	private static int nbrEnvoieRequete;
	public static int ttl;

	private static Machine machineSrc;
	private static String addrIPSrc;

	private static Machine machineDest;
	private static String addrIPDest;
	private static String addrMacDest;

	public static final int PROCESSING = 0;
	public static final int HOST_UNREACHABLE = 1;
	public static final int REQUEST_TIMED_OUT = 2;
	public static final int FINISH = 3;
	public static int codeStatus;
	private static boolean messageHostUnreachable;
	public static double tempsTotal;

	public static boolean verbose;
	public static String commande;

	public static String pingVerboseOutput;
	public static String tracerouteOutput;

	public static boolean executerRequete(Machine mMachineSrc, String strAddrIPSrc, String strAddrIPDest, boolean mVerbose) {

		boolean allerRetour = false;

		boolean aller = false;
		boolean retour = false;
		
		verbose = mVerbose;
		
		nbrEnvoieRequete = 0;
		ttl = 255;
 		tempsTotal = 0;
		codeStatus = PROCESSING;
		messageHostUnreachable = false;
		
		machineSrc = mMachineSrc;
		addrIPSrc = strAddrIPSrc;
		addrIPDest = strAddrIPDest;

		pingVerboseOutput = "";
		tracerouteOutput = "";

		aller = ICMP.requete();
		if (aller) {
			nbrEnvoieRequete++;

			machineSrc = machineDest;
			String cpAddrIPSrc = addrIPSrc;
			addrIPSrc = addrIPDest;
			addrIPDest = cpAddrIPSrc;
			
			retour = ICMP.requete();
			if (!retour && codeStatus == HOST_UNREACHABLE) {
				nbrEnvoieRequete = 0;
				messageHostUnreachable = true;
				
				machineSrc = machineDest;
				cpAddrIPSrc = addrIPSrc;
				addrIPSrc = IPv4.getStrAdresse(machineDest.getInterfaceCompatible(machineSrc).getIP().getAdresseIP());
				addrIPDest = cpAddrIPSrc;
				ICMP.requete();
			}
			if (retour) {
				nbrEnvoieRequete++;
				codeStatus = FINISH;
				allerRetour = true;
			}
		}
		else if (!aller && codeStatus == HOST_UNREACHABLE) {
			nbrEnvoieRequete = 0;
			messageHostUnreachable = true;
			
			machineSrc = machineDest;
			String cpAddrIPSrc = addrIPSrc;
			addrIPSrc = IPv4.getStrAdresse(machineDest.getInterfaceCompatible(machineSrc).getIP().getAdresseIP());
			addrIPDest = cpAddrIPSrc;
			ICMP.requete();

			allerRetour = true;
		}

		return allerRetour;
	}

	private static boolean requete() {

		if (nbrEnvoieRequete == 0 && codeStatus == PROCESSING) {
			pingVerboseOutput += verbose ? "Envoie d'un message ICMP Echo Request...\n" : "";
		}
		else if (nbrEnvoieRequete == 0 && codeStatus == HOST_UNREACHABLE) {
			pingVerboseOutput += verbose ? "\nEnvoie d'un message ICMP Host Unreachable...\n\n" : "";
		}
		else if (nbrEnvoieRequete == 1 && codeStatus == PROCESSING) {
			pingVerboseOutput += verbose ? "\nEnvoie d'un message ICMP Echo Reply...\n" : "";
		}

		boolean destinationAtteinte = false;
		Machine cpMachineSrc = machineSrc;
		int cptMachineAtteinte = 0;

		addrMacDest = ICMP.gestionARP();

		while (!destinationAtteinte && ttl > 0 && (codeStatus == PROCESSING || messageHostUnreachable)) {
			for (int j = 0; j < Reseau.getReseaux().size(); j++) {
				for (int i = 0; i < Reseau.getReseaux().get(j).getLiaisons().size(); i++) {
					for (Map.Entry<Machine, Machine> machine : Reseau.getReseaux().get(j).getLiaisons().get(i).getLiaison().entrySet()) {
						if (machine.getKey().equals(cpMachineSrc)) {
							cpMachineSrc = machine.getKey();
						}
						else if (machine.getValue().equals(cpMachineSrc)) {
							cpMachineSrc = machine.getValue();
						}
						if (cpMachineSrc != null) {
							pingVerboseOutput += verbose ? cpMachineSrc + " ---> " : "";
							CarteReseau crDest = null;
							if (cpMachineSrc instanceof Routeur) {
								ttl--;
								Routeur routeur = (Routeur) cpMachineSrc;
								crDest = ICMP.redirectionSelonRoutage(routeur);
							}
							else if (cpMachineSrc instanceof Commutateur) {
								Commutateur commutateur = (Commutateur) cpMachineSrc;
								crDest = ICMP.redirectionSelonTableMAC(commutateur);
							}
							else {
								Ordinateur ordinateur = (Ordinateur) cpMachineSrc;
								crDest = ICMP.redirectionSelonPort(ordinateur);
							}
							try {
								machineDest = crDest.getMachine();
							}
							catch (NullPointerException e) {
								
								return destinationAtteinte;
							}
							// Affichage commande traceroute
							cptMachineAtteinte++;
							double temps = calculTemps(cpMachineSrc, crDest.getMachine());
							if (nbrEnvoieRequete == 0 && codeStatus == PROCESSING) {
								double tempsArrondi = (double) Math.round(temps * 1000) / 1000;
								tracerouteOutput += commande == "traceroute" ? cptMachineAtteinte + "  " + crDest.getMAC().getAdresse()
									+ "  (" + IPv4.getStrAdresse(crDest.getIP().getAdresseIP()) + ")  " + tempsArrondi + " ms" : "";
								tracerouteOutput += commande == "traceroute" && crDest != null 
									&& !IPv4.getStrAdresse(crDest.getIP().getAdresseIP()).equals(addrIPDest) ? "\n" : "";
							}
							// Si l'adresse IP de la carte réseau est égale à celle de destination
							if (crDest != null && IPv4.getStrAdresse(crDest.getIP().getAdresseIP()).equals(addrIPDest)) {
								pingVerboseOutput += verbose ? machineDest + "\n" : "";
								destinationAtteinte = true;
								return destinationAtteinte;
							}
							else {
								cpMachineSrc = machineDest;
							}
						}
					}
				}
			}
		}
		return destinationAtteinte;
	}

	private static CarteReseau redirectionSelonTableMAC(Commutateur commutateurSrc) {

		CarteReseau crDest = null;
		int indicePort = 0;
		for (Map.Entry<Integer, String[]> rowTable : commutateurSrc.getTableMAC().getTable().entrySet()) {
			if (rowTable.getValue()[0].equals(addrMacDest)) {
				indicePort = Integer.parseInt(rowTable.getValue()[2].split("/")[1]);
			}
		}
        int i = 1;
        for (Map.Entry<CarteReseau, ArrayList<CarteReseau>> ports : commutateurSrc.getPorts().entrySet()) {
            for (int j = 0; j < ports.getValue().size(); j++) {
                if (indicePort == i) {
                    crDest = ports.getValue().get(j);
                }
                i++;
            }
        }
		return crDest;
	}

	private static CarteReseau redirectionSelonRoutage(Routeur routeur) {

		Octet[] masqueDest = IPv4.genererMasque(addrIPDest);
		Octet[] addrReseauDest = IPv4.genererAdresseReseau(addrIPDest, masqueDest);

		CarteReseau crDest = null;
		String interfaceRedirection = null;
		String interfaceRequeteARP = null;
		TableARP tableARP = routeur.getTableARP();
		TableRoutage tableRoutage = routeur.getTableRoutage();

		if (tableRoutage.existenceReseau(IPv4.getStrAdresse(addrReseauDest))) {
			/*System.out.print(pingVerboseOutput += verbose ? "L'adresse réseau de destination " + IPv4.getStrAdresse(addrReseauDest) 
				+ " est renseigné dans la table de routage\n"); */
			if (tableARP.existence(addrIPDest)) {
				/*System.out.print(pingVerboseOutput += verbose ? "L'adresse IP de destination " + addrIPDest + " est renseigné dans la table ARP\n");*/
				interfaceRedirection = tableARP.getInterface(addrIPDest);
				addrMacDest = tableARP.getMAC(addrIPDest);
			}
			else if (tableRoutage.existencePasserelle(IPv4.getStrAdresse(addrReseauDest))) {
				/*System.out.print(pingVerboseOutput += verbose ? "\nL'adresse IP de destination " + addrIPDest + " n'est pas dans le même sous-réseau");*/
				String passerelle = tableRoutage.getPasserelle(IPv4.getStrAdresse(addrReseauDest));
				if (tableARP.existence(passerelle)) {
					pingVerboseOutput += verbose ? "\n\nLa passerelle du réseau de destination est renseigné dans la table ARP\n\n" : "";
					interfaceRedirection = tableARP.getInterface(passerelle);
					addrMacDest = tableARP.getMAC(passerelle);
				}
				else {
					addrIPDest = passerelle;
				}
			}
			if (interfaceRedirection == null) {
				interfaceRequeteARP = tableRoutage.getInterface(IPv4.getStrAdresse(addrReseauDest));
			}
		}
		else if (tableARP.existence(addrIPDest)) {
			pingVerboseOutput += verbose ? "L'adresse IP de destination " + addrIPDest + " est renseigné dans la table ARP\n" : "";
			interfaceRedirection = tableARP.getInterface(addrIPDest);
			addrMacDest = tableARP.getMAC(addrIPDest);
		}
		for (Map.Entry<CarteReseau, ArrayList<CarteReseau>> port : routeur.getPorts().entrySet()) {
			if (interfaceRedirection != null) {
				if (port.getKey().getNomInterface().equals(interfaceRedirection)) {
					crDest = port.getValue().get(0);
				}
			}
			else if (interfaceRequeteARP != null) {
				CarteReseau crMachineLiee = null;
				if (port.getKey().getNomInterface().equals(interfaceRequeteARP)) {
					crMachineLiee = port.getValue().get(0);
					Machine machineLiee = crMachineLiee.getMachine();
					pingVerboseOutput += verbose ? "\n\nL'adresse IP de destination  " + addrIPDest 
						+ " n'est pas renseigné dans la table ARP\n" + routeur + " détruit le paquet ICMP...\n" : "";
					ARP.requete(routeur, addrIPDest);
					pingVerboseOutput += verbose ? "" : "";
				}
			}
			else {
				if (!machineSrc.equals(routeur) && !tableRoutage.existenceReseau(IPv4.getStrAdresse(addrReseauDest))) {
					pingVerboseOutput += verbose ? "\n\nL'adresse réseau de destination n'est pas renseigné dans la table de routage\n" 
						+ routeur + " détruit le paquet ICMP...\n" : "";
					codeStatus = HOST_UNREACHABLE;
				}
				else if (!tableRoutage.existenceReseau(IPv4.getStrAdresse(addrReseauDest))) {
					pingVerboseOutput += verbose ? "\n\nL'adresse réseau de destination n'est pas renseigné dans la table de routage\n" 
						+ routeur + " détruit le paquet ICMP...\n" : "";
					codeStatus = REQUEST_TIMED_OUT;
				}
				
				return crDest;
			}	
		}
		return crDest;
	}

	private static CarteReseau redirectionSelonPort(Ordinateur ordinateur) {

		CarteReseau crDest = null;
		for (Map.Entry<CarteReseau, ArrayList<CarteReseau>> port : ordinateur.getPorts().entrySet()) {
			crDest = port.getValue().get(0);
		}
		return crDest;
	}

	private static String gestionARP() {

		String addrMacDest = null;

		Octet[] masqueDest = IPv4.genererMasque(addrIPDest);
		Octet[] addrReseauDest = IPv4.genererAdresseReseau(addrIPDest, masqueDest);
		boolean memeSousReseau = false;

		if (!(machineSrc instanceof Routeur)) {
			CarteReseau crMachineSrc = machineSrc.getCartesR().get(0);
			TableARP tableARP = machineSrc.getTableARP();
			if (IPv4.estEgale(masqueDest, crMachineSrc.getIP().getMasque()) 
			&& IPv4.estEgale(addrReseauDest, crMachineSrc.getIP().getReseau())) {
				memeSousReseau = true;
				if (tableARP.existence(addrIPDest)) {
					pingVerboseOutput += verbose ? "L'adresse IP de destination " + addrIPDest + " est renseigné dans la table ARP\n" : "";
					addrMacDest = tableARP.getMAC(addrIPDest);				}
				else {
					pingVerboseOutput += verbose ? "L'adresse IP de destination " + addrIPDest + " n'est pas renseigné dans la table ARP\n" : "";
					addrMacDest = ARP.requete(machineSrc, addrIPDest);
					pingVerboseOutput += verbose ? "" : "";
				}
			}
			else if (tableARP.existence(IPv4.getStrAdresse(crMachineSrc.getIP().getPasserelle()))) {
				pingVerboseOutput += verbose ? "L'adresse IP de destination " + addrIPDest + " n'est pas dans le même sous-réseau\n"
					+ "La passerelle est renseigné dans la table ARP\n\n" : "";
				addrMacDest = tableARP.getMAC(IPv4.getStrAdresse(crMachineSrc.getIP().getPasserelle()));
			}
			if (addrMacDest == null && !memeSousReseau) {
				pingVerboseOutput += verbose ? "L'adresse IP de destination " + addrIPDest + " n'est pas dans le même sous-réseau\n" : "";
				if (!IPv4.adresseVide(crMachineSrc.getIP().getPasserelle())) {
					pingVerboseOutput += verbose ? "La passerelle n'est pas renseigné dans la table ARP\n" : "";
					addrMacDest = ARP.requete(machineSrc, IPv4.getStrAdresse(crMachineSrc.getIP().getPasserelle()));
					pingVerboseOutput += verbose ? "\n" : "";
				}
				else {
					pingVerboseOutput += verbose ? "La passerelle par défaut n'est pas renseigné\n" 
						+ machineSrc + " détruit le paquet ICMP...\n" : "";
					codeStatus = REQUEST_TIMED_OUT;
				}
			}
			else if (addrMacDest == null && memeSousReseau) {
				pingVerboseOutput += verbose ? "La requête ARP a été interrompue après le délai," 
					+ "le processus ARP détruit le paquet ICMP...\n" : "";
				codeStatus = REQUEST_TIMED_OUT;
			}
		}
		return addrMacDest;
	}

	private static double calculTemps(Machine machine1, Machine machine2) {

		// On considère que la vitesse correspond à celle de la vitesse de la lumière soit 299 792 458 m/s
		final int VITESSE = 299792458;
		double distance = (double) Math.sqrt(Math.pow((machine2.getX() - machine1.getX()), 2) + (Math.pow((machine2.getY() - machine1.getY()), 2)));
		// On considère une échelle telle que 1 unité = 100 000m 
		double temps = ((100000 * distance) / VITESSE);
		tempsTotal += temps;

		return temps;
	}
}