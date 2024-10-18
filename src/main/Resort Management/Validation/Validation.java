package Validation;

import Person.Booking;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Validation {

    // Kiểm tra xem đầu vào có phải là số hay không
    public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    // Kiểm tra số dương
    public static boolean isPositiveNumber(double number) {
        return number >= 0;
    }

    // Kiểm tra tuổi có đủ 18 hay không
    public static boolean isValidAge(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate birthDate = LocalDate.parse(dateOfBirth, formatter);
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(birthDate, currentDate).getYears();
            return age >= 18;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
            return false;
        }
    }

    // Kiểm tra định dạng ngày
    public static boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
            return false;
        }
    }

    // Kiểm tra CMND (9 hoặc 12 số)
    public static boolean isValidIdCard(String idCard) {
        return idCard.matches("\\d{9}|\\d{12}");
    }

    // Kiểm tra số điện thoại (bắt đầu từ 0 và đủ 10 số)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("0\\d{9}");
    }

    // Kiểm tra giới tính
    public static boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female");
    }

    // Kiểm tra định dạng email
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // Kiểm tra địa chỉ nhà (số nhà - tên đường)
    public static boolean isValidAddress(String address) {
        String addressRegex = "^\\d+\\s*-\\s*[A-Za-z\\s]+$";
        return address.matches(addressRegex);
    }

    // Kiểm tra kiểu khách hàng (Customer Type)
    public static boolean isValidCustomerType(String customerType) {
        return customerType.equalsIgnoreCase("Diamond") ||
                customerType.equalsIgnoreCase("Platinum") ||
                customerType.equalsIgnoreCase("Gold") ||
                customerType.equalsIgnoreCase("Silver") ||
                customerType.equalsIgnoreCase("Member");
    }

    // Kiểm tra định dạng mã hợp đồng phù hợp.
    public static boolean isValidContractCode(String contractCode) {
        return contractCode.matches("^CT-\\d{4}$");
    }

    public static boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Kiểm tra định dạng mã đặt phòng
    public static boolean isValidBookingId(String bookingId) {
        return bookingId.matches("^BK\\d{1,3}$");
    }

    // Kiểm tra định dạng mã khách hàng
    public static boolean isValidCustomerId(String customerId) {
        return customerId.matches("^CUS-\\d{4}$");
    }

    // Kiểm tra định dạng mã dịch vụ
    public static boolean isValidServiceCode(String serviceCode) {
        return serviceCode.matches("^SV[VLRO]-\\d{4}$");
    }

    // Kiểm tra định dạng mã booking code (BC1, BC2)
    public static boolean isValidBookingCode(String bookingCode, TreeSet<Booking> bookings) {
        return bookings.stream().anyMatch(booking -> booking.getBookingId().equalsIgnoreCase(bookingCode));
    }

}
