package com.qq.client.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteFile {

	//读文件
	public static String doReadFile(File fileUrl){
		String strFile = "",strSaveFile = "";
		try {
			BufferedReader brFile = new BufferedReader(new FileReader(fileUrl));
			while((strFile = brFile.readLine())!=null){
				strSaveFile =  strSaveFile + "`~~~"+strFile +"~~~`";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strSaveFile;
	}
	
	//写文件
	public static void doWriteFile(File fileUrl,String str){
		BufferedWriter bwFile = null;
		try {
			 bwFile = new BufferedWriter(new FileWriter(fileUrl));
			str = str.replaceAll("`~~~", "").replaceAll("~~~`", "");
			bwFile.write(str);
			bwFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					if(bwFile!=null){
						bwFile.close();
						bwFile = null;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	}
}
