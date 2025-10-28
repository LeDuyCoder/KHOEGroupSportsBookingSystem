package khoegroupsportsbookingsystem.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import khoegroupsportsbookingsystem.business.controller.BookingController;
import khoegroupsportsbookingsystem.business.controller.FacilityController;
import khoegroupsportsbookingsystem.model.Booking;
import khoegroupsportsbookingsystem.model.FacilitySchedule;
import khoegroupsportsbookingsystem.util.Acceptable;
import khoegroupsportsbookingsystem.util.DateUtil;
import khoegroupsportsbookingsystem.util.Inputter;
import khoegroupsportsbookingsystem.util.RandomUtil;

public class BookingView {
    private final BookingController bookingController;
    private final FacilityController facilityController;
    private final Inputter inputter;

    public BookingView(BookingController bookingController, Inputter inputter, FacilityController facilityController) {
        this.bookingController = bookingController;
        this.facilityController = facilityController;
        this.inputter = inputter;
    }
    
    /**
     * Hiển thị giao diện để người dùng thực hiện việc đặt sân mới.
     * <p>
     * Phương thức này hướng dẫn người dùng nhập:
     * - Tên người chơi (hợp lệ theo regex)
     * - Mã sân (facility ID)
     * - Số lượng người tham gia
     * Sau khi dữ liệu hợp lệ, hệ thống sẽ tạo một đối tượng {@link Booking}
     * và thêm vào thông qua {@code bookingController}.
     * </p>
     */
    public void showBookingView(){
        String namePlayer;
        FacilitySchedule facility;
        int numberPerson;
        LocalDateTime bookingDateTime = LocalDateTime.now();
        while (true) { 
            try {
                namePlayer = inputter.getStringInput("Enter player name: ", Acceptable.PLAYER_NAME_VALID);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) { 
            try {
                String facilityId = inputter.getStringInput("Enter facility ID: ", Acceptable.STRING_VALID);
                facility = facilityController.getFacility(facilityId);
                if (facility == null) {
                    System.out.println("Facility not found. Please try again.");
                }else{
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) { 
            try {
                numberPerson = inputter.getIntInput("Enter number of persons: ", Acceptable.INTERGER_VALID);
                if(numberPerson <= 0){
                    System.out.println("Number of persons must be greater than 0");
                }else{
                    break;
                }
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Booking booking = new Booking(RandomUtil.generateRandomDigits(), namePlayer, facility.getId(), bookingDateTime, numberPerson);
        try {
            bookingController.addBooking(booking);
            System.out.println("Booking successful!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Hiển thị danh sách chi tiết các booking theo ngày cụ thể.
     * <p>
     * Người dùng được yêu cầu nhập ngày theo định dạng {@code YYYY-MM-DD}.
     * Nếu nhập sai hoặc để trống, hệ thống sẽ tự động dùng ngày hiện tại.
     * Sau đó các booking trong ngày được lọc và hiển thị theo thứ tự thời gian.
     * </p>
     */
    public void showBookingDetailsView() {
        String date;
        while (true) { 
            try {
                date = inputter.getStringInput("Enter booking date (DD/MM/YYYY): ", Acceptable.DATE_VALID);
                break;
            } catch (IllegalArgumentException iae) {
                date = LocalDate.now().getDayOfMonth() + "/" 
                            + LocalDate.now().getMonthValue() + "/" 
                            + LocalDate.now().getYear();

                break;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            } 
        }

        Collection<Booking> bookings = bookingController.getAllBookings();
            
        List<Booking> filteredBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if(booking.getDate().toLocalDate().toString().equals(date)){
                filteredBookings.add(booking);
            }
        }

        filteredBookings.sort(new Comparator<Booking>() {
            @Override
            public int compare(Booking b1, Booking b2) {
                return b1.getDate().compareTo(b2.getDate());
            }
        });

        System.out.println("===================================================================================");
        //show booking date
        System.out.println("Bookings on " + date);

        System.out.println("==================================== Booking List =================================");
        System.out.printf("| %-15s | %-20s | %-20s | %-15s |%n",
                "Time", "Facility", "User", "NumberPerson");
        System.out.println("===================================================================================");

        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Booking booking : bookings) {
            FacilitySchedule facilitySchedule = facilityController.getFacility(booking.getFacilityId());
            System.out.printf("| %-15s | %-20s | %-20s | %-15s |%n",
                    booking.getDate().toLocalTime().getHour() + ":" + booking.getDate().toLocalTime().getMinute(),
                    facilitySchedule.getFacilityName(),
                    booking.getPlayerName(),
                    booking.getNumberPerson()
            );
        }

        System.out.println("===================================================================================");
        System.out.print("Press Enter to continue...");
        inputter.getStringWithoutRegex();
    }

    /**
     * Hiển thị giao diện để người dùng xóa một booking cụ thể.
     * <p>
     * Người dùng cần nhập:
     * - Tên người chơi
     * - Ngày đặt sân (DD/MM/YYYY)
     * - Mã sân (facility ID)
     * Nếu tìm thấy booking phù hợp, hệ thống sẽ yêu cầu xác nhận xóa.
     * </p>
     */
    public void showDeleteBookingView(){
        String playerName;
        String date;
        String facilityId;
        while (true) { 
            try {
                playerName = inputter.getStringInput("Enter player name to delete booking: ", Acceptable.PLAYER_NAME_VALID);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) { 
            try {
                date = inputter.getStringInput("Enter booking date to delete booking (DD/MM/YYYY): ", Acceptable.DATE_VALID);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) { 
            try {
                facilityId = inputter.getStringInput("Enter facility ID to delete booking: ", Acceptable.STRING_VALID);
                FacilitySchedule facility = facilityController.getFacility(facilityId);
                if (facility == null) {
                    System.out.println("Facility not found. Please try again.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Collection<Booking> bookings = bookingController.getAllBookings();
        for (Booking booking : bookings) {
            FacilitySchedule facilitySchedule = facilityController.getFacility(booking.getFacilityId());
            if(booking.getPlayerName().equalsIgnoreCase(playerName) &&
               booking.getDate().toLocalDate().toString().equals(DateUtil.formatDateToIso(date)) &&
               facilitySchedule.getId().equalsIgnoreCase(facilityId)){
                try {
                    String confirmation = inputter.getStringInput("Are you sure you want to delete this booking? (Y/N): ", Acceptable.CONFIRM_VALID);
                    if(confirmation.equalsIgnoreCase("Y")){
                        boolean deleted = bookingController.deleteBooking(booking.getBookingId());
                        if(deleted){
                            System.out.println("Booking deleted successfully.");
                            return;
                        }else{
                            System.out.println("Failed to delete booking.");
                            return;
                        }
                    } else {
                        System.out.println("Deletion cancelled.");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("No matching booking found.");
    }

    /**
     * Hiển thị báo cáo doanh thu theo tháng.
     * <p>
     * Người dùng nhập tháng/năm theo định dạng {@code MM/YYYY}.
     * Hệ thống sẽ gọi {@code bookingController.getMonthlyRevenue()} để lấy dữ liệu,
     * sau đó hiển thị bảng thống kê doanh thu từng loại sân và tổng doanh thu.
     * </p>
     */
    public void showMonthlyRevenueReportView() {
        while (true) {
            try {
                String monthYear = inputter.getStringInput(
                    "Enter month and year (MM/YYYY): ",
                    Acceptable.MONTH_YEAR_VALID
                );

                Map<String, Integer> facilityRevenueMap = bookingController.getMonthlyRevenue(monthYear);

                if (facilityRevenueMap.isEmpty()) {
                    System.out.println("No data available for " + monthYear);
                    return;
                }

                int no = 1;
                System.out.println();
                System.out.println("Revenue Report for " + monthYear);
                System.out.println("========== Revenue Report =========");
                System.out.printf("| %-5s | %-15s | %-10s |%n",
                        "No.", "Facility", "Amount");
                System.out.println("===================================");

                int totalRevenue = 0;
                for (Map.Entry<String, Integer> entry : facilityRevenueMap.entrySet()) {
                    System.out.printf("| %-5d | %-15s | %-10d |%n",
                            no++,
                            entry.getKey(),
                            entry.getValue()
                    );
                    totalRevenue += entry.getValue();
                }

                System.out.println("===================================");
                System.out.printf("| %-21s | %-10d |%n", "Total", totalRevenue);
                System.out.println("===================================");

                System.out.print("Press Enter to continue...");
                inputter.getStringWithoutRegex();
                break;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Hiển thị thống kê sử dụng dịch vụ (Service Usage Statistics).
     * <p>
     * Người dùng có thể chọn lọc theo khoảng ngày (tùy chọn, có thể bỏ trống).
     * Hệ thống sẽ gọi {@code bookingController.serviceUsageStatistics(startDate, endDate)}
     * để lấy dữ liệu thống kê số lượng người chơi sử dụng từng loại sân.
     * Kết quả được trình bày dưới dạng bảng.
     * </p>
     */
    public void showServiceUsageStatsView() {
        try {
            System.out.println("=== Service Usage Statistics Filter ===");
            System.out.println("You can leave fields empty to skip filtering.");
            System.out.println();

            String startDate;
            String endDate;

            try {
                startDate = inputter.getStringInput(
                    "Enter start date (dd/MM/yyyy) or press Enter to skip: ",
                    Acceptable.DATE_VALID
                );
            } catch (IllegalArgumentException e) {
                startDate = "";
            }

            try {
                endDate = inputter.getStringInput(
                    "Enter end date (dd/MM/yyyy) or press Enter to skip: ",
                    Acceptable.DATE_VALID
                );
            } catch (IllegalArgumentException e) {
                endDate = "";
            }

            Map<String, Integer> usageStatisticsMap = bookingController.serviceUsageStatistics(startDate, endDate);

            if (usageStatisticsMap.isEmpty()) {
                System.out.println("No data available in the Service Usage Statistics"
                        + ((!startDate.isEmpty() || !endDate.isEmpty())
                            ? " for selected date range." : ""));
                return;
            }

            System.out.println();
            System.out.println("========= Service Usage Statistics =======");
            if (!startDate.isEmpty() || !endDate.isEmpty()) {
                System.out.println("Filter date range: "
                        + (startDate.isEmpty() ? "..." : startDate)
                        + " → "
                        + (endDate.isEmpty() ? "..." : endDate));
            }
            System.out.printf("| %-20s | %-15s |%n", "Facility", "No. of Players");
            System.out.println("==========================================");

            for (Map.Entry<String, Integer> entry : usageStatisticsMap.entrySet()) {
                System.out.printf("| %-20s | %-15d |%n",
                        entry.getKey(),
                        entry.getValue()
                );
            }

            System.out.println("==========================================");
            System.out.print("Press Enter to continue...");
            inputter.getStringWithoutRegex();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
}
