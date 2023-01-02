package General;

import Outils.*;
import java.util.ArrayList;
import java.util.Map;

public class Routeur extends Machine {

	public static final int NBR_PORT_GIGA = 2;
    public static final int NBR_CR = 2;
	private int id;
    private static int nbrRouteur;
    private TableRoutage tableRoutage;

	public Routeur(int x, int y) {

		super(x, y);
		nbrRouteur++;
		this.id = nbrRouteur;
		this.tableRoutage = new TableRoutage();
        this.nbrPortMax = NBR_PORT_GIGA;
	}
	
	public TableRoutage getTableRoutage() {
        
        return tableRoutage;
    }

    public void setTableRoutage(TableRoutage tableRoutage) {
        
        this.tableRoutage = tableRoutage;
    }

	@Override
	// Permet d'ajouter une carte réseau à une machine
    public boolean ajouterInterface(CarteReseau cr) {
        
    	boolean validiteCarteR = true;
	    for (int i = 0; i < super.cartesR.size(); i++) {
		    if ((IPv4.estEgale(cartesR.get(i).getIP().getReseau(), cr.getIP().getReseau())
		    && IPv4.estEgale(cartesR.get(i).getIP().getMasque(), cr.getIP().getMasque())
		    || cartesR.get(i).getNomInterface().equals(cr.getNomInterface()))) {
		    	validiteCarteR = false;
		    }
		}
        if (validiteCarteR && super.cartesR.size() < Routeur.NBR_PORT_GIGA) {
        	super.cartesR.add(cr);
        	cr.setMachine(this);
        	String[] infosTableRoutage = {IPv4.getStrAdresse(cr.getIP().getReseau()) + "/" 
        	+ Integer.toString(IPv4.getMasqueDecimal(cr.getIP().getMasque())), 
     			"*",
        		cr.getNomInterface()};
        	this.getTableRoutage().remplir(infosTableRoutage);
        	String[] infosTableARP = {IPv4.getStrAdresse(cr.getIP().getAdresseIP()),
        		cr.getMAC().getAdresse(),
        		cr.getNomInterface()};
        	this.getTableARP().remplir(infosTableARP);
        }
        return validiteCarteR;
    }

    public CarteReseau getCarteRSelonRoute(String addrIPDest) {

    	CarteReseau carteR = null;

    	Octet[] masqueDest = IPv4.genererMasque(addrIPDest);
        Octet[] addrReseauDest = IPv4.genererAdresseReseau(addrIPDest, masqueDest);
    	for (Map.Entry<Integer, String[]> tableRoutage : this.getTableRoutage().getTable().entrySet()) {
            if (IPv4.getStrAdresse(addrReseauDest).equals(tableRoutage.getValue()[0].split("/")[0])) {
                for (Map.Entry<CarteReseau, ArrayList<CarteReseau>> port : this.getPorts().entrySet()) {
                    if (port.getKey().getNomInterface().equals(tableRoutage.getValue()[2])) {
                        carteR = port.getKey();
                    }
                }
            }
        }
        if (carteR == null) {
            carteR = this.getCartesR().get(0);
        }
        return carteR;
    }

    public String tableRoutage() {

        return this.tableRoutage.toString();
    }

    public void afficherTableRoutage() {

        System.out.println(this + "\n" + this.tableRoutage);
    }

    @Override
    public String toString() {

        return "Routeur n°" + this.id;
    }
}