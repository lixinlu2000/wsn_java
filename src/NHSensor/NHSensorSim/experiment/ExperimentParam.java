package NHSensor.NHSensorSim.experiment;

import java.util.Hashtable;

public class ExperimentParam {
	protected Hashtable paramHashtable = new Hashtable();

	public void setParamHashtable(Hashtable paramHashtable) {
		this.paramHashtable = paramHashtable;
	}

	public Hashtable getParamHashtable() {
		return paramHashtable;
	}
	
	public ExperimentParam() {

	}

}
