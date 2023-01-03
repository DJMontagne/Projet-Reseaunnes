package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import General.Liaison;
import General.Machine;
import Outils.Reseau;

public class Repere extends JPanel {

	public Repere() {

		setLayout(null);
	}

	public JLabel getLabel(Machine machine) {

		JLabel label = null;
		Component[] composants = this.getComponents();
		for (Component composant : composants) {
			if (composant instanceof JLabel && composant.getName().equals(machine.toString())) {
				label = (JLabel) composant;
			}
	    }
	    return label;
	}

	public void dessinLiaison(Graphics g) {

		Component[] composants = this.getComponents();
		for (int i = 0; i < Reseau.getReseaux().size(); i++) {
	        for (int j = 0; j < Reseau.getReseaux().get(i).getLiaisons().size(); j++) {
	            Liaison liaison = Reseau.getReseaux().get(i).getLiaisons().get(j);
	            HashMap<Machine, Machine> cable = liaison.getLiaison();
	            for (Map.Entry<Machine, Machine> machine : cable.entrySet()) {
	            	JLabel labelA = this.getLabel(machine.getKey());
	            	JLabel labelB = this.getLabel(machine.getValue());
	            	if (labelA != null && labelB != null) {
						Graphics2D g2d = (Graphics2D) g;
						g2d.setStroke(new BasicStroke(3));

						int x1 = labelA.getX() + labelA.getWidth() / 2;
						int y1 = labelA.getY() + labelA.getHeight() / 2;
						int x2 = labelB.getX() + labelB.getWidth() / 2;
						int y2 = labelB.getY() + labelB.getHeight() / 2;

						Line2D ligne2d = new Line2D.Double(x1, y1, x2, y2);
						// Dessin de la ligne
						g2d.setColor(Color.BLACK);
						g2d.draw(ligne2d);
					}
	            }
	        }
	    }
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

		dessinLiaison(g);
	}
}