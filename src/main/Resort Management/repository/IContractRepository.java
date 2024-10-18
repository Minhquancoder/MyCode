package repository;

import Person.Contract;

import java.util.List;

public interface IContractRepository {

    List<Contract> getAllContracts();

    void addContract(Contract contract);

    Contract getContractByCode(String contractCode);

//    void updateContract(String contractCode);

    void updateContract(Contract contract);
}
