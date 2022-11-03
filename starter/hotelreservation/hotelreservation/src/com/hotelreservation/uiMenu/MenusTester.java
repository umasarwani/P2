package com.hotelreservation.uiMenu;

import java.util.Scanner;

public class MenusTester {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        MainMenu.displayMainMenu(scanner);
        AdminMenu.displayAdminMenu(scanner);

    }
}
