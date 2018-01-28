package NHSensor.NHSensorSim.test;

import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.NI_NN_QRR_GW_Param;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;
import NHSensor.NHSensorSim.experiment.ParamsGeneratorEx;

public class ParamsGeneratorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * int networkID; int nodeNum; double queryRegionRate; double
		 * gridWidthRate;
		 */

		Class paramClass = NI_NN_QRR_GW_Param.class;

		IntSequence networkIDs = new IntSequence(1, 10, 1);
		IntSequence nodeNums = new IntSequence(400, 700, 100);
		DoubleSequence queryRegionRates = new DoubleSequence(0.1, 1, 0.2);
		DoubleSequence gridWidthRates = new DoubleSequence(0.1, 1, 0.2);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());

		try {
			ParamsGenerator paramsGenerator = new ParamsGenerator(paramClass,
					sequences);
			Vector params = paramsGenerator.getParams();
			System.out.println(params.size());
			System.out.println(params);

			ParamsGeneratorEx paramsGeneratorEx = new ParamsGeneratorEx(
					paramClass, sequences);
			Vector paramsEx = paramsGeneratorEx.getParams();
			System.out.println(paramsEx.size());
			System.out.println(paramsEx);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
