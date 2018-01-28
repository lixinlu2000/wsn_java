package NHSensor.NHSensorSim.exception;

public class HasNoVariableException extends SensornetBaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7381609079876653805L;
	/**
	 * 
	 */
	private String variableName;

	public HasNoVariableException(String variableName) {
		super();
		this.variableName = variableName;
	}

	public HasNoVariableException() {
		// TODO Auto-generated constructor stub
	}

}
