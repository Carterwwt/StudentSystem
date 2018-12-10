package SortEvent;

import FileContent.StudentList;
import Panel.StudentClient;
import Repository.StudentRepo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SortByID implements MouseListener {

    StudentClient client;

    public SortByID(StudentClient client) {
        this.client = client;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("SortByID");
        client.sortByID();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
