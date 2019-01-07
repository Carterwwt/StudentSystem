package DataStructure;

import java.util.ArrayList;

public class StudentList {

    ArrayList<Student> students;

    public StudentList() {
        students = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
