package Main;

import FileContent.StudentList;
import Panel.StudentClient;
import Repository.StudentRepo;


public class Main {

    public static void main(String[] args) {
        StudentList studentList = new StudentList();
        StudentRepo.setStudentList(studentList);
        StudentClient client = new StudentClient();
    }
}
