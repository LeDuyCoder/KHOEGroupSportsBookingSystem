package khoegroupsportsbookingsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    /**
     * Chuyển đổi chuỗi ngày giờ từ định dạng {@code dd/MM/yyyy HH:mm}
     * sang định dạng ISO {@code yyyy-MM-dd'T'HH:mm}.
     *
     * <p>Ví dụ:
     * <pre>
     * formatToIso("11/11/2025 09:30") ➜ "2025-11-11T09:30"
     * </pre>
     *
     * @param dateStr chuỗi ngày giờ đầu vào (theo định dạng dd/MM/yyyy HH:mm)
     * @return chuỗi ngày giờ theo định dạng ISO (yyyy-MM-dd'T'HH:mm)
     * @throws DateTimeParseException nếu chuỗi đầu vào không hợp lệ
     */
    public static String formatToIso(String dateStr) throws DateTimeParseException{
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, inputFormatter);
        return dateTime.format(outputFormatter);
    }
}
