package NHSensor.NHSensorSim.privacy;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.shape.Shape;

public class PEVAAttachment extends NodeAttachment {
	double maxValue = -1;
	int dataSize;

	public PEVAAttachment(Node node, Algorithm algorithm, double energy,
			double radioRange) {
		super(node, algorithm, energy, radioRange);
	}

	public double getMaxVaule(Shape shape) {
		if (maxValue != -1)
			return this.maxValue;
		else {
			PEVAAttachment child;
			double max = this.getVaule(new Time(0), 1);
			double childMaxValue;
			for (int i = 0; i < this.childs.size(); i++) {
				child = (PEVAAttachment) this.childs.elementAt(i);
				if (shape.contains(child.getPos())) {
					childMaxValue = child.getMaxVaule(shape);
					if (childMaxValue > max) {
						max = childMaxValue;
					}
				}
			}
			this.maxValue = max;
			return this.maxValue;
		}
	}

	public int getMessageCount(Shape shape) {
		int maxValueInt = (int) this.getMaxVaule(shape);
		int a = 1;
		int b;
		int size = this.getDataSize();
		for (int i = size - 1; i >= 0; i--) {
			b = a << i;
			if ((b & maxValueInt) != 0) {
				return i + 1;
			}
		}
		return 1;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getVaule(Time time, int attributeID) {
		return this.getAlgorithm().getDataset().getValue(this.getPos(), time,
				attributeID);
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}
}
