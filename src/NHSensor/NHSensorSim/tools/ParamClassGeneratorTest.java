package NHSensor.NHSensorSim.tools;

public class ParamClassGeneratorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ParamAttribute[] paramAttributes = {
				new ParamAttribute("networkID", "int", "1", "1", "10", "1", true),
				new ParamAttribute("nodeNum", "int", "160*3", "160*2", "160*10", "160", true),
				new ParamAttribute("queryRegionRate", "double", "0.4", "0.1", "1", "0.1", true),
				new ParamAttribute("gridWidthRate", "double", "10", "0", "10", "1", true),
				new ParamAttribute("radioRange", "double", "10", "10", "50", "10", true),
				new ParamAttribute("queryMessageSize", "int", "50", "50", "400", "50", true),
				new ParamAttribute("senseDataSize", "int", "200", "50", "400", "50", true),
				new ParamAttribute("networkWidth", "double", "100"),
				new ParamAttribute("networkHeight", "double", "100"),
				new ParamAttribute("initialEnergy", "double", "800", "800", "1600", "200", true)
		};
		
		String code = ParamClassGenerator.generate("NHSensor.NHSensorSim.tools", "AllParam", 
				paramAttributes);
		System.out.print(code);

	}

}
