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

import main.java.Interface_Graphique.ImagePanel;

public class Fenetre extends JFrame {

  private String imgCommutateurUrl = "img\\commutateur_100_100.png";
  private String imgCommutateurUrl2 = "commutateur_100_100.png";

  private String imgOrdinateurUrl = "";

  public Fenetre() 
  {
    this.setTitle("test");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(830,600);
    this.setLocationRelativeTo(null);

    Container c = this.getContentPane();  // Conteneur à alimenter

    // Gestionnaire de mise en forme
    // Inutile, c'est le gestionnaire par défaut
    BorderLayout gestionnaire = new BorderLayout();
    c.setLayout(gestionnaire);

    //URL de l'image
    /*ImageIcon icon = new ImageIcon("tatio.png");

    JLabel jlabel = new JLabel(icon);

    //ajouter les deux JLabel a JFrame
    c.add(jlabel);
    c.validate();*/

    

   try {
      System.out.println(imgCommutateurUrl == null);
      BufferedImage myPicture = ImageIO.read(new File(imgCommutateurUrl));
      JLabel picLabel = new JLabel(new ImageIcon(myPicture));
      c.add(picLabel);
   } catch (IOException ex) {
      System.out.println(ex);
   }
   
   

    // Ajout d'un bouton
    JButton bouton = new JButton("OK");
    c.add(bouton, "South");

  }
  
}
