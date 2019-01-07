package TableDataModel;

import CONSTANT.CONS;
import DataStructure.Student;
import Repository.StudentRepo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class DataModel extends AbstractTableModel {


    @Override
    public int getRowCount() {
        ArrayList<Student> list = StudentRepo.getStudentList().getStudents();
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<Student> list = StudentRepo.getStudentList().getStudents();
        Student student = list.get(rowIndex);
        if(columnIndex == 0)
            return student.getId();
        else if(columnIndex == 1)
            return student.getName();
        else
            return student.getScore();
    }

    @Override
    public String getColumnName(int column) {
        return CONS.column[column];
    }

}
