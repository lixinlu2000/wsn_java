package NHSensor.NHSensorSim.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;


public class NetworkLinkCanvas extends NetworkCanvas {

	public NetworkLinkCanvas(NetworkModel networkModel) {
		super(networkModel);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		this.drawView(g2d);
		this.drawCoordinator(g2d);
		this.drawNodes(g2d);
		this.drawSelectedNodes(g2d);
		this.drawNetwork(g2d);
		this.drawFailNodes(g2d);
		this.drawAllLinks(g2d);

		if (this.isShowDescription)
			this.drawDescription(g2d);
		this.drawOthers(g2d);
	}

}
