package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.IWQETraverseRingEventUseIterator;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.shape.Ring;

public class IKNNDemo extends AlgorithmDemo {

	public IKNNDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(35);
		alg.getParam().setQUERY_MESSAGE_SIZE(10);

		Network network = alg.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 100));
		/*
		 * NeighborAttachment root =
		 * (NeighborAttachment)alg.getNetwork().get2LRTNode
		 * ().getAttachment(alg.getName());
		 */

		Ring ring = new Ring(alg.getNetwork().getRect().getCentre(), 80, 125);
		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.getTopNodeInRing(ring).getAttachment(alg.getName());

		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, ring);
		alg.run(drawShapeEvent);

		Message mesage = new Message(10, null);
		IWQETraverseRingEventUseIterator e = new IWQETraverseRingEventUseIterator(
				root, ring, mesage, alg);
		alg.run(e);
		//
		return alg;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(35);
		alg.getParam().setQUERY_MESSAGE_SIZE(10);

		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 100));
		/*
		 * NeighborAttachment root =
		 * (NeighborAttachment)alg.getNetwork().get2LRTNode
		 * ().getAttachment(alg.getName());
		 */

		Ring ring = new Ring(alg.getNetwork().getRect().getCentre(), 80, 125);
		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.getTopNodeInRing(ring).getAttachment(alg.getName());

		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, ring);
		alg.run(drawShapeEvent);

		Message mesage = new Message(10, null);
		IWQETraverseRingEventUseIterator e = new IWQETraverseRingEventUseIterator(
				root, ring, mesage, alg);
		alg.run(e);
		//
		return alg;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "IKNN";
		String classFullName = "NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.IKNNDemo";
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
			File file = new File("./Algorithms/Other/" + "IKNN" + "/config");
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
