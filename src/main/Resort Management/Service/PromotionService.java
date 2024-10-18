package Service;

import Person.Booking;
import Person.Customer;
import repository.BookingRepository;
import repository.CustomerRepository;

import java.time.LocalDate;
import java.util.*;

import static Validation.Validation.isNumeric;
import static Validation.Validation.isPositiveNumber;

public class PromotionService {
    private BookingRepository bookingRepository = new BookingRepository();
    private CustomerRepository customerRepository = new CustomerRepository();
    private Scanner scanner = new Scanner(System.in);

    // Hàm để phân phối voucher khuyến mãi
    public void distributeVouchers() {
        List<Customer> customersForVouchers = getCustomersForCurrentMonthBookings(); // Lấy danh sách khách hàng đã đặt dịch vụ trong tháng hiện tại

        if (customersForVouchers.isEmpty()) {
            System.out.println("No customers found for the current month.");
            return;
        }

        System.out.println("Enter the number of vouchers. The total number of vouchers must equal " + customersForVouchers.size() + " customers.");

        int tenPercentVouchers, twentyPercentVouchers, fiftyPercentVouchers;
        int totalVouchers;

        // Yêu cầu nhập số lượng voucher từ người dùng, đảm bảo dữ liệu nhập vào là số >=0 và tổng số voucher phải bằng số khách hàng
        while (true) {
            tenPercentVouchers = getValidVoucherInput("Enter number of 10% vouchers: ");
            twentyPercentVouchers = getValidVoucherInput("Enter number of 20% vouchers: ");
            fiftyPercentVouchers = getValidVoucherInput("Enter number of 50% vouchers: ");

            totalVouchers = tenPercentVouchers + twentyPercentVouchers + fiftyPercentVouchers;
            if (totalVouchers == customersForVouchers.size()) {
                break;
            } else {
                System.out.println("The total number of vouchers must equal the number of customers (" + customersForVouchers.size() + "). Please try again.");
            }
        }

        Stack<Customer> customerStack = new Stack<>();
        for (Customer customer : customersForVouchers) {
            customerStack.push(customer);
        }

        System.out.println("\n=== Voucher Distribution ===");

        distributeVoucher(customerStack, tenPercentVouchers, "10%");

        distributeVoucher(customerStack, twentyPercentVouchers, "20%");

        distributeVoucher(customerStack, fiftyPercentVouchers, "50%");
    }

    // Hàm để lấy danh sách khách hàng đã đặt dịch vụ trong tháng hiện tại
    private List<Customer> getCustomersForCurrentMonthBookings() {
        List<Customer> customers = new ArrayList<>();
        LocalDate now = LocalDate.now();

        // Lấy tất cả booking và lọc theo tháng hiện tại
        for (Booking booking : bookingRepository.getAllBookings()) {
            if (booking.getStartDate().getMonth() == now.getMonth() &&
                    booking.getStartDate().getYear() == now.getYear()) {
                Customer customer = customerRepository.getById(booking.getCustomerId());
                if (customer != null) {
                    customers.add(customer);
                }
            }
        }

        customers.sort(Comparator.comparing(Customer::getId));
        return customers;
    }

    // Hàm phân phối voucher và hiển thị kết quả
    private void distributeVoucher(Stack<Customer> customerStack, int numberOfVouchers, String voucherType) {
        if (numberOfVouchers > 0) {
            System.out.println("Distributing " + numberOfVouchers + " vouchers of " + voucherType + ":");
            for (int i = 0; i < numberOfVouchers; i++) {
                if (!customerStack.isEmpty()) {
                    Customer customer = customerStack.pop();
                    System.out.println(customer.getId() + " received a " + voucherType + " voucher.");
                }
            }
        }
    }

    // Hàm để hiển thị danh sách khách hàng đã sử dụng dịch vụ theo năm
    public void displayCustomersUsedServiceByYear() {
        System.out.print("Enter the year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        List<Customer> customersUsedService = new ArrayList<>();

        // Lọc booking theo năm và lấy danh sách khách hàng
        for (Booking booking : bookingRepository.getAllBookings()) {
            if (booking.getStartDate().getYear() == year) {
                Customer customer = customerRepository.getById(booking.getCustomerId());
                if (customer != null) {
                    customersUsedService.add(customer);
                }
            }
        }

        if (customersUsedService.isEmpty()) {
            System.out.println("No customers used the service in the year " + year);
        } else {
            System.out.println("=== List of customers who used service in " + year + " ===");
            customersUsedService.forEach(Customer::showInfo);
        }
    }

    // Hàm kiểm tra và yêu cầu nhập lại số lượng voucher hợp lệ (>= 0)
    private int getValidVoucherInput(String prompt) {
        int voucherCount;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (isNumeric(input)) {
                voucherCount = Integer.parseInt(input);
                if (isPositiveNumber(voucherCount)) {
                    return voucherCount;
                }
            }
            System.out.println("Invalid input. Please enter a positive number.");
        }
    }
}
