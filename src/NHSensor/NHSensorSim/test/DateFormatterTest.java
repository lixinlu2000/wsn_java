package NHSensor.NHSensorSim.test;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * SimpleDateFormat example: Convert from a Date to a formatted String
 * 
 * Get today's date, then convert it to a String, using the date format we
 * specify.
 */
public class DateFormatterTest {
	public static void main(String[] args) {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		String folderName = formatter.format(today);

		System.out.println("Folder Name = " + folderName);
	}
}