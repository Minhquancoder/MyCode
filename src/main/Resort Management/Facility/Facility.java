package Facility;

public abstract class Facility {
    private String serviceCode;
    private String serviceName;
    private double usableArea;
    private double rentCost;
    private int maxPeople;
    private String rentType;

    public Facility(String serviceCode, String serviceName, double usableArea, double rentCost, int maxPeople, String rentType) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.usableArea = usableArea;
        this.rentCost = rentCost;
        this.maxPeople = maxPeople;
        this.rentType = rentType;
    }

    // Getters and Setters
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getUsableArea() {
        return usableArea;
    }

    public void setUsableArea(double usableArea) {
        this.usableArea = usableArea;
    }

    public double getRentCost() {
        return rentCost;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public abstract void showInfor();



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Facility)) return false;
        Facility other = (Facility) obj;
        return this.serviceCode.equals(other.serviceCode);
    }

    @Override
    public int hashCode() {
        return serviceCode.hashCode();
    }
}
