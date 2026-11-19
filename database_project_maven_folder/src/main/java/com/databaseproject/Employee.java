package com.databaseproject;

/**
 * Model class representing one row of the Employee table.
 * The password is deliberately not stored here, so it is never held in memory
 * after login.
 */
public class Employee {
    private final int empID;
    private final String firstName;
    private final String lastName;

    public Employee(int empID, String firstName, String lastName) {
        this.empID = empID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getEmpID()          { return empID; }
    public String getFirstName()   { return firstName; }
    public String getLastName()    { return lastName; }

    /**
     * @return the employee's full name, e.g. for a "Welcome, Maria Santos" label
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return empID + " | " + getFullName();
    }
}