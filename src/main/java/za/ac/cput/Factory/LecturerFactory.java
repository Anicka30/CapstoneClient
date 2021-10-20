package za.ac.cput.Factory;
/*
Description:  Factory class for Lecturer
Author: Lukhona Tetyana
Student number: 218119321
Date: 20 October 2021

 */

import za.ac.cput.Entity.Lecturer;

public class LecturerFactory {

    public static Lecturer createLecturer(String name, String surname, String id){


            Lecturer lecturer = new Lecturer.Builder().setId(id).build();
            return lecturer;
        }
}
