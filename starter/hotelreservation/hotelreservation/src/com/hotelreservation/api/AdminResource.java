package com.hotelreservation.api;

import com.hotelreservation.model.Customer;
import com.hotelreservation.model.IRoom;
import com.hotelreservation.service.CustomerService;
import com.hotelreservation.service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {
    private AdminResource(){

    }
private static AdminResource adminResourceInstance = new AdminResource();
    public static AdminResource getAdminResource(){
        return
                adminResourceInstance;
    }

    public  Customer getCustomer(String email) {
        Customer customer = CustomerService.customerServiceInstance().getCustomer(email);
        return customer;
    }
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room:rooms){
           ReservationService.reservationServiceInstance().addRoom(room);
        }

    }
    public Collection<IRoom>getAllRooms(){
        return ReservationService.reservationServiceInstance().getAllRooms();
    }

    public Collection<Customer>getAllCustomers(){

       return CustomerService.customerServiceInstance().getAllCustomers();
}

public void displayAllReservation(){
        ReservationService.reservationServiceInstance().printAllReservation();
}

}
