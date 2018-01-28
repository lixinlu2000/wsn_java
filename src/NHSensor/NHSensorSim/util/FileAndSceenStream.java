package NHSensor.NHSensorSim.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileAndSceenStream extends PrintStream {
	PrintStream out;

	public FileAndSceenStream(PrintStream out1, PrintStream out2) {
		super(out1);
		this.out = out2;
	}

	public void write(byte buf[], int off, int len) {
		try {
			super.write(buf, off, len);
			out.write(buf, off, len);
		} catch (Exception e) {
		}
	}

	public FileAndSceenStream(String fileName) {
		super(System.out);
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			this.out = out;
			System.setOut(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void flush() {
		super.flush();
		out.flush();
	}

	public static void outputToFile(String name) {
		try {
			File file = new File(name);
			File filePath = file.getParentFile();

			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			PrintStream out = new PrintStream(new FileOutputStream(file));
			PrintStream fileAndSceenStream = new FileAndSceenStream(System.out,
					out);
			System.setOut(fileAndSceenStream);

			PrintStream err = new FileAndSceenStream(System.err, out);
			System.setErr(err);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
