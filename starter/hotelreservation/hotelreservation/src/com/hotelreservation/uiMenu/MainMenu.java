package com.hotelreservation.uiMenu;
import com.hotelreservation.model.Customer;
import com.hotelreservation.model.IRoom;
import com.hotelreservation.model.Reservation;
import com.hotelreservation.service.CustomerService;
import com.hotelreservation.service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class MainMenu {


    private static void showMainMenu() {
        System.out.println("Main Menu");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("Please enter you selection, or select 5 to exit");
    }

    public static void displayMainMenu(Scanner scanner) {
        showMainMenu();
        int selection = -1;
        while (selection !=5) {
            try {
                selection = Integer.parseInt(scanner.nextLine());
                switch (selection) {
                    case 1:
                        MainMenu.findAndReserveARoom(scanner);

                        break;
                    case 2:
                        MainMenu.displayMyReservations(scanner);

                        showMainMenu();
                        break;
                    case 3:
                        MainMenu.displayCreatingAccount(scanner);
                        showMainMenu();
                        break;
                    case 4:
                        AdminMenu.displayAdminMenu(scanner);
                        break;
                        default:
                      System.out.println("------------Exit Main Menu----------------");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please select your option 1 to 4. Select 5 to exit, check error " + nfe);

            }


        }

    }

    private static void displayCreatingAccount(Scanner scanner) {
        boolean isAccountCreatedSuccessfully = false;
        if (!isAccountCreatedSuccessfully) {
            while (!isAccountCreatedSuccessfully) {

                System.out.println("Please enter your First Name: ");
                String firstName = scanner.nextLine();
                System.out.println("Please enter your Last Name: ");
                String lastName = scanner.nextLine();
                System.out.println("Please enter your email in the format of name@email.com: ");
                String email = scanner.nextLine();
                isAccountCreatedSuccessfully = doCreateAccount(firstName, lastName, email);

            }
        }

    }

    public static void findAndReserveARoom(Scanner scanner) {
        String bookARoomMessage = " Book a room? Yes(Y)/No(N)?";
        String accountConfirmationMessage = "In order to book a room, you need to create an account with us. Are you our existing customer? Yes(Y)/No(N)?";


        if (yesOrNoResponse(bookARoomMessage, scanner)) {
            if (yesOrNoResponse(accountConfirmationMessage, scanner)) {
                System.out.println("Enter Your Email in the format of name@email.com in order to check if account already exists");
                String customerEmail = scanner.nextLine();
                if (!CustomerService.customerServiceInstance().isCustomerAlreadyExist(customerEmail)) {
                    System.out.println("Account not found. Please create an account to reserve a Room.");
                    System.out.println("Create an account");

                    MainMenu.displayCreatingAccount(scanner);
                    MainMenu.showMainMenu();
                } else {
                     String inputCheckInDate = " Please enter your check in date in  MM/dd/yyyy" + " format";
                     String inputCheckOutDate = " Please enter your check out date in  MM/dd/yyyy" + " format";
                     Date checkInDate = parseInputStringToDate(inputCheckInDate, "MM/dd/yyyy", scanner);
                     Date checkOutDate = parseInputStringToDate(inputCheckOutDate, "MM/dd/yyyy", scanner);
                     Collection<IRoom> availableRoomsList = ReservationService.reservationServiceInstance().findRooms(checkInDate, checkOutDate);
                     SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                     Date newCheckInDate =addDays(checkInDate,7);
                     Date newCheckOutDate = addDays(checkOutDate,7);
                    if(availableRoomsList.isEmpty()) {
                        System.out.println("Sorry, No rooms available on this date");
                        System.out.println("We have a reccomedation "+ "from " +newCheckInDate+" to "+ newCheckOutDate+ReservationService.reservationServiceInstance().newRoomList(newCheckInDate,newCheckOutDate));
                        Collection<IRoom> advanceAvailableRooms= ReservationService.reservationServiceInstance().findRooms(
                                newCheckInDate, newCheckOutDate);
                        if(!advanceAvailableRooms.isEmpty()){
                            System.out.println(advanceAvailableRooms);
                            }else {
                               showMainMenu();
                            }
                         }
                    else {

                        ReservationService.reservationServiceInstance().printRooms(availableRoomsList);
                           System.out.println("select above available rooms list ");

                    String roomNumber = scanner.nextLine();


                    if (isRoomSelectedValid(availableRoomsList, roomNumber)) {
                        IRoom selectedRoom = ReservationService.reservationServiceInstance().getRoom(roomNumber);
                        Customer thisCustomer = CustomerService.customerServiceInstance().getCustomer(customerEmail);

                        ReservationService.reservationServiceInstance().reserveARoom(thisCustomer, selectedRoom, checkInDate, checkOutDate);

                        MainMenu.showMainMenu();
                    }else{
                        System.out.println("Reservation is failed due to invalid room number input");
                    }

                    }
                }
            }
        }
    }
     public static Date addDays(Date date, int days){
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         cal.add(Calendar.DATE, days);
         return cal.getTime();

     }

    private static boolean isRoomSelectedValid(Collection<IRoom> availableRooms, String roomNumber) {

        boolean selectedRoomFlag = false;
        for (IRoom rooms : availableRooms) {
            if (rooms.getRoomNumber().equalsIgnoreCase(roomNumber)) {
                selectedRoomFlag = true;
                return selectedRoomFlag;
            }
        }return selectedRoomFlag;
    }

    private static boolean isRoomSelectedValidAlreadyReserved(Collection<IRoom> availableRoomsAlreadyReserved, String roomNumber) {

        boolean selectedRoomFlag = false;
        for (IRoom rooms : availableRoomsAlreadyReserved) {
            if (rooms.getRoomNumber().equalsIgnoreCase(roomNumber)) {
                selectedRoomFlag = true;
                if(ReservationService.reservationServiceInstance().getCustomerReservation(roomNumber).equals(availableRoomsAlreadyReserved))
                    return selectedRoomFlag;
            }
            System.out.println(availableRoomsAlreadyReserved + "this room is not available as its reserved");
        }
        return selectedRoomFlag;
    }
    private static boolean yesOrNoResponse(String printResponse, Scanner scanner) {
        boolean isYesOrNo = true;
        System.out.println(printResponse);
        String response = scanner.nextLine();

        if (isYes(response)) {
            System.out.println("Great! lets go to next step");
        } else {
            isYesOrNo = false;
            System.out.println("Ok, lets go back to menu options.");
            displayMainMenu(scanner);
        }
        return isYesOrNo;
    }

    private static boolean isYes(String response) {
        boolean yesFlag = false;
        if (response != null) {
            if
            ("Yes".equalsIgnoreCase(response)||"Y".equalsIgnoreCase(response)) {
                yesFlag = true;
            }
        }
        return yesFlag;
    }

    private static boolean isNo(String response) {
        boolean noFlag = false;
        if (response != null) {
            if
            ("No".equalsIgnoreCase(response)||"N".equalsIgnoreCase(response)) {
                noFlag = true;
            }
        }
        return noFlag;

    }


    private static Date parseInputStringToDate(String inputCheckInDateMessage, String patternString, Scanner
            scanner) {
        SimpleDateFormat format = new SimpleDateFormat(patternString, Locale.ENGLISH);
        System.out.println(inputCheckInDateMessage);
        String dateValidate;
        Date date = null;
        while (date == null) {
            try {
                dateValidate = scanner.nextLine();
                date = format.parse(dateValidate);
            } catch (ParseException pe) {
                System.out.println("Error Message " + pe.getMessage() + " is invalid date format, please enter proper date in mm/dd/yyyy");
                System.out.println(inputCheckInDateMessage);
            }
        }
        return date;
    }
    private static Date getWeekAfter(Date date){
        long datePlus7Days =date.getTime()+7*24*60*1000;
        return new Date(datePlus7Days);
    }

    private static boolean doCreateAccount(String firstName, String lastName, String email) {
        boolean isSuccesful = false;

        try {
            CustomerService.customerServiceInstance().addCustomer(email, firstName, lastName);
            isSuccesful = true;
            System.out.println("Your Account is succesfully created, Please proceed to find or reserve a room");
        } catch (IllegalArgumentException iae) {
            System.out.println("oops! could not create account due to error" + iae);
        }
        return isSuccesful;

    }
    public static void displayMyReservations(Scanner scanner) {
        System.out.println("Customer Email:");
        String customerEmail = scanner.nextLine();
        Customer customer = CustomerService.customerServiceInstance().getCustomer(customerEmail);
        if (customer == null) {
            System.out.println("Email not registered, Please create one by selecting option 3");
        } else {
            System.out.println(" Please check below for reservation details ");
            Collection<Reservation> myReservationsDetails = ReservationService.reservationServiceInstance().getCustomerReservation(customerEmail);
            ReservationService.reservationServiceInstance().getAllReservations(myReservationsDetails);


        }


    }
}


