package NHSensor.NHSensorSim.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class DrawAnArc extends JApplet {
	BasicStroke basicStroke = new BasicStroke(5.0f);

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.red);
		g2d.setStroke(basicStroke);
		g2d.draw(new Arc2D.Double(5, 10, 150, 150, 0, 180, Arc2D.OPEN));
	}

	public static void main(String s[]) {
		JFrame frame = new JFrame("Draw Arc");
		JApplet applet = new DrawAnArc();
		frame.getContentPane().add("Center", applet);
		frame.setSize(300, 250);
		frame.show();
	}
}
