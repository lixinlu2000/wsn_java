package com.softkey;

import java.io.File;

import NHSensor.NHSensorSim.tools.UTool;

public class jsyunew3
{
	//�����İ汾
	public static native int GetVersion(String InPath);
	//��������չ�汾
	public static native int GetVersionEx(String InPath);
	//������ID
	public static native long GetID_1(String InPath);
	public static native long GetID_2(String InPath);
	//�������Ĵ�����Ϣ
	public static native long get_LastError();
	//���Ҽ�����
	public static native String FindPort(int start);
	//���ö�����
	public static native int SetReadPassword(String W_hkey, String W_lkey, String new_hkey, String new_lkey, String InPath);
	//����д����
	public static native int SetWritePassword(String W_hkey, String W_lkey, String new_hkey, String new_lkey, String InPath);
	//�Ӽ������ж�ȡһ���ֽ�
	public static native int YReadEx(short Address, short len, String HKey, String LKey, String InPath);
	//�Ӽ������ж�ȡһ���ֽڣ�һ�㲻ʹ��
	public static native int YRead(short Address, String HKey, String LKey, String InPath);
	//�ӻ������л������
	public static native short GetBuf(int pos);
	//дһ���ֽڵ���������
	public static native int YWriteEx(short Address, short len, String HKey, String LKey, String InPath);
	//дһ���ֽڵ��������У�һ�㲻ʹ��
	public static native int YWrite(short inData, short Address, String HKey, String LKey, String InPath);
	//����Ҫд��Ļ�����������
	public static native int SetBuf(int pos, short Data);
	//�Ӽ������ж��ַ���-��
	public static native String NewReadString(int Address, int len, String HKey, String LKey, String InPath);
	//д�ַ�������������-��
	public static native int NewWriteString(String InString, int Address, String HKey, String LKey, String InPath);
	//���ݾɵĶ�д�ַ�������������ʹ��
	public static native String YReadString(short Address, short len, String HKey, String LKey,String InPath);
	public static native int YWriteString(String InString, short Address, String HKey, String LKey,String InPath);
	//'������ǿ�㷨��Կһ
	public static native int SetCal_2(String Key, String InPath);
	//ʹ����ǿ�㷨һ���ַ������м���
	public static native String EncString(String InString, String InPath);
	//ʹ����ǿ�㷨һ�Զ��������ݽ��м���
	public static native int Cal(String InPath);
	//ʹ����ǿ�㷨���ַ������н���
	public static native String DecString(String InString, String Key);
	//����Ҫ���ܵĻ�����������
	public static native int SetEncBuf(int pos, short Data);
	//�ӻ������л�ȡ���ܺ������
	public static native short GetEncBuf(int pos);

	//***��ʼ������������
	public static native int ReSet(String Path);

	static
     {		
		if(UTool.isArch64bit()) {
			System.load(new File("./external-lib/jsyunew3-x64.dll").getAbsolutePath());
		}
		else {
			System.load(new File("./external-lib/jsyunew3.dll").getAbsolutePath());
			
		}
     }
 
}