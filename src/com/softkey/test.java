package com.softkey;

import NHSensor.NHSensorSim.tools.SoftKey;

/**
 * This class can take a variable number of parameters on the command
 * line. Program execution begins with the main() method. The class
 * constructor is not invoked unless an object of type 'Class1'
 * created in the main() method.
 */
public class test
{
	/**
	 * The main entry point for the application. 
	 *
	 * @param args Array of parameters passed to the application
	 * via the command line.
	 */
	public static void main (String[] args)
	{
		// TODO: Add initialization code here
		com.softkey.jsyunew3 j9=new  com.softkey.jsyunew3();
		String DevicePath;
		 //��������ж�ϵͳ���Ƿ�����ż�����������Ҫ��ָ���ļ�����,
		DevicePath=j9.FindPort(0);
		if(j9.get_LastError()!=0)
		{
		System.out.println("δ�ҵ�������,�������������ٽ��в�����");
		return ;
		}

		/*'���ڷ��ؼ��ܹ���ID�ţ����ܹ���ID����������������ɡ�
        '��ʾ1����ID�����ǿ�����Ψһ��ÿһ�Ѷ���Ψһ�ģ�������Ψһ��ָͬһ��������ͬ����ͬ�Ŀ����̲���ͬ��ÿһ����Ψһ�ģ���ָÿһ������ID������ͬ
        '��ʾ2�������ÿһ�Ѷ���Ψһ�ģ���Ҫ�ڶ���ʱ��֪����)
        '��ʾ3: IDΨһ��ָ����IDת��Ϊ16�����ַ�����������������Ψһ��*/
		int ID1, ID2;
		ID1 = (int)j9.GetID_1(DevicePath);
		if(j9.get_LastError() !=0){System.out.println("����ID1����");return ;}
		ID2 =  (int)j9.GetID_2(DevicePath);
		if(j9.get_LastError() !=0){System.out.println("����ID1����");return ;}
		System.out.println("�ѳɹ���������ID��:"+Integer.toHexString(ID1)+Integer.toHexString(ID2));
		
		{
			//���ڷ��ؼ��ܹ��İ汾��
			int version ;
			version = j9.GetVersion(DevicePath);
			if(j9.get_LastError() !=0){ System.out.println("���ذ汾�Ŵ���");return ;}
			System.out.println("�ѳɹ��������İ汾��:"+Integer.toString(version));
		}
		{
			///���������ָ���ĵ�ַ��д��ָ�������ݣ�ʹ��Ĭ�ϵ�д����
			short address=0;//Ҫд��ĵ�ַΪ0
			short len=20;//Ҫд�������Ϊ20;
			short [] buf=new short[20];short Data=0;
			for(int i=0;i<20;i++)
			{
				Data++;
				j9.SetBuf(i,Data);
			}

			if(j9.YWriteEx( address,len, "FFFFFFFF","FFFFFFFF",  DevicePath) !=0){ System.out.println("д����������ʧ��");return ;}
			System.out.println("д���������ݳɹ���");
			
		}
		{
			//�Ӽ�������ָ���ĵ�ַ�ж�ȡһ������,ʹ��Ĭ�ϵĶ�����
			short address=0;//Ҫ��ȡ�������ڼ������д������ʼ��ַ
			short len=20;//Ҫ��ȡ20���ֽڵ�����
			if(j9.YReadEx(address,len, "FFFFFFFF","FFFFFFFF", DevicePath) !=0){ System.out.println("��ȡ������ʧ��");return ;}
			short [] buf=new short[20];
			for(int i=0;i<10;i++)
			{
				buf[i]=j9.GetBuf(i);
			}
			System.out.println("��ȡ�������ݳɹ���");
		}
		
		{
			//д���ַ�������������,ʹ��Ĭ�ϵ�д����
			short address=0;//Ҫд��ĵ�ַΪ0
			int WriteLen=j9.NewWriteString("������", address, "FFFFFFFF","FFFFFFFF",  DevicePath);//WriteLen����д����ַ����ĳ��ȣ����ֽ�������
			if(j9.get_LastError() !=0){ System.out.println("д�ַ���ʧ��");return ;}
			System.out.println("д��ɹ���д����ַ����ĳ����ǣ�"+Integer.toString(WriteLen));
			
		}
		{
			//��ȡ�ַ�����,ʹ��Ĭ�ϵĶ�����
			String outString;
			short address=0;//Ҫ��ȡ���ַ����ڼ������д������ʼ��ַ
			short len=6;//ע�������6�ǳ��ȣ�Ҫ��д����ַ����ĳ�����ͬ
			outString = j9.NewReadString(address,len, "FFFFFFFF","FFFFFFFF", DevicePath);
			if(j9.get_LastError() !=0){ 
				System.out.println("���ַ���ʧ��");return ;
			}
			System.out.println("���ַ����ɹ���"+outString);

		}
		
		{
			//д���ַ��������ȣ�,ʹ��Ĭ�ϵĶ�����
			int ret;
            int nlen;
            String InString;
            byte [] buf = new byte[1];
            InString = "������";
           

            //д���ַ�������ַ1
            nlen = j9.NewWriteString(InString, (short)1, "ffffffff", "ffffffff", DevicePath);
            if( j9.get_LastError() !=0 )
            {
                System.out.println("д���ַ���(������)����") ;return  ;
            }
           //д���ַ����ĳ��ȵ���ַ0
            buf[0] = (byte)nlen;
            j9.SetBuf(0,buf[0]);
            ret = j9.YWriteEx( (short)0, (short)1, "ffffffff", "ffffffff", DevicePath);
            if( ret != 0 )
                System.out.println("д���ַ������ȴ��󡣴����룺" );
            else
                System.out.println("д���ַ���(������)�ɹ�");
		}
		
		{
			int ret;
            short nlen;
            byte [] buf = new byte[1];
            String outString;
            //�ȴӵ�ַ0������ǰд����ַ����ĳ���
            ret = j9.YReadEx((short)0, (short)1, "ffffffff", "ffffffff", DevicePath);
            nlen =j9.GetBuf(0);
            if( ret != 0 )
            {
                System.out.println("��ȡ�ַ������ȴ��󡣴����룺" );return ;
            }
          //�ٶ�ȡ��Ӧ���ȵ��ַ���
            outString = j9.NewReadString((short)1, nlen, "ffffffff", "ffffffff", DevicePath);
            if( j9.get_LastError() != 0 )
                System.out.println("��ȡ�ַ���(������)���󡣴����룺" );
            else
                System.out.println("�ѳɹ���ȡ�ַ���(������)��" + outString);
		}
		
        {
            //������ǿ�㷨һ��Կ
            //ע�⣺��ԿΪ������32����0-F�ַ������磺1234567890ABCDEF1234567890ABCDEF,����32���ַ��ģ�ϵͳ���Զ��ں��油0
             int ret;
             String Key;
             Key = "1234567890ABCDEF1234567890ABCDEF";
             ret = j9.SetCal_2(Key, DevicePath);
             if (ret != 0) { System.out.println("������ǿ�㷨��Կ����"); return; }
             System.out.println("�ѳɹ���������ǿ�㷨��Կһ");
         }
        
        {
            //'ʹ����ǿ�㷨һ���ַ������м���
            int ret,nlen;
            String InString;
            String outString;
            InString = "������";
            outString = j9.EncString(InString, DevicePath);
            if (j9.get_LastError() != 0) { System.out.println("�����ַ������ִ���"); return; }
            System.out.println("�ѳɹ�ʹ����ǿ�㷨һ���ַ������м��ܣ����ܺ���ַ���Ϊ��" + outString);
	        //�Ƽ����ܷ�����������������������������㣬ͬʱ�ڳ����ж�ʹ�ô�����ͬ���ļ������㣬Ȼ����бȽ��жϡ�
			//'�����Ƕ�Ӧ�ļ��ܴ��룬���Բο�ʹ��
			SoftKey mysoftkey = new SoftKey();
			String Outstring_2;
			InString="������";
			outString=mysoftkey.StrEnc(InString,"1234567890ABCDEF1234567890ABCDEF");
			Outstring_2=mysoftkey.StrDec(outString,"1234567890ABCDEF1234567890ABCDEF");
			System.out.println(outString);
			System.out.println(Outstring_2);
        }
        
        {
            //ʹ����ǿ�㷨һ�Զ��������ݽ��м���
            int ret,n;
            byte [] OutBuf=new byte[8];
            short Data=0;
            for(n=0;n<8;n++)
            {
            	j9.SetEncBuf(n, Data);
            	Data++;
            }
            ret = j9.Cal(DevicePath);
            if (ret != 0) { System.out.println("��������ʱʧ��"); return; }
            for(n=0;n<8;n++)
            {
            	OutBuf[n]=(byte)j9.GetEncBuf(n);

            }
            System.out.println("�ѳɹ�ʹ����ǿ�㷨һ�Զ��������ݽ����˼���");
            //�Ƽ����ܷ�����������������������������㣬ͬʱ�ڳ����ж�ʹ�ô�����ͬ���ļ������㣬Ȼ����бȽ��жϡ�
            //�����Ƕ�Ӧ�ļ��ܴ��룬���Բο�ʹ��
            /*SoftKey mysoftkey = new SoftKey();
			byte[] indata = new byte[]{ 0,1,2,3,4,5,6,7};
			byte [] outdata=new byte[8];byte [] outdata_2=new byte[8];
			mysoftkey.EnCode(indata,outdata,"1234567890ABCDEF1234567890ABCDEF");
			mysoftkey.DeCode(outdata,outdata_2,"1234567890ABCDEF1234567890ABCDEF");
			System.out.println("���ܳɹ�");*/
        }
		
		
	}
}
