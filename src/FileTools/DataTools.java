package FileTools;

import FileContent.Student;
import Repository.StudentRepo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataTools {

    private DataTools() {

    }

    public static Student getHighestStudent() {
        ArrayList<Student> students = StudentRepo.getStudentList().getStudents();
        Student highestStudent = students.get(0);
        for(Student student : students) {
            if(highestStudent.getScore() < student.getScore())
                highestStudent = student;
        }
        return highestStudent;
    }

    public static String getAverageScore() {
        ArrayList<Student> students = StudentRepo.getStudentList().getStudents();
        double sum = 0;
        for(Student student : students) {
            sum += student.getScore();
        }
        return String.format("%.2f",sum/students.size());
    }

}
