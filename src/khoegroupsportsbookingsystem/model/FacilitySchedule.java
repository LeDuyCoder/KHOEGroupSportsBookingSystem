package khoegroupsportsbookingsystem.model;

/**
 * Lớp biểu diễn thông tin lịch của một cơ sở thể thao trong hệ thống
 * KHOE Group Sports Booking System.
 * <p>
 * Mỗi đối tượng {@code FacilitySchedule} lưu các thông tin như mã sân,
 * tên sân, loại hình, vị trí, sức chứa, thời gian hoạt động, giá thuê
 * và trạng thái hiện tại (đang trống hay đã được đặt).
 * </p>
 * 
 * @author Lê Hữu Duy
 */
public class FacilitySchedule {
    private String id;
    private String facilityName;
    private String facilityType;
    private String location;
    private int capacity;
    private String availabilityStart;
    private String availabilityEnd;
    private int price;
    private boolean status;

    /**
     * Khởi tạo một đối tượng {@code FacilitySchedule} với đầy đủ thông tin.
     *
     * @param id mã sâns
     * @param facilityName tên sân
     * @param facilityType loại hình thể thao
     * @param location vị trí sân
     * @param capacity sức chứa
     * @param availabilityStart thời gian bắt đầu có thể đặt
     * @param availabilityEnd thời gian kết thúc có thể đặt
     * @param price giá thuê
     * @param status trạng thái (true = đã đặt, false = trống)
     */
    public FacilitySchedule(String id, String facilityName, String facilityType, String location, int capacity, String availabilityStart, String availabilityEnd, int price, boolean status) {
        this.id = id;
        this.facilityName = facilityName;
        this.facilityType = facilityType;
        this.location = location;
        this.capacity = capacity;
        this.availabilityStart = availabilityStart;
        this.availabilityEnd = availabilityEnd;
        this.price = price;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAvailabilityStart() {
        return availabilityStart;
    }

    public void setAvailabilityStart(String availabilityStart) {
        this.availabilityStart = availabilityStart;
    }

    public String getAvailabilityEnd() {
        return availabilityEnd;
    }

    public void setAvailabilityEnd(String availabilityEnd) {
        this.availabilityEnd = availabilityEnd;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    
    
}
