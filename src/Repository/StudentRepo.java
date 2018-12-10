package Repository;

import FileContent.StudentList;

public class StudentRepo {

    static StudentList studentList;

    public static StudentList getStudentList() {
        return studentList;
    }

    public static void setStudentList(StudentList studentList) {
        StudentRepo.studentList = studentList;
    }

}
