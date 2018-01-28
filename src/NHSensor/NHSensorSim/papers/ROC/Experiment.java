package NHSensor.NHSensorSim.papers.ROC;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.util.ExperimentUtil;
import NHSensor.NHSensorSim.util.NHSensorSimClass;
import NHSensor.NHSensorSim.util.NHSensorSimPackage;
import NHSensor.NHSensorSim.util.NHSensorSimPackageUtil;

public class Experiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Vector nHSensorSimClasses = Experiment.classes();
		// ExperimentUtil.invokeNHSensorSimClassesMain(nHSensorSimClasses);

		// ExperimentUtil.invokeClassesMain(Experiment.energy());
		// ExperimentUtil.invokeClassesMain(Experiment.averageEnergy());
		ExperimentUtil.invokeClassesMain(Experiment.suceessProbability());
	}

	public static Vector classes() {
		Vector classNames = new Vector();
		NHSensorSimPackage p = new NHSensorSimPackage(
				"NHSensor.NHSensorSim.papers.ROCAdaptiveGridTranverseRing.energy");
		classNames.add(new NHSensorSimClass(p, "NodeNumEnergy"));
		return classNames;
	}

	public static Vector allClassNames() {
		String packageName;
		Vector all = new Vector();
		Vector classNames;

		try {
			packageName = "NHSensor.NHSensorSim.papers.ROCAdaptiveGridTranverseRing.energy";
			classNames = NHSensorSimPackageUtil.getClasses(packageName);
			all.addAll(classNames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all;
	}

	public static Vector energy() {
		String packageName;
		Vector classNames = new Vector();

		try {
			packageName = "NHSensor.NHSensorSim.papers.ROCAdaptiveGridTranverseRing.energy";
			classNames = NHSensorSimPackageUtil.getClasses(packageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classNames;

	}

	public static Vector averageEnergy() {
		String packageName;
		Vector classNames = new Vector();

		try {
			packageName = "NHSensor.NHSensorSim.papers.ROCAdaptiveGridTranverseRing.averageEnergy";
			classNames = NHSensorSimPackageUtil.getClasses(packageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classNames;
	}

	public static Vector suceessProbability() {
		String packageName;
		Vector classNames = new Vector();

		try {
			packageName = "NHSensor.NHSensorSim.papers.ROCAdaptiveGridTranverseRing.suceessProbability";
			classNames = NHSensorSimPackageUtil.getClasses(packageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classNames;

	}

}
