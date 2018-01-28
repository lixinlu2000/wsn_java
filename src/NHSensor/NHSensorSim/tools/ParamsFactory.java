package NHSensor.NHSensorSim.tools;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;

public class ParamsFactory {
	int networkID = 1;
	int networkIDBegin = 1;
	int networkIDEnd = 10;
	int networkIDStep = 1;

	int nodeNum = 160*3;
	int nodeNumBegin = 160*2;
	int nodeNumEnd = 160*10;
	int nodeNumStep = 160;

	double queryRegionRate = 0.4;
	double queryRegionRateBegin = 0.1;
	double queryRegionRateEnd = 1;
	double queryRegionRateStep = 0.1;

	double gridWidthRate = 10;
	double gridWidthRateBegin = 0;
	double gridWidthRateEnd = 10;
	double gridWidthRateStep = 1;

	double radioRange = 10;
	double radioRangeBegin = 10;
	double radioRangeEnd = 50;
	double radioRangeStep = 10;

	int queryMessageSize = 50;
	int queryMessageSizeBegin = 50;
	int queryMessageSizeEnd = 400;
	int queryMessageSizeStep = 50;

	int senseDataSize = 200;
	int senseDataSizeBegin = 50;
	int senseDataSizeEnd = 400;
	int senseDataSizeStep = 50;

	double networkWidth = 100;

	double networkHeight = 100;

	double initialEnergy = 800;
	double initialEnergyBegin = 800;
	double initialEnergyEnd = 1600;
	double initialEnergyStep = 200;

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

		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		sequences.put("gridWidthRate", gridWidthRates.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence senseDataSizes = new IntSequence(this.senseDataSize);
		sequences.put("senseDataSize", senseDataSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

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

		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		sequences.put("gridWidthRate", gridWidthRates.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence senseDataSizes = new IntSequence(this.senseDataSize);
		sequences.put("senseDataSize", senseDataSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

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

		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		sequences.put("gridWidthRate", gridWidthRates.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence senseDataSizes = new IntSequence(this.senseDataSize);
		sequences.put("senseDataSize", senseDataSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector gridWidthRateVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRateBegin,
			this.gridWidthRateEnd, this.gridWidthRateStep);
		sequences.put("gridWidthRate", gridWidthRates.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence senseDataSizes = new IntSequence(this.senseDataSize);
		sequences.put("senseDataSize", senseDataSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector radioRangeVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		sequences.put("gridWidthRate", gridWidthRates.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRangeBegin,
			this.radioRangeEnd, this.radioRangeStep);
		sequences.put("radioRange", radioRanges.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence senseDataSizes = new IntSequence(this.senseDataSize);
		sequences.put("senseDataSize", senseDataSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

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

		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		sequences.put("gridWidthRate", gridWidthRates.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSizeBegin,
			this.queryMessageSizeEnd, this.queryMessageSizeStep);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence senseDataSizes = new IntSequence(this.senseDataSize);
		sequences.put("senseDataSize", senseDataSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector senseDataSizeVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		sequences.put("gridWidthRate", gridWidthRates.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence senseDataSizes = new IntSequence(this.senseDataSizeBegin,
			this.senseDataSizeEnd, this.senseDataSizeStep);
		sequences.put("senseDataSize", senseDataSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector initialEnergyVariable() {
		Class paramClass = AllParam.class;
		Hashtable sequences = new Hashtable();

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
			this.networkIDEnd, this.networkIDStep);
		sequences.put("networkID", networkIDs.getData());

		IntSequence nodeNums = new IntSequence(this.nodeNum);
		sequences.put("nodeNum", nodeNums.getData());

		DoubleSequence queryRegionRates = new DoubleSequence(this.queryRegionRate);
		sequences.put("queryRegionRate", queryRegionRates.getData());

		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		sequences.put("gridWidthRate", gridWidthRates.getData());

		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		sequences.put("radioRange", radioRanges.getData());

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		sequences.put("queryMessageSize", queryMessageSizes.getData());

		IntSequence senseDataSizes = new IntSequence(this.senseDataSize);
		sequences.put("senseDataSize", senseDataSizes.getData());

		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		sequences.put("networkWidth", networkWidths.getData());

		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergyBegin,
			this.initialEnergyEnd, this.initialEnergyStep);
		sequences.put("initialEnergy", initialEnergys.getData());

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
