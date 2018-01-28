package NHSensor.NHSensorSim.core;

import java.util.Vector;

public class TopKQueryProcessor {
	protected Vector objects;
	protected Rank rank;

	public TopKQueryProcessor(Vector objects, Rank rank) {
		this.objects = objects;
		this.rank = rank;
	}

	public Object getTop() {
		double minRank = Double.MIN_VALUE;
		double curRank;
		Object result = null;
		Object curObject;
		for (int i = 0; i < this.getObjects().size(); i++) {
			curObject = this.objects.elementAt(i);
			curRank = rank.getRank(curObject);
			if (curRank > minRank) {
				result = curObject;
				minRank = curRank;
			}
		}
		return result;
	}

	public Vector getResult(int k) {
		return null;
	}

	public Vector getObjects() {
		return objects;
	}

	public void setObjects(Vector objects) {
		this.objects = objects;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

}
