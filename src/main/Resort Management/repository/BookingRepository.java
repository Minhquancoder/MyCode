package repository;

import Person.Booking;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

public class BookingRepository implements IBookingRepository {
    private TreeSet<Booking> bookings = new TreeSet<>();
    private static final String BOOKING_FILE = "C:\\Users\\USER\\OneDrive\\Desktop\\For Code\\JAVA code\\codeJava\\FuramaResort\\data\\Booking.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BookingRepository() {
        loadFromFileBooking();
    }

    // Phương thức thêm booking mới
    @Override
    public void addBooking(Booking booking) {
        if (!bookingExists(booking.getBookingId())) {
            bookings.add(booking);
            saveToFileBooking();
            System.out.println("Booking added successfully!");
        } else {
            System.out.println("Booking ID already exists.");
        }
    }


    @Override
    public TreeSet<Booking> getAllBookings() {
        return bookings;
    }

    @Override
    public void loadFromFileBooking() {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 6) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }

                String bookingId = data[0].trim();
                LocalDate bookingDate = LocalDate.parse(data[1].trim(), DATE_FORMATTER);
                LocalDate startDate = LocalDate.parse(data[2].trim(), DATE_FORMATTER);
                LocalDate endDate = LocalDate.parse(data[3].trim(), DATE_FORMATTER);
                String customerId = data[4].trim();
                String serviceCode = data[5].trim();

                Booking booking = new Booking(bookingId, bookingDate, startDate, endDate, customerId, serviceCode);
                bookings.add(booking);
            }
        } catch (IOException e) {
            System.out.println("Error loading booking data: " + e.getMessage());
        }
    }

    // Lưu dữ liệu booking vào file CSV
    @Override
    public void saveToFileBooking() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKING_FILE))) {
            bw.write("BookingId,BookingDate,StartDate,EndDate,CustomerId,ServiceCode");
            bw.newLine();

            for (Booking booking : bookings) {
                String line = String.join(",",
                        booking.getBookingId(),
                        booking.getBookingDate().format(DATE_FORMATTER),
                        booking.getStartDate().format(DATE_FORMATTER),
                        booking.getEndDate().format(DATE_FORMATTER),
                        booking.getCustomerId(),
                        booking.getServiceCode()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving booking data: " + e.getMessage());
        }
    }

    // Kiểm tra booking tồn tại theo bookingId
    @Override
    public boolean bookingExists(String bookingId) {
        return bookings.stream().anyMatch(booking -> booking.getBookingId().equals(bookingId));
    }

    public Booking getBookingID(String bookingCode) {
        return bookings.stream()
                .filter(booking -> booking.getBookingId().equals(bookingCode))
                .findFirst()
                .orElse(null); // Trả về null nếu không tìm thấy
    }

}
