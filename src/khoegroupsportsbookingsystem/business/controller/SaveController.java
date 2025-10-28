package khoegroupsportsbookingsystem.business.controller;

import khoegroupsportsbookingsystem.business.service.BookingManager;
import khoegroupsportsbookingsystem.business.service.FacilityManager;

public class SaveController {
    private final BookingManager bookingManager;
    private final BookingManager bookingManagerOrigin;

    private final FacilityManager facilityManager;
    private final FacilityManager facilityManagerOrigin;

    public SaveController(BookingManager bookingManager, BookingManager bookingManagerOrigin, FacilityManager facilityManager, FacilityManager facilityManagerOrigin) {
        this.bookingManager = bookingManager;
        this.bookingManagerOrigin = bookingManagerOrigin;
        this.facilityManager = facilityManager;
        this.facilityManagerOrigin = facilityManagerOrigin;
    }

    public boolean isChanged() {
        return !bookingManager.equals(bookingManagerOrigin) || !facilityManager.equals(facilityManagerOrigin);
    }


    public void saveChanges(){
        bookingManager.saveBookings();
        facilityManager.saveFacilitys();
        bookingManagerOrigin.setMapBooking(bookingManager);
        facilityManagerOrigin.setMapFacility(facilityManager);
    }

}
