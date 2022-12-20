package main.java.Interface_Graphique;

import javax.swing.*; // API pour les interfaces graphiques
import java.awt.event.*; // Pour les évènements
import java.awt.*;

public class EcouteurSouris extends MouseAdapter {
    private JPanel panneau;

    public EcouteurSouris( JPanel p ) {
        this.panneau = p;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX(); // Utilisation des informations relatives à l'évènement
        int y = e.getY();
        System.out.println("Coordonnées : (" + x + "," + y + ")");
        Graphics g = panneau.getGraphics(); // Récupération du contexte.
        g.drawOval(x, y, 10, 10);
        g.dispose();
    }
}
