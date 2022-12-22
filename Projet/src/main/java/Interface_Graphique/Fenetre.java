package Interface_Graphique;

import java.awt.*;

import javax.swing.*;

public class Fenetre extends JFrame {

  private static final String imgCommutateurUrl = "src\\img\\commutateur_100_100.png";
  private static final String imgOrdinateurUrl = "src\\img\\ordinateur_100_100.png";
  private static final String imgRouteurUrl = "src\\img\\routeur_100_100.png"; 

  private JButton boutonCommutateur;
  private JButton boutonOrdinateur;
  private JButton boutonRouteur;

  private EcouteurAction ecouteurBoutonCommutateur;
  private EcouteurAction ecouteurBoutonOrdinateur;
  private EcouteurAction ecouteurBoutonRouteur;

  private static Container c;
  private static BorderLayout gestionnaire;

  private ImagePanel img;
  private JLabel piclabel;

  public static Fenetre fenetre;

  public Fenetre() {
    fenetre = this;
    this.setTitle("Simulateur Réseau");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setBounds(100, 150, 200, 300); // Position x,y Taille l,h
    this.setSize(900,750);
    this.setLocationRelativeTo(null);

    c = this.getContentPane();  // Conteneur à alimenter

    // Gestionnaire de mise en forme
    // Inutile, c'est le gestionnaire par défaut
    gestionnaire = new BorderLayout();
    c.setLayout(gestionnaire);

    //Affichage d'une image
    
    /*ImagePanel img = new ImagePanel(imgCommutateurUrl); 
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

    ajouterBoutonCommutateur();
    ajouterBoutonOrdinateur();
    ajouterBoutonRouteur();

    System.out.println("end");

  }

  public void afficherImageRouteur() 
  {
    ImagePanel img = new ImagePanel(Fenetre.getUrlImgRouteur());
    JLabel piclabel = new JLabel(new ImageIcon(img.getImage()));
    c.add(piclabel);
  }

  private void ajouterBoutonRouteur()
  {
    boutonRouteur = new JButton("Ajouter un routeur");
    // Ecouteur pour capter l'actionnement du bouton commutateur
    ecouteurBoutonRouteur = new EcouteurAction();
    // Attachement au bouton commutateur
    boutonRouteur.addActionListener(ecouteurBoutonRouteur);
    Fenetre.getContainerC().add(boutonRouteur, "South");
  }

  public static void ajouterBoutonTest()
  {
    JButton boutonTest = new JButton("Ajouter un test");
    // Ecouteur pour capter l'actionnement du bouton commutateur
    EcouteurAction ecouteurBoutonTest = new EcouteurAction();
    // Attachement au bouton commutateur
    boutonTest.addActionListener(ecouteurBoutonTest);
    Fenetre.getContainerC().add(boutonTest, "North");
  }

  private void ajouterBoutonCommutateur() 
  {
    // Ajout du bouton d'ajout de commutateur
    boutonCommutateur = new JButton("Ajouter un commutateur");
    // Ecouteur pour capter l'actionnement du bouton commutateur
    ecouteurBoutonCommutateur = new EcouteurAction();
    // Attachement au bouton commutateur
    boutonCommutateur.addActionListener(ecouteurBoutonCommutateur);
    Fenetre.getContainerC().add(boutonCommutateur, "East");
  }

  private void ajouterBoutonOrdinateur()
  {
    // Ajout du bouton d'ajout d'ordinateur
    boutonOrdinateur = new JButton("Ajouter un ordinateur");
    // Ecouteur pour capter l'actionnement du bouton commutateur
    ecouteurBoutonOrdinateur = new EcouteurAction();
    // Attachement au bouton commutateur
    boutonOrdinateur.addActionListener(ecouteurBoutonOrdinateur);
    Fenetre.getContainerC().add(boutonOrdinateur, "West");
  }

  public static String getUrlImgRouteur() {
    return imgRouteurUrl;
  }

  public static BorderLayout getGestionnaire() {
      return gestionnaire;
  }
  
  public static Container getContainerC() 
  {
    return c;
  }

  public static Fenetre getFenetre()
  {
    return fenetre;
  }
  
}
