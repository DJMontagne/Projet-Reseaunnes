package General;

import java.util.ArrayList;

public class Ordinateur extends Machine {

	public static final int NBR_PORT_FAST = 1;
	private int id;
	private static int nbrOrdinateur;
	private static ArrayList<Ordinateur> allOrdinateurs = new ArrayList<Ordinateur>(); // ArrayList regroupant tous les ordinateurs

	public Ordinateur(int x, int y) {
		
		super(x, y);
		nbrOrdinateur++;
		this.id = nbrOrdinateur;
		allOrdinateurs.add(this);
	}

	@Override
	// Permet d'ajouter une carte réseau à une machine
    public void ajouterInterface(CarteReseau cr) {
        
        if (super.cartesR.size() == 0) {
            super.cartesR.add(cr);
            cr.setMachine(this);
        }
    }

	public static ArrayList<Ordinateur> getAllOrdinateurss() {
		return allOrdinateurs;
	}

	public static Ordinateur getUnOrdinateur(int a) {
		return allOrdinateurs.get(a);
	}

    @Override
    public String toString() {

    	return "Ordinateur n°" + this.id;
    }
}