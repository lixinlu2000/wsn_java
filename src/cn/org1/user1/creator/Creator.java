package cn.org1.user1.creator;

import java.io.File;

import NHSensor.NHSensorSim.sdk.ParamAttribute;
import NHSensor.NHSensorSim.sdk.ParamClassGenerator;
import NHSensor.NHSensorSim.sdk.ParamsFactoryGenerator;
import NHSensor.NHSensorSim.util.FileUtil;
import NHSensor.NHSensorSim.util.Util;

public class Creator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParamAttribute[] paramAttributes = {
				new ParamAttribute("networkID", "int", "1", "1", "10", "1", true, true),
				new ParamAttribute("nodeNum", "int", "160*3", "160*2", "160*10", "160", true),
				new ParamAttribute("queryRegionRate", "double", "0.4", "0.1", "1", "0.1", true),
				new ParamAttribute("gridWidthRate", "double", "10", "0", "10", "1", true),
				new ParamAttribute("radioRange", "double", "10", "10", "50", "10", true),
				new ParamAttribute("queryMessageSize", "int", "50", "50", "400", "50", true),
				new ParamAttribute("answerMessageSize", "int", "200", "50", "400", "50", true),
				new ParamAttribute("networkWidth", "double", "100"),
				new ParamAttribute("networkHeight", "double", "100"),
				new ParamAttribute("initialEnergy", "double", "1500", "1500", "5000", "500", true)
		};
		String paramClassName = "AlgParam";
		String packageName = Util.getParentPackageName(Creator.class.getPackage().getName());
		
		
		File path = FileUtil.packageNameToFile("./src/", Creator.class.getPackage().getName());
		File parentPath = path.getParentFile();

		ParamClassGenerator.writeCodeToFile(parentPath, packageName, paramClassName, 
				paramAttributes);
		ParamsFactoryGenerator.writeCodeToFile(parentPath,packageName, paramClassName, 
				paramAttributes);

		System.out.println("End...........");
		
	}

}
