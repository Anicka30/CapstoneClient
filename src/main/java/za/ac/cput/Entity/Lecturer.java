package za.ac.cput.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/*
Description: Builder pattern to Lecturer Entity
Author: Lukhona Tetyana
Student number: 218119321
Date: 20 October 2021

 */
@Entity
public class Lecturer implements Serializable {

    @Id
    private String lecturerId;
    private String firstname, lastname;


    private Lecturer(){}

    private Lecturer(Builder builder){
        this.firstname =builder.name;
        this.lastname =builder.surname;
        this.lecturerId =builder.id;
    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public String getLecturerId() {
        return lecturerId;
    }



    @Override
    public String toString() {
        return "Lecturer{" +
                "name='" + firstname + '\'' +
                ", surname='" + lastname + '\'' +
                ", id='" + lecturerId + '\'' +
                '}';
    }


    public static class Builder{
        private String name, surname, id;

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;

            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;

        }


        public Lecturer build(){
            return new Lecturer(this);
        }

        public Builder copy(Lecturer lecturer){
            this.name= lecturer.firstname;
            this.surname= lecturer.lastname;
            this.id= lecturer.lecturerId;
            return this;
        }


    }


}





