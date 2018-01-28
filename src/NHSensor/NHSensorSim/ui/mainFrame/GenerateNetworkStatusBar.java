package NHSensor.NHSensorSim.ui.mainFrame;

import java.util.Vector;

import NHSensor.NHSensorSim.ui.JStatusBar;
import NHSensor.NHSensorSim.ui.NetworkModel;
import NHSensor.NHSensorSim.ui.NetworkView;

public class GenerateNetworkStatusBar extends JStatusBar implements NetworkView {
	NetworkModel networkModel;
	
	public void setNetworkModel(NetworkModel networkModel) {
		// TODO Auto-generated method stub

	}

	public NetworkModel getNetworkModel() {
		// TODO Auto-generated method stub
		return networkModel;
	}

	public void updateNetworkView() {
		// TODO Auto-generated method stub

	}

	public void setNetworkViewScale(double scale) {
		// TODO Auto-generated method stub

	}
	
	public GenerateNetworkStatusBar(NetworkModel networkModel) {
		super();
		this.networkModel = networkModel;
		
		this.addStatusCell(180);
		this.addStatusCell(200);
		this.addStatusCell(200);
		this.addStatusCell(100);
		this.addStatusCell(300);
		this.addStatusCell(200);
		this.setStatus(1, "Description: ");
		this.setStatus(2, "Network: ");
		this.setStatus(3, "Node Num: ");
		this.setStatus(4, "Query: ");
		this.setStatus(5, "Event: null");
	}
}
