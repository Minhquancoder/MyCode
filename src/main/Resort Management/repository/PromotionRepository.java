package repository;

import Person.Booking;
import Person.Customer;
import repository.BookingRepository;
import repository.CustomerRepository;

import java.util.TreeSet;

public class PromotionRepository {
    // TreeSet để lưu trữ những khách hàng đã sử dụng dịch vụ
    private static TreeSet<Customer> customersUsedService = new TreeSet<>((c1, c2) -> c1.getId().compareTo(c2.getId()));

    // Static block to load customer data from booking
    static {
        loadCustomersUsedService();
    }

    // Phương pháp tải khách hàng từ booking
    private static void loadCustomersUsedService() {
        BookingRepository bookingRepository = new BookingRepository();
        CustomerRepository customerRepository = new CustomerRepository();

        for (Booking booking : bookingRepository.getAllBookings()) {
            Customer customer = customerRepository.getById(booking.getCustomerId());
            if (customer != null) {
                customersUsedService.add(customer);
            }
        }
    }

    //Phương pháp lấy khách hàng theo năm sử dụng dịch vụ
    public TreeSet<Customer> getCustomersUsedServiceByYear(int year) {
        TreeSet<Customer> result = new TreeSet<>((c1, c2) -> c1.getId().compareTo(c2.getId()));
        BookingRepository bookingRepository = new BookingRepository();

        // Lặp qua tất cả các lần đặt chỗ để tìm các lần đặt chỗ trong năm đã chỉ định
        for (Booking booking : bookingRepository.getAllBookings()) {
            if (booking.getStartDate().getYear() == year) {
                Customer customer = getCustomerByBooking(booking);
                if (customer != null) {
                    result.add(customer);
                }
            }
        }

        return result;
    }

    // Helper method to get customer from a booking
    private Customer getCustomerByBooking(Booking booking) {
        CustomerRepository customerRepository = new CustomerRepository();
        return customerRepository.getById(booking.getCustomerId());
    }

    // Method lấy danh sách khách hàng đã sử dụng dịch vụ
    public TreeSet<Customer> getAllCustomersUsedService() {
        return customersUsedService;
    }
}
