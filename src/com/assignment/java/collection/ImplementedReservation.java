package com.assignment.java.collection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.ListIterator;
import java.util.Scanner;

public class ImplementedReservation implements ReservationInterfaces {

	Scanner scan3 = new Scanner(System.in);
	int adult, child, reservationId;
	double subTotalAmount, discountAmount, taxAmount, totalAmount;
	String status, customerName, reservationDescription;
	LocalDate reservationDate;
//	static ArrayList<ReservationPojo> ararylist = new ArrayList<>();
	ListIterator<ReservationPojo> listiterator = null;

	FileWriter writer = null;
	BufferedWriter bufferwriter = null;

	public void fileOperation() {

		try {

//			String path = "C:\\Users\\Puneeth_SB\\Desktop\\tablereservation.txt";
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));

			ReservationMenu.oos = new ObjectOutputStream(new FileOutputStream(ReservationMenu.path));

			ReservationMenu.oos.writeObject(ReservationMenu.ararylist);
			ReservationMenu.oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fileOperation2() {

		try {
//			String path = "C:\\Users\\Puneeth_SB\\Desktop\\tablereservation.txt";
//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));

			ReservationMenu.ois = new ObjectInputStream(new FileInputStream(ReservationMenu.path));

			ReservationMenu.ararylist = (ArrayList<ReservationPojo>) ReservationMenu.ois.readObject();
			ReservationMenu.ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createAReservation() {

		fileOperation2();// read
		boolean flag = true;
		main: do {
			Scanner scan = new Scanner(System.in);
			boolean name = true;
			CN: while (name) {
				System.out.print("Enter Customer Name: ");
				customerName = scan.nextLine();
				if (isStringOnlyAlphabet(customerName) == true) {
					customerName = customerName;
					break;
				} else if (isStringOnlyAlphabet(customerName) == false) {
					System.out.println("Enter a String");
					continue CN;
				}
			}

			boolean desc = true;
			RD: while (desc) {

				System.out.print("Reservation Description: ");
				reservationDescription = scan.nextLine();
				if (isStringOnlyAlphabet(reservationDescription) == true) {
					this.reservationDescription = reservationDescription;
					break;

				} else {
					System.out.println("Enter a String");
					continue RD;
				}
			}

			reservationDate = LocalDate.now();
			int year = reservationDate.getYear();
			if (DayOfWeek.WEDNESDAY == reservationDate.getDayOfWeek()) {
				discountAmount = 0.25 * subTotalAmount;
			}

			boolean id = true;
			RI: while (id) {

				try {
					System.out.print("Reservation Id: ");
					reservationId = scan.nextInt();
					listiterator = ReservationMenu.ararylist.listIterator();
					inner: while (listiterator.hasNext()) {
						ReservationPojo rp = (ReservationPojo) listiterator.next();
						if (reservationId <= 9999 && reservationId >= 1000) {
							int x = Integer.valueOf(String.valueOf(year) + String.valueOf(reservationId));
							if (rp.getReservationId() != x) {
								this.reservationId = reservationId;
								id = false;
							}

							else {
								System.out.println("ReservationId already Exist....!");
								id = true;
								continue RI;
							}
						}
					}
					if (id) {
						System.out.println("Please enter 4 digit number");
						continue RI;
					}
				}

				catch (Exception e) {
					System.out.println("Enter a number not an String");
					scan.nextLine();
					continue RI;
				}
			}
			Boolean numAdult = true;
			NA: while (numAdult) {

				try {
					System.out.print("No of Adult: ");
					adult = scan.nextInt();
					if (adult > 0 && adult <= 20) {
						numAdult = false;
					} else {
						System.out.println("For every Customer 10 Adults will be limited.. Please enter within range");
					}
				} catch (Exception e1) {
					scan.nextLine();
					System.out.println("Enter a number not an String");
					numAdult = true;
				}
			}
			Boolean numChild = true;
			NC: while (numChild) {

				try {
					System.out.print("No of Children: ");
					child = scan.nextInt();
					if (child >= 0 && child <= 10) {
						numChild = false;
					} else {
						System.out.println("For every Customer 5 Childrens will limited.. Please enter within range");
					}
				} catch (Exception e1) {
					scan.nextLine();
					System.out.println("Enter a number not an String");
					continue NC;
				}
			}

			calculation();
			reservationId = Integer.valueOf(String.valueOf(year) + String.valueOf(reservationId));
			System.out.println();
			System.out.println("Reservation Id: " + reservationId);
			System.out.println("Reservation Date: " + reservationDate);
			System.out.println("Booked Status: " + status);
			System.out.println("Sub Total Amount: " + subTotalAmount + " Rupees");
			System.out.println("Discount Amount: " + discountAmount + " Rupees");
			System.out.println("Tax Amount: " + taxAmount + " Rupees");
			System.out.println("Total Amount: " + totalAmount + "Rupees");
			ReservationMenu.ararylist.add(new ReservationPojo(reservationId, customerName, reservationDescription,
					reservationDate, adult, child, status, subTotalAmount, discountAmount, taxAmount, totalAmount));
			fileOperation();
			System.out.println("Do you want to enter more Reservation details(Y/N)");
			scan.nextLine();
			boolean condition = true;
			while (condition) {
				String str = scan.nextLine();
				if (str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("N")) {
					if (str.equalsIgnoreCase("Y")) {
						System.out.println();
						continue main;
					} else {
						flag = false;
						break;
					}
				} else {
					System.out.println("Enter correct choice:");
					scan.nextLine();
					condition = true;
				}
			}
		} while (flag);

	}

	public void calculation() {

		subTotalAmount = (adult * 500) + (child * 300);
		status = "Booked";
		taxAmount = (subTotalAmount - discountAmount) * 0.05;
		totalAmount = (subTotalAmount - discountAmount) + taxAmount;
		System.out.println("--------------------------Reservation Created Successfully---------------------------");
	}

	public static boolean isStringOnlyAlphabet(String customerName) {

		return ((customerName != null) && (!customerName.equals("")) && (customerName.matches("^[ a-zA-Z]*$")));
	}

	@Override
	public void viewReservation() {

		fileOperation2();
		for (ReservationPojo array : ReservationMenu.ararylist) {
			System.out.println(array);
		}
	}

	@Override
	public void viewByRervationId() {

		Scanner scan1 = new Scanner(System.in);
		fileOperation2();
		boolean found = true;
		VBR: while (found) {

			System.out.println("Enter Reservation Id");
			try {
				int reservationId = scan1.nextInt();
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {

					ReservationPojo rp = (ReservationPojo) listiterator.next();
					if (rp.getReservationId() == reservationId) {
						System.out
								.println("--------------------------------------------------------------------------");
						System.out.println("Reservation Details");
						System.out.println(rp);
						found = false;
					}
				}
				if (found) {
					System.out.println("Record not found....");
					found = false;
				}

			} catch (Exception inputMismatch) {
				scan1.nextLine();
				System.out.println("Enter a 8 digit number not an string...");
				continue VBR;
			}
		}

	}

	@Override
	public void sortReservation() {

		fileOperation2();
		int choice = 0;
		SR: do {

			System.out.println("*********Choose Sort Reservation Property*********");
			System.out.println("1.Sort based on Reservation Id");
			System.out.println("2.Sort based on Reservation Description");
			System.out.println("3.Sort based on Reservation Date");
			System.out.println("4.Sort based on Total Amount");
			System.out.println("5.Exit");
			System.out.println("Choose option to sort");
			try {
				choice = scan3.nextInt();
			} catch (Exception inputMismatch) {
				scan3.nextLine();
				System.out.println("Enter a number not an String...");
				continue SR;
			}
			switch (choice) {

			case 1:
				sortByReservationId();
				break;
			case 2:
				sortByReservationDescription();
				break;
			case 3:
				sortByReservationDate();
				break;
			case 4:
				sortByTotalAmount();
				break;
			case 5:
				return;
			default:
				System.out.println("Enter a valid number");
				break;
			}
		} while (choice != 5);
	}

	public void sortByReservationId() {

		boolean flag = false;
		int choice = 0;
		inner: do {

			System.out.println("*********Choose Sort Order*********");
			System.out.println("1.Ascending");
			System.out.println("2.Descending" + "\n");
			System.out.println("Enter your choice to sort");
			try {
				choice = scan3.nextInt();
			} catch (Exception inputMismatch) {
				scan3.nextLine();
				System.out.println("Enter a number not an string");
				continue inner;
			}
			switch (choice) {

			case 1:
				fileOperation2();
				Collections.sort(ReservationMenu.ararylist, new Comparator<ReservationPojo>() {
					public int compare(ReservationPojo rp1, ReservationPojo rp2) {
						return rp1.getReservationId() - rp2.getReservationId();
					}
				});
				fileOperation();
				System.out.println("---------------------------------------------------------------");
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					System.out.println(listiterator.next());
					System.out.println("-----------------------------------------------------------");
				}
				break;
			case 2:
				fileOperation2();
				Collections.sort(ReservationMenu.ararylist, new Comparator<ReservationPojo>() {
					public int compare(ReservationPojo rp1, ReservationPojo rp2) {
						return rp2.getReservationId() - rp1.getReservationId();
					}
				});
				fileOperation();
				System.out.println("---------------------------------------------------------------");
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					System.out.println(listiterator.next());
					System.out.println("-----------------------------------------------------------");
				}
				break;
			case 3:
				break;
			default:
				System.out.println("Enter a valid choice");
				break;
			}
		} while (flag);
	}

