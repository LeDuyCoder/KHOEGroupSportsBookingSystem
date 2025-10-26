package khoegroupsportsbookingsystem.business.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import khoegroupsportsbookingsystem.constants.FilePathConstants;
import khoegroupsportsbookingsystem.model.Booking;

/**
 *
 * @author Duyga
 */
public class BookingManager {
    private Map<String, Booking> mapBooking = new HashMap<>();
   
    private boolean loadBookings(){
        File file = new File(FilePathConstants.BOOKING_INFO_FILE);
        if(!file.exists()){
            return false;
        }
        
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            mapBooking = (Map<String, Booking>) objectInputStream.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
    
    private boolean saveBookings(){
        File file = new File(FilePathConstants.BOOKING_INFO_FILE);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(mapBooking);
            objectOutputStream.flush();
        } catch (IOException ex) {
            return false;
        }
        
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
