package com.databaseproject;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        EmployeeDAO dao = new EmployeeDAO();
        boolean result = dao.validateLogin(4172,"Harbor#8");
        System.out.println(result);
    }
}
