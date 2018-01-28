package NHSensor.NHSensorSim.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.verhas.licensor.License;

public class LicenseTool {
	private  License license;
	private static LicenseTool licenseTool = new LicenseTool();
	private  String licenseFileName = "license/license";
	private  String pubkeyName = "license/pubring.gpg";

	private LicenseTool() {
		license = new License();
		this.initLicense();
	}
	
	public static LicenseTool getLicenseToolInstance() {
		return licenseTool;
	}
	
	private  void initLicense() {
		try {
			license.loadKeyRing(pubkeyName, null);
			license.setLicenseEncodedFromFile(licenseFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean  checkDateAndVersionValidity() {
		if(UTool.isDeveloping) return true;
		SimpleDateFormat simple = new SimpleDateFormat(); 
		simple.applyPattern("yyyy-MM-dd"); 
		Date issueDate;
		Date validDate;
		try {
			issueDate = simple.parse(license.getFeature("issue-date"));
			validDate = simple.parse(license.getFeature("valid-date")); 
		} catch (ParseException e) {
			return false;
		} 		
		Date currentDate = new Date();
		
		if(currentDate.after(issueDate)&&currentDate.before(validDate)) {
			return true;
		}
		else return false;
	}
}
