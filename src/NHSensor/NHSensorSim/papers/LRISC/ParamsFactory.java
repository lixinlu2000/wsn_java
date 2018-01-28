package NHSensor.NHSensorSim.papers.LRISC;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;
import NHSensor.NHSensorSim.experiment.ParamsGeneratorEx;
import NHSensor.NHSensorSim.experiment.SequenceInfo;

public class ParamsFactory {
	int nodeNum = 400;
	SequenceInfo nodeNumInfo = new SequenceInfo(400, 1200, 100);

	int queryMessageSize = 10;
	SequenceInfo queryMessageSizeInfo = new SequenceInfo(5, 30, 5);

	int senseDataSize = 35;
	SequenceInfo senseDataSizeInfo = new SequenceInfo(30, 60, 5);

	double networkWidth = 450;
	double networkHeight = 450;

	double radioRange = 30 * Math.sqrt(3);

	int networkID = 1;
	SequenceInfo networkIDInfo = new SequenceInfo(1, 20, 1);

	// NOT USED
	double nodeFailProbability = 0.09;
	SequenceInfo nodeFailProbabilityInfo = new SequenceInfo(0.03, 0.3, 0.03);

	double ringLowRadius = 80;
	SequenceInfo ringLowRadiusInfo = new SequenceInfo(20, 200, 30);

	double ringWidth = 45;
	SequenceInfo ringWidthInfo = new SequenceInfo(10, 50, 5);

	// int nodeFailModelID = 1;
	// SequenceInfo nodeFailModelIDInfo =
	// new SequenceInfo(1,10,1);

	// no fail node
	int nodeFailModelID = 0;
	SequenceInfo nodeFailModelIDInfo = new SequenceInfo(1, 0, 1);

	// int failNodeNum = 8;
	// SequenceInfo failNodeNumInfo =
	// new SequenceInfo(24,72,6);

	// no fail node
	int failNodeNum = 0;
	SequenceInfo failNodeNumInfo = new SequenceInfo(4, 0, 2);

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
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(this.nodeFailModelID);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(this.nodeFailModelIDInfo);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(this.nodeFailModelIDInfo);
		IntSequence failNodeNums = new IntSequence(this.failNodeNumInfo);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("nodeNum", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("failNodeNum", ParamsGenerator.DEFAULT_GROUP_ID);

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences,
					fieldGroup);
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
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(this.nodeFailModelIDInfo);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("ringLowRadius", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("sita", ParamsGenerator.DEFAULT_GROUP_ID);

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences,
					fieldGroup);
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
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(this.nodeFailModelIDInfo);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("ringWidth", ParamsGenerator.DEFAULT_GROUP_ID);
		fieldGroup.put("sita", ParamsGenerator.DEFAULT_GROUP_ID);

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences,
					fieldGroup);
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
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

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
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(this.nodeFailModelIDInfo);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSizeInfo);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(this.nodeFailModelIDInfo);
		IntSequence failNodeNums = new IntSequence(this.failNodeNum);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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

	public Vector failNodeNumVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);

		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		IntSequence nodeFailModelIDs = new IntSequence(this.nodeFailModelIDInfo);
		IntSequence failNodeNums = new IntSequence(this.failNodeNumInfo);
		sequences.put("nodeFailModelID", nodeFailModelIDs.getData());
		sequences.put("failNodeNum", failNodeNums.getData());

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
