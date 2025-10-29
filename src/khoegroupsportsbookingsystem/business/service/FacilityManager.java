package khoegroupsportsbookingsystem.business.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import khoegroupsportsbookingsystem.constants.FilePathConstants;
import khoegroupsportsbookingsystem.model.FacilitySchedule;

/**
 * Lớp quản li danh sách các ấn thể thao trong hệ thống
 * <p>
 * {@code FacilityManager} đóng vai trò là tầng dịch vụ (service),
 * chịu trách nhiệm tải, lưu và truy xuất thông tin của các sân hoặc dịch vụ
 * trong hệ thống KHOE Sport Booking System.
 * </p>
 */
public class FacilityManager{
    private boolean dataLoaded = false;
    
    private final Map<String, FacilitySchedule> mapFacility = new HashMap<>();
    
    /**
     * Tải danh sách sân thể thao từ file hoặc nguồn dữ liệu.
     *
     * @return {@code true} nếu tải thành công, {@code false} nếu thất bại
     */
    public boolean loadFacilitys(){
        File file = new File(FilePathConstants.FACILITY_SCHEDULE_FILE);
        if(file.exists()){
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                bufferedReader.readLine(); // bỏ qua dòng đầu tiên
                while((line = bufferedReader.readLine()) != null){
                    if(line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length < 9) continue;
                    FacilitySchedule facility = new FacilitySchedule(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        Integer.parseInt(parts[4].trim()),
                        parts[5].trim(),
                        parts[6].trim(),
                        Integer.parseInt(parts[7].trim()),
                        "TRUE".equalsIgnoreCase(parts[8].trim())
                    );
                    mapFacility.put(facility.getId(), facility);
                }
            } catch(IOException | NumberFormatException e) {
                return false;
            }
        }else{
            return false;
        }
        
        dataLoaded = true;
        return true;
    }
    
    /**
     * Lưu danh sách sân thể thao hiện có xuống file hoặc cơ sở dữ liệu.
     *
     * @return {@code true} nếu lưu thành công, {@code false} nếu thất bại
     */
    public boolean saveFacilitys(){
        File file = new File(FilePathConstants.FACILITY_SCHEDULE_FILE);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            // Ghi dòng tiêu đề (header)
            bw.write("Id,Facility Name,Facility Type,Location,Capacity,Availability Start,Availability End,Price,Status");
            bw.newLine();

            // Ghi từng sân trong map
            for (FacilitySchedule f : mapFacility.values()) {
                bw.write(String.format("%s,%s,%s,%s,%d,%s,%s,%d,%s",
                        f.getId(),
                        f.getFacilityName(),
                        f.getFacilityType(),
                        f.getLocation(),
                        f.getCapacity(),
                        f.getAvailabilityStart(),
                        f.getAvailabilityEnd(),
                        f.getPrice(),
                        f.isStatus() ? "TRUE" : "FALSE"
                ));
                bw.newLine();
            }

            bw.flush(); // đảm bảo ghi hết dữ liệu ra file
            bw.close();
            System.out.println("Facility data saved successfully.");
            return true;

        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Trả về danh sách tất cả các sân thể thao hiện có trong hệ thống.
     *
     * @return danh sách các đối tượng {@link FacilitySchedule}
     */
    public Collection<FacilitySchedule> getAllFacilitys(){
        return mapFacility.values();
    }
    
    /**
     * Lấy thông tin của một sân thể thao dựa theo mã sân.
     *
     * @param idFacility mã sân cần tìm
     * @return đối tượng {@link FacilitySchedule} nếu tồn tại, ngược lại trả về {@code null}
     */
    public FacilitySchedule getFacility(String idFacility){
        return mapFacility.get(idFacility);
    }
    
    public boolean  getDataLoaded(){
        return this.dataLoaded;
    }

    public void setMapFacility(FacilityManager facilityManager) {
        this.mapFacility.clear();
        this.mapFacility.putAll(facilityManager.mapFacility);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FacilityManager that = (FacilityManager) obj;
        return mapFacility.equals(that.mapFacility);
    }

    @Override
    public int hashCode() {
        return mapFacility.hashCode();
    }

}
