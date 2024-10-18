package repository;

import Person.Booking;
import java.util.TreeSet;

public interface IBookingRepository {
    void addBooking(Booking booking); // Thêm booking mới

    TreeSet<Booking> getAllBookings(); // Lấy toàn bộ danh sách booking

    void loadFromFileBooking(); // Tải dữ liệu từ file

    void saveToFileBooking(); // Lưu dữ liệu vào file

    boolean bookingExists(String bookingId); // Kiểm tra booking có tồn tại không
}
