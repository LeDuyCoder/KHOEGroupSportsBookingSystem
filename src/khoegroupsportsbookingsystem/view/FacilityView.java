package khoegroupsportsbookingsystem.view;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import khoegroupsportsbookingsystem.business.controller.FacilityController;
import khoegroupsportsbookingsystem.model.FacilitySchedule;
import khoegroupsportsbookingsystem.util.Acceptable;
import khoegroupsportsbookingsystem.util.DateUtil;
import khoegroupsportsbookingsystem.util.Inputter;

/**
 * Lớp {@code FacilityView} dùng để hiển thị và cập nhật thông tin cơ sở (Facility) trên giao diện console.
 * 
 * <p>Sử dụng {@link FacilityController} để thao tác dữ liệu và {@link Inputter} để nhập liệu hợp lệ.
 * 
 * @author Lê Hữu Duy
 */
public class FacilityView {
    private final FacilityController facilityController;
    private final Inputter inputter;

    public FacilityView(FacilityController facilityController, Inputter inputter) {
        this.facilityController = facilityController;
        this.inputter = inputter;
    }

    /**
     * Hiển thị giao diện console cho phép người dùng cập nhật thông tin cơ sở.
     * 
     * <p>Người dùng có thể thay đổi địa điểm, sức chứa hoặc thời gian hoạt động.
     * Hệ thống kiểm tra hợp lệ dữ liệu nhập và đảm bảo thời gian bắt đầu – kết thúc cách nhau ít nhất 30 phút.
    */
    public void showUpdateView(){
        try {
            String facilityId = inputter.getStringInput("input facility id: ", Acceptable.STRING_VALID);
            FacilitySchedule facility = facilityController.getFacility(facilityId);
            if(facility == null){
                System.out.println("Facility with id " + facilityId + " does not exist.");
                return;
            }
            
            while (true) { 
                System.out.println("============ Facility Information ============");
                System.out.printf("%-20s: %s%n", "Facility ID", facility.getId());
                System.out.printf("%-20s: %s%n", "Facility Name", facility.getFacilityName());
                System.out.printf("%-20s: %s%n", "Location", facility.getLocation());
                System.out.printf("%-20s: %s%n", "Capacity", facility.getCapacity());
                System.out.printf("%-20s: %s%n", "Availability Start", facility.getAvailabilityStart());
                System.out.printf("%-20s: %s%n", "Availability End", facility.getAvailabilityEnd());
                System.out.println("===============================================");
                System.out.println("[1] Update Location");
                System.out.println("[2] Update Capacity");
                System.out.println("[3] Update Availability Start");
                System.out.println("[4] Update Availability End");
                System.out.println("[5] Exit");
            
                int userChosen = inputter.getIntInput("👉 Please enter your choice (1-5): ", Acceptable.INTERGER_VALID);
                switch (userChosen) {
                    case 1:
                        while (true) { 
                            try {
                                String newLocation = inputter.getStringInput("Enter new location: ", Acceptable.STRING_VALID);
                                if(newLocation.equals(facility.getLocation())){
                                    System.out.println("No changes made. The location is the same.");
                                } else {
                                    facility.setLocation(newLocation);
                                    System.out.println("Location updated successfully.");
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 2:
                        while (true) { 
                            try {
                                int newCapacity = inputter.getIntInput("Enter new capacity: ", Acceptable.INTERGER_VALID);
                                if(newCapacity == facility.getCapacity()){
                                    System.out.println("No changes made. The capacity is the same.");
                                } else {
                                    facility.setCapacity(newCapacity);
                                    System.out.println("Capacity updated successfully.");
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 3:
                        while (true) {
                            try {
                                String newAvailabilityStart = inputter.getStringInput("Enter new availability start (dd/mm/yyyy HH:MM): ", Acceptable.DATE_VALID);
                                LocalDateTime localDateTimeStart = LocalDateTime.parse(
                                    DateUtil.formatToIso(newAvailabilityStart)
                                );
                                LocalDateTime localDateTimeNewEnd = LocalDateTime.parse(
                                    DateUtil.formatToIso(facility.getAvailabilityEnd())
                                );
                                
                                if(newAvailabilityStart.equals(facility.getAvailabilityStart())){
                                    System.out.println("No changes made. The availability start is the same.");
                                }else if(Duration.between(localDateTimeStart, localDateTimeNewEnd).toMinutes() < 30){
                                    System.out.println("The availability period must be at least 30 minutes.");
                                } else {
                                    LocalDateTime localDateTimeEndDateTime = LocalDateTime.parse(DateUtil.formatToIso(facility.getAvailabilityEnd()));
                                    LocalDateTime localDateTimeNewStart = LocalDateTime.parse(DateUtil.formatToIso(newAvailabilityStart));
                                    if(localDateTimeNewStart.isAfter(localDateTimeEndDateTime)){
                                        System.out.println("New availability start must be before availability end (" + facility.getAvailabilityEnd() + ").");
                                        continue;
                                    }

                                    facility.setAvailabilityStart(newAvailabilityStart);
                                    System.out.println("Availability start updated successfully.");
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 4:
                        while (true) {
                            try {
                                String newAvailabilityEnd = inputter.getStringInput("Enter new availability end (dd/mm/yyyy HH:MM): ", Acceptable.DATE_VALID);
                                LocalDateTime localDateTimeEnd = LocalDateTime.parse(
                                    DateUtil.formatToIso(newAvailabilityEnd)
                                );

                                LocalDateTime localDateTimeNewStart = LocalDateTime.parse(
                                    DateUtil.formatToIso(facility.getAvailabilityStart())
                                );

                                if(newAvailabilityEnd.equals(facility.getAvailabilityEnd())){
                                    System.out.println("No changes made. The availability end is the same.");
                                }else if(Duration.between(localDateTimeNewStart, localDateTimeEnd).toMinutes() < 30){
                                    System.out.println("The availability period must be at least 30 minutes.");
                                }else {
                                    LocalDateTime localDateTimeStartDateTime = LocalDateTime.parse(DateUtil.formatToIso(facility.getAvailabilityStart()));
                                    LocalDateTime localDateTimeNewEnd = LocalDateTime.parse(DateUtil.formatToIso(newAvailabilityEnd));
                                    if(localDateTimeNewEnd.isBefore(localDateTimeStartDateTime)){
                                        System.out.println("New availability end must be after availability start (" + facility.getAvailabilityStart() + ").");
                                        continue;
                                    }


                                    facility.setAvailabilityEnd(newAvailabilityEnd);
                                    System.out.println("Availability end updated successfully.");
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    /**
     * Hiển thị danh sách tất cả cơ sở hiện có dưới dạng bảng trong console.
     * 
     * <p>Thông tin gồm: mã cơ sở, tên, loại, vị trí, sức chứa và thời gian hoạt động.
     * Dừng lại chờ người dùng nhấn phím để tiếp tục.
     */
    public void showAllFacilitysView(){
        Collection<FacilitySchedule> listFacility = facilityController.getAllFacility();
        System.out.println("============================================== List of Facilities & Services ===========================================");
        //format table header Facility Name | Type |Location | Capacity | Availability
        System.out.printf("| %-10s | %-20s | %-15s | %-30s | %-10s | %-15s |%n", "Facility Id", "Facility Name", "Type", "Location", "Capacity", "Availability");
        System.out.println("========================================================================================================================");
        if(listFacility.isEmpty()){
            System.out.println("No facility available.");
        } else {
            for(FacilitySchedule facility : listFacility){
                System.out.printf("| %-10s  | %-20s | %-15s | %-30s | %-10d | %-15s |%n",
                        facility.getId(),
                        facility.getFacilityName(),
                        facility.getFacilityType(),
                        facility.getLocation(),
                        facility.getCapacity(),
                        facility.getAvailabilityStart()
                );
            }
        }
        System.out.println("========================================================================================================================");
        System.out.print("Press any key to continue...");
        inputter.getStringWithoutRegex();
    }
}
