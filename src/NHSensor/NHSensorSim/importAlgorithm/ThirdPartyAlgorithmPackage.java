package NHSensor.NHSensorSim.importAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;

public class ThirdPartyAlgorithmPackage {
	File algorithmConfigFile;
	String algorithmConfigFileDirectory;
	AlgorithmProperty algorithmProperty;
	final String classDir = "classes"; 
	String classDirEx = '/'+classDir+'/';
	boolean isValidPackage = false;
	
	protected void checkIsValidPackage() {
		this.isValidPackage = true;
	}
	
	protected boolean hasAlgorithmClass() {
		String className = this.algorithmProperty.getClassFullName();
		String classFile = this.algorithmConfigFileDirectory + classDirEx + className.replaceAll(".", "/") + ".class";
		File file = new File(classFile);
		return file.exists();
	}

	protected boolean hasAlgorithmDeomClass() {
		String className = this.algorithmProperty.getDemoClassFullName();
		String classFile = this.algorithmConfigFileDirectory + classDirEx + className.replaceAll(".", "/") + ".class";
		File file = new File(classFile);
		return file.exists();
	}

	protected boolean hasExperimentClass() {
		String className = this.algorithmProperty.getExperimentClassFullName();
		String classFile = this.algorithmConfigFileDirectory + classDirEx + className.replaceAll(".", "/") + ".class";
		File file = new File(classFile);
		return file.exists();
	}

	protected boolean hasExperimentParamClass() {
		String className = this.algorithmProperty.getExperimentParamClassFullName();
		String classFile = this.algorithmConfigFileDirectory + classDirEx + className.replaceAll(".", "/") + ".class";
		File file = new File(classFile);
		return file.exists();
	}

	public ThirdPartyAlgorithmPackage(File algorithmConfigFile) throws Exception{
		this.algorithmConfigFile = algorithmConfigFile;
		this.algorithmConfigFileDirectory = algorithmConfigFile.getAbsolutePath();
		algorithmProperty = AlgorithmProperty.read(this.algorithmConfigFile);
		this.checkIsValidPackage();
		if(!this.isValidPackage()) throw new Exception();
		
	}

	public boolean isValidPackage() {
		return isValidPackage;
	}
	
	public void copyToPath(File destPath) throws IOException {
		File srcDir = algorithmConfigFile.getParentFile();
		String srcDirName = srcDir.getName();
		File destDir = new File(destPath, srcDirName);
		ThirdPartyAlgorithmPackage.copyFolder(srcDir, destDir);
	}
	
	public void copyClassesToPath(File destPath) throws IOException {
		File srcDir = new File(algorithmConfigFile.getParentFile(), classDir);
		ThirdPartyAlgorithmPackage.copyFolder(srcDir, destPath);
	}
	
	public static void copyFolder(File srcFolderPath, File destFolderPath)
			throws IOException {

		if (!srcFolderPath.isDirectory()) {
			InputStream in = new FileInputStream(srcFolderPath);
			OutputStream out = new FileOutputStream(destFolderPath);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();

		} else {
			if (!destFolderPath.exists()) {
				destFolderPath.mkdir();
			}

			String folder_contents[] = srcFolderPath.list();
			for (String file : folder_contents) {
				File srcFile = new File(srcFolderPath, file);
				File destFile = new File(destFolderPath, file);
				copyFolder(srcFile, destFile);
			}

		}
	}
	
	public boolean isUserAlgorithm() {
		return !this.algorithmProperty.isSystemAlgorithm();
	}
}
