package Person;

public class Employee extends Person {
    private String qualification;
    private String position;
    private double salary;

    public Employee(String id, String name, String dateOfBirth, String gender,
                    String idCard, String phoneNumber, String email,
                    String qualification, String position, double salary) {
        super(id, name, dateOfBirth, gender, idCard, phoneNumber, email);
        this.qualification = qualification;
        this.position = position;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public void showInfo() {
        System.out.println("Employee{" +
                "ID='" + super.getId() + '\'' +
                ", Name='" + super.getName() + '\'' +
                ", Date Of Birth='" + super.getDateOfBirth() + '\'' +
                ", Gender='" + super.getGender() + '\'' +
                ", ID Card='" + super.getIdCard() + '\'' +
                ", Phone='" + super.getPhoneNumber() + '\'' +
                ", Email='" + super.getEmail() + '\'' +
                ", Qualification='" + qualification + '\'' +
                ", Position='" + position + '\'' +
                ", Salary=" + salary +
                '}');
    }
}
