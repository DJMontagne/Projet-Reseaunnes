package Interface_Graphique;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageComponent extends Component {

    private final Image img;

    private int ximg, yimg;

    public ImageComponent(String fileName)
    {
        this.img = new ImageIcon(fileName).getImage();
		//System.out.println(this);
        this.setSize(Fenetre.getTailleimagex(),Fenetre.getTailleimagey());
        
        //addMouseListener(this);
    }

    public void paint(Graphics g) 
    {
        super.paint(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }

	public int getXimg() {
		return ximg;
	}

	public void setXimg(int ximg) {
		this.ximg = ximg;
	}

	public int getYimg() {
		return yimg;
	}

	public void setYimg(int yimg) {
		this.yimg = yimg;
	}

	public Image getImg() {
		return img;
	}
}