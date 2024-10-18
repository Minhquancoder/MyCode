package Facility;

public class Room extends Facility {
    private String freeService;

    public Room(String serviceCode, String serviceName, double usableArea, double rentCost,
                int maxPeople, String rentType, String freeService) {
        super(serviceCode, serviceName, usableArea, rentCost, maxPeople, rentType);
        this.freeService = freeService;
    }

    // Getter and Setter for freeService
    public String getFreeService() {
        return freeService;
    }

    public void setFreeService(String freeService) {
        this.freeService = freeService;
    }

    @Override
    public void showInfor() {
        System.out.printf("Room Information: [Service Code: %s, Service Name: %s, Usable Area: %.2f m², Rent Cost: %.2f, Max People: %d, Rent Type: %s, Free Service: %s]%n",
                getServiceCode(), getServiceName(), getUsableArea(), getRentCost(), getMaxPeople(), getRentType(), freeService);
    }

}
