package Interface_Graphique;

import javax.swing.*; // API pour les interfaces graphiques
import java.awt.event.*; // Pour les évènements
import java.awt.*;

import General.*;
import Outils.*;


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
      System.out.println(Ordinateur.getAllOrdinateurss());
      pcTest.afficherConfig();

    }
    else if (this.bouton.getText() == "Ajouter un commutateur") 
    {
      // Création d'un nouveau commutateur
      Commutateur switch1 = new Commutateur(17, 3);
      CarteReseau crTest = new CarteReseau();
      switch1.ajouterInterface(crTest);
      System.out.println("Commutateur ajouté, voici sa config :\n");
      System.out.println(Commutateur.getAllCommutateurs());
      switch1.afficherConfig();

    }
    else if (this.bouton.getText() == "Ajouter un routeur")
    {
      //Création d'un nouveau routeur et affichage de sa config
      Routeur routeurTest = new Routeur(16, 7);
      CarteReseau crTest = new CarteReseau("eno1");
      routeurTest.ajouterInterface(crTest);
      System.out.println("Routeur ajouté, voici sa config : \n");
      System.out.println(Routeur.getAllRouteurs());
      routeurTest.afficherConfig();

      // Affichage d'une image de routeur
      
      //Fenetre.getFenetre().setVisible(false);
      ImagePanel img = new ImagePanel(Fenetre.getUrlImgRouteur());
      JLabel piclabel = new JLabel(new ImageIcon(img.getImage()));
      Fenetre.getContainerC().add(piclabel);
      Fenetre.getFenetre().setVisible(true);

    }
    System.out.println("\n");
    }


}
