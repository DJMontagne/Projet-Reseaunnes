package Interface_Graphique;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame {

  //La fenetre SEULE ET UNIQUE
  private static Fenetre fenetre;
  private static final int tailleFenetreX = 900;
  private static final int tailleFenetreY = 750; 

  // Relatifs aux images
  private static final String imgCommutateurUrl = "src\\img\\commutateur.png";
  private static final String imgOrdinateurUrl = "src\\img\\ordinateur.png";
  private static final String imgRouteurUrl = "src\\img\\routeur.png"; 
  public JPanel jPanelImages;
  private String ajoutImage;
  private final static int tailleImageX = 100;
  private final static int tailleImageY = 100;

  // Relatif aux boutons
  private JButton boutonCommutateur;
  private JButton boutonOrdinateur;
  private JButton boutonRouteur;
  private EcouteurAction ecouteurBoutons = new EcouteurAction();
  private JPanel jPanelBoutons;

  // Relatif à la souris
  private static JPanel jPanelSouris = new JPanel();
  private static EcouteurSouris ecouteurSouris = new EcouteurSouris();
  

  public Fenetre() throws IOException {
    fenetre = this;
    this.setLayout(new BorderLayout());
    this.setTitle("Simulateur Réseau");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(tailleFenetreX, tailleFenetreY);
    this.setLocationRelativeTo(null);

    ajouterBoutons();

    // Création et ajout du Jpanel images a la fenetre
    jPanelImages = new JPanel();
    jPanelImages.setLayout(null);
    fenetre.add(jPanelImages);

    // Pour capter le clic de souris sur la fenetre
    jPanelSouris.addMouseListener(ecouteurSouris);

    /*ImageComponent image = new ImageComponent(imgCommutateurUrl);
    image.setLocation(0, 0);
    this.jPanelImages.add(image);

    ImageComponent image2 = new ImageComponent(imgRouteurUrl);
    image2.setLocation(0, 100);
    jPanelImages.add(image2);

    ImageComponent image3 = new ImageComponent(imgOrdinateurUrl);
    image3.setLocation(0, 200);
    jPanelImages.add(image3);
    //fenetre.add(image3);

    ImageComponent image5 = new ImageComponent(imgCommutateurUrl);
    image5.setLocation(100,100);
    jPanelImages.add(image5);

    //ImageComponent img4 = image2;
    //img4.setLocation(0,400);
    //jPanelImages.add(img4);*/
  
    
    System.out.println("end");

    
  }


  // Méthodes d'ajout des boutons
  private void ajouterBoutons() 
  {
    jPanelBoutons = new JPanel();
    jPanelBoutons.setLayout(new FlowLayout());
    ajouterBoutonRouteur();
    ajouterBoutonCommutateur();
    ajouterBoutonOrdinateur();
    this.add(jPanelBoutons,BorderLayout.SOUTH);
  }

  private void ajouterBoutonRouteur()
  {
    boutonRouteur = new JButton("Ajouter un routeur");
    // Attachement au bouton commutateur
    boutonRouteur.addActionListener(ecouteurBoutons);
    jPanelBoutons.add(boutonRouteur);
  }

  private void ajouterBoutonCommutateur() 
  {
    // Ajout du bouton d'ajout de commutateur
    boutonCommutateur = new JButton("Ajouter un commutateur");
    // Attachement au bouton commutateur
    boutonCommutateur.addActionListener(ecouteurBoutons);
    jPanelBoutons.add(boutonCommutateur);
  }

  private void ajouterBoutonOrdinateur()
  {
    // Ajout du bouton d'ajout d'ordinateur
    boutonOrdinateur = new JButton("Ajouter un ordinateur");
    // Attachement au bouton commutateur
    boutonOrdinateur.addActionListener(ecouteurBoutons);
    jPanelBoutons.add(boutonOrdinateur);
  }

  //Getters 
  public EcouteurAction getEcouteurBoutons() 
  {
      return this.ecouteurBoutons;
  }

  public String getAjoutImage() 
  {
      return this.ajoutImage;
  }

  public JPanel getjPanelImages() 
  {
    return this.jPanelImages;
  }


  //Setters
  public void setAjoutImage(String ajoutImage) 
  {
      this.ajoutImage = ajoutImage;
  }
  

  //Getters static
  public static String getUrlImgRouteur() 
  {
    return imgRouteurUrl;
  }

  public static String getUrlImgCommutateur() 
  {
      return imgCommutateurUrl;
  }

  public static String getUrlImgOrdinateur() 
  {
      return imgOrdinateurUrl;
  }

  public static Fenetre getFenetre()
  {
    return fenetre;
  }

  public static JPanel getjPanelSouris() 
  {
      return jPanelSouris;
  }

  public static EcouteurSouris getEcouteurSouris() 
  {
      return ecouteurSouris;
  }

  public static int getTaillefenetrex() 
  {
      return tailleFenetreX;
  }

  public static int getTaillefenetrey() 
  {
      return tailleFenetreY;
  }

  public static int getTailleimagex() 
  {
      return tailleImageX;
  }

  public static int getTailleimagey() 
  {
      return tailleImageY;
  }

  
}
