package Interface_Graphique;

import javax.swing.*;
import java.awt.event.*; // Pour les évènements
import java.awt.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Outils.*;

public class Fenetre extends JFrame {

  private String imgCommutateurUrl = "img\\commutateur_100_100.png";
  private String imgCommutateurUrl2 = "commutateur_100_100.png";

  private String imgOrdinateurUrl = "";

  public Fenetre() {
    this.setTitle("test");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBounds(100, 150, 200, 300); // Position x,y Taille l,h
    //this.setSize(830,600);
    this.setLocationRelativeTo(null);

    Container c = this.getContentPane();  // Conteneur à alimenter

    // Gestionnaire de mise en forme
    // Inutile, c'est le gestionnaire par défaut
    BorderLayout gestionnaire = new BorderLayout();
    c.setLayout(gestionnaire);

    //Affichage d'une image 
    ImagePanel img = new ImagePanel(imgCommutateurUrl);
    JLabel piclabel = new JLabel(new ImageIcon(img.getImage()));
    c.add(piclabel);

    // Panneau de dessin avant l'écouteur.
    PanneauDessin dessin = new PanneauDessin();
    dessin.setBackground(Color.CYAN);
    c.add(dessin);

    // Création de l'écouteur
    EcouteurSouris ecouteur = new EcouteurSouris(dessin); 
    // Attachement de l'écouteur au panneau.
    dessin.addMouseListener(ecouteur);

    // Ajout d'un bouton
    JButton bouton = new JButton("OK");
    // Ecouteur pour capter l'actionnement
    EcouteurAction ecouteurBouton = new EcouteurAction();
    // Attachement au bouton
    bouton.addActionListener(ecouteurBouton);
    c.add(bouton, "South");


  }
  
}
