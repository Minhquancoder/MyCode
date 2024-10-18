package Facility;

public class House extends Facility {
    private String roomStandard;
    private int numberOfFloors;

    public House(String serviceCode, String serviceName, double usableArea, double rentCost,
                 int maxPeople, String rentType, String roomStandard, int numberOfFloors) {
        super(serviceCode, serviceName, usableArea, rentCost, maxPeople, rentType);
        this.roomStandard = roomStandard;
        this.numberOfFloors = numberOfFloors;
    }

    public String getRoomStandard() {
        return roomStandard;
    }

    public void setRoomStandard(String roomStandard) {
        this.roomStandard = roomStandard;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    @Override
    public void showInfor() {
        System.out.printf("House Information: [Service Code: %s, Service Name: %s, Usable Area: %.2f m², Rent Cost: %.2f, Max People: %d, Rent Type: %s, Room Standard: %s, Number of Floors: %d]%n",
                getServiceCode(), getServiceName(), getUsableArea(), getRentCost(), getMaxPeople(), getRentType(), roomStandard, numberOfFloors);
    }

}
