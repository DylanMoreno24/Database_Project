package com.databaseproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.databaseproject.RoomDAO.Room;

/**
 * All SQL for the Room table
 * RoomDAO
 */
public class RoomDAO {
    /**
     * returns every room, used to fill the room dropdown on the reservation form
     * @return a list containing all the rooms in the system
     */
    public List<Room> getAllRooms() throws SQLException {
        ArrayList<Room> result = new ArrayList<>();
        String query = "SELECT pkRoomNum, PricePerNight FROM Room";

        try(Connection conn = new DatabaseConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new Room(
                        rs.getInt("pkRoomNum"),
                        rs.getDouble("PricePerNight")
                        )
                    );
                }
            }
        }
        return result;
    }

    /**
     * returns the price for one room, used in the total cost calculation
     * @param roomNum   room number you want to get the price of
     * @return          the price of the specified room
     */
    public double getRoomPrice(int roomNum) throws SQLException {
        double price = 0;
        String query = "SELECT PricePerNight FROM Room WHERE pkRoomNum IS ?";

        try (Connection conn = new DatabaseConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, roomNum);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        price = rs.getDouble("PricePerNight");
                    }
                }
            }
        return price;
    }

    /**
     * returns rooms with no reservation overlapping those dates
     * @param checkIn       date of check in
     * @param checkOut      date of check out
     * @return              all available rooms within the provided check-in/out dates
     */
    public List<Room> getAvailableList(LocalDate checkIn, LocalDate checkOut) throws SQLException {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in.");
        }

        List<Room> availableList = new ArrayList<>();

        String query = "SELECT pkRoomNum, PricePerNight FROM Room WHERE pkRoomNum NOT IN "
                    + "(SELECT fkRoomNum FROM Reservation "
                    + "WHERE fkRoomNum IS NOT NULL AND CheckInDate < ? AND CheckOutDate > ?) "
                    + "ORDER BY pkRoomNum";

        try (Connection conn = new DatabaseConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDate(1, java.sql.Date.valueOf(checkOut));   // vs. existing CheckInDate
            ps.setDate(2, java.sql.Date.valueOf(checkIn));    // vs. existing CheckOutDate

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    availableList.add(new Room(rs.getInt("pkRoomNum"), rs.getDouble("PricePerNight")));
                }
            }
        }
        return availableList;
    }

}
