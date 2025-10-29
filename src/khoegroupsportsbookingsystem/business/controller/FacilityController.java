package khoegroupsportsbookingsystem.business.controller;

import java.util.Collection;
import khoegroupsportsbookingsystem.business.service.FacilityManager;
import khoegroupsportsbookingsystem.model.FacilitySchedule;
import khoegroupsportsbookingsystem.util.Acceptable;
import khoegroupsportsbookingsystem.util.Inputter;

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
    private final FacilityManager facilityManagerOrigin;
    private final Inputter inputter;

    /**
     * Khởi tạo một {@code FacilityController} mới với đối tượng {@link FacilityManager} được truyền vào.
     *
     * @param facilityManager đối tượng dùng để thao tác dữ liệu cơ sở thể thao
     */
    public FacilityController(FacilityManager facilityManager, FacilityManager facilityManagerOrigin, Inputter inputter) {
        this.facilityManager = facilityManager;
        this.facilityManagerOrigin = facilityManagerOrigin;
        this.inputter = inputter;
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
     * Kiểm tra xem dữ liệu cơ sở thể thao đã được tải vào hệ thống hay chưa.
     *
     * @return {@code true} nếu dữ liệu đã được tải, {@code false} nếu chưa.
     */
    public boolean getDataLoaded(){
        return facilityManager.getDataLoaded();
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
     * <p>
     * Nếu dữ liệu đã được tải trước đó, người dùng sẽ được hỏi xác nhận
     * có muốn tải lại dữ liệu hay không. Việc tải lại sẽ xóa toàn bộ dữ liệu
     * hiện tại trong bộ nhớ.
     * </p>
     *
     * @return {@code true} nếu tải thành công, {@code false} nếu người dùng
     * từ chối tải lại hoặc xảy ra lỗi khi tải.
     */
    @Override
    public boolean load(){
        if(getDataLoaded()){
            System.out.println("Facility data has been loaded. Reloading will reset current data in memory.");
            while (true) { 
                try {
                    String confirm = inputter.getStringInput("Do you want to reload? (Y/N): ", Acceptable.CONFIRM_VALID);
                    if(confirm.equalsIgnoreCase("N")){
                        return false;
                    }else if(confirm.equalsIgnoreCase("Y")){
                        facilityManager.loadFacilitys();
                        facilityManagerOrigin.loadFacilitys();
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Reloading cancelled.");
                }
            }
        }


        return true;
    }
}
