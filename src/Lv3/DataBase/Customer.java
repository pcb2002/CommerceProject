package Lv3.DataBase;

public class Customer {
    private String customerName;
    private String email;
    private CustomerGrade grade;

    public Customer(String customerName, String email, CustomerGrade grade) {
        this.customerName = customerName;
        this.email = email;
        this.grade = grade;
    }

}