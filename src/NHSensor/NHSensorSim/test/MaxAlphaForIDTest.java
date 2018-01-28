package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.IDCTraverseSectorInRectEvent;
import NHSensor.NHSensorSim.math.MaxAlphaForIDC;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.PolarPosition;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Sector;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.ShapeUtil;
import NHSensor.NHSensorSim.ui.Animator;

public class MaxAlphaForIDTest {

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

		Message queryAndPatialAnswerMesage = new Message(alg.getParam()
				.getANSWER_SIZE()
				+ alg.getParam().getQUERY_MESSAGE_SIZE(), null);

		Position centre = root.getPos();

		double startRadian = ShapeUtil.caculateAlpha2(rect, centre);

		double sb = centre.distance(rect.getLB());
		double beta = Radian.relativeTo(rect.getLB().bearing(centre), 0);
		double r = alg.getParam().getRADIO_RANGE();

		MaxAlphaForIDC maxAlphaForIDC = new MaxAlphaForIDC(r, beta, sb);
		double alpha = maxAlphaForIDC.getMaxAlpha();

		Sector sector = new Sector(root.getPos(), startRadian, alpha);
		SectorInRect sectorInRect = new SectorInRect(sector, rect);

		DrawShapeEvent drawSectorInRectEvent = new DrawShapeEvent(alg,
				sectorInRect);
		alg.run(drawSectorInRectEvent);

		DrawShapeEvent ds1 = new DrawShapeEvent(alg,
				new Circle(rect.getLB(), r));
		alg.run(ds1);

		PolarPosition pp1 = new PolarPosition(centre, sb - r, centre
				.bearing(rect.getLB()));
		DrawShapeEvent ds2 = new DrawShapeEvent(alg, new Circle(pp1
				.toPosition(), r));
		alg.run(ds2);

		DrawShapeEvent ds3 = new DrawShapeEvent(alg, new Circle(sectorInRect
				.getEF(), r));
		alg.run(ds3);

		double endlineLength = centre.distance(sectorInRect.getEF());
		PolarPosition pp2 = new PolarPosition(centre, endlineLength - r, centre
				.bearing(sectorInRect.getEF()));
		DrawShapeEvent ds4 = new DrawShapeEvent(alg, new Circle(pp2
				.toPosition(), r));
		alg.run(ds4);

		IDCTraverseSectorInRectEvent e = new IDCTraverseSectorInRectEvent(root,
				sectorInRect, SectorDirection.FAR, queryAndPatialAnswerMesage,
				alg);
		alg.run(e);
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
