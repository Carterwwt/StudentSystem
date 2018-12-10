package Panel;

import CONSTANT.CONS;
import DataModel.DataModel;
import FileContent.Student;
import FileContent.StudentList;
import Repository.StudentRepo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;

@SuppressWarnings("all")

public class StudentClient extends JFrame {

    Container container = getContentPane();
    JPanel scorePanel;
    JPanel analyPanel;
    JScrollPane scroll;
    JTable table;

    public StudentClient() {
        scorePanel = new JPanel();
        setLayout(null);
        setTitle("学生成绩管理系统------Created By CarterWang");
        setBounds(550,200, CONS.frameWidth,CONS.frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set panel
        setUpScorePanel();
        setUpAnalyPanel();
        setVisible(true);
    }

    private void setUpScorePanel() {

        // set scorePanel
        scorePanel.setLayout(null);
        scorePanel.setBounds(CONS.scorePanelX,CONS.scorePanelY,CONS.scorePanelWidth,CONS.scorePanelHeight);
        scorePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        scorePanel.setVisible(true);

        //set table
        setUpTable();

        // Set ScrollPanel
        scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10,35,CONS.scrollViewWidth,CONS.scrollViewHeight);
        scorePanel.add(scroll);

        // Set label
        JLabel label = new JLabel("成绩单");
        label.setFont(new Font("宋体", Font.BOLD,14));
        label.setBounds(10,10,50,20);
        label.setVisible(true);
        scorePanel.add(label);

        add(scorePanel);

    }

    private void setUpAnalyPanel() {
        analyPanel = new JPanel();
        analyPanel.setBounds(CONS.analyPanelX,CONS.analyPanelY,CONS.analyPanelWidth,CONS.analyPanelHeight);
        analyPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        JLabel label = new JLabel("Data Analysis");
        label.setVisible(true);
        analyPanel.add(label);
        add(analyPanel);
    }

    private void setUpTable() {

        table = new JTable(new DataModel());
        table.setRowHeight(20);
        table.setFont(new Font("宋体",Font.PLAIN,13));
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
