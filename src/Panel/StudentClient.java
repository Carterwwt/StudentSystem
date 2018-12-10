package Panel;

import CONSTANT.CONS;
import DataModel.DataModel;
import FileContent.Student;
import FileContent.StudentList;
import FileTools.Tools;
import Repository.StudentRepo;
import SortEvent.SortByID;
import SortEvent.SortByName;
import SortEvent.SortByScore;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@SuppressWarnings("all")

public class StudentClient extends JFrame {

    Container container = getContentPane();
    JPanel scorePanel;
    JScrollPane scroll;
    JTable table;

    public StudentClient() {
        scorePanel = new JPanel();
        setLayout(null);
        setTitle("学生成绩管理系统------Created By CarterWang");
        setBounds(570,200, CONS.frameWidth,CONS.frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUpScrollView();
        setVisible(true);
    }

    private void setUpScrollView() {

        // set scorePanel
        scorePanel.setLayout(null);
        scorePanel.setBounds(CONS.scorePanelX,CONS.scorePanelY,CONS.scorePanelWidth,CONS.scorePanelHeight);
        scorePanel.setVisible(true);

        //set table
        setUpTable();

        // Set ScrollPanel
        scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10,20,CONS.scrollViewWidth,CONS.scrollViewHeight);
        scorePanel.add(scroll);
        add(scorePanel);

    }

    private void setUpTable() {

        table = new JTable(new DataModel());
        table.setRowHeight(20);
        JTableHeader header = table.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int pick = header.columnAtPoint(e.getPoint());
                switch (pick) {
                    case 0:
                        sortByID();
                        break;
                    case 1:
                        sortByName();
                        break;
                    case 2:
                        sortByScore();
                        break;
                    default:
                        break;
                }
            }
        });
        scorePanel.add(table);
    }

    public void sortByID() {
        System.out.println("Sort by ID");
        StudentList list = StudentRepo.getStudentList();
        Collections.sort(list.getStudents(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(o1.getId().compareTo(o2.getId()) <= 0)
                    return -1;
                else
                    return 1;
            }
        });
    }

    public void sortByName() {
        System.out.println("Sort by Name");
        StudentList list = StudentRepo.getStudentList();
        Collections.sort(list.getStudents(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(o1.getName().compareTo(o2.getName()) <= 0)
                    return -1;
                else
                    return 1;
            }
        });
    }

    public void sortByScore() {
        System.out.println("Sort by Score");
        StudentList list = StudentRepo.getStudentList();
        Collections.sort(list.getStudents(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(o1.getScore() > o2.getScore())
                    return -1;
                else
                    return 1;
            }
        });
    }


}
