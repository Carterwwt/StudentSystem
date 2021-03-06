package Panel;

import CONSTANT.CONS;
import Chart.BarChart;
import Chart.PieChart;
import DataStructure.Percentage;
import TableDataModel.DataModel;
import DataStructure.Student;
import FileTools.FileTools;
import FileTools.DataTools;
import Repository.StudentRepo;
import Exception.FileIOException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

@SuppressWarnings("all")

public class StudentClient extends JFrame {

    //I can't find the problem!!! Or there is no big here!

    TableRowSorter<AbstractTableModel> rowSorter;
    Thread searchThread;

    JPanel searchPanel;
    JPanel scorePanel;
    JPanel analyPanel;
    JPanel analyView;
    JPanel bottomPanel;
    JScrollPane scroll;
    JTable table;

    JFileChooser chooser;
    JFileChooser saver;
    File fileOpened;
    File fileToBeSaved;

    AbstractTableModel dataModel;

    JMenuBar menuBar;
    JMenu fileMenu;

    JMenuItem openTxtFile;
    JMenuItem saveTxtFile;
    JMenuItem saveObjectFile;
    JMenuItem readObjectFile;
    JMenuItem clear;

    JTextField searchArea;
    JTextField highestScoreArea;
    JTextField lowestScoreArea;
    JTextField averageScoreArea;

    JTextField[] numOfStu;
    JTextField[] perOfGrade;

    JLabel searchLabel;
    JLabel highestSocreLabel;
    JLabel lowestScoreLabel;
    JLabel averageScoreLabel;
    JLabel APlusLabel;
    JLabel ALabel;
    JLabel BLabel;
    JLabel CLabel;
    JLabel DLabel;

    JLabel filePath;

    JLabel[] takeUp;
    JLabel[] percentage;

    JButton barChartButton;
    JButton pieChartButton;
    JFrame barChartFrame;
    JFrame pieChartFrame;

    boolean isAnalyViewComponentsSetUp = false;
    boolean beingUpdated = false;

    public StudentClient() {
        chooser = new JFileChooser(".");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        saver = new JFileChooser(".");
        setLayout(null);
        setResizable(false);
        setTitle("学生成绩管理系统------Created By CarterWang");
        setBounds(400, 200, CONS.frameWidth, CONS.frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUpAllPanel();
        setVisible(true);
    }

    private void setUpAllPanel() {

        FileTools.removeData();
        setUpSearchPanel();
        setUpScorePanel();
        setUpAnalyPanel();
        setUpBottomPanel();

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.setBorderPainted(true);

        openTxtFile = new JMenuItem("Open TXT file");
        saveTxtFile = new JMenuItem("Save TXT file to");
        saveObjectFile = new JMenuItem("Save Object file to");
        readObjectFile = new JMenuItem("Open Object file");
        clear = new JMenuItem("Clear");

        saveTxtFile.setEnabled(false);
        saveObjectFile.setEnabled(false);
        clear.setEnabled(false);

        openTxtFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {


                int response = chooser.showOpenDialog(menuBar);
                if (response == JFileChooser.APPROVE_OPTION) {
                    fileOpened = chooser.getSelectedFile();
                    try {

                        FileTools.readTXTFile(fileOpened, StudentRepo.getStudentList());

                        refreshTable();
                        refreshBottomPanel();
                        refreshAnalyView();
                        refreshBottomPanel();

                        saveTxtFile.setEnabled(true);
                        saveObjectFile.setEnabled(true);
                        clear.setEnabled(true);
                        FileTools.outputList(StudentRepo.getStudentList().getStudents());
                    } catch (FileIOException ee) {
                        ee.showMessageDialog();
                        disableAllJmenuItems();
                    }
                    repaint();
                }
            }
        });

        saveTxtFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (saveTxtFile.isEnabled()) {
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "TXT文件(*.txt)", ".txt");
                    saver.setFileFilter(filter);

