package NHSensor.NHSensorSim.ui;

public interface NetworkView {
	public void setNetworkModel(NetworkModel networkModel);

	public NetworkModel getNetworkModel();

	public void updateNetworkView();
	
	public void setNetworkViewScale(double scale) ;

}
