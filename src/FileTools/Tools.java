package FileTools;

import FileContent.Student;
import FileContent.StudentList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Tools {

    public static void setUpFile(StudentList studentList) {
        Scanner in = new Scanner(System.in);
        /*
        System.out.println("Please input class name: ");
        String className = in.nextLine();
        System.out.println("Please input course name: ");
        String courseName = in.nextLine();
        String fileName = className + "-" + courseName + ".txt";
        */
        in.close();
        String fileName = "网络工程2班-JAVA";
        String txt = ".txt";
        String score = ".score";

        // Read TXT File
        Tools.readTXTFile(fileName + txt,studentList);

        // Create object File
        File file = new File(fileName + score);
        if(!file.exists()) {
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Save Object File
        Tools.saveObjectFile(file,studentList);

        // Read Object File
        Tools.readObjectFile(file,studentList);

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

    public static void readTXTFile(String fileName, StudentList studentList) {

        File file = new File(fileName);
        if(file.exists()) {
            try {
                FileReader in = new FileReader(file);
                BufferedReader reader = new BufferedReader(in);
                String s = "";
                while((s = reader.readLine()) != null) {
                    String[] result = s.split("\\p{Punct}");
                    Student student = new Student(result[0],result[1],Integer.parseInt(result[2]));
                    studentList.getStudents().add(student);
                }
                reader.close();
                in.close();
                System.out.println("Read TXT File Successfully!");
                // Test output
                //outputList(studentList);
            } catch (IOException e ) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not existed!");
        }

    }

    public static void readObjectFile(File file, StudentList studentList) {
        if(file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                studentList.setStudents((ArrayList<Student>)objectIn.readObject());
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e) {
                System.out.println("Can't get object");
            }
        }
        System.out.println("Read Object File Successfully!");
        //outputList(studentList);
    }

    public static void saveObjectFile(File file, StudentList studentList) {
        if(file.exists()) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(studentList.getStudents());
                objectOut.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
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
