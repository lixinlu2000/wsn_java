package NHSensor.NHSensorSim.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParamsFactoryGenerator {
	
	public static void writeCodeToFile(File path, String packageString, String paramClassName, ParamAttribute[] paramAttributes) {
		String fileName = "ParamsFactory.java";
		File file = new File(path, fileName);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(ParamsFactoryGenerator.generate(packageString, paramClassName, paramAttributes));
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String generate(String packageString, String paramClassName, ParamAttribute[] paramAttributes) {
		StringBuffer classCode = new StringBuffer();
		
		//package
		classCode.append("package ");
		classCode.append(packageString);
		classCode.append(";\n\n");
		
		//import
		classCode.append("import java.lang.reflect.Method;\n");
		classCode.append("import java.util.Hashtable;\n");
		classCode.append("import java.util.Vector;\n\n");
		classCode.append("import NHSensor.NHSensorSim.experiment.DoubleSequence;\n");
		classCode.append("import NHSensor.NHSensorSim.experiment.IntSequence;\n");
		classCode.append("import NHSensor.NHSensorSim.experiment.ParamsGenerator;\n\n");
		
		//class declaration
		classCode.append("public class ParamsFactory {\n");
		String paramName,type;
		for(int i=0;i<paramAttributes.length;i++) {
			paramName = paramAttributes[i].getName();
			type = paramAttributes[i].getType();
			classCode.append("\t"+type+" "+paramName+" = "+paramAttributes[i].getDefaultValue()+";\n");
			if(paramAttributes[i].isAdjustable()) {
				classCode.append("\t"+type+" "+paramName+"Begin = "+paramAttributes[i].getBegin()+";\n");
				classCode.append("\t"+type+" "+paramName+"End = "+paramAttributes[i].getEnd()+";\n");
				classCode.append("\t"+type+" "+paramName+"Step = "+paramAttributes[i].getStep()+";\n");
			}
			classCode.append("\n");
		}

		//constructor
		classCode.append("\tpublic ParamsFactory() {\n");
		classCode.append("\n");
		classCode.append("\t}\n\n");

		//variable method
		classCode.append("\tpublic Vector variable(String variableName) throws Exception {\n");
		classCode.append("\t\tString methodName = variableName + \"Variable\";\n");
		classCode.append("\t\tClass classType = this.getClass();\n");
		classCode.append("\t\tMethod method = classType.getMethod(methodName, new Class[] {});\n");
		classCode.append("\t\treturn (Vector) (method.invoke(this, new Object[] {}));\n");
		classCode.append("\t}\n\n");

		//variable mathods
		String attrVariableMethodCode;
		for(int i=0;i<paramAttributes.length;i++) {
			if(!paramAttributes[i].isAdjustable()) continue; 
			attrVariableMethodCode = ParamsFactoryGenerator.generateAttrVariableMethod(paramAttributes[i], 
					paramClassName, paramAttributes);
			classCode.append(attrVariableMethodCode);
			classCode.append("\n");
		}

		
		//end braket
		classCode.append("}\n");
		return classCode.toString();
	}
	
	public static String generateParamAttributeSequence(ParamAttribute pa, boolean adjust) {
		StringBuffer code = new StringBuffer();
		String sequenceClassName = pa.getSequenceClassName();
		String sequenceName = pa.getName()+"s";
		String name = pa.getName();
		
		if(adjust) {
			code.append("\t\t"+sequenceClassName+" "+sequenceName+" = new "+sequenceClassName+"(this."+name+"Begin,\n");
			code.append("\t\t\t"+"this."+name+"End, "+"this."+name+"Step);\n");
		}
		else {
			code.append("\t\t"+sequenceClassName+" "+sequenceName+" = new "+sequenceClassName+"(this."+name+");\n");			
		}

		code.append("\t\tsequences.put(\""+name+"\", "+sequenceName+".getData());\n");
		return code.toString();
	}
	
	public static String generateAttrVariableMethod(ParamAttribute pa, String paramClassName, ParamAttribute[] paramAttributes) {
		StringBuffer code = new StringBuffer();
		
		code.append("\tpublic Vector "+pa.getName()+"Variable() {\n");
		code.append("\t\tClass paramClass = "+paramClassName+".class;\n");
		code.append("\t\tHashtable sequences = new Hashtable();\n\n");
		
		String sequenceDeclaration;
		boolean adjust;
		for(int i=0;i<paramAttributes.length;i++) {
			adjust = (paramAttributes[i]==pa);
			if(paramAttributes[i].isAlwaysAdjustable()) adjust=true;
			sequenceDeclaration = ParamsFactoryGenerator.generateParamAttributeSequence(paramAttributes[i], adjust);
			code.append(sequenceDeclaration);
			code.append("\n");
		}
		
		code.append("\t\tParamsGenerator paramsGenerator;\n");
		code.append("\t\ttry {\n");
		code.append("\t\t\tparamsGenerator = new ParamsGenerator(paramClass, sequences);\n");
		code.append("\t\t\treturn paramsGenerator.getParams();\n");
		code.append("\t\t} catch (Exception e) {\n");
		code.append("\t\t\te.printStackTrace();\n");
		code.append("\t\t}\n");
		code.append("\t\treturn new Vector();\n");
		code.append("\t}\n");

		return code.toString();
	}
	}
