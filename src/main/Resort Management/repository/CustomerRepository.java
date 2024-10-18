package repository;

import Person.Customer;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class CustomerRepository implements ICustomerRepository {
    private List<Customer> customers = new ArrayList<>();

    private static final String EMPLOYEE_FILE = "C:\\Users\\USER\\OneDrive\\Desktop\\For Code\\JAVA code\\codeJava\\FuramaResort\\data\\Customer.csv";

    public CustomerRepository(){
        loadFromFileCUS();
    }

    private void loadFromFileCUS() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\USER\\OneDrive\\Desktop\\For Code\\JAVA code\\codeJava\\FuramaResort\\data\\Customer.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 9) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }

                Customer customer = new Customer(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim(),
                        data[6].trim(),
                        data[7].trim(),
                        data[8].trim()
                );

                customers.add(customer);
            }
        } catch (IOException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }
    }



    private void saveToFileCUS() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\USER\\OneDrive\\Desktop\\For Code\\JAVA code\\codeJava\\FuramaResort\\data\\Customer.csv"))) {
            bw.write("ID,Name,DateOfBirth,Gender,ID Card,Phone Number,Email,Customer Type,Address");
            bw.newLine();

            for (Customer customer : customers) {
                String customerCsv = String.join(",",
                        customer.getId(),
                        customer.getName(),
                        customer.getDateOfBirth(),
                        customer.getGender(),
                        customer.getIdCard(),
                        customer.getPhoneNumber(),
                        customer.getEmail(),
                        customer.getCustomerType(),
                        customer.getAddress()
                );
                bw.write(customerCsv);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving customer data: " + e.getMessage());
        }
    }


    @Override
    public List<Customer> getAll() {
        return customers;
    }

    @Override
    public Customer getById(String id) {
        for(Customer customer: customers){
            if(customer.getId().equals(id)){
                return customer;
            }
        }
        return null;
    }

    @Override
    public void add(Customer customer) {
        customers.add(customer);
        saveToFileCUS();
    }

    @Override
    public void update(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(customer.getId())) {
                customers.set(i, customer);
                saveToFileCUS();
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        customers.removeIf(customer -> customer.getId().equals(id));
        saveToFileCUS();
    }

    @Override
    public void save() {
    }
}
