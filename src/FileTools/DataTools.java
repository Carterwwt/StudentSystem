package FileTools;

import DataStructure.Percentage;
import DataStructure.Student;
import Repository.StudentRepo;

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

    public static Student getLowestStudent() {
        ArrayList<Student> students = StudentRepo.getStudentList().getStudents();
        Student lowestStudent = students.get(0);
        for(Student student : students) {
            if(lowestStudent.getScore() > student.getScore())
                lowestStudent = student;
        }
        return lowestStudent;
    }

    public static Percentage getPercentage() {
        ArrayList<Student> students = StudentRepo.getStudentList().getStudents();
        int totalStudent = students.size();
        int numOfGradeAPlus = 0;
        int numOfGradeA = 0;
        int numOfGradeB = 0;
        int numOfGradeC = 0;
        int numOfGradeD = 0;
        double percentOfAPlus = 0;
        double percentOfA = 0;
        double percentOfB = 0;
        double percentOfC = 0;
        double percentOfD = 0;

        for(Student student : students) {
            int score = student.getScore();
            if(score >= 90 && score <= 100)
                numOfGradeAPlus++;
            else if(score >= 80 && score <90)
                numOfGradeA++;
            else if(score >= 70 && score < 80)
                numOfGradeB++;
            else if(score >= 60 && score < 70)
                numOfGradeC++;
            else
                numOfGradeD++;
        }
        percentOfAPlus = numOfGradeAPlus*100.0/totalStudent;
        percentOfA = numOfGradeA*100.0/totalStudent;
        percentOfB = numOfGradeB*100.0/totalStudent;
        percentOfC = numOfGradeC*100.0/totalStudent;
        percentOfD = numOfGradeD*100.0/totalStudent;

        Percentage percentage = new Percentage(numOfGradeAPlus,numOfGradeA,numOfGradeB,numOfGradeC,numOfGradeD,percentOfAPlus,percentOfA,percentOfB,percentOfC,percentOfD);
        return percentage;
    }


}
