package khoegroupsportsbookingsystem.util;

import java.util.Scanner;

/**
 * Lớp tiện ích để xử lý nhập dữ liệu từ console với xác thực.
 * <p>
 * Cung cấp các phương thức đọc chuỗi và số nguyên từ người dùng,
 * đồng thời kiểm tra dữ liệu nhập theo biểu thức chính quy (regex) sử dụng {@link Acceptable}.
 * </p>
 * Lớp này sẽ ném {@link Exception} nếu dữ liệu nhập trống hoặc không khớp với pattern.
 * 
 * @author Duyga
 */
public class Inputter {
    private final Scanner scanner;

    public Inputter(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Yêu cầu người dùng nhập chuỗi và xác thực theo biểu thức chính quy.
     *
     * @param notificationMsg Thông báo hiển thị trước khi nhập
     * @param regex Biểu thức chính quy để kiểm tra dữ liệu nhập
     * @return Chuỗi hợp lệ mà người dùng nhập
     * @throws Exception nếu dữ liệu trống hoặc không hợp lệ
     */
    public String getStringInput(String notificationMsg, String regex) throws Exception {
        System.out.print(notificationMsg);
        String dataUserInput = scanner.nextLine().trim();

        if (dataUserInput.isEmpty()) {
            throw new Exception("Input cannot be empty!");
        }

        if (Acceptable.isValid(dataUserInput, regex)) {
            return dataUserInput;
        } else {
            throw new Exception("The input value is not valid. Please try again.");
        }
    }
    
    /**
     * Yêu cầu người dùng nhập số nguyên và xác thực theo biểu thức chính quy.
     *
     * @param notificationMsg Thông báo hiển thị trước khi nhập
     * @param regex Biểu thức chính quy để kiểm tra dữ liệu nhập
     * @return Số nguyên hợp lệ mà người dùng nhập; trả về -1 nếu người dùng không nhập gì
     * @throws Exception nếu dữ liệu không hợp lệ hoặc không phải số nguyên
     */
    public int getIntInput(String notificationMsg, String regex) throws Exception {
        System.out.print(notificationMsg);
        String input = scanner.nextLine();
        
        if (input.isEmpty()) {
            return -1;
        }
        
        if (Acceptable.isValid(input, regex)) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new Exception("Input is not a valid integer");
            }
        } else {
            throw new Exception("Not matches with regex");
        }
    }
}
