package com.databaseproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the Reservation table: adding and deleting reservations,
 * looking up a customer's stays, and totaling what a customer has been charged.
 * ReservationDAO
 */
public class ReservationDAO {

    /**
     * Adds a reservation to the Reservation table. The reservation ID is not
     * inserted, so the database assigns it (this requires pkReservationID to be
     * an AutoNumber column). The price is taken from the Reservation object as-is.
     *
     * @param r     the reservation to add (its ID value is ignored)
     * @return true if the row was inserted, otherwise false
     * @throws SQLException if the database cannot be reached or the insert fails
     */
    public boolean addReservation(Reservation r) throws SQLException {
        String query = "INSERT INTO Reservation "
                     + "(fkEmpID, fkCustomerID, fkRoomNum, CheckInDate, CheckOutDate, Price) "
                     + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, r.getFkEmpID());
            ps.setInt(2, r.getFkCustomerID());
            ps.setInt(3, r.getFkRoomNum());
            ps.setDate(4, r.getCheckInDate());     // already a java.sql.Date
            ps.setDate(5, r.getCheckOutDate());
            ps.setDouble(6, r.getPrice());

            return ps.executeUpdate() == 1;        // true if one row was inserted
        }
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param reservationID     ID of the reservation to delete
     * @return true if a row was deleted, otherwise false (no reservation with that ID)
     * @throws SQLException if the database cannot be reached or the delete fails
     */
    public boolean deleteReservation(int reservationID) throws SQLException {
        String query = "DELETE FROM Reservation WHERE pkReservationID = ?";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, reservationID);

            return ps.executeUpdate() == 1;        // true if one row was deleted
        }
    }

    /**
     * Returns every reservation belonging to a customer, ordered by check-in date.
     *
     * @param customerID    ID of the customer whose reservations you want
     * @return list of that customer's reservations (empty list if none, never null)
     * @throws SQLException if the database cannot be reached or the query fails
     */
    public List<Reservation> getStayDates(int customerID) throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();

        String query = "SELECT pkReservationID, fkEmpID, fkCustomerID, fkRoomNum, CheckInDate, CheckOutDate, Price "
                     + "FROM Reservation "
                     + "WHERE fkCustomerID = ? "
                     + "ORDER BY CheckInDate";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservationList.add(new Reservation(
                            rs.getInt("pkReservationID"),
                            rs.getInt("fkEmpID"),
                            rs.getInt("fkCustomerID"),
                            rs.getInt("fkRoomNum"),
                            rs.getDate("CheckInDate"),
                            rs.getDate("CheckOutDate"),
                            rs.getDouble("Price")));
                }
            }
        }
        return reservationList;
    }

    /**
     * Returns the total amount a customer has been charged, which is the sum of
     * the Price of all of their reservations.
     *
     * @param customerID    ID of the customer
     * @return the sum of the customer's reservation prices, or 0.0 if they have
     *         no reservations (or the customer does not exist)
     * @throws SQLException if the database cannot be reached or the query fails
     */
    public double getTotalCost(int customerID) throws SQLException {
        String query = "SELECT SUM(Price) FROM Reservation WHERE fkCustomerID = ?";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);        // SUM is NULL with no reservations; getDouble turns that into 0.0
                }
            }
        }
        return 0;
    }
}