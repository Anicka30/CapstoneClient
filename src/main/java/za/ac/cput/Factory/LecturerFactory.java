package za.ac.cput.Factory;
/*
Description:  Factory class for Lecturer
Author: Lukhona Tetyana
Student number: 218119321
Date: 20 October 2021

 */

import za.ac.cput.Entity.Lecturer;
import za.ac.cput.util.GenericHelper;

public class LecturerFactory {

    public static Lecturer createLecturer (String name, String surname, String id){
        String lecturerId = GenericHelper.generateId();

            Lecturer lecturer = new Lecturer.Builder()
                    .setName(name)
                    .setSurname(surname)
                    .setId(id)
                    .build();
            return lecturer;
        }

}
