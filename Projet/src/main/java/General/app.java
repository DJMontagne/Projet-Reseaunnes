/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package General;

import Outils.*;
import java.util.ArrayList;
/**
 *
 * @author victo
 */
public class app {
    public static void main(String[] args) {
        
        Ordinateur pc1 = new Ordinateur(1,1);
        CarteReseau cr1 = new CarteReseau("eth0", "192.168.10.3", "255.255.255.0", "192.168.10.1");
        pc1.ajouterInterface(cr1);
        pc1.afficherConfig();   

        System.out.println("\n");

        Ordinateur pc2 = new Ordinateur(15,15);
        CarteReseau cr2 = new CarteReseau("eth1", "192.168.10.4");
        pc2.ajouterInterface(cr2);
        pc2.afficherConfig();

        System.out.println("\n");

        Routeur route1 = new Routeur(16, 7);
        CarteReseau cr5 = new CarteReseau("eno1", "192.168.10.1");
        CarteReseau cr6 = new CarteReseau("enp1s0", "172.32.50.7");
        route1.ajouterInterface(cr5);
        route1.ajouterInterface(cr6);
        route1.afficherConfig();

        System.out.println("\n");

        Commutateur switch1 = new Commutateur(17, 3);
        CarteReseau cr7 = new CarteReseau("eno1", "192.168.10.130");
        switch1.ajouterInterface(cr7);
        switch1.afficherConfig();

        Routeur route2 = new Routeur(5, 50);
        CarteReseau cr8 = new CarteReseau("eno2", "172.32.50.8");
        route2.ajouterInterface(cr8);
        route2.afficherConfig();

        System.out.println("\n");
        
        Liaison c1 = new Liaison("droit");
        c1.lier(pc1, switch1);

        Liaison c4 = new Liaison("droit");
        c4.lier(route1, switch1);

        Liaison c5 = new Liaison("droit");
        c5.lier(switch1, pc2);

        Liaison c3 = new Liaison("croisé");
        c3.lier(route1, route2);

        System.out.println("\n");

        route1.afficherPorts();
        route2.afficherPorts();
        switch1.afficherPorts();
        pc1.afficherPorts();

        System.out.println("\n");
        
        System.out.println("Réseaux : " + Reseau.getReseaux());
        
        System.out.println("\n");
        
        System.out.print("Chemin : ");
        ARP.requete(pc1, "192.168.10.4");

        System.out.println("\n");

        pc1.afficherTableARP();
        System.out.println("\n");
        pc2.afficherTableARP();
        switch1.afficherTableMAC();
        route1.afficherTableRoutage();

        //System.out.println(Reseau.getReseauSelonMachine(pc1).getChemin(route2, pc2));
    }
}
