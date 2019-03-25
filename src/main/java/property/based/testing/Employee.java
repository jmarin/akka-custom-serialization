package property.based.testing;

import javax.validation.constraints.Email;

public class Employee {

    private final String firstName;

    private final String lastName;

    private final int age;

    private final Phone phoneNumber;

    @Email
    private final String email;

    public Employee(String firstName, String lastName, int age, Phone phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


}
