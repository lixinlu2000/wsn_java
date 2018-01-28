package NHSensor.NHSensorSim.papers.LKNN;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGeneratorEx;
import NHSensor.NHSensorSim.experiment.SequenceInfo;

public class ParamsFactory {
	int nodeNum = 400;
	SequenceInfo nodeNumInfo = new SequenceInfo(160, 640, 80);

	int queryMessageSize = 10;
	SequenceInfo queryMessageSizeInfo = new SequenceInfo(10, 60, 10);

	int senseDataSize = 30;
	SequenceInfo senseDataSizeInfo = new SequenceInfo(20, 60, 5);

	double networkWidth = 100;
	double networkHeight = 100;

	double radioRange = 20;
	SequenceInfo radioRangeInfo = new SequenceInfo(10.5, 15, 0.5);

	int networkID = 1;
	SequenceInfo networkIDInfo = new SequenceInfo(1, 20, 1);

	double ringLowRadius = 20;
	SequenceInfo ringLowRadiusInfo = new SequenceInfo(20, 40, 5);

	double ringWidth = 10;
	SequenceInfo ringWidthInfo = new SequenceInfo(5, 10, 1);

	int linkID = 0;
	SequenceInfo linkIDInfo = new SequenceInfo(1, 5, 1);
	
	 //uniform
//	int holeNumber = 0;
//	SequenceInfo holeNumberInfo = new SequenceInfo(1, 1, 1);
//
//	int holeModelID = 1;
//	SequenceInfo holeModelIDInfo = new SequenceInfo(1, 1, 1);
//
//	double maxHoleRadius = 15;
//	SequenceInfo maxHoleRadiusInfo = new SequenceInfo(4, 4, 4);

	//nonuniform
	int holeNumber = 4;
	SequenceInfo holeNumberInfo = new SequenceInfo(0, 5, 1);

	int holeModelID = 1;
	SequenceInfo holeModelIDInfo = new SequenceInfo(1, 4, 1);

	double maxHoleRadius = 15;
	SequenceInfo maxHoleRadiusInfo = new SequenceInfo(5, 30, 5);

	public ParamsFactory() {
	}

	public Vector variable(String variableName) throws Exception {
		String methodName = variableName + "Variable";
		Class classType = this.getClass();
		Method method = classType.getMethod(methodName, new Class[] {});
		return (Vector) (method.invoke(this, new Object[] {}));
	}

	public Vector nullVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkID);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);
		IntSequence linkIDs = new IntSequence(
				this.linkID);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector networkIDVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		
		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());


		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector nodeNumVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNumInfo);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());

		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());


		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector ringLowRadiusVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(
				this.ringLowRadiusInfo);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());

		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());


		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector ringWidthVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidthInfo);
		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());

		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());


		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector queryMessageSizeVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		IntSequence queryMessageSizes = new IntSequence(
				this.queryMessageSizeInfo);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());

		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());


		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector answerMessageSizeVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSizeInfo);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());


		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}
	
	public Vector radioRangeVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRangeInfo);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());


		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}


	public Vector linkIDVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());


		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector holeNumberVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);

		IntSequence holeNumbers = new IntSequence(this.holeNumberInfo);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadius);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());


		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector maxHoleRadiusVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence linkIDs = new IntSequence(
				this.linkIDInfo);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);

		IntSequence holeNumbers = new IntSequence(this.holeNumber);
		IntSequence holeModelIDs = new IntSequence(this.holeModelIDInfo);
		DoubleSequence maxHoleRadiuss = new DoubleSequence(
				this.maxHoleRadiusInfo);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());
		sequences.put("linkID", linkIDs.getData());


		sequences.put("holeNumber", holeNumbers.getData());
		sequences.put("holeModelID", holeModelIDs.getData());
		sequences.put("maxHoleRadius", maxHoleRadiuss.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

}
