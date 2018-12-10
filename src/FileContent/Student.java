package FileContent;

import java.io.Serializable;

public class Student implements Serializable {

    String id;
    String name;
    int score;

    public Student(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Student() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "ID: " + id + "   " + "Name: " + name + "   " + "Score: " + score;
    }

}
