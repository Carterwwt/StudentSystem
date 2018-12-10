package DataModel;

import FileContent.Student;
import Repository.StudentRepo;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class DataModel extends AbstractTableModel {

    ArrayList<Student> list = StudentRepo.getStudentList().getStudents();

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
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
        if(column == 0)
            return "ID";
        else if(column == 1)
            return "Name";
        else
            return "Score";
    }
}
