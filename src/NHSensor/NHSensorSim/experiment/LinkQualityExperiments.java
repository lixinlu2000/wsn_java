package NHSensor.NHSensorSim.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LinkQualityExperiments {
	public static Vector readExperimentsFromFile(File file) {
		try {
			Vector experiments = new Vector();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String curLine = new String();
			while ((curLine = locateExperiment(br)) != null) {
				LinkQualityExperiment experiment = new LinkQualityExperiment();
				experiment.readExperiment(br, curLine);
				experiments.add(experiment);
			}
			fr.close();
			return experiments;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String locateExperiment(BufferedReader br) {
		try {
			String lineData;
			lineData = br.readLine();
			while (lineData != null && !lineData.startsWith("EXPERIMENT")) {
				lineData = br.readLine();
			}
			if (lineData != null)
				if (lineData.startsWith("EXPERIMENT")) {
					return lineData;
				}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Vector experiments = LinkQualityExperiments
				.readExperimentsFromFile(new File(
						"C:/Documents and Settings/Administrator/×ÀÃæ/NHSensorSim/NHSensorSim/dataset/dataset/indoor-ceiling-array/2003_05_05/datafile-mica1-lab-power-pktsize50.txt"));
		// for(int i=0;i<experiments.size();i++)
		// System.out.println(experiments.get(i).toString());
	}
}
