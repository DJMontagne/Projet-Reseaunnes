/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    

    
package General;
import java.io.IOException;

import Interface_Graphique.*;
import Outils.*;


/**
 *
 * @author victo
 */
public class app {
    public static void main(String[] args) throws IOException {

        Ordinateur pc1 = new Ordinateur(1,1);
        CarteReseau cr1 = new CarteReseau("eth0", "192.168.10.3", "255.255.255.0", "192.168.10.1");
        pc1.ajouterInterface(cr1);
        pc1.afficherConfig();   

        System.out.println("\n");

        Ordinateur pc2 = new Ordinateur(134, 78);
        CarteReseau cr2 = new CarteReseau("eth1", "192.168.10.4");
        cr2.setPasserelle("192.168.10.1");
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

        Commutateur switch1 = new Commutateur(5000000, 0);
        CarteReseau cr7 = new CarteReseau("eno1", "192.168.10.130");
        switch1.ajouterInterface(cr7);
        switch1.afficherConfig();

        System.out.println("\n");

        Commutateur switch2 = new Commutateur(23, 4);
        CarteReseau cr11 = new CarteReseau("enp2f1", "192.168.10.100");
        switch2.ajouterInterface(cr11);
        switch2.afficherConfig();

        System.out.println("\n");

        Ordinateur pc3 = new Ordinateur(5, 50);
        CarteReseau cr8 = new CarteReseau("eno2", "10.32.1.7");
        cr8.setPasserelle("10.32.1.1");
        pc3.ajouterInterface(cr8);
        pc3.afficherConfig();

        System.out.println("\n");

        Routeur route2 = new Routeur(164, 23);
        CarteReseau cr9 = new CarteReseau("eno3", "172.32.50.1");
        CarteReseau cr10 = new CarteReseau("enp2s1", "10.32.1.1");
        route2.ajouterInterface(cr9);
        route2.ajouterInterface(cr10);
        route2.afficherConfig();

        System.out.println("\n");
        
        Liaison c1 = new Liaison("droit");
        c1.lier(pc1, switch1);

        Liaison c4 = new Liaison("droit");
        c4.lier(route1, switch1);

        Liaison c6 = new Liaison("croisé");
        c6.lier(switch2, switch1);

        Liaison c5 = new Liaison("droit");
        c5.lier(switch2, pc2);

        Liaison c2 = new Liaison("croisé");
        c2.lier(route2, route1);

        Liaison c3 = new Liaison("croisé");
        c3.lier(route2, pc3);


        System.out.println("\n");

        System.out.println("Réseaux : " + Reseau.getReseaux());

        System.out.println("\n");
        
        route1.afficherPorts();
        pc3.afficherPorts();
        switch1.afficherPorts();
        pc1.afficherPorts();
        switch2.afficherPorts();
        
        System.out.println("\n");
        
        System.out.print("Chemin : ");
        
        String macAddrPC2 = ARP.requete(pc1, "192.168.10.4");
        ARP.requete(route2, "10.32.1.7");
        ARP.requete(route1, "192.168.10.4");
        ARP.requete(route1, "172.32.50.1");

        route1.getTerminal().ipRouteAdd("10.0.0.0", 8, "172.32.50.1");
        route2.getTerminal().ipRouteAdd("192.168.10.0", 24, "172.32.50.7");

        System.out.println("\n");

        pc1.afficherTableARP();
        System.out.println("\n");
        pc2.afficherTableARP();
        System.out.println("\n");
        switch1.afficherTableMAC();
        System.out.println("\n");
        route1.afficherTableARP();
        System.out.println("\n");
        route1.afficherTableRoutage();
        System.out.println("\n");
        pc3.afficherTableARP();
        System.out.println("\n");
        route2.afficherTableRoutage();
        System.out.println("\n");
        route2.afficherTableARP();
        System.out.println("\n");
        switch2.afficherTableMAC();

        pc2.getTerminal().ping("172.32.50.1", false);
        System.out.println();
        pc2.getTerminal().traceroute("192.168.10.1");

        //System.out.println(Reseau.getReseauSelonMachine(pc1).getChemin(route2, pc2));

        // Tests Interface
        System.out.println("Début tests Interface \n ");

        Fenetre fenetre1 = new Fenetre();
        fenetre1.setVisible(true);
    }
}