                    int respone = saver.showSaveDialog(menuBar);
                    if (respone == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = saver.getSelectedFile();
                        FileFilter txtFilter = (FileNameExtensionFilter) saver.getFileFilter();
                        fileToBeSaved = new File(selectedFile.getAbsolutePath() + ((FileNameExtensionFilter) txtFilter).getExtensions()[0]);
                        try {
                            FileTools.saveTXTFile(fileToBeSaved, StudentRepo.getStudentList());
                        } catch (FileIOException ee) {
                            ee.showMessageDialog();
                        }
                        System.out.println("File to be saved: " + fileToBeSaved.getAbsolutePath());
                    }
                }
            }
        });

        saveObjectFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (saveObjectFile.isEnabled()) {
                    try {
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "对象文件(*.score)", ".score");
                        saver.setFileFilter(filter);

                        int respone = saver.showSaveDialog(menuBar);
                        if (respone == JFileChooser.APPROVE_OPTION) {

                            File selectedFile = saver.getSelectedFile();
                            FileFilter txtFilter = (FileNameExtensionFilter) saver.getFileFilter();
                            fileToBeSaved = new File(selectedFile.getAbsolutePath() + ((FileNameExtensionFilter) txtFilter).getExtensions()[0]);

                            System.out.println(fileToBeSaved.getAbsolutePath());
                            // saveObjectFile
                            FileTools.saveObjectFile(fileToBeSaved, StudentRepo.getStudentList());
                        }
                    } catch (FileIOException ee) {
                        ee.showMessageDialog();
                    }
                }

            }
        });

        readObjectFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (readObjectFile.isEnabled()) {
                    try {
                        int response = chooser.showOpenDialog(menuBar);
                        if (response == JFileChooser.APPROVE_OPTION) {
                            fileOpened = chooser.getSelectedFile();
                            FileTools.readObjectFile(fileOpened, StudentRepo.getStudentList());
                            refreshAnalyView();
                            refreshTable();
                            refreshBottomPanel();
                            saveTxtFile.setEnabled(true);
                            clear.setEnabled(true);
                            saveObjectFile.setEnabled(true);
                            repaint();
                            FileTools.outputList(StudentRepo.getStudentList().getStudents());
                        }
                    } catch (FileIOException ee) {
                        disableAllJmenuItems();
                        ee.showMessageDialog();
                    }
                }


            }
        });

        clear.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                beingUpdated = true;

                FileTools.removeData();
                fileOpened = null;
                fileToBeSaved = null;
                refreshTable();
                refreshAnalyView();
                refreshBottomPanel();
                disableAllJmenuItems();

                beingUpdated = false;
            }
        });

        fileMenu.add(openTxtFile);
        fileMenu.add(saveTxtFile);
        fileMenu.addSeparator();
        fileMenu.add(readObjectFile);
        fileMenu.add(saveObjectFile);
        fileMenu.addSeparator();
        fileMenu.add(clear);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

    }

    private void setUpSearchPanel() {

        searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBounds(15, 2, CONS.frameWidth - 35, 50);
        searchPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        setUpSearchTextField();
        setUpSearchLabel();

        add(searchPanel);
    }

    private void setUpSearchTextField() {
        searchArea = new JTextField();
        searchArea.setBounds(2, 5, 200, 40);
        searchArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        searchArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearchResult(searchArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearchResult(searchArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearchResult(searchArea.getText());
            }
        });

        searchPanel.add(searchArea);
    }

    private void setUpSearchLabel() {
        searchLabel = new JLabel("输入查询(点击表头可排序)");
        searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        searchLabel.setBounds(210, 5, 200, 40);
        searchPanel.add(searchLabel);
    }

    private void updateSearchResult(String regex) {
        if (regex.contains("\\"))
            rowSorter.setRowFilter(RowFilter.regexFilter("找不到的放弃吧"));
        else
            rowSorter.setRowFilter(RowFilter.regexFilter(regex));
    }

    private void setUpScorePanel() {

        scorePanel = new JPanel();

        // set scorePanel
        scorePanel.setLayout(null);
        scorePanel.setBounds(CONS.scorePanelX, CONS.scorePanelY, CONS.scorePanelWidth, CONS.scorePanelHeight);
        scorePanel.setBorder(BorderFactory.createLoweredBevelBorder());

        //set table
        setUpTable();

        // Set ScrollPanel
        scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10, 35, CONS.scrollViewWidth, CONS.scrollViewHeight);
        scorePanel.add(scroll);

        // Set label
        JLabel label = new JLabel("Score");
        label.setFont(new Font("Helvetica", Font.BOLD, 14));
        label.setBounds(10, 10, 50, 20);
        label.setVisible(true);
        scorePanel.add(label);

        add(scorePanel);
        scorePanel.setVisible(true);

    }

    private void setUpAnalyPanel() {
        analyPanel = new JPanel();
        analyPanel.setBounds(CONS.analyPanelX, CONS.analyPanelY, CONS.analyPanelWidth, CONS.analyPanelHeight);
        analyPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        analyPanel.setLayout(null);

        // Set Title
        JLabel label = new JLabel("Data Analysis");
        label.setFont(new Font("Helvetica", Font.BOLD, 14));
        label.setBounds(10, 10, 100, 20);
        label.setVisible(true);

        // Set AnalyViewPanel
        analyView = new JPanel();
        analyView.setBorder(BorderFactory.createLoweredBevelBorder());
        analyView.setBounds(CONS.analyViewX, CONS.analyViewY, CONS.analyViewWidth, CONS.analyViewHeight);
        analyView.setVisible(true);
        analyView.setLayout(null);

        // Set AnalyViewComponents
        if (!StudentRepo.getStudentList().getStudents().isEmpty())
            setUpAnalyViewComponents();

        analyPanel.add(analyView);
        analyPanel.add(label);
        add(analyPanel);
        analyPanel.setVisible(true);

    }

    private void setUpBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setBounds(15, CONS.frameHeight - 90, CONS.frameWidth - 33, 40);
        bottomPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomPanel.setLayout(null);

        // filePath
        filePath = new JLabel();
        filePath.setBounds(5, 12, 600, 20);
        filePath.setFont(new Font("宋体", Font.PLAIN, 13));
        if (fileOpened != null)
            filePath.setText(fileOpened.getAbsolutePath() + "  共" + StudentRepo.getStudentList().getStudents().size() + "人");

        bottomPanel.add(filePath);
        add(bottomPanel);
        bottomPanel.setVisible(true);
    }

    private void setUpTable() {

        dataModel = new DataModel();

        table = new JTable(dataModel);
        table.setRowHeight(25);
        table.setFont(new Font("宋体", Font.PLAIN, 14));
        table.setRowSelectionAllowed(false);
        rowSorter = new TableRowSorter<AbstractTableModel>(dataModel);
        table.setRowSorter(rowSorter);

        // Set header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 22));
        header.setFont(new Font("Helvetica", Font.PLAIN, 14));

        setUpCellRender();

        //scorePanel.add(table);
    }

    private void setUpCellRender() {
        // Set Cell Render
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer()// 设置表格间隔色
        {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                // table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                if (row % 2 == 0)
                    setBackground(new Color(236, 236, 236));
                else if (row % 2 == 1)
                    setBackground(Color.white);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };


        for (int i = 0; i < 3; i++) {
            table.getColumn(CONS.column[i]).setCellRenderer(renderer);
        }

    }

    private void setUpHighestScoreLabel() {
        highestSocreLabel = new JLabel();
        highestSocreLabel.setBounds(CONS.dataLabelX, CONS.highestY, CONS.dataLabelWidth, CONS.dataHeight);
        highestSocreLabel.setText("Highest Score");
        analyView.add(highestSocreLabel);
    }

    private void setUpHighestScoreArea() {
        highestScoreArea = new JTextField();
        highestScoreArea.setBounds(CONS.dataAreaX, CONS.highestY, CONS.dataAreaWidth, CONS.dataHeight);
        Student student = DataTools.getHighestStudent();
        highestScoreArea.setText(String.valueOf(student.getScore()));
        highestScoreArea.setHorizontalAlignment(JTextField.RIGHT);
        highestScoreArea.setEditable(false);
        analyView.add(highestScoreArea);
    }

    private void setUpLowestScoreLabel() {
        lowestScoreLabel = new JLabel();
        lowestScoreLabel.setBounds(CONS.dataLabelX, CONS.lowestY, CONS.dataLabelWidth, CONS.dataHeight);
        lowestScoreLabel.setText("Lowest Score");
        analyView.add(lowestScoreLabel);
    }

    private void setUpLowestScoreArea() {
        lowestScoreArea = new JTextField();
        lowestScoreArea.setBounds(CONS.dataAreaX, CONS.lowestY, CONS.dataAreaWidth, CONS.dataHeight);
        Student student = DataTools.getLowestStudent();
        lowestScoreArea.setText(String.valueOf(student.getScore()));
        lowestScoreArea.setHorizontalAlignment(JTextField.RIGHT);
        lowestScoreArea.setEditable(false);
        analyView.add(lowestScoreArea);
    }

    private void setUpAverageScoreLabel() {
        averageScoreLabel = new JLabel();
        averageScoreLabel.setBounds(CONS.dataLabelX, CONS.averageY, CONS.dataLabelWidth, CONS.dataHeight);
        averageScoreLabel.setText("Average Score");
        analyView.add(averageScoreLabel);
    }

    private void setUpAverageScoreArea() {
        averageScoreArea = new JTextField();
        averageScoreArea.setBounds(CONS.dataAreaX, CONS.averageY, CONS.dataAreaWidth, CONS.dataHeight);
        String average = DataTools.getAverageScore();
        averageScoreArea.setHorizontalAlignment(JTextField.RIGHT);
        averageScoreArea.setText(average);
        averageScoreArea.setVisible(true);
        averageScoreArea.setEditable(false);
        analyView.add(averageScoreArea);
    }


    private void setUpAnalyViewComponents() {

        if (!isAnalyViewComponentsSetUp)
            isAnalyViewComponentsSetUp = true;

        // high low average
        setUpHighestScoreLabel();
        setUpHighestScoreArea();
        setUpLowestScoreLabel();
        setUpLowestScoreArea();
        setUpAverageScoreLabel();
        setUpAverageScoreArea();
        setUpButtons();
        setUpPercentage();

    }

    private void setUpButtons() {
        barChartButton = new JButton("Create barChart");
        pieChartButton = new JButton("Create pieChart");
        barChartButton.setBounds(CONS.barChartX,CONS.chartButtonY,CONS.chartButtonWidth,CONS.chartButtonHeight);
        pieChartButton.setBounds(CONS.pieChartX,CONS.chartButtonY,CONS.chartButtonWidth,CONS.chartButtonHeight);

        barChartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                BarChart barChart = new BarChart(DataTools.getPercentage());
                barChartFrame = barChart.getBarChartFrame();
                barChartFrame.setBounds(400,400,750,630);
                barChartFrame.setVisible(true);
                barChartButton.setFocusPainted(false);
            }
        });

        pieChartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                PieChart pieChart = new PieChart(DataTools.getPercentage());
                pieChartFrame = pieChart.getPieChartFrame();
                pieChartFrame.setBounds(400,400,750,630);
                pieChartFrame.setVisible(true);
                pieChartButton.setFocusPainted(false);
            }
        });

        analyView.add(barChartButton);
        analyView.add(pieChartButton);
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

        APlusLabel.setBounds(CONS.dataLabelX, CONS.APlusY, CONS.gradeLabelWidth, CONS.dataHeight);
        ALabel.setBounds(CONS.dataLabelX, CONS.AY, CONS.gradeLabelWidth, CONS.dataHeight);
        BLabel.setBounds(CONS.dataLabelX, CONS.BY, CONS.gradeLabelWidth, CONS.dataHeight);
        CLabel.setBounds(CONS.dataLabelX, CONS.CY, CONS.gradeLabelWidth, CONS.dataHeight);
        DLabel.setBounds(CONS.dataLabelX, CONS.DY, CONS.gradeLabelWidth, CONS.dataHeight);

        analyView.add(APlusLabel);
        analyView.add(ALabel);
        analyView.add(BLabel);
        analyView.add(CLabel);
        analyView.add(DLabel);

        takeUp = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            takeUp[i] = new JLabel("takes up");
            takeUp[i].setBounds(CONS.gradeLabelX, CONS.APlusY + i * CONS.space, CONS.gradePerWidth, CONS.dataHeight);
            analyView.add(takeUp[i]);
        }

        percentage = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            percentage[i] = new JLabel("%");
            percentage[i].setBounds(CONS.percentageX, CONS.APlusY + i * CONS.space, 50, CONS.dataHeight);
            analyView.add(percentage[i]);
        }

    }

    private void setUpPercentageData(Percentage percentage) {

        numOfStu = new JTextField[5];

        numOfStu[0] = new JTextField(String.valueOf(percentage.getNumOfGradeAPlus()));
        numOfStu[1] = new JTextField(String.valueOf(percentage.getNumOfGradeA()));
        numOfStu[2] = new JTextField(String.valueOf(percentage.getNumOfGradeB()));
        numOfStu[3] = new JTextField(String.valueOf(percentage.getNumOfGradeC()));
        numOfStu[4] = new JTextField(String.valueOf(percentage.getNumOfGradeD()));

        for (int i = 0; i < 5; i++) {
            numOfStu[i].setBounds(CONS.dataAreaX, CONS.APlusY + i * CONS.space, CONS.dataAreaWidth, CONS.dataHeight);
            numOfStu[i].setEditable(false);
            numOfStu[i].setHorizontalAlignment(JTextField.RIGHT);
            analyView.add(numOfStu[i]);
        }

        perOfGrade = new JTextField[5];

        perOfGrade[0] = new JTextField(String.format("%.2f", percentage.getPercentOfAPlus()));
        perOfGrade[1] = new JTextField(String.format("%.2f", percentage.getPercentOfA()));
        perOfGrade[2] = new JTextField(String.format("%.2f", percentage.getPercentOfB()));
        perOfGrade[3] = new JTextField(String.format("%.2f", percentage.getPercentOfC()));
        perOfGrade[4] = new JTextField(String.format("%.2f", percentage.getPercentOfD()));

        for (int i = 0; i < 5; i++) {
            perOfGrade[i].setBounds(320, CONS.APlusY + i * CONS.space, 55, CONS.dataHeight);
            perOfGrade[i].setEditable(false);
            perOfGrade[i].setHorizontalAlignment(JTextField.RIGHT);
            analyView.add(perOfGrade[i]);
        }

    }

    private void refreshTable() {
        dataModel = new DataModel();
        table.setModel(dataModel);
        setUpCellRender();
    }

    private void refreshAnalyView() {

        Student highestStudent = DataTools.getHighestStudent();
        highestScoreArea.setText(String.valueOf(highestStudent.getScore()));
        Student lowestStudent = DataTools.getLowestStudent();
        lowestScoreArea.setText(String.valueOf(lowestStudent.getScore()));
        averageScoreArea.setText(DataTools.getAverageScore());

        Percentage percentage = DataTools.getPercentage();
        numOfStu[0].setText(String.valueOf(percentage.getNumOfGradeAPlus()));
        numOfStu[1].setText(String.valueOf(percentage.getNumOfGradeA()));
        numOfStu[2].setText(String.valueOf(percentage.getNumOfGradeB()));
        numOfStu[3].setText(String.valueOf(percentage.getNumOfGradeC()));
        numOfStu[4].setText(String.valueOf(percentage.getNumOfGradeD()));

        perOfGrade[0].setText(String.format("%.2f", percentage.getPercentOfAPlus()));
        perOfGrade[1].setText(String.format("%.2f", percentage.getPercentOfA()));
        perOfGrade[2].setText(String.format("%.2f", percentage.getPercentOfB()));
        perOfGrade[3].setText(String.format("%.2f", percentage.getPercentOfC()));
        perOfGrade[4].setText(String.format("%.2f", percentage.getPercentOfD()));

    }

    private void refreshBottomPanel() {
        if (filePath != null)
            if (fileOpened != null)
                filePath.setText(fileOpened.getAbsolutePath() + "  共" + StudentRepo.getStudentList().getStudents().size() + "人");
            else
                filePath.setText("");
    }

    private void disableAllJmenuItems() {
        if (fileOpened == null) {
            saveTxtFile.setEnabled(false);
            saveObjectFile.setEnabled(false);
            clear.setEnabled(false);
        }
    }
}

