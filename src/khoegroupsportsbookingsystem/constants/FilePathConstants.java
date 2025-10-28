package khoegroupsportsbookingsystem.constants;

/**
 * Lớp {@code FilePathConstants} chứa các hằng số chỉ định đường dẫn
 * đến các tệp dữ liệu được sử dụng trong chương trình.
 * <p>
 * Lớp này giúp việc quản lý và thay đổi đường dẫn file dễ dàng hơn,
 * tránh việc ghi cứng (hardcode) trong nhiều nơi của mã nguồn.
 * </p>
 * 
 * <ul>
 *   <li>{@link #FACILITY_SCHEDULE_FILE}: Đường dẫn đến file lưu thông tin các facility schedule.</li>
 *      <li>{@link #BOOKING_INFO_FILE}: Đường dẫn đến file lưu thông tin các booking.</li>
 * </ul>
 * 
 * @author Lê Hữu Duy
 */
public class FilePathConstants {
        public static final String FACILITY_SCHEDULE_FILE = "data/facility_schedule_Final.csv";
        public static final String BOOKING_INFO_FILE = "data/BookingInfo.dat";
}
