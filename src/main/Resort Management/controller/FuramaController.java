package controller;

import Service.CustomerService;
import Service.EmployeeService;
import Service.FacilityService;
import Service.BookingService;
import Service.ContractService;
import Service.PromotionService;
import View.Menu;

public class FuramaController extends Menu<String> {
    private EmployeeService employeeService = new EmployeeService();
    private CustomerService customerService = new CustomerService();
    private FacilityService facilityService = new FacilityService();
    private BookingService bookingService = new BookingService();
    private ContractService contractService = new ContractService();
    private PromotionService promotionService = new PromotionService();

    // Menu chính
    static String[] displayMainMenu = new String[] {
            "Employee Management",
            "Customer Management",
            "Facility Management",
            "Booking Management",
            "Promotion Management",
            "Exit"
    };

    public FuramaController() {
        super("Furama Resort Management System", displayMainMenu);
    }

    @Override
    public void execute(int ch) {
        switch (ch) {
            case 1:
                this.displayEmployeeMenu();
                break;
            case 2:
                this.displayCustomerMenu();
                break;
            case 3:
                this.displayFacilityMenu();
                break;
            case 4:
                this.displayBookingMenu();
                break;
            case 5:
                this.displayPromotionMenu();
                break;
            case 6:
                System.out.println("Exit..");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Các phương thức hiển thị menu cho quản lý nhân viên, khách hàng, dịch vụ, đặt phòng
    private void displayEmployeeMenu() {
        String[] employeeChoice = new String[] {
                "Display list employees",
                "Add new employee",
                "Edit employee",
                "Return main menu"
        };

        Menu<String> employeeMenu = new Menu<String>("Employee Management System", employeeChoice) {
            @Override
            public void execute(int ch) {
                switch (ch) {
                    case 1:
                        employeeService.display();
                        break;
                    case 2:
                        employeeService.addNew();
                        break;
                    case 3:
                        employeeService.update();
                        break;
                    case 4:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        };
        employeeMenu.run();
    }

    private void displayCustomerMenu() {
        String[] customerChoice = new String[] {
                "Display list customers",
                "Add new customer",
                "Edit customer",
                "Return main menu"
        };

        Menu<String> customerMenu = new Menu<String>("Customer Management System", customerChoice) {
            @Override
            public void execute(int ch) {
                switch (ch) {
                    case 1:
                        customerService.displayCus();
                        break;
                    case 2:
                        customerService.addCus();
                        break;
                    case 3:
                        customerService.UpdateCus();
                        break;
                    case 4:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        };
        customerMenu.run();
    }

    private void displayFacilityMenu() {
        String[] facilityChoice = new String[] {
                "Display list facility",
                "Add new facility",
                "Display list facility maintenance",
                "Return main menu"
        };

        Menu<String> facilityMenu = new Menu<String>("Facility Management System", facilityChoice) {
            @Override
            public void execute(int ch) {
                switch (ch) {
                    case 1:
                        facilityService.displayListFacilities();
                        break;
                    case 2:
                        facilityService.addNewFacilityMenu();
                        break;
                    case 3:
                        facilityService.displayListFacilityMaintenance();
                        break;
                    case 4:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        };
        facilityMenu.run();
    }

    // Menu quản lý đặt phòng
    private void displayBookingMenu() {
        String[] bookingChoice = new String[] {
                "Add new booking",
                "Display list bookings",
                "Create new contracts",
                "Display list contracts",
                "Edit contracts",
                "Return main menu"
        };

        Menu<String> bookingMenu = new Menu<String>("Booking Management System", bookingChoice) {
            @Override
            public void execute(int ch) {
                switch (ch) {
                    case 1:
                        bookingService.addNewBooking();
                        break;
                    case 2:
                        bookingService.displayBookings();
                        break;
                    case 3:
                        contractService.addNewContract();
                        break;
                    case 4:
                        contractService.displayAllContracts();
                        break;
                    case 5:
                        contractService.editContract();
                        break;
                    case 6:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        };
        bookingMenu.run();
    }

    // Menu quản lý khuyến mãi
    private void displayPromotionMenu() {
        String[] promotionChoice = new String[] {
                "Display customers who used service by year",
                "Display list customers get voucher ",
                "Return to main menu"
        };

        Menu<String> promotionMenu = new Menu<String>("Promotion Management System", promotionChoice) {
            @Override
            public void execute(int ch) {
                switch (ch) {
                    case 1:
                        promotionService.displayCustomersUsedServiceByYear();
                        break;
                    case 2:
                        promotionService.distributeVouchers();
                        break;
                    case 3:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        };
        promotionMenu.run();
    }
}
