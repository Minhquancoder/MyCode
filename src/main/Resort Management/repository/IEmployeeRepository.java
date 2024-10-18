package repository;

import Person.Employee;
import java.util.List;

public interface IEmployeeRepository extends Repository<Employee> {

    List<Employee> getAll();

    Employee getById(String id);

    void add(Employee employee);

    void update(Employee employee);

    void delete(String id);
}
