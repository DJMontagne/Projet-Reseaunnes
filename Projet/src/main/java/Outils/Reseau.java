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
            if (machine.getKey() instanceof Routeur && machine.getValue() instanceof Routeur) {
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

    public String getChemin(Machine machineSrc, Machine machineDest) {

        String chemin = "";
        boolean machineAtteinte = false;
        while (!machineAtteinte) {
            for (int i = 0; i < this.liaisons.size(); i++) {
                for (Map.Entry<Machine, Machine> machine : this.liaisons.get(i).getLiaison().entrySet()) {
                    if (machine.getKey().equals(machineSrc)) {
                        chemin += machine.getKey().toString() + " ---> ";
                        if (machine.getValue() != machineDest) {
                            machineSrc = machine.getValue();
                        }
                        else {
                            chemin += machine.getValue().toString();
                            machineAtteinte = true;
                            i = this.liaisons.size() - 1;
                        }
                    }
                    else if (machine.getValue().equals(machineSrc)) {
                        chemin += machine.getValue().toString() + " ---> ";                        
                        if (machine.getKey() != machineDest) {
                            machineSrc = machine.getKey();
                        }
                        else {
                            chemin += machine.getKey().toString();
                            machineAtteinte = true;
                            i = this.liaisons.size() - 1;
                        }
                    }
                }
            }
        }
        return chemin;
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