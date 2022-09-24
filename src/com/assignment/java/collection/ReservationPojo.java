package com.assignment.java.collection;

import java.io.Serializable;
import java.time.LocalDate;

public class ReservationPojo implements Serializable {
	
	private int reservationId;
	private String customerName;
	private String reservationDescription;
	private LocalDate reservationDate;
	private int adult;
	private int child;
	private String status;
	private double subTotalAmount;
	private double discountAmount;
	private double taxAmount;
	private double totalAmount;
	
	public ReservationPojo() {
		
	}
	
	public ReservationPojo(int reservationId, String customerName, String reservationDescription, LocalDate reservationDate,
			int adult, int child, String status, double subTotalAmount, double discountAmount, double taxAmount,
			double totalAmount) {
		
		this.reservationId = reservationId;
		this.customerName = customerName;
		this.reservationDescription = reservationDescription;
		this.reservationDate = reservationDate;
		this.adult = adult;
		this.child = child;
		this.status = status;
		this.subTotalAmount = subTotalAmount;
		this.discountAmount = discountAmount;
		this.taxAmount = taxAmount;
		this.totalAmount = totalAmount;
	}
	
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getReservationId() {
		return reservationId;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerName() {
		return customerName;
	}
	
	public void setReservationDescription(String reservationDescription) {
		this.reservationDescription = reservationDescription;
	}
	public String getReservationDescription() {
		return reservationDescription;
	}
	
	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}
	public LocalDate getReservationDate() {
		return reservationDate;
	}
	
	public void setChild(int child) {
		this.child = child;
	}
	public int getChild() {	
		return child;
	}
	
	public void setAdult(int adult) {
		this.adult = adult;
	}
	public int getAdult() {
		return adult;
	}
	
	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}
	public double getSubTotalAmount() {
		return subTotalAmount;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}

	@Override
	public String toString() {
		
		return "\n"+"-----------------------------------------------------------------------------------------------------------------------------------------------------------"+"\n"
		+"Reservation Id | Reservation Des |    Reservation Date     | NoOfAdults | NoOfChildren |  SubTotal   | Discount  |   Tax    |   Total   |"+"  Status     |"+"\n" 
		+"    "+reservationId+"           "+reservationDescription+"               "+reservationDate+"       "+"        "+adult+"   "+"       "+child+"      "+"       "+subTotalAmount+"        "+discountAmount+"         "+taxAmount+"        "+totalAmount+"     "+status+"\n"+

		"-------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	}


}
