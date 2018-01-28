package NHSensor.NHSensorSim.test;

import java.util.Vector;

import voronoi.Voronoi;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWComplexity;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

public class VoronoiTest {

	/**.3
	 * @param args
	 */
	public static void main(String[] args) {
		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		Network network = sensorSim.getNetwork();
		Vector nodes = network.getNodes();
		
		double[][] poss = new double[4][2];
		poss[0][0] = 1;
		poss[0][1] = 1;
		poss[1][0] = 2;
		poss[1][1] = 2;
		poss[2][0] = 3;
		poss[2][1] = 6;
		poss[3][0] = 5;
		poss[3][1] = 2;
		
		MWNumericArray mwArray = MWNumericArray.newInstance(new int[]{4,2}, MWClassID.DOUBLE, MWComplexity.REAL);
		mwArray.set(new int[]{1,1}, 1);
		mwArray.set(new int[]{1,2}, 1);
		mwArray.set(new int[]{2,1}, 2);
		mwArray.set(new int[]{2,2}, 2);
		mwArray.set(new int[]{3,1}, 3);
		mwArray.set(new int[]{3,2}, 6);
		mwArray.set(new int[]{4,1}, 5);
		mwArray.set(new int[]{4,2}, 2);

		Voronoi voronoi;
		Object[] results;
		try {
			voronoi = new Voronoi();
			results = voronoi.vinoroiCellArea(1, mwArray);
			System.out.println(results);
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
