package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Repere extends JPanel {

	private JLabel labelA;
	private JLabel labelB;
	private ArrayList<Line2D> lignes;
	
	public Repere() {

		setLayout(null);
	   	setBorder(BorderFactory.createLineBorder(Color.RED));
	   	this.lignes = new ArrayList<>();
	}

	public void setLabelA(JLabel mLabelA) {

		this.labelA = mLabelA;
	}

	public void setLabelB(JLabel mLabelB) {

		this.labelB = mLabelB;
	}

	private void dessinGrille(Graphics g) {
		g.setColor(new Color(220, 220, 220));
		int largeur = getWidth();
	    int hauteur = getHeight();

		for (int x = 0; x < largeur; x += 20) {
			g.drawLine(x, 0, x, hauteur);
		}

		for (int y = 0; y < hauteur; y += 20) {
			g.drawLine(0, y, largeur, y);
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		dessinGrille(g);

		if (labelA != null && labelB != null) {

			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));

			// Récupération des coordonnées des deux composants
			int x1 = this.labelA.getX() + this.labelA.getWidth() / 2;
			int y1 = this.labelA.getY() + this.labelA.getHeight() / 2;
			int x2 = this.labelB.getX() + this.labelB.getWidth() / 2;
			int y2 = this.labelB.getY() + this.labelB.getHeight() / 2;

			Line2D ligne = new Line2D.Double(x1, y1, x2, y2);
			this.lignes.add(ligne);

			for (Line2D ligne2d : this.lignes) {
				// Dessin de la ligne
				g2d.setColor(Color.BLACK);
				g2d.draw(ligne2d);
			}
		}
	}
}