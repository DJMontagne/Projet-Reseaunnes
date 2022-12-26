package Interface_Graphique;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.events.MouseEvent;

import General.*;


public class Fenetre extends JFrame {

  // Les liens des images
  private static final String imgCommutateurUrl = "src\\img\\commutateur_100_100.png";
  private static final String imgOrdinateurUrl = "src\\img\\ordinateur_100_100.png";
  private static final String imgRouteurUrl = "src\\img\\routeur_100_100.png"; 

  // Relatif aux boutons
  private JButton boutonCommutateur;
  private JButton boutonOrdinateur;
  private JButton boutonRouteur;
  private EcouteurAction ecouteurBoutons = new EcouteurAction();
  private JPanel jPanelBoutons;

  // Relatif à la souris
  private static JPanel jPanelSouris = new JPanel();
  private static EcouteurSouris ecouteurSouris = new EcouteurSouris();
  private static int xSouris;
  private static int ySouris;

  private String ajoutImage;

  // Les trucs inutiles ????
  private static Container c;
  private static BorderLayout gestionnaire;
  private JPanel contentPannel;
  
  //La fenetre SEULE ET UNIQUE
  private static Fenetre fenetre;

  public Fenetre() throws IOException, InterruptedException {
    fenetre = this;
    this.setTitle("Simulateur Réseau");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(900,750);

    /*c = this.getContentPane();  // Conteneur à alimenter

    // Gestionnaire de mise en forme
    // Inutile, c'est le gestionnaire par défaut
    gestionnaire = new BorderLayout();
    c.setLayout(gestionnaire);*/

    contentPannel = new JPanel();
    //this.setContentPane(contentPannel);
    //contentPannel.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPannel.setLayout(new FlowLayout());

    ImageIcon icone = new ImageIcon(imgCommutateurUrl);
    ImageIcon icone2 = new ImageIcon(imgRouteurUrl);

    JLabel image = new JLabel(icone);
    contentPannel.add(image);
    image.setBounds(0, 0, 65, 65);
    fenetre.add(image);
    JLabel image2 = new JLabel(icone2);
    image2.setBounds(0, 76, 65, 65);
    fenetre.add(image2);


    jPanelBoutons = new JPanel();
    jPanelBoutons.setLayout(new FlowLayout());
    ajouterBoutons();

    
    System.out.println("end");

    
  }


  // Méthodes d'ajout des boutons
  private void ajouterBoutons() 
  {
    ajouterBoutonRouteur();
    ajouterBoutonCommutateur();
    ajouterBoutonOrdinateur();
    fenetre.add(jPanelBoutons,BorderLayout.SOUTH);
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

  //Méthodes d'ajout d'images au clic sur la fenetre
  public void ajouterImageRouteur() throws IOException 
  {
    

    if (ajoutImage == "Routeur" & ecouteurSouris.getClickAgain() == true) {
      System.out.println("Choisis où placer ton routeur");
      PannelImage panelimg = new PannelImage(imgRouteurUrl);
      panelimg.setX(xSouris); panelimg.setY(ySouris);
      fenetre.add(panelimg);
      fenetre.setVisible(true);
    }

    /*while (ecouteurSouris.getClickAgain() != true) 
    {
      //Thread.sleep(2000);
      //System.out.println("Choisis ou placer ton routeur");
      //System.out.println(ecouteurSouris.getClickAgain());
      if (ecouteurSouris.getClickAgain() == true) {
        System.out.println("test");
      }
      //ecouteurSouris.setClickAgain(true);
    } */
    //Thread.sleep(3000);
   
    
  }

  //Getters 
  public EcouteurAction getEcouteurBoutons() 
  {
      return ecouteurBoutons;
  }

  public String getAjoutImage() {
      return this.ajoutImage;
  }


  //Setters
  public static void setxSouris(int xSouris) {
      Fenetre.xSouris = xSouris;
  }

  public static void setySouris(int ySouris) {
      Fenetre.ySouris = ySouris;
  }

  public void setAjoutImage(String ajoutImage) {
      this.ajoutImage = ajoutImage;
  }
  

  //Getters static
  public static String getUrlImgRouteur() {
    return imgRouteurUrl;
  }

  public static String getUrlImgCommutateur() {
      return imgCommutateurUrl;
  }

  public static String getUrlImgOrdinateur() {
      return imgOrdinateurUrl;
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

  public static JPanel getjPanelSouris() 
  {
      return jPanelSouris;
  }
  
  public static EcouteurSouris getEcouteurSouris() 
  {
      return ecouteurSouris;
  }

  public static int getxSouris() 
  {
      return xSouris;
  }

  public static int getySouris() 
  {
    return ySouris;
  }

  
  
}