	public void sortByReservationDescription() {

		boolean flag = false;
		int choice = 0;
		do {

			System.out.println("*********Choose Sort Order*********");
			System.out.println("1.Ascending");
			System.out.println("2.Descending");
			System.out.println("3.Exist" + "\n");
			System.out.println("Enter your choice to sort");
			try {
				choice = scan3.nextInt();
			} catch (Exception inputMismatch) {
				scan3.nextLine();
				System.out.println("Enter a number not an String...");
				continue;
			}
			switch (choice) {

			case 1:
				fileOperation2();
				Collections.sort(ReservationMenu.ararylist, new Comparator<ReservationPojo>() {
					public int compare(ReservationPojo rp1, ReservationPojo rp2) {
						return rp1.getReservationDescription().compareTo(rp2.getReservationDescription());
					}
				});
				fileOperation();
				System.out.println("---------------------------------------------------------------");
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					System.out.println(listiterator.next());
					System.out.println("-----------------------------------------------------------");
				}
				break;
			case 2:
				fileOperation2();
				Collections.sort(ReservationMenu.ararylist, new Comparator<ReservationPojo>() {
					public int compare(ReservationPojo rp1, ReservationPojo rp2) {
						return rp2.getReservationDescription().compareTo(rp1.getReservationDescription());
					}
				});
				fileOperation();
				System.out.println("---------------------------------------------------------------");
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					System.out.println(listiterator.next());
					System.out.println("-----------------------------------------------------------");
				}
				break;
			case 3:
				break;
			default:
				System.out.println("Enter valid choice");
				break;
			}
		} while (flag);
	}

	public void sortByReservationDate() {

		boolean flag = false;
		int choice = 0;
		do {

			System.out.println("*********Choose Sort Order*********");
			System.out.println("1.Ascending");
			System.out.println("2.Descending");
			System.out.println("3.Exist" + "\n");
			System.out.println("Enter your choice to sort");
			try {
				choice = scan3.nextInt();
			} catch (Exception inputMismatch) {
				scan3.nextLine();
				System.out.println("Enter a number not an String...");
				continue;
			}
			switch (choice) {
			case 1:
				fileOperation2();
				Collections.sort(ReservationMenu.ararylist, new Comparator<ReservationPojo>() {
					public int compare(ReservationPojo rp1, ReservationPojo rp2) {
						return rp1.getReservationDate().compareTo(rp2.getReservationDate());
					}
				});
				fileOperation();
				System.out.println("---------------------------------------------------------------");
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					System.out.println(listiterator.next());
					System.out.println("-----------------------------------------------------------");
				}
				break;
			case 2:
				fileOperation2();
				Collections.sort(ReservationMenu.ararylist, new Comparator<ReservationPojo>() {
					public int compare(ReservationPojo rp1, ReservationPojo rp2) {
						return rp2.getReservationDate().compareTo(rp1.getReservationDate());
					}
				});
				fileOperation();
				System.out.println("---------------------------------------------------------------");
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					System.out.println(listiterator.next());
					System.out.println("-----------------------------------------------------------");
				}
			case 3:
				break;
			default:
				System.out.println("Enter valid choice");
				break;
			}
		} while (flag);
	}

	public void sortByTotalAmount() {

		boolean flag = false;
		int choice = 0;
		do {

			System.out.println("*********Choose Sort Order*********");
			System.out.println("1.Ascending");
			System.out.println("2.Descending" + "\n");
			System.out.println("Enter your choice to sort");
			try {
				choice = scan3.nextInt();
			} catch (Exception inputMismatch) {
				scan3.nextLine();
				System.out.println("Enter a number not an String...");
				continue;
			}
			switch (choice) {

			case 1:
				fileOperation2();
				Collections.sort(ReservationMenu.ararylist, new Comparator<ReservationPojo>() {
					public int compare(ReservationPojo rp1, ReservationPojo rp2) {
						return (int) (rp1.getTotalAmount() - rp2.getTotalAmount());
					}
				});
				fileOperation();
				System.out.println("---------------------------------------------------------------");
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					System.out.println(listiterator.next());
					System.out.println("-----------------------------------------------------------");
				}
				break;
			case 2:
				fileOperation2();
				Collections.sort(ReservationMenu.ararylist, new Comparator<ReservationPojo>() {
					public int compare(ReservationPojo rp1, ReservationPojo rp2) {
						return (int) (rp2.getTotalAmount() - rp1.getTotalAmount());
					}
				});
				fileOperation();
				System.out.println("---------------------------------------------------------------");
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					System.out.println(listiterator.next());
					System.out.println("-----------------------------------------------------------");
				}
				break;
			case 3:
				break;
			default:
				System.out.println("Enter valid choice");
				break;
			}
		} while (flag);
	}

	@Override
	public void deleteReservationById() {

		Scanner scan1 = new Scanner(System.in);
		fileOperation2();
		boolean flag = true;
		main: while (flag) {

			boolean found = false;
			System.out.print("Enter Reservation Id to Delete");
			try {
				int reservationId = scan1.nextInt();
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					ReservationPojo rp = (ReservationPojo) listiterator.next();
					if (rp.getReservationId() == reservationId) {
						System.out.println("Deleted Successfully");
						listiterator.remove();
						found = true;
					}
				}
				if (!found) {
					System.out.println("Record Not Found");
				} else {
					fileOperation();
				}
				System.out.println("Do you want to enter more Reservation details(Y/N)");
				scan1.nextLine();
				boolean condition = true;
				while (condition) {
					String str = scan1.nextLine();
					if (str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("N")) {
						if (str.equalsIgnoreCase("Y")) {
							System.out.println();
							continue main;
						} else {
							flag = false;
							break;
						}
					} else {
						System.out.println("Enter correct choice:");
						condition = true;
					}
				}
			} catch (InputMismatchException inputMismatch) {
				scan1.nextLine();
				System.out.println("Enter a 8 digit Number");
				continue main;
			}

		}
	}

	@Override
	public void cancelReservationById() {

		fileOperation2();
		boolean flag = true;
		CRI: while (flag) {

			System.out.println("Enter Reservation Id to Cancel");
			try {
				int reservationId = scan3.nextInt();
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {

					ReservationPojo rp = (ReservationPojo) listiterator.next();
					if (rp.getReservationId() == reservationId) {
						if (!(rp.getStatus().equals("Cancelled"))) {
							rp.setStatus("Cancelled");
							flag = false;
							break;
						} else {
							System.out.println("Reservation Already Cancelled");
						}
					}
				}
				if (!flag) {
					System.out.println("Reservation " + reservationId + " " + "Cancelled Successfully");
					System.out.println();
					fileOperation();
				} else {
					System.out.println("Record not found?...");
				}
				System.out.println("Do you want to cancel other reservation (Y/N)");
				scan3.nextLine();
				boolean condition = true;

				while (condition) {

					String str = scan3.nextLine();
					if (str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("N")) {
						if (str.equalsIgnoreCase("Y")) {
							flag = true;
							continue CRI;
						} else {
							return;
						}
					} else {
						System.out.println("Enter correct choice:");
						condition = true;
					}
				}

			} catch (Exception inputMismatch) {
				scan3.nextLine();
				System.out.println("Enter a number with in Range");
				continue CRI;
			}
			flag = false;
		}
	}

	@Override
	public void confirmReservationById() {

		fileOperation2();
		boolean flag = true;
		int count = 0;
		CRI: while (flag) {

			System.out.println("Enter Reservation Id to Confirm");
			try {
				int reservationId = scan3.nextInt();
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {

					ReservationPojo rp = (ReservationPojo) listiterator.next();
					if (rp.getReservationId() == reservationId) {
						if (!((rp.getStatus().equals("Confirmed")) || (rp.getStatus().equals("Cancelled")))) {
							rp.setStatus("Confirmed");
							flag = false;
						} else {
							System.out.println("Reservation Already Confirmed or Cancelled");
							flag = false;
							count = 1;
						}
					}
				}
				if (flag) {
					System.out.println("Record not found?...");
				}
				if (count != 1) {
					System.out.println("Reservation " + reservationId + " " + "Confirmed Successfully");
					System.out.println();
					fileOperation();
				}

				System.out.println("Do you want to Confirm other reservation (Y/N)");
				boolean condition = true;
				while (condition) {

					String str = scan3.nextLine();
					if (str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("N")) {
						if (str.equalsIgnoreCase("Y")) {
							flag = true;
							continue CRI;
						} else {
							return;
						}
					} else {
						System.out.println("Enter correct choice:");
						condition = true;
					}
				}

			} catch (Exception inputMismatch) {
				scan3.nextLine();
				System.out.println("Enter a 8 digit number");
			}
		}
	}

	public void run1() {

		String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String path3 = "C:\\Users\\Puneeth_SB\\Desktop\\Reservation _Report_" + fileSuffix + ".txt";
		File file = new File(path3);
		FileWriter writer = null;
		BufferedWriter bufferwriter = null;
		int choice = 0;
		do {
			System.out.println("******** Choose Report Type*********");
			System.out.println("1.Export All");
			System.out.println("2.Export By Status");
			System.out.println("3.Exit");
			System.out.println("Enter your choice");
			choice = scan3.nextInt();

			switch (choice) {
			case 1:
				fileOperation2();
				try {
					writer = new FileWriter(file);
//					bufferwriter = new BufferedWriter(writer);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				for (ReservationPojo array : ReservationMenu.ararylist) {
					System.out.println(String.valueOf(array));
					try {
						writer.write(String.valueOf(array));
						writer.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
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

					switch (option) {
					case 1:
						fileOperation2();
						try {
							writer = new FileWriter(path3);
//							bufferwriter = new BufferedWriter(writer);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						listiterator = ReservationMenu.ararylist.listIterator();
						while (listiterator.hasNext()) {
							ReservationPojo rp = (ReservationPojo) listiterator.next();
							if (rp.getStatus().equalsIgnoreCase("Booked")) {
								try {
									writer.write(String.valueOf(rp));
									writer.flush();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						break;
					case 2:
						fileOperation2();
						try {
							writer = new FileWriter(path3);
//							bufferwriter = new BufferedWriter(writer);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						listiterator = ReservationMenu.ararylist.listIterator();
						while (listiterator.hasNext()) {
							ReservationPojo rp = (ReservationPojo) listiterator.next();
							if (rp.getStatus().equalsIgnoreCase("Cancelled")) {
								try {
									writer.write(String.valueOf(rp));
									writer.flush();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						break;
					case 3:
						fileOperation2();
						try {
							writer = new FileWriter(path3);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						listiterator = ReservationMenu.ararylist.listIterator();
						while (listiterator.hasNext()) {
							ReservationPojo rp = (ReservationPojo) listiterator.next();
							if (rp.getStatus().equalsIgnoreCase("Confirmed")) {
								try {
									writer.write(String.valueOf(rp));
									;
									writer.flush();
									;
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						break;
					default:
						System.out.println("Invalid choice");
					}
				} while (bl);
				break;
			case 3:
				break;
			default:
				System.out.println("Invalid choice");
			}
		} while (choice != 3);

	}

	public void generateReservationReport1() {

		Thread t1 = new Thread();
		t1.start();
	}

	public void generateReservationReport() {

		int choice = 0;
		do {

			System.out.println("******** Choose Report Type*********");
			System.out.println("1.Export All");
			System.out.println("2.Export By Status");
			System.out.println("3.Exit");
			System.out.println("Enter your choice");
			choice = scan3.nextInt();

			switch (choice) {

			case 1:
				fileOperation2();
				ArrayList<ReservationPojo> list = new ArrayList<ReservationPojo>();
				listiterator = ReservationMenu.ararylist.listIterator();
				while (listiterator.hasNext()) {
					ReservationPojo rp = (ReservationPojo) listiterator.next();
					list.add(rp);
				}
				Thread t1 = new Thread1(list);
				Thread t2 = new Thread2(list);
				t1.start();
				t2.start();
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

					switch (option) {

					case 1:
						fileOperation2();
						ArrayList list1 = new ArrayList();
						listiterator = ReservationMenu.ararylist.listIterator();
						while (listiterator.hasNext()) {
							ReservationPojo rp = (ReservationPojo) listiterator.next();
							if (rp.getStatus().equalsIgnoreCase("Booked")) {
								list1.add(rp);
							}
						}
						Thread1 t3 = new Thread1(list1);
						Thread2 t4 = new Thread2(list1);
						t3.start();
						t4.start();
//					}
						break;

					case 2:
						fileOperation2();
						ArrayList list2 = new ArrayList();
						listiterator = ReservationMenu.ararylist.listIterator();
						while (listiterator.hasNext()) {
							ReservationPojo rp1 = (ReservationPojo) listiterator.next();
							if (rp1.getStatus().equalsIgnoreCase("Cancelled")) {
								list2.add(rp1);
							}
						}
						Thread1 t5 = new Thread1(list2);
						Thread2 t6 = new Thread2(list2);
						t5.start();
						t6.start();
//						}
						break;

					case 3:
						fileOperation2();
						ArrayList list4 = new ArrayList();
						listiterator = ReservationMenu.ararylist.listIterator();
						while (listiterator.hasNext()) {
							ReservationPojo rp = (ReservationPojo) listiterator.next();
							if (rp.getStatus().equalsIgnoreCase("Confirmed")) {
								list4.add(rp);
							}
						}
						Thread1 t7 = new Thread1(list4);
						Thread2 t8 = new Thread2(list4);
						t7.start();
						t8.start();
//						}						
						break;

					default:
						System.out.println("Invalid choice");
					}
				} while (bl);
				break;
			case 3:
				break;
			default:
				System.out.println("Invalid choice");
			}
		} while (choice != 3);

	}

	@Override
	public void exit() {
		System.exit(0);
	}

}
