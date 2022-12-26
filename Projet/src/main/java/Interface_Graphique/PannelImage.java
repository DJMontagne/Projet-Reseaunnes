package Interface_Graphique;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PannelImage extends JPanel{

    private BufferedImage image;
    private int ximg, yimg;

    public PannelImage (String url) throws IOException {
        image = ImageIO.read(new File(url));
        this.ximg = 400;
        this.yimg = 325;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, this.ximg, this.yimg, null);
    }

    public void setX(int x) {
        this.ximg = x;
    }

    public void setY(int y) {
        this.yimg = y;
    }
}
