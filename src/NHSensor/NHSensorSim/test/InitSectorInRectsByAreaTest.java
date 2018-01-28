package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Sector;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.ShapeUtil;
import NHSensor.NHSensorSim.ui.Animator;

public class InitSectorInRectsByAreaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(4, 450, 450, 800);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(30);

		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.get2LRTNode().getAttachment(alg.getName());
		double width = 400;
		double height = 400;
		double minx = alg.getNetwork().getRect().getCentre().getX() - width / 2;
		double maxx = alg.getNetwork().getRect().getCentre().getX() + width / 2;
		double miny = alg.getNetwork().getRect().getCentre().getY() - height
				/ 2;
		double maxy = alg.getNetwork().getRect().getCentre().getY() + height
				/ 2;
		Rect rect = new Rect(minx, maxx, miny, maxy);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, rect);
		alg.run(drawShapeEvent);

		Position centre = root.getPos();
		int count = 30;
		double[] alphas = ShapeUtil.caculateAlphasSectorInRectsByArea(centre,
				rect, count);
		double startRadian = ShapeUtil.caculateAlpha1(rect, centre);
		double sita;
		Sector sector;
		SectorInRect[] sectorInRects = new SectorInRect[count];
		DrawShapeEvent drawSectorInRectEvent;

		for (int i = 0; i < count; i++) {
			if (i == 0)
				sita = alphas[i];
			else
				sita = alphas[i] - alphas[i - 1];
			sector = new Sector(root.getPos(), startRadian, sita);
			sectorInRects[i] = new SectorInRect(sector, rect);
			startRadian += sita;
			drawSectorInRectEvent = new DrawShapeEvent(alg, sectorInRects[i]);
			alg.run(drawSectorInRectEvent);
		}

		Animator animator = new Animator(alg);
		animator.start();

	}
}
