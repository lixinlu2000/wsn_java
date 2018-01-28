package NHSensor.NHSensorSim.papers.RISC;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.core.GKNNUtil;
import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;
import NHSensor.NHSensorSim.experiment.ParamsGeneratorEx;
import NHSensor.NHSensorSim.experiment.SequenceInfo;

public class GridTraverseRingParamsFactoryOld {
	int nodeNum = 1200;
	SequenceInfo nodeNumInfo = new SequenceInfo(400, 2400, 400);

	int queryMessageSize = 35;
	SequenceInfo queryMessageSizeInfo = new SequenceInfo(30, 60, 5);

	int senseDataSize = 10;
	SequenceInfo senseDataSizeInfo = new SequenceInfo(5, 30, 5);

	double networkWidth = 450;
	double networkHeight = 450;

	double radioRange = 30 * Math.sqrt(3);

	int networkID = 1;
	SequenceInfo networkIDInfo = new SequenceInfo(1, 20, 1);

	double nodeFailProbability = 0.09;
	SequenceInfo nodeFailProbabilityInfo = new SequenceInfo(0.03, 0.3, 0.03);

	double ringLowRadius = 80;
	SequenceInfo ringLowRadiusInfo = new SequenceInfo(40, 160, 20);

	double ringWidth = 45;
	SequenceInfo ringWidthInfo = new SequenceInfo(10, 45, 5);

	double sita;
	int sitasSize = 5;
	SequenceInfo sitaInfo;

	public GridTraverseRingParamsFactoryOld() {
	}

	protected double caculateMaxSita(double ringLowRadius, double ringWidth) {
		return GKNNUtil.calculateRingSectorSita(ringLowRadius, ringLowRadius
				+ ringWidth, this.radioRange);
	}

	protected DoubleSequence caculateSitaSequence(double ringLowRadius,
			double ringWidth, int size) {
		double maxSita = this.caculateMaxSita(ringLowRadius, ringWidth);
		double sitaBegin = maxSita / 2;
		double sitaStep = (maxSita - sitaBegin) / (size - 1);
		return new DoubleSequence(
				new SequenceInfo(sitaBegin, maxSita, sitaStep));
	}

	protected DoubleSequence caculateSitaSequence(
			DoubleSequence ringLowRadiuss, double ringWidth) {
		DoubleSequence sitaSequence = new DoubleSequence();
		double ringLowRadius;
		double maxSita;

		for (int i = 0; i < ringLowRadiuss.getSize(); i++) {
			ringLowRadius = ringLowRadiuss.elementAt(i);
			maxSita = this.caculateMaxSita(ringLowRadius, ringWidth);
			sitaSequence.add(maxSita);
		}
		return sitaSequence;
	}

	protected DoubleSequence caculateSitaSequence(double ringLowRadius,
			DoubleSequence ringWidths) {
		DoubleSequence sitaSequence = new DoubleSequence();
		double ringWidth;
		double maxSita;

		for (int i = 0; i < ringWidths.getSize(); i++) {
			ringWidth = ringWidths.elementAt(i);
			maxSita = this.caculateMaxSita(ringLowRadius, ringWidth);
			sitaSequence.add(maxSita);
		}
		return sitaSequence;
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
		sita = this.caculateMaxSita(ringLowRadius, ringWidth);
		DoubleSequence sitas = new DoubleSequence(this.sita);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("sita", sitas.getData());

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
		sita = this.caculateMaxSita(ringLowRadius, ringWidth);
		DoubleSequence sitas = new DoubleSequence(this.sita);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("sita", sitas.getData());

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
		sita = this.caculateMaxSita(ringLowRadius, ringWidth);
		DoubleSequence sitas = new DoubleSequence(this.sita);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("sita", sitas.getData());

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
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence sitas = this.caculateSitaSequence(ringLowRadiuss,
				ringWidth);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("sita", sitas.getData());

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
		DoubleSequence sitas = this.caculateSitaSequence(ringLowRadius,
				ringWidths);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("sita", sitas.getData());

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
		sita = this.caculateMaxSita(ringLowRadius, ringWidth);
		DoubleSequence sitas = new DoubleSequence(this.sita);

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
		sequences.put("sita", sitas.getData());

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
		sita = this.caculateMaxSita(ringLowRadius, ringWidth);
		DoubleSequence sitas = new DoubleSequence(this.sita);

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
		sequences.put("sita", sitas.getData());

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

	public Vector nodeFailProbabilityVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbabilityInfo);
		sita = this.caculateMaxSita(ringLowRadius, ringWidth);
		DoubleSequence sitas = new DoubleSequence(this.sita);

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
		sequences.put("sita", sitas.getData());

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

	public Vector sitaVariable() {
		Class paramClass = TraverseRingExperimentParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDInfo);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence ringLowRadiuss = new DoubleSequence(this.ringLowRadius);
		DoubleSequence ringWidths = new DoubleSequence(this.ringWidth);
		IntSequence queryMessageSizes = new IntSequence(this.queryMessageSize);
		IntSequence answerMessageSizes = new IntSequence(this.senseDataSize);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		DoubleSequence sitas = this.caculateSitaSequence(this.ringLowRadius,
				this.ringWidth, this.sitasSize);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("ringLowRadius", ringLowRadiuss.getData());
		sequences.put("ringWidth", ringWidths.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("sita", sitas.getData());

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
