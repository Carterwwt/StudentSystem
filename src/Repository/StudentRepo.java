package Repository;

import DataStructure.StudentList;

public class StudentRepo {

    static StudentList studentList;

    public static StudentList getStudentList() {
        return studentList;
    }

    public static void setStudentList(StudentList studentList) {
        StudentRepo.studentList = studentList;
    }

}
