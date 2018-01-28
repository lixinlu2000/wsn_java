package NHSensor.NHSensorSim.sdk;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ParamClassGenerator {
	
	public static void writeCodeToFile(File path, String packageString, String className, ParamAttribute[] paramAttributes) {
		String fileName = className+".java";
		File file = new File(path, fileName);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(ParamClassGenerator.generate(packageString, className, paramAttributes));
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String generate(String packageString, String className, ParamAttribute[] paramAttributes) {
		StringBuffer classCode = new StringBuffer();
		
		//package
		classCode.append("package ");
		classCode.append(packageString);
		classCode.append(";\n\n");
		
		//import
		classCode.append("import NHSensor.NHSensorSim.experiment.ExperimentParam;\n");
		classCode.append("import NHSensor.NHSensorSim.util.ReflectUtil;\n\n");
		
		//class declaration
		classCode.append("public class "+className+" extends ExperimentParam {\n");
		
		//default constructor
		classCode.append("\tpublic "+className+"() {\n\n");
		classCode.append("\t}\n\n");

		//set get code
		for(int i=0;i<paramAttributes.length;i++) {
			classCode.append(ParamClassGenerator.generateSetMethodCode(paramAttributes[i]));
			classCode.append("\n");
			classCode.append(ParamClassGenerator.generateGetMethodCode(paramAttributes[i]));
			classCode.append("\n");
		}
		
		//toString method
		classCode.append("\tpublic String toString() {\n");
		classCode.append("\t\ttry {\n");
		classCode.append("\t\t\treturn ReflectUtil.objectToString(this);\n");
		classCode.append("\t\t} catch (Exception e) {\n");
		classCode.append("\t\t\te.printStackTrace();\n");
		classCode.append("\t\t\treturn e.toString();\n");
		classCode.append("\t\t}\n");
		classCode.append("\t}\n\n");
		
		//fromString method
		classCode.append("\tpublic static "+className+" fromString(String str) throws Exception {\n");
		classCode.append("\t\treturn ("+className+") "+"ReflectUtil.stringToObject(str, "+className+".class);\n");
		classCode.append("\t}\n");
		
		//end braket
		classCode.append("}\n");
		return classCode.toString();
	}
	
	public static String generateSetMethodCode(ParamAttribute paramAttribute) {
		StringBuffer code = new StringBuffer();
		String paramName = paramAttribute.getName();
		String firstLetter = paramName.substring(0, 1).toUpperCase();
		String setMethodName = "set" + firstLetter + paramName.substring(1);
		code.append("\tpublic void "+setMethodName+"("+paramAttribute.getType()+" "+paramName+") {\n");
		code.append("\t\tthis.getParamHashtable().put(\""+paramName+"\", "+paramName+");\n");
		code.append("\t}\n");
		return code.toString();
	}
	
	public static String generateGetMethodCode(ParamAttribute paramAttribute) {
		StringBuffer code = new StringBuffer();
		String paramName = paramAttribute.getName();
		String firstLetter = paramName.substring(0, 1).toUpperCase();
		String getMethodName = "get" + firstLetter + paramName.substring(1);
		code.append("\tpublic "+paramAttribute.getType()+" "+getMethodName+"() {\n");
		
		if(paramAttribute.getType().equals("int")) {
			code.append("\t\treturn ((Integer)(this.getParamHashtable().get(\""+paramName+"\"))).intValue();\n");
		}
		else if(paramAttribute.getType().equals("double")) {
			code.append("\t\treturn ((Double)(this.getParamHashtable().get(\""+paramName+"\"))).doubleValue();\n");
		}
		code.append("\t}\n");
		return code.toString();
	}

}
