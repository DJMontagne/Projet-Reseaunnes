package Interface_Graphique;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EcouteurSouris implements MouseListener  {

    private int xSouris,ySouris;
    private Boolean clickAgain = false;
    
    @Override
    public void mouseClicked( MouseEvent e )
    {
        // Clique gauche de la souris
        if(e.getButton()==MouseEvent.BUTTON1)
        {
            // Récupération de la position
            Point position = e.getPoint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e)  {
        xSouris = e.getX(); ySouris = e.getY();
        this.clickAgain = true;
        Fenetre.setxSouris(xSouris); Fenetre.setySouris(ySouris);
        System.out.println(e.getX() + "," + e.getY());
        //System.out.println(Fenetre.getxSouris() + "," + Fenetre.getySouris());

        if (Fenetre.getFenetre().getAjoutImage() == "Routeur" & clickAgain == true) {
            System.out.println("Choisis où placer ton routeur");
            ImageIcon icon = new ImageIcon(Fenetre.getUrlImgRouteur());
            JLabel image = new JLabel(icon);
            Fenetre.getFenetre().add(image);

            Fenetre.getFenetre().setVisible(true);
            clickAgain = false;
            
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    //Getters
    public int getXSouris() {
        return xSouris;
    }

    public int getYSouris() {
        return ySouris;
    }

    public Boolean getClickAgain() {
        return this.clickAgain;
    }

    //Setters 
    public void setClickAgain(Boolean clickAgain) {
        this.clickAgain = clickAgain;
    }
}
