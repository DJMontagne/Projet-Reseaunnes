package main.java.Interface_Graphique;

import javax.swing.*; // API pour les interfaces graphiques
import java.awt.event.*; // Pour les évènements
import java.awt.*;

/**
 * Classe permettant de capter l'actionnement du bouton.
 * Il n'y a pas d'adaptateur, seulement un écouteur
 * car il n'y a qu'une méthode à implémenter.
 */
public class EcouteurAction implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        System.out.println("Bouton pressé !");
      }
}
