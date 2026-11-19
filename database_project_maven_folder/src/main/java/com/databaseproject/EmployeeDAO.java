package com.databaseproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * All SQL for the Employee table and the login check.
 * EmployeeDAO
 */
public class EmployeeDAO {

    /**
     * Checks whether the ID and password match a row in the Employee table.
     * @param empID         ID of the employee
     * @param password      password of the employee
     * @return true if the ID and password match a row, otherwise false
     * @throws SQLException if the database cannot be reached or the query fails
     */
    public boolean validateLogin(int empID, String password) throws SQLException {
        // reject missing input before touching the database
        if (password == null || password.isEmpty()) {
            return false;
        }

        String query = "SELECT pkEmpID FROM Employee WHERE pkEmpID = ? AND EmpPassword = ?";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, empID);            // first ? -> empID
            ps.setString(2, password);      // second ? -> password

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();           // true if a matching row exists
            }
        }
    }
}