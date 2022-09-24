package com.assignment.java.collection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Methods {
		
	Scanner scan3 = new Scanner(System.in);
	ImplementedReservation implemented = new ImplementedReservation();
	String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	String path3 = "C:\\Users\\Puneeth_SB\\Desktop\\Reservation _Report_"+fileSuffix+".txt";
//	String path3 = "C:\\Users\\Puneeth_SB\\Desktop\\Reservation _Report.txt";
	File file = new File(path3);
	FileWriter writer =null;
	BufferedWriter out = null;
	
	
	public void logic() {
		int choice = 0;
		do {
			System.out.println("******** Choose Report Type*********"); 
			System.out.println("1.Export All");
			System.out.println("2.Export By Status");
			System.out.println("3.Exit");
			System.out.println("Enter your choice");
			String str;
			choice = scan3.nextInt();
			switch(choice) {
			case 1:
				str = "All";
					status(str);
				break;
			case 2:
				boolean bl = false;
				int option = 0;
				do {
					System.out.println("******** Choose Status*********");
					System.out.println("1.Booked");
					System.out.println("2.Cancelled");
					System.out.println("3.Confirmed");
					System.out.println("Choose an option based on Status");
					option = scan3.nextInt();
					switch(option) {
					case 1:
						str = "Confirm";
							status(str);
						break;
					case 2:
						str = "Cancelled";
							status(str);
						break;
					case 3:
						str = "Booked";
							status(str);
						break;
					}
				}
				while(bl);
				break;
			case 3:
				break;
			}
		}while(choice!=3);

	}
		
	public void status(String str){
		implemented.fileOperation2();
		try {
			writer = new FileWriter(path3);
//		implemented.listiterator = implemented.ararylist.listIterator();
		while(implemented.listiterator.hasNext()) {
			ReservationPojo rp = (ReservationPojo)implemented.listiterator.next();
			if(rp.getStatus().equalsIgnoreCase("Confirmed")) {
				try {
					writer.write(String.valueOf(rp));
					writer.flush();;
				} catch (IOException e) {
					e.printStackTrace();
			}
		}
		}
		writer.close();
		}
		 catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}



