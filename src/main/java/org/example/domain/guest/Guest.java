package org.example.domain.guest;

import org.example.domain.guest.dto.GuestDTO;

public class Guest {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Gender gender;

    Guest(long id, String firstName, String lastName, int age, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        if (gender==null){
            if (firstName.charAt(firstName.length()-1)=='a'){
                this.gender = Gender.FEMALE;
            }else
            this.gender = Gender.MALE;
        }else
        this.gender = gender;
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
}