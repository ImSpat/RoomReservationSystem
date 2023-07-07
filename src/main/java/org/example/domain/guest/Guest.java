package org.example.domain.guest;

import org.example.domain.guest.dto.GuestDTO;

import javax.persistence.*;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private int age;

    @Enumerated
    private Gender gender;

    public Guest(long id, String firstName, String lastName, int age, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        if (gender == null) {
            if (firstName.charAt(firstName.length() - 1) == 'a') {
                this.gender = Gender.FEMALE;
            } else
                this.gender = Gender.MALE;
        } else
            this.gender = gender;
    }

    public Guest(String firstName, String lastName, int age, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public Guest() {

    }

    public long getId() {
        return id;
    }

    public String getInfo() {
        return String.format("%d %s %s (%d) (%s) ", this.id, this.firstName, this.lastName, this.age, this.gender);
    }

    String toCSV() {
        return String.format("%s,%s,%s,%d,%s%s",
                id,
                firstName,
                lastName,
                age,
                gender,
                System.getProperty("line.separator"));
    }


    public GuestDTO getAsDTO() {
        String gender = "Mężczyzna";
        if (this.gender.equals(Gender.FEMALE)) {
            gender = "Kobieta";
        }

        return new GuestDTO(this.id, this.firstName, this.lastName, this.age, gender);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}