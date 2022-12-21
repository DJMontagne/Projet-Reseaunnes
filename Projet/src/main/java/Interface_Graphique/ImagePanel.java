package Interface_Graphique;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String url) {
       try 
       {                
          image = ImageIO.read(new File(url));
       } 
       catch (IOException ex) 
       {
            System.out.println(ex);
       }
    }

    public BufferedImage getImage() 
    {
        return this.image;
    }
}
