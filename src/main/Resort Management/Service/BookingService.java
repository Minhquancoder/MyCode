package Service;

import Person.Booking;
import Person.Customer;
import repository.BookingRepository;
import repository.CustomerRepository;
import repository.FacilityRepository;
import Facility.Facility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static Validation.Validation.isValidDate;
import static Validation.Validation.isValidBookingId;

public class BookingService {
    private BookingRepository bookingRepository = new BookingRepository();
    private CustomerRepository customerRepository = new CustomerRepository();
    private FacilityRepository facilityRepository = new FacilityRepository();
    private Scanner scanner = new Scanner(System.in);

    public void addNewBooking() {
        String bookingId = getBookingId();

        // Kiểm tra định dạng của Booking ID
        if (!isValidBookingId(bookingId)) {
            System.out.println("Invalid Booking ID format. Please enter again.");
            return;
        }

        LocalDate bookingDate = LocalDate.now();
        LocalDate startDate = getStartDate(bookingDate);
        LocalDate endDate = getEndDate(startDate);

        // Kiểm tra xem booking có hợp lệ không
        if (!isBookingValid(startDate)) {
            System.out.println("Booking cannot be created (booking mới nhập vào trước ngày bắt đầu của một booking đã tồn tại trong hệ thống).");
            return;
        }

        // Hiển thị danh sách khách hàng
        System.out.println("=== List of Customers ===");
        customerRepository.getAll().forEach(Customer::showInfo);

        String customerId = getCustomerId();

        // Hiển thị danh sách dịch vụ
        System.out.println("=== List of Facilities ===");
        facilityRepository.getAll().keySet().forEach(facility -> facility.showInfor());

        // Nhận mã dịch vụ và kiểm tra tính hợp lệ
        String serviceCode;
        while (true) {
            serviceCode = getServiceCode();
            // Kiểm tra nếu serviceCode có trong danh sách dịch vụ
            String finalServiceCode = serviceCode;
            if (facilityRepository.getAll().keySet().stream().anyMatch(facility -> facility.getServiceCode().equals(finalServiceCode))) {
                break;
            } else {
                System.out.println("Service code not found. Please enter a valid service code in list.");
            }
        }
        Booking booking = new Booking(bookingId, bookingDate, startDate, endDate, customerId, serviceCode);
        bookingRepository.addBooking(booking);

        // Cập nhật số lần sử dụng cho dịch vụ đã booking
        facilityRepository.updateUsageCount(serviceCode);
        checkMaintenance();
    }

    private LocalDate getStartDate(LocalDate bookingDate) {
        LocalDate startDate;
        while (true) {
            System.out.print("Enter start date (dd/MM/yyyy): ");
            String input = scanner.nextLine().trim();
            if (isValidDate(input)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                startDate = LocalDate.parse(input, formatter);
                // Kiểm tra nếu startDate phải sau bookingDate
                if (startDate.isBefore(bookingDate)) {
                    System.out.println("Start date must be after the booking date: " + bookingDate);
                } else {
                    return startDate;
                }
            } else {
                System.out.println("Invalid date format. Please enter again.");
            }
        }
    }

    private LocalDate getEndDate(LocalDate startDate) {
        LocalDate endDate;
        while (true) {
            System.out.print("Enter end date (dd/MM/yyyy): ");
            String input = scanner.nextLine().trim();
            if (isValidDate(input)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                endDate = LocalDate.parse(input, formatter);
                // Kiểm tra nếu endDate phải sau startDate
                if (!endDate.isAfter(startDate)) {
                    System.out.println("End date must be after start date: " + startDate);
                } else {
                    return endDate;
                }
            } else {
                System.out.println("Invalid date format. Please enter again.");
            }
        }
    }

    public String getBookingId() {
        String bookingId;
        while (true) {
            System.out.print("Enter booking ID (format: BK1, BK2, etc.): ");
            bookingId = scanner.nextLine().trim();
            if (isValidBookingId(bookingId)) {
                return bookingId;
            } else {
                System.out.println("Invalid Booking ID format. Please enter again.");
                System.out.println("Format should be in format BK + number (BK1, BK2).");
            }
        }
    }

    private String getCustomerId() {
        String customerId;
        while (true) {
            System.out.print("Enter customer ID: ");
            customerId = scanner.nextLine().trim();

            // Kiểm tra xem customerId có trong danh sách khách hàng không
            String finalCustomerId = customerId;
            boolean customerExists = customerRepository.getAll().stream()
                    .anyMatch(customer -> customer.getId().equals(finalCustomerId));

            if (customerExists) {
                return customerId;
            } else {
                System.out.println("Customer ID not found. Please enter a valid customer ID in list.");
            }
        }
    }


    private String getServiceCode() {
        System.out.print("Enter service code: ");
        return scanner.nextLine().trim();
    }

    public void displayBookings() {
        System.out.println("=== List of Bookings ===");
        bookingRepository.getAllBookings().forEach(booking -> System.out.println(booking));
    }

    private void checkMaintenance() {
        System.out.println("=== Facilities Needing Maintenance ===");
        for (Facility facility : facilityRepository.getAll().keySet()) {
            int usageCount = facilityRepository.getAll().get(facility);
            if (usageCount >= 5) {
                System.out.println(facility.getServiceName() + " (Service Code: " + facility.getServiceCode() + ") needs maintenance.");
            }
        }
    }

    private boolean isBookingValid(LocalDate startDate) {
        for (Booking existingBooking : bookingRepository.getAllBookings()) {
            // Nếu ngày bắt đầu của booking mới trước ngày bắt đầu của một booking đã có, không hợp lệ
            if (startDate.isBefore(existingBooking.getStartDate())) {
                return false;
            }
        }
        return true;
    }

}
