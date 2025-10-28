package khoegroupsportsbookingsystem.business.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import khoegroupsportsbookingsystem.business.service.BookingManager;
import khoegroupsportsbookingsystem.business.service.FacilityManager;
import khoegroupsportsbookingsystem.model.Booking;
import khoegroupsportsbookingsystem.model.FacilitySchedule;
import khoegroupsportsbookingsystem.util.DateUtil;

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
    private final BookingManager bookingManagerOrigin;
    private final FacilityManager facilityManager;

    /**
     * Khởi tạo {@code BookingController} với {@link BookingManager} được truyền vào.
     *
     * @param bookingManager đối tượng quản lý dữ liệu booking
     */
    public BookingController(BookingManager bookingManager, BookingManager bookingManagerOrigin, FacilityManager facilityManager) {
        this.bookingManager = bookingManager;
        this.bookingManagerOrigin = bookingManagerOrigin;
        this.facilityManager = facilityManager;
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
        Booking booking = getBooking(bookingId);
        facilityManager.getFacility(booking.getFacilityId()).setStatus(false);
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
            FacilitySchedule facilitySchedule = facilityManager.getFacility(booking.getFacilityId());
            if(facilitySchedule.getId().equalsIgnoreCase(idFacility)){
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

            FacilitySchedule facilitySchedule = facilityManager.getFacility(booking.getFacilityId());
            String facilityType = facilitySchedule.getFacilityType();
            int price = facilitySchedule.getPrice();
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
            FacilitySchedule facilitySchedule = facilityManager.getFacility(booking.getFacilityId());
            String facilityType = facilitySchedule.getFacilityType();
            int price = facilitySchedule.getPrice();
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
        FacilitySchedule facilitySchedule = facilityManager.getFacility(booking.getFacilityId());
        if(facilitySchedule.isStatus()){
            throw new Exception("The facility is currently unavailable for booking.");
        }else{
            if(booking.getNumberPerson() <= facilitySchedule.getCapacity()){
                bookingManager.addBooking(booking);
                facilitySchedule.setStatus(true);
            }else{
                throw new Exception("Not enough capacity to complete the registration.");
            }
            return true;
        }
    }

    /**
     * Tính toán và trả về doanh thu theo từng loại sân trong một tháng cụ thể.
     * <p>
     * Hàm này nhận vào chuỗi tháng/năm (định dạng {@code MM/YYYY}), sau đó duyệt qua toàn bộ danh sách booking
     * để lọc những booking có ngày nằm trong cùng tháng và năm.  
     * Doanh thu được tính dựa trên giá của từng lịch sân ({@code FacilitySchedule})
     * và được cộng dồn theo loại sân ({@code facilityType}).
     * </p>
     *
     * @param monthYear Chuỗi tháng/năm cần thống kê, định dạng {@code MM/YYYY}.
     * @return Bản đồ ({@code Map}) chứa tên loại sân làm khóa và tổng doanh thu làm giá trị.  
     *         Nếu không có dữ liệu phù hợp, trả về bản đồ rỗng.
     *
     * @throws NumberFormatException nếu chuỗi đầu vào không đúng định dạng tháng/năm.
     */
    public Map<String, Integer> getMonthlyRevenue(String monthYear) {
        Map<String, Integer> facilityRevenueMap = new HashMap<>();

        try {
            // Tách tháng / năm từ chuỗi nhập "MM/YYYY"
            String[] parts = monthYear.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            for (Booking booking : getAllBookings()) {
                LocalDate bookingDate = booking.getDate().toLocalDate();

                if (bookingDate.getMonthValue() == month && bookingDate.getYear() == year) {
                    FacilitySchedule facilitySchedule = facilityManager.getFacility(booking.getFacilityId());
                    String facilityType = facilitySchedule.getFacilityType();
                    int price = facilitySchedule.getPrice();

                    if (facilityRevenueMap.containsKey(facilityType)) {
                        int current = facilityRevenueMap.get(facilityType);
                        facilityRevenueMap.put(facilityType, current + price);
                    } else {
                        facilityRevenueMap.put(facilityType, price);
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid date format (expected MM/YYYY).");
        }

        return facilityRevenueMap;
    }

    /**
     * Thống kê số lần sử dụng dịch vụ theo loại sân trong khoảng thời gian nhất định.
     * <p>
     * Hàm này lọc danh sách booking theo khoảng thời gian được cung cấp
     * (ngày bắt đầu và ngày kết thúc).  
     * Nếu người dùng bỏ trống cả hai giá trị, hệ thống sẽ thống kê toàn bộ dữ liệu hiện có.
     * </p>
     *
     * @param startDate Ngày bắt đầu lọc dữ liệu, định dạng {@code dd/MM/yyyy}.  
     *                  Có thể để trống ("") nếu không muốn giới hạn thời gian bắt đầu.
     * @param endDate   Ngày kết thúc lọc dữ liệu, định dạng {@code dd/MM/yyyy}.  
     *                  Có thể để trống ("") nếu không muốn giới hạn thời gian kết thúc.
     * @return Bản đồ ({@code Map}) chứa tên loại sân làm khóa và tổng số người sử dụng làm giá trị.  
     *         Nếu không có dữ liệu phù hợp, trả về bản đồ rỗng.
     *
     * @throws DateTimeParseException nếu định dạng ngày không hợp lệ.
     */
    public Map<String, Integer> serviceUsageStatistics(String startDate, String endDate) {
        Map<String, Integer> usageStatisticsMap = new HashMap<>();

        LocalDate start = null;
        LocalDate end = null;

        try {
            if (!startDate.isEmpty()) {
                start = LocalDate.parse(DateUtil.formatDateToIso(startDate)); 
            }
            if (!endDate.isEmpty()) {
                end = LocalDate.parse(DateUtil.formatDateToIso(endDate));
            }
        } catch (DateTimeParseException e){}

        for (Booking booking : getAllBookings()) {
            LocalDate bookingDate = booking.getDate().toLocalDate();

            if (start != null && bookingDate.isBefore(start)) {
                continue;
            }
            if (end != null && bookingDate.isAfter(end)) {
                continue;
            }

            FacilitySchedule facilitySchedule = facilityManager.getFacility(booking.getFacilityId());
            String facilityType = facilitySchedule.getFacilityType();
            int numberPerson = booking.getNumberPerson();

            if (usageStatisticsMap.containsKey(facilityType)) {
                int currentValue = usageStatisticsMap.get(facilityType);
                usageStatisticsMap.put(facilityType, currentValue + numberPerson);
            } else {
                usageStatisticsMap.put(facilityType, numberPerson);
            }
        }

        return usageStatisticsMap;
    }


    /**
     * Tải dữ liệu đặt sân từ file.
     *
     * @return {@code true} nếu tải thành công, {@code false} nếu lỗi
     */
    @Override
    public boolean load() {
        return bookingManager.loadBookings() && bookingManagerOrigin.loadBookings();
    }

    /**
     * Lưu dữ liệu đặt sân xuống file.
     *
     * @return {@code true} nếu lưu thành công, {@code false} nếu lỗi
     */
    @Override
    public boolean save() {
        return bookingManager.saveBookings();
    }
}
