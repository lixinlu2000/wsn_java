package NHSensor.NHSensorSim.papers.HSA;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;

public class ParamsFactory {
	int networkID = 1;
	int networkIDBegin = 1;
	int networkIDEnd = 20;
	int networkIDStep = 1;

	int nodeNum = 160*5;
	int nodeNumBegin = 160*2;
	int nodeNumEnd = 160*10;
	int nodeNumStep = 160;

	double queryRegionRate = 0.3;
	double queryRegionRateBegin = 0.1;
	double queryRegionRateEnd = 1;
	double queryRegionRateStep = 0.1;

	int queryMessageSize = 50;
	int queryMessageSizeBegin = 50;
	int queryMessageSizeEnd = 400;
	int queryMessageSizeStep = 50;

	int answerMessageSize = 200;
	int answerMessageSizeBegin = 50;
	int answerMessageSizeEnd = 400;
	int answerMessageSizeStep = 50;

	double networkWidth = 100;

	double networkHeight = 100;

	int subAreaNum = 25;
	int subAreaNumBegin = 10;
	int subAreaNumEnd = 40;
	int subAreaNumStep = 1;

	double datasetMax = 10;

	int datasetID = 1;
	int datasetIDBegin = 1;
	int datasetIDEnd = 1;
	int datasetIDStep = 1;

	double delta = 8;
	double deltaBegin = 1;
	double deltaEnd = 5;
	double deltaStep = 1;

	double initialEnergy = 1500;

	double radioRange = 10;

	public ParamsFactory() {

	}

	public Vector variable(String variableName) throws Exception {
		String methodName = variableName + "Variable";
		Class classType = this.getClass();
		Method method = classType.getMethod(methodName, new Class[] {});
		return (Vector) (method.invoke(this, new Object[] {}));
	}

