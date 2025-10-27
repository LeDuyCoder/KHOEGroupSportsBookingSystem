
package khoegroupsportsbookingsystem;

import java.util.Scanner;
import khoegroupsportsbookingsystem.business.controller.BookingController;
import khoegroupsportsbookingsystem.business.controller.FacilityController;
import khoegroupsportsbookingsystem.business.service.BookingManager;
import khoegroupsportsbookingsystem.business.service.FacilityManager;
import khoegroupsportsbookingsystem.util.Acceptable;
import khoegroupsportsbookingsystem.util.Inputter;
import khoegroupsportsbookingsystem.view.BookingView;
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
        BookingView bookingView = new BookingView(bookingController, inputter, facilityController);

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
                        if(facilityController.getAllFacility().isEmpty()){
                            System.out.println("No facilities available. Please import facilities first.");
                            break;
                        }
                        bookingView.showBookingView();
                        break;
                    case 5:
                        bookingView.showBookingDetailsView();
                        break;
                    case 6:
                        bookingView.showDeleteBookingView();
                        break;
                    case 7:
                        bookingView.showMonthlyRevenueReportView();
                        break;
                    case 8:
                        bookingView.showServiceUsageStatsView();
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
