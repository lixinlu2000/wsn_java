package NHSensor.NHSensorSim.papers.E2STA;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;

public class ParamsFactory1 {
	int nodeNum = 2050;
	int nodeNumBegin = 160 * 2;
	int nodeNumEnd = 160 * 10;
	int nodeNumStep = 160;

	double queryRegionRate = 0.9;
	double[] queryRegionRateArray = new double[] { 0.05, 0.1, 0.2, 0.3, 0.4, 0.5,
			0.6, 0.7, 0.8, 0.9 };
//	double[] queryRegionRateArray = new double[] { 0.02, 0.04, 0.06, 0.08, 0.1,
//			 0.15, 0.2, 0.25, 0.3, 0.35,
//			 0.4, 0.45, 0.5, 0.55, 0.6,
//			 0.65, 0.7, 0.75, 0.8, 0.85,
//			 0.9,0.95,0.999};

	double gridWidthRate = 10;
	// double gridWidthRate = 0.4;
	double gridWidthRatesBegin = 2;
	double gridWidthRatesEnd = 15;
	double gridWidthRatesStep =1;

	double radioRange = 50;
	double radioRangesBegin = 10;
	double radioRangesEnd = 50;
	double radioRangesStep = 10;

	int queryMessageSize = 32;
	int queryMessageSizeBegin = 50;
	int queryMessageSizeEnd = 400;
	int queryMessageSizeStep = 50;

	int senseDataSize = 480;
	int senseDataSizeBegin = 50;
	int senseDataSizeEnd = 400;
	int senseDataSizeStep = 50;

	double networkWidth = 1000;
	double networkHeight = 1000;

	int networkIDBegin = 1;
	int networkIDEnd = 20;// 30
	int networkIDStep = 1;

	double nodeFailProbability = 0;
	double nodeFailProbabilityBegin = 0.01;
	double nodeFailProbabilityEnd = 0.3;
	double nodeFailProbabilityStep = 0.05;

	// query node position ID
	int nodeFailModelID = 0;
	int nodeFailModelIDBegin = 1;
	int nodeFailModelIDEnd = 0;// 20
	int nodeFailModelIDStep = 1;

	int failNodeNum = 0;
	int failNodeNumBegin = 2;
	int failNodeNumEnd = 0;// 15
	int failNodeNumStep = 2;

	public ParamsFactory1() {

	}

	public Vector variable(String variableName) throws Exception {
		String methodName = variableName + "Variable";
		Class classType = this.getClass();
		Method method = classType.getMethod(methodName, new Class[] {});
		return (Vector) (method.invoke(this, new Object[] {}));
	}

	public Vector networkIDVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector nodeNumVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNumBegin,
				this.nodeNumEnd, this.nodeNumStep);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector radioRangeVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRangesBegin,
				this.radioRangesEnd, this.radioRangesStep);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector gridWidthRateVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector queryRegionRateVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				queryRegionRateArray);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector queryMessageSizeVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		Vector queryMessageSizes = new Vector();
		Vector answerMessageSizes = new Vector();
		Vector queryAndPatialAnswerSizes = new Vector();
		Vector resultSizes = new Vector();

		for (int qs = this.queryMessageSizeBegin; qs <= this.queryMessageSizeEnd; qs += this.queryMessageSizeStep) {
			ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
					this.senseDataSize, qs);
			queryMessageSizes.addElement(new Integer(itineraryAlgParam
					.getQuerySize()));
			answerMessageSizes.addElement(new Integer(itineraryAlgParam
					.getAnswerSize()));
			queryAndPatialAnswerSizes.addElement(new Integer(itineraryAlgParam
					.getQueryAndPatialAnswerSize()));
			resultSizes.addElement(new Integer(itineraryAlgParam
					.getResultSize()));
		}

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes);
		sequences.put("answerMessageSize", answerMessageSizes);
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes);
		sequences.put("resultSize", resultSizes);
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("queryMessageSize", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("answerMessageSize", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("queryAndPatialAnswerSize",
				ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("resultSize", ParamsGenerator.DEFAULT_GROUP_ID);
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences,
					fieldGroup);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector answerMessageSizeVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		Vector queryMessageSizes = new Vector();
		Vector answerMessageSizes = new Vector();
		Vector queryAndPatialAnswerSizes = new Vector();
		Vector resultSizes = new Vector();

		for (int sds = this.senseDataSizeBegin; sds <= this.senseDataSizeEnd; sds += this.senseDataSizeStep) {
			ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(sds,
					this.queryMessageSize);
			queryMessageSizes.addElement(new Integer(itineraryAlgParam
					.getQuerySize()));
			answerMessageSizes.addElement(new Integer(itineraryAlgParam
					.getAnswerSize()));
			queryAndPatialAnswerSizes.addElement(new Integer(itineraryAlgParam
					.getQueryAndPatialAnswerSize()));
			resultSizes.addElement(new Integer(itineraryAlgParam
					.getResultSize()));
		}

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes);
		sequences.put("answerMessageSize", answerMessageSizes);
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes);
		sequences.put("resultSize", resultSizes);
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("queryMessageSize", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("answerMessageSize", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("queryAndPatialAnswerSize",
				ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("resultSize", ParamsGenerator.DEFAULT_GROUP_ID);
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences,
					fieldGroup);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector nodeFailProbabilityVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbabilityBegin, this.nodeFailProbabilityEnd,
				this.nodeFailProbabilityStep);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector nodeNumGridWidthRateVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNumBegin,
				this.nodeNumEnd, this.nodeNumStep);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector radioRangeGridWidthRateVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRangesBegin,
				this.radioRangesEnd, this.radioRangesStep);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector failNodeNumVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());

		ParamsGenerator paramsGenerator;
		try {
			paramsGenerator = new ParamsGenerator(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}
}
