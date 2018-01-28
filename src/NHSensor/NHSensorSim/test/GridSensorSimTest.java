package NHSensor.NHSensorSim.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;

public class GridSensorSimTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SensorSim sensorSim = SensorSim.createGridSensorSim(199, 199, 1, 1, 1.0);
//		SensorSim sensorSim = SensorSim.createGridSensorSim(20, 20, 1, 1, 1.0);
		sensorSim.getSimulator().addHandleAndTraceEventListener();
		
		Network network = sensorSim.getNetwork();
		Vector nodes = network.getNodes();
		Node node;
		StringBuffer sdata = new StringBuffer("sdata = [\n");
		
		for(int i=0;i<nodes.size();i++) {
			node = (Node)nodes.elementAt(i);
			sdata.append(node.getPos().getX());
			sdata.append(" ");
			sdata.append(node.getPos().getY());
			sdata.append(";\n");
		}
		sdata.append("]");
		System.out.println(sdata.toString());
		
		FileWriter fw;
		try {
			fw = new FileWriter("sdata.txt");
			String str = sdata.toString();
			fw.write(str,0,str.length());  
			fw.flush();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
