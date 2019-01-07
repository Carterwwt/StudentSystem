package DataStructure;

public class Percentage {

    private int numOfGradeAPlus;
    private int numOfGradeA;
    private int numOfGradeB;
    private int numOfGradeC;
    private int numOfGradeD;

    private double percentOfAPlus;
    private double percentOfA;
    private double percentOfB;
    private double percentOfC;
    private double percentOfD;

    public Percentage(int numOfGradeAPlus, int numOfGradeA, int numOfGradeB, int numOfGradeC, int numOfGradeD, double percentOfAPlus, double percentOfA, double percentOfB, double percentOfC, double percentOfD) {
        this.numOfGradeAPlus = numOfGradeAPlus;
        this.numOfGradeA = numOfGradeA;
        this.numOfGradeB = numOfGradeB;
        this.numOfGradeC = numOfGradeC;
        this.numOfGradeD = numOfGradeD;
        this.percentOfAPlus = percentOfAPlus;
        this.percentOfA = percentOfA;
        this.percentOfB = percentOfB;
        this.percentOfC = percentOfC;
        this.percentOfD = percentOfD;
    }

    public int getNumOfGradeAPlus() {
        return numOfGradeAPlus;
    }

    public int getNumOfGradeA() {
        return numOfGradeA;
    }

    public int getNumOfGradeB() {
        return numOfGradeB;
    }

    public int getNumOfGradeC() {
        return numOfGradeC;
    }

    public int getNumOfGradeD() {
        return numOfGradeD;
    }

    public double getPercentOfAPlus() {
        return percentOfAPlus;
    }

    public double getPercentOfA() {
        return percentOfA;
    }

    public double getPercentOfB() {
        return percentOfB;
    }

    public double getPercentOfC() {
        return percentOfC;
    }

    public double getPercentOfD() {
        return percentOfD;
    }
}
