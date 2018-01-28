package NHSensor.NHSensorSim.util;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import NHSensor.NHSensorSim.shape.Arc;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Rect;

public class Convertor {
	public static Rectangle2D.Double rectToRectangle2DDouble(Rect rect) {
		return new Rectangle2D.Double(rect.getMinX(), rect.getMinY(), rect
				.getWidth(), rect.getHeight());
	}

	public static Point2D.Double positionToPoint2DDouble(Position p) {
		return new Point2D.Double(p.getX(), p.getY());
	}

	public static Arc2D.Double arcToArc2DDouble(Arc arc) {
		Rect rect = arc.getCircle().minBox();
		return new Arc2D.Double(rect.getLT().getX(), rect.getLT().getY(), rect
				.getWidth(), rect.getHeight(), Radian.getDegree(arc
				.getStartRadian()), Radian.getDegree(arc.getSita()), arc
				.getType());
	}

	public static double convertRadian(double radian) {
		double twoPI = 2 * Math.PI;

		while (radian >= twoPI) {
			radian -= twoPI;
		}

		while (radian < 0) {
			radian += twoPI;
		}

		return radian;
	}

	public static double relativeRadianNPiToPi(double bearing1, double bearing2) {
		double relativeBearing = bearing1 - bearing2;
		relativeBearing = Convertor.convertRadian(relativeBearing);

		if (relativeBearing > Math.PI)
			relativeBearing = relativeBearing - 2 * Math.PI;
		return relativeBearing;
	}

	public static double radianToDegree(double radian) {
		double r = Convertor.convertRadian(radian);
		return r * 180 / Math.PI;
	}

}
