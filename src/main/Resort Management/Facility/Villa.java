package Facility;

public class Villa extends Facility {
    private String roomStandard;
    private double poolArea;
    private int numberOfFloors;

    public Villa(String serviceCode, String serviceName, double usableArea, double rentCost,
                 int maxPeople, String rentType, String roomStandard, double poolArea, int numberOfFloors) {
        super(serviceCode, serviceName, usableArea, rentCost, maxPeople, rentType);
        this.roomStandard = roomStandard;
        this.poolArea = poolArea;
        this.numberOfFloors = numberOfFloors;
    }

    // Getters and Setters
    public String getRoomStandard() {
        return roomStandard;
    }

    public void setRoomStandard(String roomStandard) {
        this.roomStandard = roomStandard;
    }

    public double getPoolArea() {
        return poolArea;
    }

    public void setPoolArea(double poolArea) {
        this.poolArea = poolArea;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    @Override
    public void showInfor() {
        System.out.printf("Villa Information: [Service Code: %s, Service Name: %s, Usable Area: %.2f m², Rent Cost: %.2f, Max People: %d, Rent Type: %s, Room Standard: %s, Pool Area: %.2f m², Number of Floors: %d]%n",
                getServiceCode(), getServiceName(), getUsableArea(), getRentCost(), getMaxPeople(), getRentType(), roomStandard, poolArea, numberOfFloors);
    }

}
