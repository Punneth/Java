package com.assignment.java.collection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Thread2 extends Thread {
	
	ArrayList list = new ArrayList();
		public Thread2(ArrayList reservationList) {
		
		for(int i=0;i<reservationList.size()/2;i++) {
			list.add(reservationList.get(i));
		}
	}
	public void run() {
		
		for(int i=list.size()/2; i<list.size();i++) {
			
			String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String path3 = "C:\\Users\\Puneeth_SB\\Desktop\\Reservation _Report_"+fileSuffix+".txt";
			File file = new File(path3);
			FileWriter writer =null;
			try {
				writer = new FileWriter(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for(int j=list.size()/2;j<list.size();j++) {
				try {
					writer.write(String.valueOf(list));
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//			try {
//				writer.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
	}
	
	
}

