
package khoegroupsportsbookingsystem;

import java.util.Scanner;
import khoegroupsportsbookingsystem.util.Acceptable;
import khoegroupsportsbookingsystem.util.Inputter;
import khoegroupsportsbookingsystem.view.MainView;

/**
 *
 * @author Duyga
 */
public class KHOEGroupSportsBookingSystem {

    public static Boolean LOOP = true;

    public static void main(String[] args) {
        Inputter inputter = new Inputter(new Scanner(System.in));
        MainView mainView = new MainView();
        mainView.showMenuView();
        while(LOOP){
            try {
                int userChosen = inputter.getIntInput("Please select an option: ", Acceptable.INTERGER_VALID);
                switch (userChosen) {
                    case 1:
                        System.out.println("Import Facility from CSV file selected.");
                        break;
                    case 2:
                        System.out.println("Update Facility Information selected.");
                        break;
                    case 3:
                        System.out.println("View Facilities & Services selected.");
                        break;
                    case 4:
                        System.out.println("Book a Facility / Service selected.");
                        break;
                    case 5:
                        System.out.println("View Bookings selected.");
                        break;
                    case 6:
                        System.out.println("Cancel a Booking selected.");
                        break;
                    case 7:
                        System.out.println("Monthly Revenue Report selected.");
                        break;
                    case 8:
                        System.out.println("Service Usage Statistics selected.");
                        break;
                    case 9:
                        System.out.println("Save All Data selected.");
                        break;
                    case 10:
                        System.out.println("Exiting the system. Goodbye!");
                        LOOP = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please select a number between 1 and 10.");
                }
            } catch (Exception e) {            
                System.out.println("Invalid input. Please enter a number corresponding to the menu options.");
            }
        }
        
    }
    
}
