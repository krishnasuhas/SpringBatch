package com.hasi.springbatch.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Billionaire {
    @Id
    private String firstName;
    private String lastName;
    private String career;

    public Billionaire() {
    }

    public Billionaire(String firstName, String lastName, String career) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.career = career;
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

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    @Override
    public String toString() {
        return "Billionaires{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", career='" + career + '\'' +
                '}';
    }
}
