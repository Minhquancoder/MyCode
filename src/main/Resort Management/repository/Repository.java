package repository;

import Person.Person;

public interface Repository<C extends Person> {
    void save();
}