	public Vector networkIDVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence answerMessageSizes = new IntSequence(this.answerMessageSize);
		sequences.put("answerMessageSize", answerMessageSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence subAreaNums = new IntSequence(this.subAreaNum);
		sequences.put("subAreaNum", subAreaNums.getData());

		DoubleSequence datasetMaxs = new DoubleSequence(this.datasetMax);
		sequences.put("datasetMax", datasetMaxs.getData());

		IntSequence datasetIDs = new IntSequence(this.datasetIDBegin,
			this.datasetIDEnd, this.datasetIDStep);
		sequences.put("datasetID", datasetIDs.getData());

		DoubleSequence deltas = new DoubleSequence(this.delta);
		sequences.put("delta", deltas.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector nodeNumVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNumBegin,
			this.nodeNumEnd, this.nodeNumStep);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence answerMessageSizes = new IntSequence(this.answerMessageSize);
		sequences.put("answerMessageSize", answerMessageSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence subAreaNums = new IntSequence(this.subAreaNum);
		sequences.put("subAreaNum", subAreaNums.getData());

		DoubleSequence datasetMaxs = new DoubleSequence(this.datasetMax);
		sequences.put("datasetMax", datasetMaxs.getData());

		IntSequence datasetIDs = new IntSequence(this.datasetIDBegin,
			this.datasetIDEnd, this.datasetIDStep);
		sequences.put("datasetID", datasetIDs.getData());

		DoubleSequence deltas = new DoubleSequence(this.delta);
		sequences.put("delta", deltas.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector queryRegionRateVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRateBegin,
			this.queryRegionRateEnd, this.queryRegionRateStep);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence answerMessageSizes = new IntSequence(this.answerMessageSize);
		sequences.put("answerMessageSize", answerMessageSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence subAreaNums = new IntSequence(this.subAreaNum);
		sequences.put("subAreaNum", subAreaNums.getData());

		DoubleSequence datasetMaxs = new DoubleSequence(this.datasetMax);
		sequences.put("datasetMax", datasetMaxs.getData());

		IntSequence datasetIDs = new IntSequence(this.datasetIDBegin,
			this.datasetIDEnd, this.datasetIDStep);
		sequences.put("datasetID", datasetIDs.getData());

		DoubleSequence deltas = new DoubleSequence(this.delta);
		sequences.put("delta", deltas.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector queryMessageSizeVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSizeBegin,
			this.queryMessageSizeEnd, this.queryMessageSizeStep);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence answerMessageSizes = new IntSequence(this.answerMessageSize);
		sequences.put("answerMessageSize", answerMessageSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence subAreaNums = new IntSequence(this.subAreaNum);
		sequences.put("subAreaNum", subAreaNums.getData());

		DoubleSequence datasetMaxs = new DoubleSequence(this.datasetMax);
		sequences.put("datasetMax", datasetMaxs.getData());

		IntSequence datasetIDs = new IntSequence(this.datasetIDBegin,
			this.datasetIDEnd, this.datasetIDStep);
		sequences.put("datasetID", datasetIDs.getData());

		DoubleSequence deltas = new DoubleSequence(this.delta);
		sequences.put("delta", deltas.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector answerMessageSizeVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence answerMessageSizes = new IntSequence(this.answerMessageSizeBegin,
			this.answerMessageSizeEnd, this.answerMessageSizeStep);
		sequences.put("answerMessageSize", answerMessageSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence subAreaNums = new IntSequence(this.subAreaNum);
		sequences.put("subAreaNum", subAreaNums.getData());

		DoubleSequence datasetMaxs = new DoubleSequence(this.datasetMax);
		sequences.put("datasetMax", datasetMaxs.getData());

		IntSequence datasetIDs = new IntSequence(this.datasetIDBegin,
			this.datasetIDEnd, this.datasetIDStep);
		sequences.put("datasetID", datasetIDs.getData());

		DoubleSequence deltas = new DoubleSequence(this.delta);
		sequences.put("delta", deltas.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector subAreaNumVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence answerMessageSizes = new IntSequence(this.answerMessageSize);
		sequences.put("answerMessageSize", answerMessageSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence subAreaNums = new IntSequence(this.subAreaNumBegin,
			this.subAreaNumEnd, this.subAreaNumStep);
		sequences.put("subAreaNum", subAreaNums.getData());

		DoubleSequence datasetMaxs = new DoubleSequence(this.datasetMax);
		sequences.put("datasetMax", datasetMaxs.getData());

		IntSequence datasetIDs = new IntSequence(this.datasetIDBegin,
			this.datasetIDEnd, this.datasetIDStep);
		sequences.put("datasetID", datasetIDs.getData());

		DoubleSequence deltas = new DoubleSequence(this.delta);
		sequences.put("delta", deltas.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector datasetIDVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence answerMessageSizes = new IntSequence(this.answerMessageSize);
		sequences.put("answerMessageSize", answerMessageSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence subAreaNums = new IntSequence(this.subAreaNum);
		sequences.put("subAreaNum", subAreaNums.getData());

		DoubleSequence datasetMaxs = new DoubleSequence(this.datasetMax);
		sequences.put("datasetMax", datasetMaxs.getData());

		IntSequence datasetIDs = new IntSequence(this.datasetIDBegin,
			this.datasetIDEnd, this.datasetIDStep);
		sequences.put("datasetID", datasetIDs.getData());

		DoubleSequence deltas = new DoubleSequence(this.delta);
		sequences.put("delta", deltas.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector deltaVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence answerMessageSizes = new IntSequence(this.answerMessageSize);
		sequences.put("answerMessageSize", answerMessageSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence subAreaNums = new IntSequence(this.subAreaNum);
		sequences.put("subAreaNum", subAreaNums.getData());

		DoubleSequence datasetMaxs = new DoubleSequence(this.datasetMax);
		sequences.put("datasetMax", datasetMaxs.getData());

		IntSequence datasetIDs = new IntSequence(this.datasetIDBegin,
			this.datasetIDEnd, this.datasetIDStep);
		sequences.put("datasetID", datasetIDs.getData());

		DoubleSequence deltas = new DoubleSequence(this.deltaBegin,
			this.deltaEnd, this.deltaStep);
		sequences.put("delta", deltas.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

}
