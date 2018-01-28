package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.events.Event;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSR;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.routing.gpsr.GraphType;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class GPSRDemo extends AlgorithmDemo {

	public GPSRDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim("Random2");
		sensorSim.addAlgorithm(GPSRAlg.NAME);
		GPSRAlg gpsrAlg = (GPSRAlg) sensorSim.getAlgorithm(GPSRAlg.NAME);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		int graphType = GraphType.GG;
		gpsrAlg.setGraphType(graphType);
		gpsrAlg.init();

		Node source = sensorSim.getNetwork().getLBNode();
		GPSRAttachment sourceAttachment = (GPSRAttachment) source
				.getAttachment(gpsrAlg.getName());
		Position destPos = sensorSim.getNetwork().getRect().getCentre();
		Message message = new Message(10, null);

		Event gpsr = new GPSR(sourceAttachment, destPos, message, gpsrAlg);
		gpsrAlg.run(gpsr);
		return gpsrAlg;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		Param param = new Param();
		Simulator simulator = new Simulator();
		Node orig = network.getLBNode();
		Rect queryRect = new Rect(0, 0, 0, 0);
		Query query = new Query(orig, queryRect);
		SensorSim sensorSim = new SensorSim(query, network, simulator, param);
		sensorSim.addAlgorithm(GPSRAlg.NAME);
		GPSRAlg gpsrAlg = (GPSRAlg) sensorSim.getAlgorithm(GPSRAlg.NAME);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		int graphType = GraphType.GG;
		gpsrAlg.setGraphType(graphType);
		gpsrAlg.init();

		Node source = sensorSim.getNetwork().getLBNode();
		GPSRAttachment sourceAttachment = (GPSRAttachment) source
				.getAttachment(gpsrAlg.getName());
		Position destPos = sensorSim.getNetwork().getRect().getCentre();
		Message message = new Message(10, null);

		Event gpsr = new GPSR(sourceAttachment, destPos, message, gpsrAlg);
		gpsrAlg.run(gpsr);
		return gpsrAlg;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "位置路由协议GPSR";
		String classFullName = "NHSensor.NHSensorSim.routing.gpsr.GPSRAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.GPSRDemo";
		boolean isSystemAlgorithm = true;
		String experimentClassFullName = "";
		String experimentParamClassFullName ="";

		try {
			AlgorithmDemo algorithmDemo = (AlgorithmDemo) Class.forName(
					demoClassFullName).newInstance();
			name = algorithmDemo.getRunnedAlgorithmInstance().getName();

			AlgorithmProperty ap = new AlgorithmProperty(name, description,
					classFullName, demoClassFullName, isSystemAlgorithm,
					experimentClassFullName, experimentParamClassFullName);
			File file = new File("./Algorithms/Routing/" + name + "/config");
			if (!file.getParentFile().exists()) {
				System.out.println("目标文件所在路径不存在，准备创建。。。");
				if (!file.getParentFile().mkdirs()) {
					System.out.println("创建目录文件所在的目录失败！");
					return;
				}
			}
			ap.save(file);
			System.out.println("结束！！！！！！！！！！！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
