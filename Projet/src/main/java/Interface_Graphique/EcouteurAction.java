package Interface_Graphique;

// Pour les évènements
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// API pour les interfaces graphiques
import javax.swing.JButton;
import javax.swing.JFrame;

import General.CarteReseau;
import General.Commutateur;
import General.Ordinateur;
import General.Routeur;


/**
 * Classe permettant de capter l'actionnement du bouton.
 * Il n'y a pas d'adaptateur, seulement un écouteur
 * car il n'y a qu'une méthode à implémenter.
 */
public class EcouteurAction extends JFrame implements ActionListener {

  private JButton bouton;

  public void actionPerformed(ActionEvent e) {
    
    this.bouton = (JButton) e.getSource();

    //Fenetre.getFenetre().getjPanelImages().updateUI(); // ABSOLUMENT NECESAIRE


    // Pour capter le clic de souris sur la fenetre lorsqu'un bouton est cliqué
    Fenetre.getFenetre().add(Fenetre.getjPanelSouris());
    Fenetre.getFenetre().setVisible(true);

    CarteReseau nvlCR;

    switch(this.bouton.getText())
    {
      case "Ajouter un ordinateur" :
        // Création d'un nouveau ordinateur et affichage de sa config

        Ordinateur pcTest = new Ordinateur(1, 1);
        nvlCR = new CarteReseau();
        pcTest.ajouterInterface(nvlCR);
        System.out.println("Ordinateur ajouté, voici sa config : \n");
        //System.out.println(Ordinateur.getAllOrdinateurss());
        pcTest.afficherConfig();

        // Affichage d'une image de ordinateur
        
        Fenetre.getFenetre().setAjoutImage("Ordinateur");

        break;
      case "Ajouter un commutateur" :
        // Création d'un nouveau commutateur et affichage de sa config

        Commutateur switch1 = new Commutateur(17, 3);
        nvlCR = new CarteReseau();
        switch1.ajouterInterface(nvlCR);
        System.out.println("Commutateur ajouté, voici sa config :\n");
        //System.out.println(Commutateur.getAllCommutateurs());
        switch1.afficherConfig();

        // Affichage d'une image de commutateur 
        
        Fenetre.getFenetre().setAjoutImage("Commutateur");
        break;
      case "Ajouter un routeur" :
        //Création d'un nouveau routeur et affichage de sa config

        Routeur routeurTest = new Routeur(16, 7);
        nvlCR = new CarteReseau("eno1");
        routeurTest.ajouterInterface(nvlCR);
        System.out.println("Routeur ajouté, voici sa config : \n");
        //System.out.println(Routeur.getAllRouteurs());
        routeurTest.afficherConfig();

        // Affichage d'une image de routeur

        Fenetre.getFenetre().setAjoutImage("Routeur");
        break;
    }

    System.out.println("\n");

    //Afin de toujorus update la fenetre
    Fenetre.getFenetre().setVisible(true);

  }
  


}
