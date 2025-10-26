package khoegroupsportsbookingsystem.business.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import khoegroupsportsbookingsystem.model.Booking;

/**
 *
 * @author Duyga
 */
public class BookingManager {
    private final Map<String, Booking> mapBooking = new HashMap<>();
   
    private boolean loadBookings(){
        return true;
    }
    
    private boolean saveBookings(){
        return true;
    }
    
    private Booking getBooking(String bookingId){
        return mapBooking.get(bookingId);
    }
    
    private Collection<Booking> getAllBooking(){
        return mapBooking.values();
    }
    
    private boolean deleteBooking(String bookingId){
        if(mapBooking.remove(bookingId) != null){
            return true;
        }else{
            return false;
        }
    }
}
