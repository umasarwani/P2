package com.hotelreservation.service;

import com.hotelreservation.model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

    public class CustomerService {
        private CustomerService() {
        }

        private static CustomerService customerServiceInstance = new CustomerService();

        public static CustomerService customerServiceInstance() {

            return customerServiceInstance;
        }

        private Map<String, Customer> customerMap = new HashMap<>();

        public void addCustomer(String email, String firstName, String lastName) {
            if (!isCustomerAlreadyExist(email)) {
                Customer newCustomer = new Customer(firstName, lastName, email);
                customerMap.put(email, newCustomer);

            } else {
                throw new IllegalArgumentException("This email" + email + "  account already exists");
            }
        }

        public boolean isCustomerAlreadyExist(String email) {
            boolean customerExistFlag = false;
            //validating if customer email is already existing
            Customer customer = customerMap.get(email);
            if (customer != null) {
                customerExistFlag = true;

            }
            return customerExistFlag;
        }

        public Customer getCustomer(String customerEmail) {
            return customerMap.get(customerEmail);

    }
        public Collection<Customer>getAllCustomers (){
            return customerMap.values();
        }



}




