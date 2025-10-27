package khoegroupsportsbookingsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String formatToIso(String dateStr) {
        // Format đầu vào: 11/11/2025 09:30
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        // Format đầu ra: 2025-11-11T09:30
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime dateTime = LocalDateTime.parse(dateStr, inputFormatter);
        return dateTime.format(outputFormatter);
    }
}
