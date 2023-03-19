package org.example;

public class Guest {

    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;

    public Guest(String firstName, String lastName, int age, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public String getInfo() {
        return String.format("Dodano nowego gościa: %s %s (%d) %s ", this.firstName, this.lastName, this.age, this.gender);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}