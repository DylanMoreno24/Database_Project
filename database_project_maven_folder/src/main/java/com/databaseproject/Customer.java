package com.databaseproject;

/**
 * Model class representing one row of the Customer table.
 */
public class Customer {
    private final int customerID;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;

    public Customer(int customerID, String firstName, String lastName, String phoneNumber) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerID()      { return customerID; }
    public String getFirstName()    { return firstName; }
    public String getLastName()     { return lastName; }
    public String getPhoneNumber()  { return phoneNumber; }

    @Override
    public String toString() {
        return customerID + " | " + firstName + " " + lastName + " | " + phoneNumber;
    }
}
