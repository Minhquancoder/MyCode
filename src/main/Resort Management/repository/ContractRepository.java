package repository;

import Person.Contract;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContractRepository implements IContractRepository {
    private static final String CONTRACT_FILE_PATH = "C:\\Users\\USER\\OneDrive\\Desktop\\For Code\\JAVA code\\codeJava\\FuramaResort\\data\\contract.csv";
    private List<Contract> contracts = new ArrayList<>();

    // Constructor to load contracts from file at the beginning
    public ContractRepository() {
        loadContractsFromFile();
    }

    @Override
    public List<Contract> getAllContracts() {
        return contracts;
    }

    @Override
    public void addContract(Contract contract) {
        contracts.add(contract);
        saveContractsToFile();
    }

    @Override
    public Contract getContractByCode(String contractCode) {
        for (Contract contract : contracts) {
            if (contract.getContractCode().equals(contractCode)) {
                return contract;
            }
        }
        return null;
    }

    @Override
    public void updateContract(Contract contract) {
        for (int i = 0; i < contracts.size(); i++) {
            if (contracts.get(i).getContractCode().equals(contract.getContractCode())) {
                contracts.set(i, contract);
                saveContractsToFile();
                break;
            }
        }
    }

    // Method to save contracts to a file
    private void saveContractsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONTRACT_FILE_PATH))) {
            for (Contract contract : contracts) {
                bw.write(contract.getContractCode() + "," + contract.getBookingCode() + "," +
                        contract.getDeposit() + "," + contract.getTotalPayment());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contracts: " + e.getMessage());
        }
    }

    // Method to load contracts from the file
    private void loadContractsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(CONTRACT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Contract contract = new Contract(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                    contracts.add(contract);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading contracts: " + e.getMessage());
        }
    }
}
