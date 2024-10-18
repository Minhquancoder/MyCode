package Service;

import Person.Customer;
import repository.CustomerRepository;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static Validation.Validation.*;

public class CustomerService implements ICustomerService {

    private repository.CustomerRepository CustomerRepository = new CustomerRepository();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void displayCus() {
        List<Customer> customers = CustomerRepository.getAll();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer customer : customers) {
                customer.showInfo();
            }
        }
    }

    @Override
    public void addCus() {
        while (true) {
            Customer customer = inputCustomer();
            boolean exists = CustomerRepository.getAll().stream()
                    .anyMatch(e -> e.getId().equalsIgnoreCase(customer.getId()) || e.getName().equalsIgnoreCase(customer.getName()));
            if (exists) {
                System.out.println("Customer with ID " + customer.getId() + " or name " + customer.getName() + " already exists. Please enter a different ID or name.");
            } else {
                CustomerRepository.add(customer);
                System.out.println("Customer added successfully!");
                break;
            }
        }
    }

    @Override
    public void UpdateCus() {
        // Hiển thị danh sách khách hàng trước khi cập nhật
        displayCus();
        Customer existingCustomer;
        while (true) {
            System.out.print("Enter customer ID you want to update: ");
            String id = scanner.nextLine();
            existingCustomer = CustomerRepository.getById(id);
            if (existingCustomer != null) {
                break;
            } else {
                System.out.println("Customer ID not found. Please enter a valid ID from the list.");
            }
        }

        System.out.println("Editing customer: " + existingCustomer.getName());
        Customer updatedCustomer = inputCustomerUpdate(existingCustomer);
        updatedCustomer.setId(existingCustomer.getId());
        CustomerRepository.update(updatedCustomer);
        System.out.println("Customer updated successfully!");
    }

    private Customer inputCustomer() {
        String id, name, dateOfBirth, gender, idCard, phoneNumber, email, customerType, address;

        while (true) {
            System.out.print("Enter customer ID (KH-4 chữ số): ");
            id = scanner.nextLine();
            if (Pattern.matches("KH-\\d{4}", id)) break;
            else System.out.println("Invalid ID format.");
        }

        while (true) {
            System.out.print("Enter name (viết hoa chữ cái đầu và không nhận tiếng việt): ");
            name = scanner.nextLine();
            if (Pattern.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$", name)) break;
            else System.out.println("Invalid name format. Capitalize the first letter of each word.");
        }

        while (true) {
            System.out.print("Enter date of birth (dd/MM/yyyy): ");
            dateOfBirth = scanner.nextLine();
            if (isValidAge(dateOfBirth)) break;
            else System.out.println("Customer must be at least 18 years old.");
        }

        while (true) {
            System.out.print("Enter ID card number (9 or 12 digits): ");
            idCard = scanner.nextLine().trim();
            if (isValidIdCard(idCard)) {
                break;
            } else {
                System.out.println("Invalid ID card number. It must be either 9 or 12 digits.");
            }
        }

        while (true) {
            System.out.print("Enter phone number (10 digits, starts with 0): ");
            phoneNumber = scanner.nextLine();
            if (isValidPhoneNumber(phoneNumber)) break;
            else System.out.println("Invalid phone number.");
        }

        while (true) {
            System.out.print("Enter gender (male/female): ");
            gender = scanner.nextLine().trim();
            if (isValidGender(gender)) {
                break;
            } else {
                System.out.println("Invalid gender. Please enter 'male' or 'female'.");
            }
        }

        while (true) {
            System.out.print("Enter email (VD: nguyenvana@gmail.com): ");
            email = scanner.nextLine();
            if (isValidEmail(email)) break;
            else System.out.println("Invalid email format.");
        }

        while (true) {
            System.out.print("Enter customer type (Diamond, Platinum, Gold, Silver, Member): ");
            customerType = scanner.nextLine().trim();
            if (isValidCustomerType(customerType)) {
                break;
            } else {
                System.out.println("Invalid customer type. Please enter one of the following: Diamond, Platinum, Gold, Silver, Member.");
            }
        }

        while (true) {
            System.out.print("Enter address (format: số nhà - tên đường và không nhận tiếng việt): ");
            address = scanner.nextLine().trim();
            if (isValidAddress(address)) {
                break;
            } else {
                System.out.println("Invalid address format. Please enter in format: số nhà- tên đường.");
            }
        }
        return new Customer(id, name, dateOfBirth, gender, idCard, phoneNumber, email, customerType, address);
    }

    // Phương thức cập nhật khách hàng
    private Customer inputCustomerUpdate(Customer existingCustomer) {
        String name, dateOfBirth, gender, idCard, phoneNumber, email, customerType, address;

        // Cập nhật từng thông tin, giữ nguyên nếu không nhập
        System.out.print("Enter name (current: " + existingCustomer.getName() + "): ");
        name = scanner.nextLine().trim();
        if (name.isEmpty()) name = existingCustomer.getName();

        System.out.print("Enter date of birth (current: " + existingCustomer.getDateOfBirth() + "): ");
        dateOfBirth = scanner.nextLine().trim();
        if (dateOfBirth.isEmpty() || !isValidAge(dateOfBirth)) dateOfBirth = existingCustomer.getDateOfBirth();

        System.out.print("Enter gender (current: " + existingCustomer.getGender() + "): ");
        gender = scanner.nextLine().trim();
        if (gender.isEmpty() || !isValidGender(gender)) gender = existingCustomer.getGender();

        System.out.print("Enter ID card number (current: " + existingCustomer.getIdCard() + "): ");
        idCard = scanner.nextLine().trim();
        if (idCard.isEmpty() || !isValidIdCard(idCard)) idCard = existingCustomer.getIdCard();

        System.out.print("Enter phone number (current: " + existingCustomer.getPhoneNumber() + "): ");
        phoneNumber = scanner.nextLine().trim();
        if (phoneNumber.isEmpty() || !isValidPhoneNumber(phoneNumber)) phoneNumber = existingCustomer.getPhoneNumber();

        System.out.print("Enter email (current: " + existingCustomer.getEmail() + "): ");
        email = scanner.nextLine().trim();
        if (email.isEmpty() || !isValidEmail(email)) email = existingCustomer.getEmail();

        System.out.print("Enter customer type (current: " + existingCustomer.getCustomerType() + "): ");
        customerType = scanner.nextLine().trim();
        if (customerType.isEmpty() || !isValidCustomerType(customerType)) customerType = existingCustomer.getCustomerType();

        System.out.print("Enter address (current: " + existingCustomer.getAddress() + "): ");
        address = scanner.nextLine().trim();
        if (address.isEmpty() || !isValidAddress(address)) address = existingCustomer.getAddress();

        return new Customer(existingCustomer.getId(), name, dateOfBirth, gender, idCard, phoneNumber, email, customerType, address);
    }

    @Override
    public void display() {
        displayCus();
    }

    public static boolean isValidCustomerType(String customerType) {
        String[] validCustomerTypes = {"Diamond", "Platinum", "Gold", "Silver", "Member"};
        for (String validType : validCustomerTypes) {
            if (customerType.equalsIgnoreCase(validType)) {
                return true;
            }
        }
        return false;
    }
}
