package Service;

import Facility.Facility;
import Person.Contract;
import Person.Booking;
import repository.ContractRepository;
import repository.BookingRepository;
import repository.FacilityRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Scanner;

import static Validation.Validation.*;

public class ContractService implements IContractService {
    private ContractRepository contractRepository = new ContractRepository();
    private BookingRepository bookingRepository = new BookingRepository();
    private BookingService bookingService = new BookingService();
    private FacilityRepository facilityRepository = new FacilityRepository();
    private FacilityService facilityService = new FacilityService();
    private Scanner scanner = new Scanner(System.in);

    // Method to add a new contract
    @Override
    public void addNewContract() {
        String contractCode;
        while (true) {
            System.out.print("Enter Contract Code (e.g., CT-0001): ");
            contractCode = scanner.nextLine().trim();
            if (isValidContractCode(contractCode)) {
                break;
            } else {
                System.out.println("Invalid Contract Code. Format should be CT-XXXX.");
            }
        }

        System.out.println("=== List of Available Booking Codes ===");
        bookingRepository.getAllBookings().forEach(booking -> System.out.println(booking.getBookingId()));  // Hiển thị mã Booking
        String bookingCode;
        while (true) {
            System.out.print("Enter Booking Code from the list (e.g., BK1, BK2): ");
            bookingCode = scanner.nextLine().trim();
            // Kiểm tra nếu bookingCode có trong danh sách booking
            if (isValidBookingCode(bookingCode, bookingRepository.getAllBookings())) {
                break;
            } else {
                System.out.println("Booking Code not found. Please enter a valid code in the list.");
            }
        }

        // Tính tổng tiền và tiền cọc
        double totalPayment = calculateTotalPayment(bookingCode);
        if (totalPayment == 0) {
            System.out.println("Error: Unable to calculate total payment. Invalid rental type or booking information.");
            return;
        }
        double deposit = totalPayment * 0.1; // Tiền cọc là 10% của tổng tiền

        Contract newContract = new Contract(contractCode, bookingCode, deposit, totalPayment);
        contractRepository.addContract(newContract);
        System.out.println("Contract added successfully! Deposit: " + deposit + ", Total Payment: " + totalPayment);
    }

    // Tính tổng tiền phải trả dựa trên mã booking và kiểu thuê
    private double calculateTotalPayment(String bookingCode) {
        // Lấy thông tin booking từ BookingRepository
        Booking booking = bookingRepository.getBookingID(bookingCode);
        if (booking == null) {
            System.out.println("Booking not found.");
            return 0;
        }
        String serviceCode = booking.getServiceCode();
        Facility facility = facilityRepository.getByServiceCode(serviceCode);
        if (facility == null) {
            System.out.println("Service not found.");
            return 0;
        }

        // Lấy chi phí thuê và loại thuê
        double rentalCost = facility.getRentCost();
        String rentalType = facility.getRentType();

        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        long duration;

        // Tính toán tổng tiền dựa trên kiểu thuê là "day", "week", hoặc "month"
        switch (rentalType.toLowerCase()) {
            case "day":
                duration = endDate.toEpochDay() - startDate.toEpochDay();
                return duration * rentalCost;

            case "week":
                duration = (endDate.toEpochDay() - startDate.toEpochDay()) / 7;
                return duration * rentalCost;

            case "month":
                duration = Period.between(startDate, endDate).toTotalMonths();
                return duration * rentalCost;

            default:
                System.out.println("Invalid rental type.");
                return 0;
        }
    }

    @Override
    public void displayAllContracts() {
        List<Contract> contracts = contractRepository.getAllContracts();

        if (contracts.isEmpty()) {
            System.out.println("No contracts available.");
        } else {
            System.out.println("=== List of Contracts ===");
            for (Contract contract : contracts) {
                System.out.println(contract);
            }
        }
    }

    // Edit Contract
    @Override
    public void editContract() {
        displayAllContracts();
        System.out.print("Enter Contract Code to edit: ");
        String contractCode = scanner.nextLine().trim();

        Contract contract = contractRepository.getContractByCode(contractCode);
        if (contract == null) {
            System.out.println("Contract not found.");
            return;
        }

        System.out.println("Editing contract: " + contract);
        System.out.println("Choose field to update: ");
        System.out.println("1. Booking Code");
        System.out.println("2. Deposit");
        System.out.println("3. Total Payment");
        System.out.println("4. Cancel");

        int choice = Integer.parseInt(scanner.nextLine().trim());
        switch (choice) {
            case 1:
                String newBookingCode;
                while (true) {
                    System.out.print("Enter new Booking Code (e.g., BK1, BK2): ");
                    newBookingCode = scanner.nextLine().trim();
                    // Kiểm tra nếu bookingCode có trong danh sách booking
                    if (isValidBookingCode(newBookingCode, bookingRepository.getAllBookings())) {
                        contract.setBookingCode(newBookingCode); // Cập nhật booking code
                        break; // Thoát khỏi vòng lặp nếu tìm thấy booking
                    } else {
                        System.out.println("Booking Code not found. Please enter a valid code.");
                    }
                }
                break;

            case 2:
                double newDeposit;
                while (true) {
                    System.out.print("Enter new Deposit (must be a positive number): ");
                    String input = scanner.nextLine().trim();
                    if (isValidDouble(input)) {
                        newDeposit = Double.parseDouble(input);
                        if (isPositiveNumber(newDeposit)) {
                            contract.setDeposit(newDeposit);
                            break;
                        } else {
                            System.out.println("Deposit must be a positive number.");
                        }
                    } else {
                        System.out.println("Invalid deposit amount.");
                    }
                }
                break;

            case 3:
                double newTotalPayment;
                while (true) {
                    System.out.print("Enter new Total Payment (must be a positive number): ");
                    String input = scanner.nextLine().trim();
                    if (isValidDouble(input)) {
                        newTotalPayment = Double.parseDouble(input);
                        if (isPositiveNumber(newTotalPayment)) {
                            contract.setTotalPayment(newTotalPayment);
                            break;
                        } else {
                            System.out.println("Total payment must be a positive number.");
                        }
                    } else {
                        System.out.println("Invalid total payment amount.");
                    }
                }
                break;

            case 4:
                System.out.println("Edit cancelled.");
                return;

            default:
                System.out.println("Invalid option.");
                return;
        }

        contractRepository.updateContract(contract);
        System.out.println("Contract updated successfully!");
    }
}
