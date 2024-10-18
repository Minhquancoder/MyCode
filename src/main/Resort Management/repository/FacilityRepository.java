package repository;

import Facility.Facility;
import Facility.Villa;
import Facility.House;
import Facility.Room;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class FacilityRepository implements IFacilityRepository {

    private static final String FILE_PATH = "C:\\Users\\USER\\OneDrive\\Desktop\\For Code\\JAVA code\\codeJava\\FuramaResort\\data\\facility.csv";
    private static Map<Facility, Integer> facilityMap = new LinkedHashMap<>();

    // Static block để khởi tạo dữ liệu từ file CSV
    static {
        loadFromFile();
    }

    // Phương thức đọc dữ liệu từ file CSV và khởi tạo facilityMap
    private static void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 8) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }

                String serviceCode = data[1].trim();
                String serviceName = data[2].trim();
                double usableArea;
                double rentCost;
                int maxPeople;
                String rentType;

                try {
                    usableArea = Double.parseDouble(data[3].trim());
                    rentCost = Double.parseDouble(data[4].trim());
                    maxPeople = Integer.parseInt(data[5].trim());
                    rentType = data[6].trim();
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number in line: " + line);
                    continue;
                }

                Facility facility = null;

                if (serviceCode.startsWith("SVVL")) { // Villa
                    if (data.length != 10) {
                        System.out.println("Invalid Villa data: " + line);
                        continue;
                    }
                    String roomStandardVilla = data[7].trim();
                    double poolArea;
                    int numberOfFloorsVilla;
                    try {
                        poolArea = Double.parseDouble(data[8].trim());
                        numberOfFloorsVilla = Integer.parseInt(data[9].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing number in Villa data: " + line);
                        continue;
                    }
                    facility = new Villa(serviceCode, serviceName, usableArea, rentCost,
                            maxPeople, rentType, roomStandardVilla, poolArea, numberOfFloorsVilla);

                } else if (serviceCode.startsWith("SVHO")) { // House
                    if (data.length != 9) {
                        System.out.println("Invalid House data: " + line);
                        continue;
                    }
                    String roomStandardHouse = data[7].trim();
                    int numberOfFloorsHouse;
                    try {
                        numberOfFloorsHouse = Integer.parseInt(data[8].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing number in House data: " + line);
                        continue;
                    }
                    facility = new House(serviceCode, serviceName, usableArea, rentCost,
                            maxPeople, rentType, roomStandardHouse, numberOfFloorsHouse);

                } else if (serviceCode.startsWith("SVRO")) { // Room
                    if (data.length != 8) {
                        System.out.println("Invalid Room data: " + line);
                        continue;
                    }
                    String freeService = data[7].trim();
                    facility = new Room(serviceCode, serviceName, usableArea, rentCost,
                            maxPeople, rentType, freeService);
                } else {
                    System.out.println("Unknown facility type identifier: " + serviceCode + " in line: " + line);
                    continue;
                }

                if (facility != null) {
                    facilityMap.put(facility, 0); // Khởi tạo số lần sử dụng là 0
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading facility data: " + e.getMessage());
        }
    }

    @Override
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("Type,ServiceCode,ServiceName,UsableArea,RentCost,MaxPeople,RentType,RoomStandard,PoolArea,NumberOfFloors,FreeService");
            bw.newLine();

            for (Map.Entry<Facility, Integer> entry : facilityMap.entrySet()) {
                Facility facility = entry.getKey();
                String line = "";

                if (facility instanceof Villa) {
                    Villa villa = (Villa) facility;
                    line = String.join(",",
                            "SVVL", // Villa identifier
                            villa.getServiceCode(),
                            villa.getServiceName(),
                            String.valueOf(villa.getUsableArea()),
                            String.valueOf(villa.getRentCost()),
                            String.valueOf(villa.getMaxPeople()),
                            villa.getRentType(),
                            villa.getRoomStandard(),
                            String.valueOf(villa.getPoolArea()),
                            String.valueOf(villa.getNumberOfFloors())
                    );
                } else if (facility instanceof House) {
                    House house = (House) facility;
                    line = String.join(",",
                            "SVHO", // House identifier
                            house.getServiceCode(),
                            house.getServiceName(),
                            String.valueOf(house.getUsableArea()),
                            String.valueOf(house.getRentCost()),
                            String.valueOf(house.getMaxPeople()),
                            house.getRentType(),
                            house.getRoomStandard(),
                            String.valueOf(house.getNumberOfFloors())
                    );
                } else if (facility instanceof Room) {
                    Room room = (Room) facility;
                    line = String.join(",",
                            "SVRO", // Room identifier
                            room.getServiceCode(),
                            room.getServiceName(),
                            String.valueOf(room.getUsableArea()),
                            String.valueOf(room.getRentCost()),
                            String.valueOf(room.getMaxPeople()),
                            room.getRentType(),
                            room.getFreeService()
                    );
                }

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving facility data: " + e.getMessage());
        }
    }

    @Override
    public void addNewFacility(Facility facility) {
        if (facilityMap.containsKey(facility)) {
            System.out.println("Facility already exists.");
        } else {
            facilityMap.put(facility, 0); // Khởi tạo số lần sử dụng là 0
            System.out.println("Facility added successfully!");
            save();
        }
    }

    @Override
    public Map<Facility, Integer> getAll() {
        return facilityMap;
    }

    @Override
    public void updateUsageCount(String serviceCode) {
        for (Map.Entry<Facility, Integer> entry : facilityMap.entrySet()) {
            Facility facility = entry.getKey();
            if (facility.getServiceCode().equals(serviceCode)) {
                int currentUsageCount = entry.getValue();
                facilityMap.put(facility, currentUsageCount + 1); // Tăng số lần sử dụng
                System.out.println("Updated usage count for " + serviceCode + ": " + (currentUsageCount + 1));
                return;
            }
        }
        System.out.println("Facility not found for service code: " + serviceCode);
    }

    public Facility getByServiceCode(String serviceCode) {
        for (Facility facility : facilityMap.keySet()) {
            if (facility.getServiceCode().equals(serviceCode)) {
                return facility;
            }
        }
        return null;
    }

}
