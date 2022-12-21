package Interface_Graphique;

import javax.swing.*; // API pour les interfaces graphiques
import java.awt.event.*; // Pour les évènements
import java.awt.*;

import General.*;
import Outils.*;
import Interface_Graphique.*;


/**
 * Classe permettant de capter l'actionnement du bouton.
 * Il n'y a pas d'adaptateur, seulement un écouteur
 * car il n'y a qu'une méthode à implémenter.
 */
public class EcouteurAction extends JFrame implements ActionListener {

  private JButton bouton;

  public void actionPerformed(ActionEvent e) {
    
    this.bouton = (JButton) e.getSource();
    
    if (this.bouton.getText() == "Ajouter un ordinateur") 
    {
      // Création d'un nouveau ordinateur
      Ordinateur pcTest = new Ordinateur(1, 1);
      CarteReseau crTest = new CarteReseau();
      pcTest.ajouterInterface(crTest);
      System.out.println("Ordinateur ajouté, voici sa config : \n");
      pcTest.afficherConfig();

    }
    else if (this.bouton.getText() == "Ajouter un commutateur") 
    {
      // Création d'un nouveau commutateur
      Commutateur switch1 = new Commutateur(17, 3);
      CarteReseau crTest = new CarteReseau();
      switch1.ajouterInterface(crTest);
      System.out.println("Commutateur ajouté, voici sa config :\n");
      switch1.afficherConfig();

    }
    else if (this.bouton.getText() == "Ajouter un routeur")
    {
      //Création d'un nouveau routeur
      Routeur routeurTest = new Routeur(16, 7);
      CarteReseau crTest = new CarteReseau("eno1", "192.168.10.1");
      routeurTest.ajouterInterface(crTest);
      System.out.println("Routeur ajouté, voici sa config : \n");
      routeurTest.afficherConfig();

    }
    System.out.println("\n");
    }
}
