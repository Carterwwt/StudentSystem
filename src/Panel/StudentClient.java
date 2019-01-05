package Panel;

import CONSTANT.CONS;
import DataModel.DataModel;
import FileContent.Student;
import FileTools.FileTools;
import FileTools.DataTools;
import Repository.StudentRepo;
import Exception.FileIOException;

import javax.swing.*;
import javax.swing.table.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

@SuppressWarnings("all")

public class StudentClient extends JFrame {//I can't find the problem!!! Or there is no big here!

    JPanel scorePanel;
    JPanel analyPanel;
    JPanel analyView;
    JScrollPane scroll;
    JTable table;
    JFileChooser chooser;
    File file;
    AbstractTableModel dataModel;

    JMenuBar menuBar;
    JMenu fileMenu;

    JMenuItem openTxtFile;
    JMenuItem saveObjectFile;
    JMenuItem readObjectFile;
    JMenuItem clear;

    JTextArea highestScoreArea;
    JTextArea averageScoreArea;
    JLabel highestSocreLabel;
    JLabel averageScoreLabel;

    boolean isAnalyViewComponentsSetUp = false;

    public StudentClient() {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        setLayout(null);
        setTitle("学生成绩管理系统------Created By CarterWang");
        setBounds(400,200, CONS.frameWidth,CONS.frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set Menu
        setUpMenu();
        setVisible(true);
    }

    private void setUpMenu() {

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        openTxtFile = new JMenuItem("Open TXT file");
        saveObjectFile = new JMenuItem("Save Object file");
        readObjectFile = new JMenuItem("Read Object file");
        clear = new JMenuItem("Clear");

        saveObjectFile.setEnabled(false);
        readObjectFile.setEnabled(false);
        clear.setEnabled(false);

        openTxtFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int response = chooser.showOpenDialog(menuBar);
                if(response == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    try {
                        FileTools.readTXTFile(file, StudentRepo.getStudentList());
                        refreshTable();

                        if(analyView != null) {
                            if(!isAnalyViewComponentsSetUp)
                                setUpAnalyViewComponents();
                            else
                                refreshAnalyView();
                        }

                        saveObjectFile.setEnabled(true);
                        readObjectFile.setEnabled(false);
                        clear.setEnabled(true);
                        FileTools.outputList(StudentRepo.getStudentList().getStudents());
                    } catch (FileIOException ee) {
                        ee.showMessageDialog();
                        disableAllJmenuItems();
                    }
                    if(scorePanel == null && analyPanel == null) {
                        setUpScorePanel();
                        setUpAnalyPanel();
                        repaint();
                    } else {
                        repaint();
                    }

                }
            }
        });

        saveObjectFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(saveObjectFile.isEnabled()) {
                    try {
                        FileTools.saveObjectFile(file, StudentRepo.getStudentList());
                        readObjectFile.setEnabled(true);
                    } catch (FileIOException ee) {
                        readObjectFile.setEnabled(false);
                        ee.showMessageDialog();
                    }
                }

            }
        });

        readObjectFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(readObjectFile.isEnabled()) {
                    try {
                        FileTools.readObjectFile(file, StudentRepo.getStudentList());
                        refreshTable();
                        FileTools.outputList(StudentRepo.getStudentList().getStudents());
                    } catch (FileIOException ee) {
                        ee.showMessageDialog();
                    }
                }
            }
        });

        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                FileTools.removeData();
                refreshTable();
                disableAllJmenuItems();
            }
        });

        fileMenu.add(openTxtFile);
        fileMenu.add(saveObjectFile);
        fileMenu.add(readObjectFile);
        //fileMenu.add(clear);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

    }

    private void setUpScorePanel() {

        scorePanel = new JPanel();

        // set scorePanel
        scorePanel.setLayout(null);
        scorePanel.setBounds(CONS.scorePanelX,CONS.scorePanelY,CONS.scorePanelWidth,CONS.scorePanelHeight);
        scorePanel.setBorder(BorderFactory.createLoweredBevelBorder());

        //set table
        setUpTable();

        // Set ScrollPanel
        scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10,35,CONS.scrollViewWidth,CONS.scrollViewHeight);
        scorePanel.add(scroll);

        // Set label
        JLabel label = new JLabel("Score");
        label.setFont(new Font("Helvetica", Font.BOLD,14));
        label.setBounds(10,10,50,20);
        label.setVisible(true);
        scorePanel.add(label);

        add(scorePanel);
        scorePanel.setVisible(true);

    }

    private void setUpAnalyPanel() {
        analyPanel = new JPanel();
        analyPanel.setBounds(CONS.analyPanelX,CONS.analyPanelY,CONS.analyPanelWidth,CONS.analyPanelHeight);
        analyPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        analyPanel.setLayout(null);

        // Set Title
        JLabel label = new JLabel("Data Analysis");
        label.setFont(new Font("Helvetica", Font.BOLD,14));
        label.setBounds(10,10,100,20);
        label.setVisible(true);

        // Set AnalyViewPanel
        analyView = new JPanel();
        analyView.setBorder(BorderFactory.createLoweredBevelBorder());
        analyView.setBounds(CONS.analyViewX,CONS.analyViewY,CONS.analyViewWidth,CONS.analyViewHeight);
        analyView.setVisible(true);
        analyView.setLayout(null);

        // Set AnalyViewComponents
        if(!StudentRepo.getStudentList().getStudents().isEmpty())
            setUpAnalyViewComponents();

        analyPanel.add(analyView);
        analyPanel.add(label);
        add(analyPanel);
        analyPanel.setVisible(true);

    }

    private void setUpTable() {

        dataModel = new DataModel();

        table = new JTable(dataModel);
        table.setRowHeight(20);
        table.setFont(new Font("宋体",Font.PLAIN,13));
        table.setRowSelectionAllowed(false);
        RowSorter<AbstractTableModel> rowSorter = new TableRowSorter<AbstractTableModel>(dataModel);
        table.setRowSorter(rowSorter);

        // Set header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(),22));
        header.setFont(new Font("Helvetica",Font.PLAIN,14));

        // Set Cell Render
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer()// 设置表格间隔色
        {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                // table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                if (row % 2 == 0)
                    setBackground(new Color(236,236,236));
                else if (row % 2 == 1)
                    setBackground(Color.white);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        ArrayList<Student> list = StudentRepo.getStudentList().getStudents();

        for(int i=0;i<3;i++) {
            table.getColumn(CONS.column[i]).setCellRenderer(renderer);
        }

        scorePanel.add(table);
    }

    private void setUpHighestScoreLabel() {
        highestSocreLabel = new JLabel();
        highestSocreLabel.setBounds(CONS.highestScoreLabelX,CONS.highestScoreLabelY,CONS.highestScoreLabelWidth,CONS.highestScoreLabelHeight);
        highestSocreLabel.setText("HighestScore");
        highestSocreLabel.setVisible(true);
    }

    private void setUpHighestScoreArea() {
        highestScoreArea = new JTextArea();
        highestScoreArea.setBounds(CONS.highestScoreAreaX,CONS.highestScoreAreaY,CONS.highestScoreAreaWidth,CONS.highestScoreAreaHeight);
        Student student = DataTools.getHighestStudent();
        highestScoreArea.setText(String.valueOf(student.getScore()));
        highestScoreArea.setVisible(true);
        highestScoreArea.setEditable(false);
    }

    private void setUpAverageScoreLabel() {
        averageScoreLabel = new JLabel();
        averageScoreLabel.setBounds(CONS.averageScoreLabelX,CONS.averageScoreLabelY,CONS.highestScoreLabelWidth,CONS.highestScoreLabelHeight);
        averageScoreLabel.setText("AverageScore");
        averageScoreLabel.setVisible(true);
    }

    private void setUpAverageScoreArea() {
        averageScoreArea = new JTextArea();
        averageScoreArea.setBounds(CONS.averageScoreAreaX,CONS.averageScoreAreaY,CONS.averageScoreAreaWidth,CONS.averageScoreAreaHeight);
        String average = DataTools.getAverageScore();
        averageScoreArea.setText(average);
        averageScoreArea.setVisible(true);
        averageScoreArea.setEditable(false);
    }

    private void setUpAnalyViewComponents() {

        if(!isAnalyViewComponentsSetUp)
            isAnalyViewComponentsSetUp = true;

        setUpHighestScoreLabel();
        setUpHighestScoreArea();
        setUpAverageScoreLabel();
        setUpAverageScoreArea();

        addComponentsToAnalyView();

    }

    private void addComponentsToAnalyView() {
        analyView.add(highestScoreArea);
        analyView.add(highestSocreLabel);
        analyView.add(averageScoreLabel);
        analyView.add(averageScoreArea);
    }

    private void refreshTable() {
        if(table != null) {
            table.validate();
            table.updateUI();
        }
    }

    private void refreshAnalyView() {
        Student student = DataTools.getHighestStudent();
        highestScoreArea.setText(String.valueOf(student.getScore()));
        averageScoreArea.setText(DataTools.getAverageScore());
    }

    private void disableAllJmenuItems() {
        saveObjectFile.setEnabled(false);
        readObjectFile.setEnabled(false);
        clear.setEnabled(false);
    }

}
