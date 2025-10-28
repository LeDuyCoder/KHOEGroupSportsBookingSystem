
package khoegroupsportsbookingsystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Đại diện cho một lượt đặt sân trong hệ thống KHOE Group Sports Booking System.
 * <p>
 * Một lượt đặt sân (booking) bao gồm thông tin về người chơi, lịch sân được đặt,
 * thời gian đặt và số lượng người tham gia.
 * </p>
 *
 * @author Lê Hữu Duy
 */
public class Booking implements Serializable{
    private String bookingId;
    private String playerName;
    private String facilityId;
    private LocalDateTime date;
    private int numberPerson;

    /**
     * Khởi tạo một đối tượng {@code Booking} mới với các thông tin được cung cấp.
     *
     * @param bookingId        mã đặt chỗ duy nhất
     * @param playerName       tên người chơi thực hiện đặt chỗ
     * @param facilitySchedule lịch sân được đặt
     * @param date             ngày và giờ đặt sân
     * @param numberPerson     số lượng người tham gia
     */
    public Booking(String bookingId, String playerName, String facilityId, LocalDateTime date, int numberPerson) {
        this.bookingId = bookingId;
        this.playerName = playerName;
        this.facilityId = facilityId;
        this.date = date;
        this.numberPerson = numberPerson;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getNumberPerson() {
        return numberPerson;
    }

    public void setNumberPerson(int numberPerson) {
        this.numberPerson = numberPerson;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Booking other = (Booking) obj;
        return Objects.equals(bookingId, other.bookingId)
                && Objects.equals(playerName, other.playerName)
                && Objects.equals(facilityId, other.facilityId)
                && Objects.equals(date, other.date)
                && numberPerson == other.numberPerson;
    }
}
