package com.databaseproject;

import java.sql.Date;

/**
 * Model class representing one row of the Reservation table.
 */
public class Reservation {
    private final int pkReservationID;
    private final int fkEmpID;
    private final int fkCustomerID;
    private final int fkRoomNum;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final double price;

    public Reservation(int pkReservationID, int fkEmpID, int fkCustomerID, int fkRoomNum,
                       Date checkInDate, Date checkOutDate, double price) {
        this.pkReservationID = pkReservationID;
        this.fkEmpID = fkEmpID;
        this.fkCustomerID = fkCustomerID;
        this.fkRoomNum = fkRoomNum;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.price = price;
    }

    public int getPkReservationID()   { return pkReservationID; }
    public int getFkEmpID()           { return fkEmpID; }
    public int getFkCustomerID()      { return fkCustomerID; }
    public int getFkRoomNum()         { return fkRoomNum; }
    public Date getCheckInDate()      { return checkInDate; }
    public Date getCheckOutDate()     { return checkOutDate; }
    public double getPrice()          { return price; }

    @Override
    public String toString() {
        return "Reservation " + pkReservationID
                + " | Employee " + fkEmpID
                + " | Customer " + fkCustomerID
                + " | Room " + fkRoomNum
                + " | " + checkInDate + " to " + checkOutDate
                + " | $" + price;
    }
}