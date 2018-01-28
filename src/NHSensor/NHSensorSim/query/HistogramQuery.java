package NHSensor.NHSensorSim.query;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.TraversedNodes;

public class HistogramQuery extends QueryBase {
	HistogramInfo histogramInfo;
	int sampleEpoch;
	int sampleTimes;

	public HistogramQuery(Node orig, HistogramInfo histogramInfo,
			int sampleEpoch, int sampleTimes) {
		super(orig);
		this.histogramInfo = histogramInfo;
		this.sampleEpoch = sampleEpoch;
		this.sampleTimes = sampleTimes;
	}

	public HistogramQuery(Node orig, HistogramInfo histogramInfo) {
		super(orig);
		this.histogramInfo = histogramInfo;
	}

	public HistogramQuery(Node orig) {
		super(orig);
	}

	public HistogramInfo getHistogramInfo() {
		return histogramInfo;
	}

	public void setHistogramInfo(HistogramInfo histogramInfo) {
		this.histogramInfo = histogramInfo;
	}

	public int getSampleEpoch() {
		return sampleEpoch;
	}

	public void setSampleEpoch(int sampleEpoch) {
		this.sampleEpoch = sampleEpoch;
	}

	public int getSampleTimes() {
		return sampleTimes;
	}

	public void setSampleTimes(int sampleTimes) {
		this.sampleTimes = sampleTimes;
	}

	public TraversedNodes getTraversedNodes(Network network) {
		// TODO Auto-generated method stub
		return null;
	}

}
