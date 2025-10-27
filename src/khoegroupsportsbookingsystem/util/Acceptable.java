package khoegroupsportsbookingsystem.util;

/**
 * Interface chứa các hằng số và phương thức tiện ích để kiểm tra tính hợp lệ của dữ liệu.
 * <p>
 * Hỗ trợ kiểm tra:
 * <ul>
 *   <li>Số nguyên (INTEGER_VALID)</li>
 *   <li>Tên người chơi (PLAYER_NAME_VALID)</li>
 *   <li>Ngày theo định dạng dd/MM/yyyy (DATE_VALID)</li>
 * </ul>
 * </p>
 * @author Lê Hữu Duy
 */
public interface Acceptable {
    public static final String INTERGER_VALID = "\\d+";
    public static final String PLAYER_NAME_VALID = "^[\\p{L} ]{2,18}$";
    public static final String DATE_TIME_VALID = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4} ([01][0-9]|2[0-3]):[0-5][0-9]$";
    public static final String TIME_VALID = "^([01][0-9]|2[0-3]):[0-5][0-9]$";
    public static final String DATE_VALID = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
    public static final String MONTH_YEAR_VALID = "^(0[1-9]|1[0-2])/\\d{4}$";
    public static final String CONFIRM_VALID = "^[YyNn]$";
    public static final String STRING_VALID = "^(?!\\s*$).+$";
    
    public static boolean isValid(String data, String pattern){
        return data.matches(pattern);
    }
} 
