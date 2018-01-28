package NHSensor.NHSensorSim.papers.CSA;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.experiment.Constants;
import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;

public class ParamsFactory {
	int nodeNum = 160 * 3;
	int nodeNumBegin = 160 * 2;
	int nodeNumEnd = 160 * 7;
	int nodeNumStep = 160;

	double queryRegionRate = 0.5;
	double[] queryRegionRateArray = new double[] { 0.1, 0.2, 0.3, 0.4, 0.5,
			0.6, 0.7, 0.8, 0.9, 0.99 };

	double gridWidthRate = Constants.IWQE_GRID_WIDTH_RATE;
	// double gridWidthRate = 0.4;
	double gridWidthRatesBegin = 0.44;
	double gridWidthRatesEnd = 0.99;
	double gridWidthRatesStep = 0.04;

	double radioRange = 10;
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

	double networkWidth = 100;
	double networkHeight = 100;

	int networkIDBegin = 1;
	int networkIDEnd = 5;// 30
	int networkIDStep = 1;

//	 if has no fail node
	 int nodeFailModelID = 0;
	 int nodeFailModelIDBegin = 1;
	 int nodeFailModelIDEnd = 0;//20
	 int nodeFailModelIDStep = 1;
		
	 int failNodeNum = 0;
	 int failNodeNumBegin = 2;
	 int failNodeNumEnd = 0;//15
	 int failNodeNumStep = 2;

	// if has fail node
//	int nodeFailModelID = 1;
//	int nodeFailModelIDBegin = 1;
//	int nodeFailModelIDEnd = 5;
//	int nodeFailModelIDStep = 1;
//
//	int failNodeNum = 10;
//	int failNodeNumBegin = 4;
//	int failNodeNumEnd = 20;
//	int failNodeNumStep = 2;
	 
	 //uniform
//	int holeNumber = 0;
//	int holeNumberBegin = 0;
//	int holeNumberEnd = 5;
//	int holeNumberStep = 1;
//
//	int holeModelID = 1;
//	int holeModelIDBegin = 1;
//	int holeModelIDEnd = 1;
//	int holeModelIDStep = 1;
//
//	double maxHoleRadius = 0;
//	double maxHoleRadiusBegin = 4;
//	double maxHoleRadiusEnd = 4;
//	double maxHoleRadiusStep = 2;

	//nonuniform
	int holeNumber = 4;
	int holeNumberBegin = 0;
	int holeNumberEnd = 5;
	int holeNumberStep = 1;

	int holeModelID = 1;
	int holeModelIDBegin = 1;
	int holeModelIDEnd = 4;
	int holeModelIDStep = 1;

	double maxHoleRadius = 15;
	double maxHoleRadiusBegin = 5;
	double maxHoleRadiusEnd = 30;
	double maxHoleRadiusStep = 5;

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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
				queryRegionRateArray);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("queryMessageSize", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("answerMessageSize", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("queryAndPatialAnswerSize",
				ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("resultSize", ParamsGenerator.DEFAULT_GROUP_ID);
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("queryMessageSize", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("answerMessageSize", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("queryAndPatialAnswerSize",
				ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("resultSize", ParamsGenerator.DEFAULT_GROUP_ID);
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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

	public Vector maxHoleRadiusVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadiusBegin,
				this.maxHoleRadiusEnd, this.maxHoleRadiusStep);

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
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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

	public Vector holeNumberVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
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
		IntSequence holeNumbers = new IntSequence(this.holeNumberBegin, this.holeNumberEnd,
				this.holeNumberStep);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelID);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelID);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDBegin, this.holeModelIDEnd,
				this.holeModelIDStep);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(this.maxHoleRadius);

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
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

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
