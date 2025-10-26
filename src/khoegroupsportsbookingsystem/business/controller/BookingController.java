package khoegroupsportsbookingsystem.business.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import khoegroupsportsbookingsystem.business.service.BookingManager;
import khoegroupsportsbookingsystem.model.Booking;

/**
 * Lớp điều khiển (Controller) chịu trách nhiệm xử lý các thao tác liên quan đến đặt sân (Booking)
 * trong hệ thống KHOE Group Sports Booking System.
 * <p>
 * Lớp này đóng vai trò trung gian giữa tầng giao diện (UI) và tầng dịch vụ (Service),
 * giúp quản lý dữ liệu đặt sân như thêm, xóa, tìm kiếm, thống kê doanh thu
 * và kiểm tra tính hợp lệ của lượt đặt sân.
 * </p>
 * @author Lê Hữu Duy
 */
public class BookingController extends BaseController{
    private final BookingManager bookingManager;

    /**
     * Khởi tạo {@code BookingController} với {@link BookingManager} được truyền vào.
     *
     * @param bookingManager đối tượng quản lý dữ liệu booking
     */
    public BookingController(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }
    
    /**
     * Lấy danh sách toàn bộ lượt đặt sân hiện có.
     *
     * @return danh sách các đối tượng {@link Booking}
     */
    public Collection<Booking> getAllBookings(){
        return bookingManager.getAllBooking();
    }
    
    /**
     * Lấy thông tin chi tiết của một lượt đặt sân theo mã đặt sân.
     *
     * @param bookingId mã đặt sân cần tìm
     * @return đối tượng {@link Booking} nếu tồn tại, ngược lại trả về {@code null}
     */
    public Booking getBooking(String bookingId){
        return bookingManager.getBooking(bookingId);
    }
    
    /**
     * Kiểm tra xem một mã đặt sân đã tồn tại trong hệ thống hay chưa.
     *
     * @param bookingId mã đặt sân cần kiểm tra
     * @return {@code true} nếu tồn tại, {@code false} nếu không
     */
    public boolean isExistBooking(String bookingId){
        return bookingManager.getBooking(bookingId) != null;
    }
    
    /**
     * Xóa một lượt đặt sân khỏi hệ thống.
     *
     * @param bookingId mã đặt sân cần xóa
     * @return {@code true} nếu xóa thành công, {@code false} nếu không tìm thấy mã đặt sân
     */
    public boolean deleteBooking(String bookingId){
        return bookingManager.deleteBooking(bookingId);
    }
    
    /**
     * Lấy danh sách các lượt đặt sân trong một ngày cụ thể.
     *
     * @param date ngày cần lọc
     * @return danh sách các lượt đặt sân diễn ra trong ngày đó
     */
    public Collection<Booking> getAllBookingByDate(LocalDate date){
        Collection<Booking> bookings = getAllBookings();
        List<Booking> filterBookingsByDay = new ArrayList<>();
        for(Booking booking : bookings){
            if(booking.getDate().toLocalDate().equals(date)){
                filterBookingsByDay.add(booking);
            }
        }
        return filterBookingsByDay;
    }
    
    /**
     * Lấy danh sách các lượt đặt sân theo mã cơ sở thể thao.
     *
     * @param idFacility mã cơ sở thể thao cần lọc
     * @return danh sách các lượt đặt sân thuộc cơ sở đó
     */
    public Collection<Booking> getAllBookingsByIdFacility(String idFacility){
        Collection<Booking> bookings = getAllBookings();
        List<Booking> filterBookingsByIdFacility = new ArrayList<>();
        for(Booking booking : bookings){
            if(booking.getFacilitySchedule().getId().equalsIgnoreCase(idFacility)){
                filterBookingsByIdFacility.add(booking);
            }
        }
        return filterBookingsByIdFacility;
    }
    
