package Main;

import FileContent.StudentList;
import FileTools.Tools;
import Panel.StudentClient;
import Repository.StudentRepo;


public class Main {

    public static void main(String[] args) {
        StudentList studentList = new StudentList();
        StudentRepo.setStudentList(studentList);
        Tools.setUpFile(StudentRepo.getStudentList());
        StudentClient client = new StudentClient();
    }
}
