
package khoegroupsportsbookingsystem;

import java.util.Scanner;
import khoegroupsportsbookingsystem.business.controller.BookingController;
import khoegroupsportsbookingsystem.business.controller.FacilityController;
import khoegroupsportsbookingsystem.business.service.BookingManager;
import khoegroupsportsbookingsystem.business.service.FacilityManager;
import khoegroupsportsbookingsystem.util.Acceptable;
import khoegroupsportsbookingsystem.util.Inputter;
import khoegroupsportsbookingsystem.view.FacilityView;
import khoegroupsportsbookingsystem.view.MainView;

/**
 *
 * @author Lê Hữu Duy
 */
public class KHOEGroupSportsBookingSystem {

    public static Boolean LOOP = true;

    public static void main(String[] args) {
        Inputter inputter = new Inputter(new Scanner(System.in));
        MainView mainView = new MainView();
        
        BookingManager bookingManager = new BookingManager();
        FacilityManager facilityManager = new FacilityManager();

        BookingController bookingController = new BookingController(bookingManager);
        FacilityController facilityController = new FacilityController(facilityManager, inputter);

        FacilityView facilityView = new FacilityView(facilityController, inputter);

        while(LOOP){
            try {
                mainView.showMenuView();
                int userChosen = inputter.getIntInput("Please select an option: ", Acceptable.INTERGER_VALID);
                switch (userChosen) {
                    case 1:
                        if(facilityController.load()){
                            System.out.println("Facilities & Services loaded successfully.");
                        } else {
                            System.out.println("Failed to load Facilities & Services.");
                        }
                        break;
                    case 2:
                        facilityView.showUpdateView();
                        break;
                    case 3:
                        facilityView.showAllFacilitysView();
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
