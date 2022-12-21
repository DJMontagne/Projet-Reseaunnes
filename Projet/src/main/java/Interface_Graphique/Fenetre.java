package Interface_Graphique;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Fenetre extends JFrame {

  private final String imgCommutateurUrl = "src\\img\\commutateur_100_100.png";
  private final String imgOrdinateurUrl = "src\\img\\ordinateur_100_100.png";

  public JButton boutonCommutateur;
  public JButton boutonOrdinateur;
  public JButton boutonRouteur;

  private EcouteurAction ecouteurBoutonCommutateur;
  private EcouteurAction ecouteurBoutonOrdinateur;
  private EcouteurAction ecouteurBoutonRouteur;

  public Fenetre() {
    this.setTitle("test");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setBounds(100, 150, 200, 300); // Position x,y Taille l,h
    this.setSize(830,600);
    this.setLocationRelativeTo(null);

    Container c = this.getContentPane();  // Conteneur à alimenter

    // Gestionnaire de mise en forme
    // Inutile, c'est le gestionnaire par défaut
    BorderLayout gestionnaire = new BorderLayout();
    c.setLayout(gestionnaire);

    //Affichage d'une image

    ImagePanel img = new ImagePanel(imgCommutateurUrl); 
    JLabel piclabel = new JLabel(new ImageIcon(img.getImage()));
    // Ecouteur pour capter le click sur l'image
   // EcouteurAction ecouteurClickImg = new EcouteurAction();
    // Attachement a l'image
   // piclabel.addActionListener(ecouteurClickImg);
    c.add(piclabel);

    /* 
    // Panneau de dessin avant l'écouteur.
    PanneauDessin dessin = new PanneauDessin();
    c.add(dessin);

    // Création de l'écouteur
    EcouteurSouris ecouteur = new EcouteurSouris(dessin); 
    // Attachement de l'écouteur au panneau.
    dessin.addMouseListener(ecouteur);
    */

    // Ajout du bouton d'ajout de commutateur
    boutonCommutateur = new JButton("Ajouter un commutateur");
    // Ecouteur pour capter l'actionnement du bouton commutateur
    ecouteurBoutonCommutateur = new EcouteurAction();
    // Attachement au bouton commutateur
    boutonCommutateur.addActionListener(ecouteurBoutonCommutateur);
    c.add(boutonCommutateur, "East");

    // Ajout du bouton d'ajout d'ordinateur
    boutonOrdinateur = new JButton("Ajouter un ordinateur");
    // Ecouteur pour capter l'actionnement du bouton commutateur
    ecouteurBoutonOrdinateur = new EcouteurAction();
    // Attachement au bouton commutateur
    boutonOrdinateur.addActionListener(ecouteurBoutonOrdinateur);
    c.add(boutonOrdinateur, "West");

    // Ajout du bouton d'ajout d'ordinateur
    boutonRouteur = new JButton("Ajouter un routeur");
    // Ecouteur pour capter l'actionnement du bouton commutateur
    ecouteurBoutonRouteur = new EcouteurAction();
    // Attachement au bouton commutateur
    boutonRouteur.addActionListener(ecouteurBoutonRouteur);
    c.add(boutonRouteur, "South");

  }
  
}
