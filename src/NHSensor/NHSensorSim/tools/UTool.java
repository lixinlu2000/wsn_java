package NHSensor.NHSensorSim.tools;

import java.util.Random;

import javax.swing.JOptionPane;


public class UTool {
	static boolean isDeveloping = true;
	
	public static boolean isArch64bit() {
		boolean is64bit = false;
		if (System.getProperty("os.name").contains("Windows")) {
		    is64bit = (System.getenv("ProgramFiles(x86)") != null);
		} else {
		    is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
		}
		return is64bit;
	}
	
	public static void exitWithMessageDialog() {
		JOptionPane.showMessageDialog(null, "请插入加密狗后再运行", "Atos-SensorSim", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0); 
	}

	public static void checkKey() {
		if(isDeveloping) return ;
		com.softkey.jsyunew3 j9=new  com.softkey.jsyunew3();
		String devicePath;
		 //这个用于判断系统中是否存在着加密锁。不需要是指定的加密锁,
		devicePath=j9.FindPort(0);
		if(j9.get_LastError()!=0)
		{
			exitWithMessageDialog();
		}

        String Key = "1234567890ABCDEF1234567890ABCDEF";
        int ret = j9.SetCal_2(Key, devicePath);
        if (ret != 0) {
        	exitWithMessageDialog();
        }

       int nlen;
       Random random = new Random();       
       String inString = random.toString()+random.nextDouble();
       String outString = j9.EncString(inString, devicePath);
       if (j9.get_LastError() != 0) { exitWithMessageDialog(); }

		SoftKey mysoftkey = new SoftKey();
		String Outstring_2;
		Outstring_2=mysoftkey.StrEnc(inString,"1234567890ABCDEF1234567890ABCDEF");
		System.out.println(outString);
		System.out.println(Outstring_2);
		
		if(!outString.equals(Outstring_2)) {
			exitWithMessageDialog();
		}
		
		short address=0;
		short len=26;
		outString = j9.NewReadString(address,len, "DDEA8A37","7CDEB385", devicePath);
		if(j9.get_LastError() !=0){ 
			exitWithMessageDialog();
		}
		if(!outString.equals("南京芯传汇电子科技有限公司")) {
			exitWithMessageDialog();
		}
	}	
}
