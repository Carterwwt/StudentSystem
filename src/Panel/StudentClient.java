package Panel;

import CONSTANT.CONS;
import DataStructure.Percentage;
import TableDataModel.DataModel;
import DataStructure.Student;
import FileTools.FileTools;
import FileTools.DataTools;
import Repository.StudentRepo;
import Exception.FileIOException;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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

    JTextField highestScoreArea;
    JTextField lowestScoreArea;
    JTextField averageScoreArea;

    JTextField perTextAPlus;
    JTextField perTextA;
    JTextField perTextB;
    JTextField perTextC;
    JTextField perTextD;

    JTextField[] numOfStu;

    JLabel highestSocreLabel;
    JLabel lowestScoreLabel;
    JLabel averageScoreLabel;
    JLabel APlusLabel;
    JLabel ALabel;
    JLabel BLabel;
    JLabel CLabel;
    JLabel DLabel;

    JLabel[] takeUp;
    JLabel[] percentage;

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


        for(int i=0;i<3;i++) {
            table.getColumn(CONS.column[i]).setCellRenderer(renderer);
        }

        scorePanel.add(table);
    }

    private void setUpHighestScoreLabel() {
        highestSocreLabel = new JLabel();
        highestSocreLabel.setBounds(CONS.dataLabelX,CONS.highestY,CONS.dataLabelWidth,CONS.dataHeight);
        highestSocreLabel.setText("Highest Score");
        highestSocreLabel.setVisible(true);
    }

    private void setUpHighestScoreArea() {
        highestScoreArea = new JTextField();
        highestScoreArea.setBounds(CONS.dataAreaX,CONS.highestY,CONS.dataAreaWidth,CONS.dataHeight);
        Student student = DataTools.getHighestStudent();
        highestScoreArea.setText(String.valueOf(student.getScore()));
        highestScoreArea.setHorizontalAlignment(JTextField.RIGHT);
        highestScoreArea.setVisible(true);
        highestScoreArea.setEditable(false);
    }

    private void setUpLowestScoreLabel() {
        lowestScoreLabel = new JLabel();
        lowestScoreLabel.setBounds(CONS.dataLabelX,CONS.lowestY,CONS.dataLabelWidth,CONS.dataHeight);
        lowestScoreLabel.setText("Lowest Score");
        lowestScoreLabel.setVisible(true);
    }

    private void setUpLowestScoreArea() {
        lowestScoreArea = new JTextField();
        lowestScoreArea.setBounds(CONS.dataAreaX,CONS.lowestY,CONS.dataAreaWidth,CONS.dataHeight);
        Student student = DataTools.getLowestStudent();
        lowestScoreArea.setText(String.valueOf(student.getScore()));
        lowestScoreArea.setHorizontalAlignment(JTextField.RIGHT);
        lowestScoreArea.setVisible(true);
        lowestScoreArea.setEditable(false);
    }

    private void setUpAverageScoreLabel() {
        averageScoreLabel = new JLabel();
        averageScoreLabel.setBounds(CONS.dataLabelX,CONS.averageY,CONS.dataLabelWidth,CONS.dataHeight);
        averageScoreLabel.setText("Average Score");
        averageScoreLabel.setVisible(true);
    }

    private void setUpAverageScoreArea() {
        averageScoreArea = new JTextField();
        averageScoreArea.setBounds(CONS.dataAreaX,CONS.averageY,CONS.dataAreaWidth,CONS.dataHeight);
        String average = DataTools.getAverageScore();
        averageScoreArea.setHorizontalAlignment(JTextField.RIGHT);
        averageScoreArea.setText(average);
        averageScoreArea.setVisible(true);
        averageScoreArea.setEditable(false);
    }


    private void setUpAnalyViewComponents() {

        if(!isAnalyViewComponentsSetUp)
            isAnalyViewComponentsSetUp = true;

        // high low average
        setUpHighestScoreLabel();
        setUpHighestScoreArea();
        setUpLowestScoreLabel();
        setUpLowestScoreArea();
        setUpAverageScoreLabel();
        setUpAverageScoreArea();

        // percentage
        setUpPercentage();

        addComponentsToAnalyView();

    }

    private void setUpPercentage() {
        Percentage percentage = DataTools.getPercentage();
        setUpPercentageLabel();
        setUpPercentageData(percentage);
    }

    private void setUpPercentageLabel() {
        APlusLabel = new JLabel("Grade A+ (90-100)");
        ALabel = new JLabel("Grade A (80-89)");
        BLabel = new JLabel("Grade B (70-79)");
        CLabel = new JLabel("Grade C (60-69)");
        DLabel = new JLabel("Grade D (0-60)");

        APlusLabel.setBounds(CONS.dataLabelX,CONS.APlusY,CONS.gradeLabelWidth,CONS.dataHeight);
        APlusLabel.setVisible(true);
        ALabel.setBounds(CONS.dataLabelX,CONS.AY,CONS.gradeLabelWidth,CONS.dataHeight);
        ALabel.setVisible(true);
        BLabel.setBounds(CONS.dataLabelX,CONS.BY,CONS.gradeLabelWidth,CONS.dataHeight);
        BLabel.setVisible(true);
        CLabel.setBounds(CONS.dataLabelX,CONS.CY,CONS.gradeLabelWidth,CONS.dataHeight);
        CLabel.setVisible(true);
        DLabel.setBounds(CONS.dataLabelX,CONS.DY,CONS.gradeLabelWidth,CONS.dataHeight);
        DLabel.setVisible(true);

        takeUp = new JLabel[5];
        for(int i=0;i<5;i++) {
            takeUp[i] = new JLabel("takes up");
            takeUp[i].setBounds(CONS.gradeLabelX,CONS.APlusY + i*40,CONS.gradePerWidth,CONS.dataHeight);
            takeUp[i].setVisible(true);
        }

        percentage = new JLabel[5];
        for(int i=0;i<5;i++) {
            percentage[i] = new JLabel("%");
            percentage[i].setBounds(CONS.percentageX,CONS.APlusY + i*40,50,20);
            percentage[i].setVisible(true);
        }

    }

    private void setUpPercentageData(Percentage percentage) {

        numOfStu = new JTextField[5];

        numOfStu[0] = new JTextField(String.valueOf(percentage.getNumOfGradeAPlus()));
        numOfStu[1] = new JTextField(String.valueOf(percentage.getNumOfGradeA()));
        numOfStu[2] = new JTextField(String.valueOf(percentage.getNumOfGradeB()));
        numOfStu[3] = new JTextField(String.valueOf(percentage.getNumOfGradeC()));
        numOfStu[4] = new JTextField(String.valueOf(percentage.getNumOfGradeD()));

        for(int i=0;i<5;i++) {
            numOfStu[i].setBounds(CONS.dataAreaX,CONS.APlusY + i*40,CONS.dataAreaWidth,CONS.dataHeight);
            numOfStu[i].setVisible(true);
            numOfStu[i].setHorizontalAlignment(JTextField.RIGHT);
        }

    }

    private void addComponentsToAnalyView() {
        analyView.add(highestScoreArea);
        analyView.add(highestSocreLabel);
        analyView.add(lowestScoreLabel);
        analyView.add(lowestScoreArea);
        analyView.add(averageScoreLabel);
        analyView.add(averageScoreArea);

        analyView.add(APlusLabel);
        analyView.add(ALabel);
        analyView.add(BLabel);
        analyView.add(CLabel);
        analyView.add(DLabel);

        for(int i=0;i<5;i++) {
            analyView.add(takeUp[i]);
            analyView.add(percentage[i]);
            analyView.add(numOfStu[i]);
        }

    }

    private void refreshTable() {
        if(table != null) {
            table.validate();
            table.updateUI();
        }
    }

    private void refreshAnalyView() {
        Student highestStudent = DataTools.getHighestStudent();
        highestScoreArea.setText(String.valueOf(highestStudent.getScore()));
        Student lowestStudent = DataTools.getLowestStudent();
        lowestScoreArea.setText(String.valueOf(lowestStudent.getScore()));
        averageScoreArea.setText(DataTools.getAverageScore());
    }

    private void disableAllJmenuItems() {
        saveObjectFile.setEnabled(false);
        readObjectFile.setEnabled(false);
        clear.setEnabled(false);
    }

}
