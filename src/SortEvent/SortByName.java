package SortEvent;

import Panel.StudentClient;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SortByName implements MouseListener {

    StudentClient client;

    public SortByName(StudentClient client) {
        this.client = client;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("SortByName");
        client.sortByName();
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
