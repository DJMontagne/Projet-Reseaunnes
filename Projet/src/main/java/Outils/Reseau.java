package Outils;

import General.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bpotetma
 */

public class Reseau {

    private ArrayList<Liaison> liaisons = new ArrayList<>();
    private static ArrayList<Reseau> reseaux = new ArrayList<>();
    private static int nbrReseau;

    public Reseau() {

        nbrReseau++;
        Reseau.reseaux.add(this);
        System.out.println("Réseau n°" + nbrReseau + " créé");
    }
    
    public ArrayList<Liaison> getLiaisons() {

        return this.liaisons;
    }

    public static ArrayList<Reseau> getReseaux() {

        return Reseau.reseaux;
    }

    public static int getNbrReseau() {

        return Reseau.nbrReseau;
    }
    
    public boolean existenceLiaison(Liaison liaison) {

        boolean existenceLiaison = false;
        for (int i = 0; i < this.liaisons.size(); i++) {
            if (this.liaisons.get(i).estEgale(liaison)) {
                existenceLiaison = true;
            }
        }
        return existenceLiaison;
    }

    public void ajouterLiaison(Liaison liaison) {

        if (!existenceLiaison(liaison)) {
            this.liaisons.add(liaison);
        }
    }

    public boolean existenceMachine(Machine mMachine) {

        boolean existenceMachine = false;
        if (!this.liaisons.isEmpty()) {
            for (int i = 0; i < this.liaisons.size(); i++) {
                for (Map.Entry<Machine, Machine> machine : this.liaisons.get(i).getLiaison().entrySet()) {
                    if (mMachine.equals(machine.getKey()) || mMachine.equals(machine.getValue())) {
                        existenceMachine = true;
                    }
                }
            }
        }
        return existenceMachine;
    }

    public static Reseau getReseauSelonMachine(Machine machine) {

        Reseau reseauMachineExistante = null;
        if (!Reseau.reseaux.isEmpty()) {
            for (int i = 0; i < Reseau.reseaux.size(); i++) {
                if (Reseau.reseaux.get(i).existenceMachine(machine)) {
                    reseauMachineExistante = Reseau.reseaux.get(i);
                }
            }
        }
        return reseauMachineExistante;
    }

    public static Reseau getReseauSelonMachines(Machine machine1, Machine machine2) {

        Reseau reseauMachineExistante = null;
        if (!Reseau.reseaux.isEmpty()) {
            for (int i = 0; i < Reseau.reseaux.size(); i++) {
                if (Reseau.reseaux.get(i).existenceMachine(machine1) 
                && Reseau.reseaux.get(i).existenceMachine(machine2)) {
                    reseauMachineExistante = Reseau.reseaux.get(i);
                }
            }
        }
        return reseauMachineExistante;
    }

    public static Reseau getReseauSelonLiaison(Liaison liaison) {

        Reseau reseau = null;
        for (int i = 0; i < Reseau.reseaux.size(); i++) {
            for (int j = 0; j < Reseau.reseaux.get(i).getLiaisons().size(); j++) {
                if (Reseau.reseaux.get(i).getLiaisons().get(j).equals(liaison)) {
                    reseau = Reseau.reseaux.get(i);
                }
            }
        }
        return reseau;
    }

    public ArrayList<Liaison> getLiaisonSelonMachine(Machine mMachine) {

        ArrayList<Liaison> liaisonsMachine = new ArrayList<>();
        for (int j = 0; j < this.liaisons.size(); j++) {
            for (Map.Entry<Machine, Machine> machine : this.liaisons.get(j).getLiaison().entrySet()) {
                if (machine.getKey().equals(mMachine)|| machine.getValue().equals(mMachine)) {
                    liaisonsMachine.add(this.liaisons.get(j));
                }
            }
        }
        return liaisonsMachine;
    }

    public static void ajouterAuReseau(Liaison liaison) {
        
        Reseau reseau = null;
        for (Map.Entry<Machine, Machine> machine : liaison.getLiaison().entrySet()) {
            if ((machine.getKey() instanceof Routeur && getReseauSelonMachine(machine.getKey()) != null)
            || (machine.getValue() instanceof Routeur && getReseauSelonMachine(machine.getValue()) != null)) {
                reseau = new Reseau();
                reseau.ajouterLiaison(liaison);
            }
            else {
                reseau = Reseau.getReseauSelonMachine(machine.getKey());
                if (reseau != null) {
                    reseau.ajouterLiaison(liaison);
                }
                else {
                    reseau = Reseau.getReseauSelonMachine(machine.getValue());
                    if (reseau != null) {
                        reseau.ajouterLiaison(liaison);
                    }
                    else {
                        reseau = new Reseau();
                        reseau.ajouterLiaison(liaison);
                    }
                }
            }
        }
    }

    public String toString() {

        String affichage = "{";
        for (int i = 0; i < this.liaisons.size(); i++) {
            for (Map.Entry<Machine, Machine> machine : this.liaisons.get(i).getLiaison().entrySet()) {
                if (i != this.liaisons.size() - 1) {
                    affichage += machine.getKey().toString() + " <---> ";
                    affichage += machine.getValue().toString() + "    ";
                }
                else {
                    affichage += machine.getKey().toString() + " <---> ";
                    affichage += machine.getValue().toString();                    
                }
            }
        }
        affichage += "}";
        return affichage;
    }
}