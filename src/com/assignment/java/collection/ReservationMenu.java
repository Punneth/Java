package com.assignment.java.collection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class ReservationMenu {
	
	static ArrayList<ReservationPojo> ararylist = new ArrayList<ReservationPojo>();
	static String path = "C:\\Users\\Puneeth_SB\\Desktop\\tablereservation.txt";
	static ObjectOutputStream oos =  null;		
	static ObjectInputStream ois = null;
	
	public static void main(String[] args)  throws IOException,FileNotFoundException{
		
		Scanner scan = new Scanner(System.in);
		ImplementedReservation reservation = new ImplementedReservation();
		int choice = 0;
		
		outer:do {
			
			System.out.println("************Table Reservation System*************");

			System.out.println("1. Create a Reservation.");
			System.out.println("2. View Reservations.");
			System.out.println("3. View By Reservation Id.");
			System.out.println("4. Sort Reservation.");
			System.out.println("5. Delete Reservation by Id.");
			System.out.println("6. Cancel Reservation by Id.");
			System.out.println("7. Confirm Reservation by Id.");
			System.out.println("8. Generate Reservation Report.");
			System.out.println("9. Exit.");
			System.out.print("Enter your choice:");
			System.out.println();
			try {
				choice = scan.nextInt();
			}
			catch(Exception e) {
				System.out.println("Enter a number: ");
				scan.nextLine();
				continue outer;	
			}
			switch(choice) {
			case 1:
				System.out.println("Create Reservation: ");
				reservation.createAReservation();
				break;
			case 2:
				System.out.println("View Reservation: ");
				reservation.viewReservation();
				break;
			case 3:
				System.out.println("View Reservation by Id: ");
				reservation.viewByRervationId();
				break;
			case 4:
				System.out.println("Sort Reservation: ");
				reservation.sortReservation();
				break;
			case 5:
				System.out.println("Delete Reservation by Id: ");
				reservation.deleteReservationById();
				break;
			case 6:
				System.out.println("Cancel Reservation by Id: ");
				reservation.cancelReservationById();
				break;
			case 7:
				System.out.println("Confirm Reservation by Id: ");
				reservation.confirmReservationById();
				break;
			case 8:
				System.out.println("Generate Reservation Report: ");
				reservation.generateReservationReport();
				break;
			case 9:
				System.out.println("Exit");
				break;
			default:
				System.out.println("Enter a number within the range");
			}
				
		}while(choice!=9);
	}
}
 
