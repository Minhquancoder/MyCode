package Service;

import Facility.Facility;
import Facility.Villa;
import Facility.House;
import Facility.Room;
import repository.BookingRepository;
import repository.FacilityRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Scanner;

import static Validation.Validation.isNumeric;

public class FacilityService implements IFacilityService {
    private FacilityRepository facilityRepository = new FacilityRepository();
    private Scanner scanner = new Scanner(System.in);
    private BookingRepository bookingRepository;

    @Override
    public void displayListFacilities() {
        Map<Facility, Integer> facilities = facilityRepository.getAll();

        if (facilities.isEmpty()) {
            System.out.println("No facilities available.");
            return;
        }

        System.out.println("=== List of Facilities ===");
        for (Map.Entry<Facility, Integer> entry : facilities.entrySet()) {
            Facility facility = entry.getKey();
            int usageCount = entry.getValue();

            facility.showInfor();
            System.out.println("Usage count: " + usageCount);
            System.out.println("-------------------------------");
        }
    }


    @Override
    public void addNewFacilityMenu() {
        while (true) {
            System.out.println("=== Add New Facility ===");
            System.out.println("1. Add New Villa");
            System.out.println("2. Add New House");
            System.out.println("3. Add New Room");
            System.out.println("4. Back to menu");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    addNewVilla();
                    break;
                case 2:
                    addNewHouse();
                    break;
                case 3:
                    addNewRoom();
                    break;
                case 4:
                    System.out.println("Returning to the main menu...");
                    return;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

    @Override
    public void displayListFacilityMaintenance() {
        System.out.println("=== Facilities Needing Maintenance ===");
        for (Map.Entry<Facility, Integer> entry : facilityRepository.getAll().entrySet()) {
            if (entry.getValue() >= 5) {
                System.out.println(entry.getKey());
            }
        }
        System.out.println("There are no facilities need to maintenance !");
    }

    private void addNewVilla() {
        String serviceCode = getServiceCode("SVVL");
        String serviceName = getServiceName();
        double usableArea = getUsableArea();
        double rentCost = getRentCost();
        int maxPeople = getMaxPeople();
        String rentType = getRentType();
        String roomStandard = getRoomStandard();
        double poolArea = getPoolArea();
        int numberOfFloors = getNumberOfFloors();

        Villa villa = new Villa(serviceCode, serviceName, usableArea, rentCost, maxPeople, rentType, roomStandard, poolArea, numberOfFloors);
        facilityRepository.addNewFacility(villa);
    }

    private void addNewHouse() {
        String serviceCode = getServiceCode("SVHO");
        String serviceName = getServiceName();
        double usableArea = getUsableArea();
        double rentCost = getRentCost();
        int maxPeople = getMaxPeople();
        String rentType = getRentType();
        String roomStandard = getRoomStandard();
        int numberOfFloors = getNumberOfFloors();

        House house = new House(serviceCode, serviceName, usableArea, rentCost, maxPeople, rentType, roomStandard, numberOfFloors);
        facilityRepository.addNewFacility(house);
    }

    private void addNewRoom() {
        String serviceCode = getServiceCode("SVRO");
        String serviceName = getServiceName();
        double usableArea = getUsableArea();
        double rentCost = getRentCost();
        int maxPeople = getMaxPeople();
        String rentType = getRentType();
        String freeService = getFreeService();

        Room room = new Room(serviceCode, serviceName, usableArea, rentCost, maxPeople, rentType, freeService);
        facilityRepository.addNewFacility(room);
    }

    // Phương thức để nhận mã dịch vụ
    private String getServiceCode(String prefix) {
        String serviceCode;
        while (true) {
            System.out.print("Enter service code (e.g., " + prefix + "-0012): ");
            serviceCode = scanner.nextLine().trim();
            if (serviceCode.matches("^" + prefix + "-\\d{4}$")) {
                return serviceCode;
            } else {
                System.out.println("Invalid service code format.");
            }
        }
    }

    // Phương thức nhận tên dịch vụ
    private String getServiceName() {
        String serviceName;
        while (true) {
            System.out.print("Enter service name: ");
            serviceName = scanner.nextLine().trim();
            if (serviceName.matches("^[A-Z][a-zA-Z ]*$")) {
                return serviceName;
            } else {
                System.out.println("Invalid service name format.");
            }
        }
    }

    // Phương thức nhận diện tích sử dụng
    private double getUsableArea() {
        double usableArea;
        while (true) {
            System.out.print("Enter usable area (must be greater than 30): ");
            String input = scanner.nextLine().trim();
            if (isNumeric(input)) {
                usableArea = Double.parseDouble(input);
                if (usableArea > 30) {
                    return usableArea;
                } else {
                    System.out.println("Usable area must be greater than 30 m².");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value for usable area.");
            }
        }
    }

    // Phương thức nhận chi phí thuê
    private double getRentCost() {
        double rentCost;
        while (true) {
            System.out.print("Enter rent cost (must be positive): ");
            String input = scanner.nextLine().trim();
            if (isNumeric(input)) {
                rentCost = Double.parseDouble(input);
                if (rentCost > 0) {
                    return rentCost;
                } else {
                    System.out.println("Rent cost must be positive.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value for rent cost.");
            }
        }
    }

    // Phương thức nhận số lượng người tối đa
    private int getMaxPeople() {
        int maxPeople;
        while (true) {
            System.out.print("Enter max people (must be > 0 and < 20): ");
            String input = scanner.nextLine().trim();
            if (isNumeric(input)) {
                maxPeople = Integer.parseInt(input);
                if (maxPeople > 0 && maxPeople < 20) {
                    return maxPeople;
                } else {
                    System.out.println("Max people must be greater than 0 and less than 20.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value for max people.");
            }
        }
    }

    // Phương thức nhận kiểu thuê
    private String getRentType() {
        String rentType;
        while (true) {
            System.out.print("Enter rent type (Week, Month, Year, Days): ");
            rentType = scanner.nextLine().trim();
            if (rentType.matches("^[A-Z][a-zA-Z ]*$")) {
                return rentType;
            } else {
                System.out.println("Invalid rent type format.");
            }
        }
    }

    // Phương thức nhận tiêu chuẩn phòng
    private String getRoomStandard() {
        System.out.print("Enter room standard: ");
        return scanner.nextLine().trim();
    }

    // Phương thức nhận diện tích hồ bơi
    private double getPoolArea() {
        double poolArea;
        while (true) {
            System.out.print("Enter pool area (must be greater than 0): ");
            String input = scanner.nextLine().trim();
            if (isNumeric(input)) { // Kiểm tra đầu vào là số
                poolArea = Double.parseDouble(input);
                if (poolArea > 0) {
                    return poolArea;
                } else {
                    System.out.println("Pool area must be greater than 0.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value for pool area.");
            }
        }
    }

    // Phương thức nhận số tầng
    private int getNumberOfFloors() {
        int numberOfFloors;
        while (true) {
            System.out.print("Enter number of floors (must be a positive integer): ");
            String input = scanner.nextLine().trim();
            if (isNumeric(input)) {
                numberOfFloors = Integer.parseInt(input);
                if (numberOfFloors > 0) {
                    return numberOfFloors;
                } else {
                    System.out.println("Number of floors must be a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value for number of floors.");
            }
        }
    }

    // Phương thức nhận dịch vụ miễn phí (cho Room)
    private String getFreeService() {
        System.out.print("Enter free service: ");
        return scanner.nextLine().trim();
    }

    @Override
    public void display() {
    }

    // Tăng số lần sử dụng dựa trên booking
    public void updateUsageCountFromBookings() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        for (Map.Entry<Facility, Integer> entry : facilityRepository.getAll().entrySet()) {
            Facility facility = entry.getKey();
            int currentUsageCount = entry.getValue();
            long bookingCount = bookingRepository.getAllBookings().stream()
                    .filter(booking -> booking.getServiceCode().equals(facility.getServiceCode()) &&
                            booking.getStartDate().getMonth() == currentMonth.getMonth() &&
                            booking.getStartDate().getYear() == currentMonth.getYear())
                    .count();

            // Cập nhật số lần sử dụng cho facility
            String serviceCode = facility.getServiceCode();
            for (int i = 0; i < bookingCount; i++) {
                facilityRepository.updateUsageCount(serviceCode); // Gọi từng lần cho mỗi booking
            }
        }
    }

}
