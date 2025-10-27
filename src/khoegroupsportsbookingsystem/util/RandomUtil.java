package khoegroupsportsbookingsystem.util;

import java.security.SecureRandom;

public class RandomUtil {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Sinh chuỗi ngẫu nhiên gồm 15 ký tự số (0–9).
     *
     * @return chuỗi số ngẫu nhiên có độ dài 15
     */
    public static String generateRandomDigits() {
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}