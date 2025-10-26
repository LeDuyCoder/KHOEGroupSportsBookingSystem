package khoegroupsportsbookingsystem.business.controller;

import java.util.Collection;
import khoegroupsportsbookingsystem.business.service.FacilityManager;
import khoegroupsportsbookingsystem.model.FacilitySchedule;

/**
 * Lớp {@code FacilityController} chịu trách nhiệm điều phối việc xử lý dữ liệu
 * liên quan đến cơ sở thể thao (Facility) trong hệ thống KHOE Group Sports Booking System.
 * <p>
 * Lớp này đóng vai trò trung gian giữa tầng giao diện (View) và tầng xử lý dữ liệu
 * ({@link FacilityManager}), giúp đảm bảo nguyên tắc tách biệt giữa logic nghiệp vụ và hiển thị.
 * </p>
 *
 * <p>
 * {@code FacilityController} kế thừa từ lớp {@link BaseController} và
 * triển khai hai phương thức {@link #load()} và {@link #save()} để tải và lưu dữ liệu cơ sở.
 * </p>
 *
 * @author Lê Hữu Duy
 */
public class FacilityController extends BaseController {
    private final FacilityManager facilityManager;

    /**
     * Khởi tạo một {@code FacilityController} mới với đối tượng {@link FacilityManager} được truyền vào.
     *
     * @param facilityManager đối tượng dùng để thao tác dữ liệu cơ sở thể thao
     */
    public FacilityController(FacilityManager facilityManager) {
        this.facilityManager = facilityManager;
    }
    
    /**
     * Lấy toàn bộ danh sách các cơ sở thể thao hiện có trong hệ thống.
     *
     * @return {@code Collection} chứa các đối tượng {@link FacilitySchedule}.
     */
    public Collection<FacilitySchedule> getAllFacility(){
        return facilityManager.getAllFacilitys();
    }
    
     /**
     * Lấy thông tin chi tiết của một cơ sở thể thao dựa trên mã định danh.
     *
     * @param idFacility mã cơ sở cần tìm
     * @return đối tượng {@link FacilitySchedule} nếu tồn tại, ngược lại trả về {@code null}.
     */
    public FacilitySchedule getFacility(String idFacility){
        return facilityManager.getFacility(idFacility);
    }
    
    /**
     * Kiểm tra xem một cơ sở thể thao có tồn tại trong hệ thống hay không.
     *
     * @param idFacility mã cơ sở cần kiểm tra
     * @return {@code true} nếu cơ sở tồn tại, {@code false} nếu không.
     */
    public boolean isExistFacility(String idFacility){
        return facilityManager.getFacility(idFacility) != null;
    }
    
    /**
     * Ghi toàn bộ dữ liệu cơ sở thể thao hiện tại xuống file.
     *
     * @return {@code true} nếu lưu thành công, {@code false} nếu xảy ra lỗi khi lưu.
     */
    @Override
    public boolean save(){
        return facilityManager.saveFacilitys();
    }
    
    /**
     * Tải dữ liệu cơ sở thể thao từ file vào hệ thống.
     *
     * @return {@code true} nếu tải dữ liệu thành công, {@code false} nếu xảy ra lỗi hoặc file không tồn tại.
     */
    @Override
    public boolean load(){
        return facilityManager.loadFacilitys();
    }
}
