package SortEvent;

import Panel.StudentClient;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SortByScore implements MouseListener {

    StudentClient client;

    public SortByScore(StudentClient client) {
        this.client = client;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("SortByScore");
        client.sortByScore();
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
