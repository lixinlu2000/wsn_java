package NHSensor.NHSensorSim.papers.EDC.CDA_GBA;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.experiment.Constants;
import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;

public class ParamsFactory {
	int nodeNum = 400;
	int nodeNumBegin = 320;
	int nodeNumEnd = 160 * 10;
	int nodeNumStep = 160;

	double queryRegionRate = 0.6;
	double[] queryRegionRateArray = new double[] { 0.1, 0.2, 0.3, 0.4, 0.5,
			0.6, 0.7, 0.8, 0.9, 0.99 };

	double gridWidthRate = Constants.IWQE_GRID_WIDTH_RATE;
	// double gridWidthRate = 0.4;
	double gridWidthRatesBegin = 0.2;
	double gridWidthRatesEnd = 0.99;
	double gridWidthRatesStep = 0.1;

	double radioRange = 50;
	double radioRangesBegin = 10;
	double radioRangesEnd = 50;
	double radioRangesStep = 10;

	int queryMessageSize = 20;
	int queryMessageSizeBegin = 20;
	int queryMessageSizeEnd = 100;
	int queryMessageSizeStep = 20;

	int senseDataSize = 40;
	int senseDataSizeBegin = 20;
	int senseDataSizeEnd = 100;
	int senseDataSizeStep = 20;

	double networkWidth = 400;
	double networkHeight = 400;

	int networkIDBegin = 1;
	int networkIDEnd = 15;// 30
	int networkIDStep = 1;

	double nodeFailProbability = 0.04;
	double nodeFailProbabilityBegin = 0.01;
	double nodeFailProbabilityEnd = 0.3;
	double nodeFailProbabilityStep = 0.05;

	int k = 30;
	int kBegin = 10;
	int kEnd = 60;
	int kStep = 10;

	int nodeFailModelID = 0;
	int nodeFailModelIDBegin = 1;
	int nodeFailModelIDEnd = 1;// 20
	int nodeFailModelIDStep = 1;

	int failNodeNum = 4;
	int failNodeNumBegin = 1;
	int failNodeNumEnd = 10;// 15
	int failNodeNumStep = 1;

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

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNumBegin,
				this.failNodeNumEnd, this.failNodeNumStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("nodeNum", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("failNodeNum", ParamsGenerator.DEFAULT_GROUP_ID);

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
				this.queryRegionRateArray);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());

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
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());

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
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		IntSequence ks = new IntSequence(this.k);
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
		sequences.put("k", ks.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(
				this.nodeFailModelIDBegin, this.nodeFailModelIDEnd,
				this.nodeFailModelIDStep);
		IntSequence failNodeNums = new IntSequence(this.failNodeNumBegin,
				this.failNodeNumEnd, this.failNodeNumStep);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
