package com.hotelreservation.uiMenu;


import com.hotelreservation.api.AdminResource;
import com.hotelreservation.model.Customer;
import com.hotelreservation.model.IRoom;
import com.hotelreservation.model.Room;
import com.hotelreservation.model.RoomType;
import com.hotelreservation.service.CustomerService;
import com.hotelreservation.service.ReservationService;

import java.util.Collection;
import java.util.Scanner;

    public class AdminMenu {

        public static void showAdminMenu() {
            System.out.println("Admin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("6. Exit");
            System.out.println(" Please select your option 1-5 or select 6 to exit");
        }

        public static void displayAdminMenu(Scanner scanner) {
            showAdminMenu();
            int selection = -1;
            while (selection !=6) {
                try {
                    selection = Integer.parseInt(scanner.nextLine());
                    switch (selection) {
                        case 1:
                            System.out.println("See All Customers");
                            AdminMenu.displaySeeAllCustomers(scanner);
//                                String allCustomers = scanner.nextLine();
                            break;
                        case 2:
                            System.out.println(" See all Rooms");
                            AdminMenu.displayAllRooms(scanner);
                            String allRooms = scanner.nextLine();

                            break;
                        case 3:
                            System.out.println("See all reservations");
                            AdminMenu.seeAllReservations(scanner);
                            break;
                        case 4:
                            System.out.println("Add New Room");
                                AdminMenu.displayAddRoom(scanner);
                            break;
                        case 5:
                            MainMenu.displayMainMenu(scanner);
                            break;
                            default:
                            System.out.println("------------Exit Admin Menu----------------");

                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Please select your option 1 to 5. Select 6 to exit Menu, check error " + nfe);

                }

            }
        }


        private static void seeAllReservations(Scanner scanner) {
            ReservationService.reservationServiceInstance().printAllReservation();
            showAdminMenu();

        }
        private static void seeAvailableReservations(Scanner scanner) {
            ReservationService.reservationServiceInstance().printNoReservations();

        }

        private static void displayAllRooms(Scanner scanner) {
           Collection<IRoom> availableAllRooms = ReservationService.reservationServiceInstance().getAllRooms();
            ReservationService.reservationServiceInstance().printRooms(availableAllRooms);
            //System.out.println(ReservationService.reservationServiceInstance().getAllRooms().toString());
            showAdminMenu();
        }

        private static void displaySeeAllCustomers(Scanner scanner) {

            System.out.println(AdminResource.getAdminResource().getAllCustomers());
            showAdminMenu();

        }

        private static void displayAddRoom(Scanner scanner) {

            String roomNumber = null;
            double price = 0;
            RoomType roomType = null;
            System.out.println("Enter Room Number");
            roomNumber = scanner.nextLine();
            System.out.println("Enter price per night");
            price = 0.0;
            boolean priceFlag = false;
            while (!priceFlag) {
                try {
                    price = Double.parseDouble(scanner.nextLine());
                    priceFlag = true;

                } catch (NullPointerException npe) {
                    System.out.println("Please enter valid amount per night");

                } catch (NumberFormatException nfe) {
                    System.out.println(nfe + " amount input is not correct, Enter valid amount ");
                }
            }
            System.out.println("Enter room Type :  1 for Single. 2 for Double");
            boolean roomTypeFlag = false;
            int selectedRoomType = 0;
            do {
                try {
                    selectedRoomType = Integer.parseInt(scanner.nextLine());
                    switch (selectedRoomType) {
                        case 1:
                            roomType = RoomType.Single;
                            roomTypeFlag = false;
                            break;
                        case 2:
                            roomType = RoomType.Double;
                            roomTypeFlag = false;
                            break;
                        default:
                            System.out.println("Entered input is wrong, please select 1 or 2 ");
                            roomTypeFlag = true;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Entered Format is not accepted. Please enter number 1 or 2");




                }


            }
            while (roomTypeFlag) ;
            Room NewRoom = new Room(roomNumber, price, roomType);
            ReservationService.reservationServiceInstance().addRoom(NewRoom);
            System.out.println(" New Room added sucessfully!");

            showAdminMenu();

        }
    }



