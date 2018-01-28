package NHSensor.NHSensorSim.test;

public class GetSystemInfo {
	static void suckOsInfo() {

		// Get OS Name (Like: Windows 7, Windows 8, Windows XP, etc.)
		String osVersion = System.getProperty("os.name");
		System.out.print(osVersion);

		String pFilesX86 = System.getenv("ProgramFiles(X86)");
		if (pFilesX86 != (null)) {
			// Put here the code to execute when Windows x64 are Detected
			System.out.println(" 64bit");
		} else {
			// Put here the code to execute when Windows x32 are Detected
			System.out.println(" 32bit");
		}

		System.out.println("Now getSystemInfo class will EXIT");
		System.exit(0);

	}
	
	public static void main(String[] args) {
		suckOsInfo();
	}

}
