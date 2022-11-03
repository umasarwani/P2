package com.hotelreservation.api;

import com.hotelreservation.model.Customer;
import com.hotelreservation.model.IRoom;
import com.hotelreservation.model.Reservation;
import com.hotelreservation.service.CustomerService;
import com.hotelreservation.service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private HotelResource() {
    }

    private static HotelResource hotelResourceInstance = new HotelResource();

    public static HotelResource getHotelResource() {

        return hotelResourceInstance;
    }


    public Customer getCustomer(String email) {

        return CustomerService.customerServiceInstance().getCustomer(email);

    }

    public void createACustomer(String email, String firstName, String lastName) {
        CustomerService.customerServiceInstance().addCustomer(email, firstName, lastName);

    }

    public IRoom getRoom(String roomNumber) {
        return
                ReservationService.reservationServiceInstance().getRoom(roomNumber);
    }


    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = CustomerService.customerServiceInstance().getCustomer(customerEmail);
        Reservation reservation = ReservationService.reservationServiceInstance().reserveARoom(customer, room, checkInDate, checkOutDate);

return reservation;

}


    public Collection<Reservation>getCustomerReservation(String customerEmail){
      Customer customer =  CustomerService.customerServiceInstance().getCustomer(customerEmail);
       return ReservationService.reservationServiceInstance().getCustomerReservation(customerEmail);
}
     public Collection <IRoom> findARoom(Date checkIn, Date checkOut){
        return ReservationService.reservationServiceInstance().findRooms(checkIn,checkOut);
}


}