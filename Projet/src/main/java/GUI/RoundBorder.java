package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class RoundBorder extends AbstractBorder {
    
    private Color color;
    private int thickness = 1;
    private int radius = 10;

    public RoundBorder(int radius) {
        
        this.radius = radius;
    }

    public RoundBorder(int radius, Color color) {
        
        this.radius = radius;
        this.color = color;
    }

    public RoundBorder(int radius, int thickness, Color color) {
        
        this(radius);
        this.thickness = thickness;
        this.color = color;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int r = radius;
        int w = width;
        int h = height;
        RoundRectangle2D round = new RoundRectangle2D.Float(x, y, w-1, h-1, r, r);
        Container parent = c.getParent();
        if(parent!=null) {
            g2.setColor(parent.getBackground());
            Area corner = new Area(new Rectangle2D.Float(x, y, w, h));
            corner.subtract(new Area(round));
            g2.fill(corner);
        }
        g2.setColor(color != null ? color : c.getForeground());
        g2.setStroke(new BasicStroke(thickness));
        g2.draw(round);
        g2.dispose();
    }

    public Insets getBorderInsets(Component c) {
        
        return new Insets(radius+thickness, radius+thickness, radius+thickness, radius+thickness);
    }
}