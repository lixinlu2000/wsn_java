package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.papers.EST.FullFloodEnergyExperiment;
import NHSensor.NHSensorSim.ui.AnimatorPanel;

public class AlgorithmDemoPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public AlgorithmDemoPanel() {
		//Test
		boolean showAnimator = false;
		String paramString = "networkID(4) nodeNum(300) queryRegionRate(0.4) gridWidthRate(0.8660254037844386) queryMessageSize(30) answerMessageSize(50) networkWidth(100.0) networkHeight(100.0) radioRange(10.0) queryAndPatialAnswerSize(100) resultSize(50)nodeFailProbability(0.04)k(0)nodeFailModelID(0)failNodeNum(0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		FullFloodEnergyExperiment e = new FullFloodEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		//
		Algorithm alg = e.getAlgorithm();
		this.add(new AnimatorPanel(alg));

	}

}
