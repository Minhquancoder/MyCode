package Service;

import Person.Employee;
import repository.IEmployeeRepository;
import repository.EmployeeRepository;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static Validation.Validation.*;

public class EmployeeService implements IEmployeeService {

    private IEmployeeRepository employeeRepository = new EmployeeRepository();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void display() {
        List<Employee> employees = employeeRepository.getAll();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                employee.showInfo();
            }
        }
    }

    @Override
    public void addNew() {
        while (true) {
            Employee employee = inputEmployee();
            boolean exists = employeeRepository.getAll().stream()
                    .anyMatch(e -> e.getId().equalsIgnoreCase(employee.getId()) || e.getName().equalsIgnoreCase(employee.getName()));
            if (exists) {
                System.out.println("Employee with ID " + employee.getId() + " or name " + employee.getName() + " already exists. Please enter a different ID or name.");
            } else {
                employeeRepository.add(employee);
                System.out.println("Employee added successfully!");
                break;
            }
        }
    }

    @Override
    public void update() {
        // Hiển thị danh sách nhân viên trước khi cập nhật
        display();

        Employee existingEmployee;
        while (true) {
            System.out.print("Enter employee ID to update: ");
            String id = scanner.nextLine();
            existingEmployee = employeeRepository.getById(id);
            if (existingEmployee != null) {
                break;
            } else {
                System.out.println("Employee ID not found. Please enter a valid ID from the list.");
            }
        }

        System.out.println("Editing employee: " + existingEmployee.getName());
        Employee updatedEmployee = inputEmployeeUpdate(existingEmployee);
        updatedEmployee.setId(existingEmployee.getId());
        employeeRepository.update(updatedEmployee);
        System.out.println("Employee updated successfully!");
    }

    @Override
    public void delete() {
        System.out.print("Enter employee ID to delete: ");
        String id = scanner.nextLine();
        Employee existingEmployee = employeeRepository.getById(id);
        if (existingEmployee != null) {
            employeeRepository.delete(id);
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("Employee with ID " + id + " does not exist in the list.");
        }
    }

    // Phương thức nhập thông tin nhân viên mới
    private Employee inputEmployee() {
        String id, name, dateOfBirth, gender, idCard, phoneNumber, email, qualification, position;
        double salary;

        // Kiểm tra mã nhân viên (Nv-4 chữ số)
        while (true) {
            System.out.print("Enter employee ID (Nv-4 chữ số): ");
            id = scanner.nextLine();
            if (Pattern.matches("Nv-\\d{4}", id)) break;
            else System.out.println("Invalid ID format.");
        }

        // Kiểm tra định dạng tên
        while (true) {
            System.out.print("Enter name (viết hoa chữ cái đầu và không nhận tiếng việt): ");
            name = scanner.nextLine();
            if (Pattern.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$", name)) break;
            else System.out.println("Invalid name format. Capitalize the first letter of each word.");
        }

        // Kiểm tra tuổi (đủ 18)
        while (true) {
            System.out.print("Enter date of birth (dd/MM/yyyy): ");
            dateOfBirth = scanner.nextLine();
            if (isValidAge(dateOfBirth)) break;
            else System.out.println("Employee must be at least 18 years old.");
        }

        // Kiểm tra CMND (9 hoặc 12 số)
        while (true) {
            System.out.print("Enter ID card number (9 or 12 digits): ");
            idCard = scanner.nextLine().trim();
            if (isValidIdCard(idCard)) {
                break;
            } else {
                System.out.println("Invalid ID card number. It must be either 9 or 12 digits.");
            }
        }

        // Kiểm tra số điện thoại (bắt đầu bằng 0, đủ 10 số)
        while (true) {
            System.out.print("Enter phone number (10 digits, starts with 0): ");
            phoneNumber = scanner.nextLine();
            if (isValidPhoneNumber(phoneNumber)) break;
            else System.out.println("Invalid phone number.");
        }

        // Nhập giới tính
        while (true) {
            System.out.print("Enter gender (male/female): ");
            gender = scanner.nextLine().trim();
            if (isValidGender(gender)) {
                break;
            } else {
                System.out.println("Invalid gender. Please enter 'male' or 'female'.");
            }
        }

        // Nhập email
        while (true) {
            System.out.print("Enter email (VD: nguyenvana@gmail.com): ");
            email = scanner.nextLine();
            if (isValidEmail(email)) break;
            else System.out.println("Invalid email format.");
        }

        // Nhập trình độ học vấn
        while (true) {
            System.out.print("Enter education level (Qualification): ");
            qualification = scanner.nextLine().trim();
            if (!qualification.matches("\\d+")) break;
            else System.out.println("Invalid input. Please enter a valid education level (not a number).");
        }

        // Nhập vị trí
        while (true) {
            System.out.print("Enter position: ");
            position = scanner.nextLine().trim();
            if (!position.matches("\\d+")) break;
            else System.out.println("Invalid input. Please enter a valid position (not a number).");
        }

        // Kiểm tra lương (phải > 0)
        while (true) {
            System.out.print("Enter salary: ");
            try {
                salary = Double.parseDouble(scanner.nextLine());
                if (salary > 0) break;
                else System.out.println("Salary must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid salary.");
            }
        }
        return new Employee(id, name, dateOfBirth, gender, idCard, phoneNumber, email, qualification, position, salary);
    }

    // Phương thức cập nhật thông tin nhân viên hiện có
    private Employee inputEmployeeUpdate(Employee existingEmployee) {
        String name, dateOfBirth, gender, idCard, phoneNumber, email, qualification, position;
        double salary;

        // Cho phép người dùng cập nhật từng thông tin, nếu không nhập thì giữ nguyên
        System.out.print("Enter name (current: " + existingEmployee.getName() + "): ");
        name = scanner.nextLine().trim();
        if (name.isEmpty()) name = existingEmployee.getName();

        System.out.print("Enter date of birth (current: " + existingEmployee.getDateOfBirth() + "): ");
        dateOfBirth = scanner.nextLine().trim();
        if (dateOfBirth.isEmpty() || !isValidAge(dateOfBirth)) dateOfBirth = existingEmployee.getDateOfBirth();

        System.out.print("Enter gender (current: " + existingEmployee.getGender() + "): ");
        gender = scanner.nextLine().trim();
        if (gender.isEmpty() || !isValidGender(gender)) gender = existingEmployee.getGender();

        System.out.print("Enter ID card number (current: " + existingEmployee.getIdCard() + "): ");
        idCard = scanner.nextLine().trim();
        if (idCard.isEmpty() || !isValidIdCard(idCard)) idCard = existingEmployee.getIdCard();

        System.out.print("Enter phone number (current: " + existingEmployee.getPhoneNumber() + "): ");
        phoneNumber = scanner.nextLine().trim();
        if (phoneNumber.isEmpty() || !isValidPhoneNumber(phoneNumber)) phoneNumber = existingEmployee.getPhoneNumber();

        System.out.print("Enter email (current: " + existingEmployee.getEmail() + "): ");
        email = scanner.nextLine().trim();
        if (email.isEmpty() || !isValidEmail(email)) email = existingEmployee.getEmail();

        System.out.print("Enter education level (current: " + existingEmployee.getQualification() + "): ");
        qualification = scanner.nextLine().trim();
        if (qualification.isEmpty()) qualification = existingEmployee.getQualification();

        System.out.print("Enter position (current: " + existingEmployee.getPosition() + "): ");
        position = scanner.nextLine().trim();
        if (position.isEmpty()) position = existingEmployee.getPosition();

        System.out.print("Enter salary (current: " + existingEmployee.getSalary() + "): ");
        try {
            salary = Double.parseDouble(scanner.nextLine().trim());
            if (salary <= 0) salary = existingEmployee.getSalary();
        } catch (NumberFormatException e) {
            salary = existingEmployee.getSalary();
        }

        return new Employee(existingEmployee.getId(), name, dateOfBirth, gender, idCard, phoneNumber, email, qualification, position, salary);
    }
}
