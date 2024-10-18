package repository;

import Person.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IEmployeeRepository {
    private static final String EMPLOYEE_FILE = "C:\\Users\\USER\\OneDrive\\Desktop\\For Code\\JAVA code\\codeJava\\FuramaResort\\data\\Employee.csv";

    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        loadFromFile();
    }

    @Override
    public List<Employee> getAll() {
        return employees;
    }

    @Override
    public Employee getById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public void add(Employee employee) {
        employees.add(employee);
        saveToFile();
    }

    @Override
    public void update(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(employee.getId())) {
                employees.set(i, employee);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        employees.removeIf(employee -> employee.getId().equals(id));
        saveToFile();
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 10) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }
                Employee employee = new Employee(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim(),
                        data[6].trim(),
                        data[7].trim(),
                        data[8].trim(),
                        Double.parseDouble(data[9].trim())
                );
                employees.add(employee);
            }
        } catch (IOException e) {
            System.out.println("Error loading employee data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing salary: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EMPLOYEE_FILE))) {
            bw.write("ID,Name,DateOfBirth,Gender,ID Card,Phone Number,Email,Education Level,Position,Salary");
            bw.newLine();

            for (Employee employee : employees) {
                String employeeCsv = String.join(",",
                        employee.getId(),
                        employee.getName(),
                        employee.getDateOfBirth(),
                        employee.getGender(),
                        employee.getIdCard(),
                        employee.getPhoneNumber(),
                        employee.getEmail(),
                        employee.getQualification(),
                        employee.getPosition(),
                        String.valueOf(employee.getSalary())
                );
                bw.write(employeeCsv);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving employee data: " + e.getMessage());
        }
    }

    @Override
    public void save() {

    }
}
