/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoegroupsportsbookingsystem.business.controller;

/**
 * Lớp trừu tượng {@code BaseController} định nghĩa cấu trúc chung
 * cho các controller trong hệ thống KHOE Group Sports Booking System.
 * <p>
 * Các lớp controller kế thừa lớp này phải tự cài đặt (override)
 * hai phương thức {@link #load()} và {@link #save()} để thực hiện
 * việc tải và lưu dữ liệu tương ứng với chức năng của chúng.
 * </p>
 *
 * @author Lê Hữu Duy
 */
abstract class BaseController {
    /**
     * Tải dữ liệu cần thiết cho controller.
     *
     * @return {@code true} nếu tải dữ liệu thành công,
     *         {@code false} nếu xảy ra lỗi hoặc không có dữ liệu.
     */
    abstract boolean load();
    
    /**
     * Lưu dữ liệu hiện tại của controller xuống file hoặc bộ nhớ.
     *
     * @return {@code true} nếu lưu thành công,
     *         {@code false} nếu xảy ra lỗi khi lưu dữ liệu.
     */
    abstract boolean save();
}
