package khoegroupsportsbookingsystem.business.service;

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
 * Lớp {@code BookingManager} chịu trách nhiệm quản lý danh sách đặt sân (Booking)
 * trong hệ thống KHOE Group Sports Booking System.
 * <p>
 * Dữ liệu được lưu trữ trong file theo đường dẫn được định nghĩa tại
 * {@link khoegroupsportsbookingsystem.constants.FilePathConstants#BOOKING_INFO_FILE}.
 * </p>
 *
 * @author Lê Hữu Duy
 */
public class BookingManager {
    private Map<String, Booking> mapBooking = new HashMap<>();
   
    /**
     * Đọc dữ liệu booking từ file .dat và nạp vào map.
     *
     * @return {@code true} nếu đọc file thành công, {@code false} nếu file không tồn tại
     *         hoặc xảy ra lỗi khi đọc.
     */
    public boolean loadBookings(){
        File file = new File(FilePathConstants.BOOKING_INFO_FILE);
        if(!file.exists()){
            System.out.println("Booking data file does not exist.");
            return false;
        }
        
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            mapBooking = (Map<String, Booking>) objectInputStream.readObject();

            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * Ghi toàn bộ dữ liệu booking trong map ra file .dat.
     *
     * @return {@code true} nếu ghi thành công, {@code false} nếu xảy ra lỗi khi ghi file.
     */
    public boolean saveBookings(){
        File file = new File(FilePathConstants.BOOKING_INFO_FILE);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(mapBooking);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException ex) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Lấy thông tin một booking theo mã định danh.
     *
     * @param bookingId mã đặt sân cần tìm
     * @return đối tượng {@link Booking} nếu tồn tại, ngược lại trả về {@code null}.
     */
    public Booking getBooking(String bookingId){
        return mapBooking.get(bookingId);
    }
    
    /**
     * Lấy toàn bộ danh sách các booking hiện có trong hệ thống.
     *
     * @return một {@code Collection} chứa tất cả các {@link Booking}.
     */
    public Collection<Booking> getAllBooking(){
        return mapBooking.values();
    }
    
    /**
     * Xóa một booking khỏi danh sách theo mã định danh.
     *
     * @param bookingId mã đặt sân cần xóa
     * @return {@code true} nếu xóa thành công, {@code false} nếu booking không tồn tại.
     */
    public boolean deleteBooking(String bookingId){
        return mapBooking.remove(bookingId) != null;
    }
    
    
    /**
     * Thêm một đặt sân mới vào danh sách quản lý booking.
     * <p>
     * Phương thức này sẽ lưu đối tượng {@link Booking} vào map,
     * với khóa là mã đặt sân (bookingId) để đảm bảo mỗi booking là duy nhất.
     * Nếu mã booking đã tồn tại, dữ liệu cũ sẽ bị ghi đè.
     * </p>
     *
     * @param booking đối tượng {@link Booking} cần thêm vào danh sách
     */
    public void addBooking(Booking booking){
        mapBooking.put(booking.getBookingId(), booking);
    }

    public void setMapBooking(BookingManager bookingManager) {
        this.mapBooking.clear();
        this.mapBooking.putAll(bookingManager.mapBooking);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BookingManager that = (BookingManager) obj;
        return mapBooking.equals(that.mapBooking);
    }

    @Override
    public int hashCode() {
        return mapBooking.hashCode();
    }
}
