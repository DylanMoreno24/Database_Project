package com.databaseproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the Customer table.
 * CustomerDAO
 */
public class CustomerDAO {
    /**
     * Adds a customer to the Customer table.
     * @param firstName     first name of customer
     * @param lastName      last name of customer
     * @param phoneNumber   phone number of customer
     * @return true if the row was inserted, otherwise false
     * @throws SQLException if the database cannot be reached or the insert fails
     */
    public boolean addCustomer(String firstName, String lastName, String phoneNumber) throws SQLException {
        String query = "INSERT INTO Customer (FirstName, LastName, PhoneNumber) VALUES (?, ?, ?)";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phoneNumber);

            return ps.executeUpdate() == 1;   // true if one row was inserted
        }
    }

    /**
     * Deletes a customer by their ID.
     * @param customerID    ID of the customer to delete
     * @return true if a row was deleted, otherwise false (no customer with that ID)
     * @throws SQLException if the database cannot be reached or the delete fails
     */
    public boolean deleteCustomer(int customerID) throws SQLException {
        String query = "DELETE FROM Customer WHERE pkCustomerID = ?";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerID);

            return ps.executeUpdate() == 1;   // true if one row was deleted
        }
    }

    /**
     * Finds customers whose first or last name contains the search text.
     * An empty search returns every customer.
     * @param name  text to search for
     * @return list of matching customers (empty list if none match, never null)
     * @throws SQLException if the database cannot be reached or the query fails
     */
    public List<Customer> searchByName(String name) throws SQLException {
        List<Customer> results = new ArrayList<>();

        String query = "SELECT pkCustomerID, FirstName, LastName, PhoneNumber "
                     + "FROM Customer "
                     + "WHERE FirstName LIKE ? OR LastName LIKE ? "
                     + "ORDER BY LastName, FirstName";

        // treat null as empty, trim stray spaces, add wildcards around the text
        String pattern = "%" + (name == null ? "" : name.trim()) + "%";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, pattern);   // FirstName LIKE ?
            ps.setString(2, pattern);   // LastName LIKE ?

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(new Customer(
                            rs.getInt("pkCustomerID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("PhoneNumber")));
                }
            }
        }
        return results;
    }
}