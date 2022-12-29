package Interface_Graphique;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent e)  {

        Fenetre.getFenetre().getjPanelImages().updateUI(); // ABSOLUMENT NECESAIRE

        xSouris = e.getX(); ySouris = e.getY();
        System.out.println(e.getX() + "," + e.getY());

        ajouterImageClic(Fenetre.getFenetre().getAjoutImage());
        
    }

    // Méthode d'ajout d'images au clic
    public void ajouterImageClic(String type)
    {
        ImageComponent image;
        switch(type) 
        {
            case "Ordinateur" :
                System.out.println("ajout de l'ordi au clic on y va");
                image = new ImageComponent(Fenetre.getUrlImgOrdinateur());
                image.setLocation(xSouris-(Fenetre.getTailleimagex()/2), ySouris-(Fenetre.getTailleimagey()/2));
                Fenetre.getFenetre().getjPanelImages().add(image);
                Fenetre.getFenetre().setVisible(true);
                break;
            case "Commutateur" :
                System.out.println("AJout commutateur au clic c'est parti");
                image = new ImageComponent(Fenetre.getUrlImgCommutateur());
                image.setLocation(xSouris-(Fenetre.getTailleimagex()/2), ySouris-(Fenetre.getTailleimagey()/2));
                Fenetre.getFenetre().getjPanelImages().add(image);
                Fenetre.getFenetre().setVisible(true);
                break;
            case "Routeur" :
                System.out.println("Ajout Routeur au clic en avant !");
                image = new ImageComponent(Fenetre.getUrlImgRouteur());
                image.setLocation(xSouris-(Fenetre.getTailleimagex()/2), ySouris-(Fenetre.getTailleimagey()/2));
                Fenetre.getFenetre().getjPanelImages().add(image);
                Fenetre.getFenetre().setVisible(true);
                break;
    }
    // Retirer écouteur souris
    Fenetre.getFenetre().remove(Fenetre.getjPanelSouris());
    Fenetre.getFenetre().setVisible(true);
  }

    @Override
    public void mouseReleased(MouseEvent arg0) {}

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
