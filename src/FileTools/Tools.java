package FileTools;

import FileContent.Student;
import FileContent.StudentList;
import Repository.StudentRepo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import Exception.FileIOException;

public class Tools {



    public static void setUpFile(File file) {

        StudentList studentList = StudentRepo.getStudentList();

        // Sort StudentList
        Collections.sort(studentList.getStudents(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(o1.getScore() >= o2.getScore())
                    return -1;
                return 1;
            }
        });

    }

    public static void readTXTFile(File file, StudentList studentList) throws FileIOException {

        if(file.getName().endsWith(".txt")) {
            if (file.exists()) {
                try {
                    FileReader in = new FileReader(file);
                    BufferedReader reader = new BufferedReader(in);
                    String s = "";
                    while ((s = reader.readLine()) != null) {
                        String[] result = s.split("\\p{Punct}");
                        Student student = new Student(result[0], result[1], Integer.parseInt(result[2]));
                        studentList.getStudents().add(student);
                    }
                    reader.close();
                    in.close();
                    System.out.println("Read TXT File Successfully!");
                    // Test output
                    //outputList(studentList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("File not existed!");
            }

        } else {
            throw(new FileIOException("Not a TXT file!"));
        }

    }

    public static void readObjectFile(File file, StudentList studentList) throws FileIOException {

        file = new File(file.getName().replace(".txt",".score"));

        if(file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                studentList.setStudents((ArrayList<Student>)objectIn.readObject());
            } catch (IOException e) {
                e.printStackTrace();
                throw (new FileIOException("File IO Error!"));
            }
            catch (ClassNotFoundException e) {
                throw (new FileIOException("Can't get a object"));
            }
        }
        System.out.println("Read Object File Successfully!");
        //outputList(studentList);
    }

    public static void saveObjectFile(File file, StudentList studentList) throws FileIOException {
        file = new File(file.getName().replace(".txt",".score"));

        if(file.exists()) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(studentList.getStudents());
                objectOut.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw (new FileIOException("Save Object File Error!"));
            }
        }
        System.out.println("Save Object File Successfully!");
        System.out.println("---------------------------------------");
    }

    public static void outputList(StudentList studentList) {
        for(Student student : studentList.getStudents()) {
            System.out.println(student.toString());
        }
        System.out.println("---------------------------------------");
    }

}
