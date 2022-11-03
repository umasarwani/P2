package com.hotelreservation.model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String emailRegex = "^(.+)@(.+).(.+)$";

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {

        return email;
    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        Pattern pattern = Pattern.compile(email);
        if (isEmailValid(email) ){
            pattern.matcher(email).matches();
                       this.email = email;
        } else { throw new IllegalArgumentException("The input email is Invalid " + email);

        }
    }
    private boolean isEmailValid (String email){
        Pattern pattern = Pattern.compile(emailRegex);

        if (pattern.matcher(email).matches()) {
            return true;
        }
            return false;
    }
    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}






