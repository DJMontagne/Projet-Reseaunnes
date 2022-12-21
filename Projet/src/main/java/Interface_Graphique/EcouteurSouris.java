package Interface_Graphique;

import javax.swing.*; // API pour les interfaces graphiques
import java.awt.event.*; // Pour les évènements
import java.awt.*;

public class EcouteurSouris extends MouseAdapter {
    
    private JLabel label;

    public EcouteurSouris( JLabel p ) {
        this.label = p;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX(); // Utilisation des informations relatives à l'évènement
        int y = e.getY();
        System.out.println("Coordonnées : (" + x + "," + y + ")");
        
    }
}
