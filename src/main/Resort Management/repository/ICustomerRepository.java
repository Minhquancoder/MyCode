package repository;

import Person.Customer;

import java.util.List;

public interface ICustomerRepository extends Repository<Customer> {
    List<Customer> getAll();

    Customer getById(String id);

    void add(Customer customer);

    void update(Customer customer);

    void delete(String id);

}