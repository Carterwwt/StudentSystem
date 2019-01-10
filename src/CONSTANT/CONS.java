package CONSTANT;

public class CONS {
    public static final String[] column = {"ID","Name","Score"};

    //Jframe
    public static final int frameWidth = 970;
    public static final int frameHeight = 670;
    public static final int frameMidX = frameWidth/2;
    public static final int frameMidY = frameHeight/2;

    //ScorePanel
    public static final int scrollViewWidth = 430;
    public static final int scrollViewHeight = 450;
    public static final int scorePanelWidth = 450;
    public static final int scorePanelHeight = 520;
    public static final int scorePanelX = 15;
    public static final int scorePanelY = frameMidY - scorePanelHeight/2 - 20;

    //AnalyPanel
    public static final int analyPanelX = scorePanelX + scorePanelWidth + 35;
    public static final int analyPanelY = scorePanelY;
    public static final int analyPanelWidth = 450;
    public static final int analyPanelHeight = scorePanelHeight;
    public static final int analyViewX = 10;
    public static final int analyViewY = 35;
    public static final int analyViewWidth = 430;
    public static final int analyViewHeight = 450;

    // highestY
    public static final int highestY = 20;

    // lowestY
    public static final int lowestY = 65;

    // averageY
    public static final int averageY = 110;

    // dataLabel dataArea
    public static final int dataLabelX = 20;
    public static final int dataAreaX = 185;
    public static final int dataLabelWidth = 120;
    public static final int dataAreaWidth = 60;
    public static final int dataHeight = 25;

    public static final int space = 43;

    // gradeLabel
    public static final int APlusY = 165;
    public static final int AY = APlusY + space;
    public static final int BY = APlusY + space * 2;
    public static final int CY = APlusY + space * 3;
    public static final int DY = APlusY + space * 4;
    public static final int gradeLabelX = 255;
    public static final int gradeLabelWidth = 140;
    public static final int gradePerWidth = 80;
    public static final int percentageX = 380;

    public static final int chartButtonWidth = 130;
    public static final int chartButtonHeight = 35;
    public static final int barChartX = analyViewWidth/2 - (chartButtonWidth*2 + 20)/2;
    public static final int pieChartX = analyPanelWidth/2 + 10;
    public static final int chartButtonY = 390;

}