    /**
     * Thống kê doanh thu theo từng loại cơ sở trong một tháng và năm cụ thể.
     *
     * @param month tháng cần thống kê
     * @param year  năm cần thống kê
     * @return map chứa cặp <facilityName, totalPrice>
     */
    public Map<String, Integer> revenueReport(Month month, Year year) {
        Map<String, Integer> revenueMap = new HashMap<>();
        for (Booking booking : getAllBookings()) {
            LocalDateTime bookingDate = booking.getDate();
            if (bookingDate.getYear() != year.getValue() ||
                bookingDate.getMonthValue() != month.getValue()) {
                continue;
            }
            String facilityType = booking.getFacilitySchedule().getFacilityType();
            int price = booking.getFacilitySchedule().getPrice();
            if (revenueMap.containsKey(facilityType)) {
                int currentRevenue = revenueMap.get(facilityType);
                revenueMap.put(facilityType, currentRevenue + price);
            } else {
                revenueMap.put(facilityType, price);
            }
        }
        return revenueMap;
    }
    
    /**
     * Thống kê tần suất sử dụng dịch vụ theo từng loại cơ sở.
     *
     * @return map chứa cặp <facilityName, totalPrice>
     */
    public Map<String, Integer> serviceUsageStatistics(){
        Map<String, Integer> usageStatisticsMap = new HashMap<>();
        for (Booking booking : getAllBookings()) {
            
            String facilityType = booking.getFacilitySchedule().getFacilityType();
            int price = booking.getFacilitySchedule().getPrice();
            if (usageStatisticsMap.containsKey(facilityType)) {
                int currentRevenue = usageStatisticsMap.get(facilityType);
                usageStatisticsMap.put(facilityType, currentRevenue + price);
            } else {
                usageStatisticsMap.put(facilityType, price);
            }
        }
        
        return usageStatisticsMap;
    }

    /**
     * Kiểm tra số lượng người tham gia trong lượt đặt sân mới có vượt quá sức chứa của cơ sở không.
     *
     * @param booking đối tượng {@link Booking} cần kiểm tra
     * @return {@code true} nếu hợp lệ, {@code false} nếu vượt quá sức chứa
     */
    public boolean checkAmountValid(Booking booking){
        Collection<Booking> listBooking = getAllBookingsByIdFacility(booking.getFacilitySchedule().getId());
        
        int totalBooking = 0;
        for(Booking b : listBooking){
            totalBooking += b.getNumberPerson();
        }
        
        return totalBooking + booking.getNumberPerson() <= booking.getFacilitySchedule().getCapacity();
    }
    

    /**
     * Thêm một lượt đặt sân mới vào hệ thống sau khi kiểm tra sức chứa của cơ sở.
     * <p>
     * Nếu số lượng người trong lượt đặt vượt quá sức chứa của cơ sở,
     * phương thức sẽ ném ra ngoại lệ {@link Exception} để thông báo lỗi.
     * Ngược lại, dữ liệu đặt sân sẽ được thêm vào danh sách quản lý.
     * </p>
     *
     * @param booking đối tượng {@link Booking} cần thêm
     * @return {@code true} nếu thêm thành công
     * @throws Exception nếu cơ sở không đủ sức chứa để hoàn tất đặt sân
     */
    public boolean addBooking(Booking booking) throws Exception{
        if(checkAmountValid(booking)){
            bookingManager.addBooking(booking);
        }else{
            throw new Exception("Not enough capacity to complete the registration.");
        }
        return true;
    }
    
    /**
     * Tải dữ liệu đặt sân từ file.
     *
     * @return {@code true} nếu tải thành công, {@code false} nếu lỗi
     */
    @Override
    boolean load() {
        return bookingManager.loadBookings();
    }

    /**
     * Lưu dữ liệu đặt sân xuống file.
     *
     * @return {@code true} nếu lưu thành công, {@code false} nếu lỗi
     */
    @Override
    boolean save() {
        return bookingManager.saveBookings();
    }
}
