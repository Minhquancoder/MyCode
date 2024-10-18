package Person;

public class Customer extends Person {
    private String customerType;
    private String address;

    public Customer(String id, String name, String dateOfBirth, String gender, String idCard, String phoneNumber, String email, String customerType, String address) {
        super(id, name, dateOfBirth, gender, idCard, phoneNumber, email);
        this.customerType = customerType;
        this.address = address;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void showInfo() {
        System.out.println("Customer{" +
                "ID='" + super.getId() + '\'' +
                ", Name='" + super.getName() + '\'' +
                ", Date Of Birth='" + super.getDateOfBirth() + '\'' +
                ", Gender='" + super.getGender() + '\'' +
                ", ID Card='" + super.getIdCard() + '\'' +
                ", Phone='" + super.getPhoneNumber() + '\'' +
                ", Email='" + super.getEmail() + '\'' +
                ", Customer Type='" + customerType + '\'' +
                ", Address='" + address + '\'' +
                '}');
    }
}
