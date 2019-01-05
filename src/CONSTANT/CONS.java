package CONSTANT;

public class CONS {
    public static final String[] column = {"ID","Name","Score"};

    //Jframe
    public static final int frameWidth = 1000;
    public static final int frameHeight = 700;
    public static final int frameMidX = frameWidth/2;
    public static final int frameMidY = frameHeight/2;

    //ScorePanel
    public static final int scrollViewWidth = 430;
    public static final int scrollViewHeight = 400;
    public static final int scorePanelWidth = 450;
    public static final int scorePanelHeight = 450;
    public static final int scorePanelX = 20;
    public static final int scorePanelY = frameMidY - scorePanelHeight/2 - 20;

    //AnalyPanel
    public static final int analyPanelX = scorePanelX + scorePanelWidth + 25;
    public static final int analyPanelY = scorePanelY;
    public static final int analyPanelWidth = 450;
    public static final int analyPanelHeight = 450;
    public static final int analyViewX = 10;
    public static final int analyViewY = 35;
    public static final int analyViewWidth = 430;
    public static final int analyViewHeight = 400;

    // highestScoreArea
    public static final int highestScoreAreaWidth = 40;
    public static final int highestScoreAreaHeight = 20;
    public static final int highestScoreAreaX = analyViewWidth/2 - highestScoreAreaWidth/2 + 20;
    public static final int highestScoreAreaY = 20;

    // highestScoreLabel
    public static final int highestScoreLabelWidth = 120;
    public static final int highestScoreLabelHeight = 20;
    public static final int highestScoreLabelX = analyViewWidth/2 - highestScoreLabelWidth + 20;
    public static final int highestScoreLabelY = 20;

    // averageScoreArea
    public static final int averageScoreAreaWidth = 50;
    public static final int averageScoreAreaHeight = 20;
    public static final int averageScoreAreaX = analyViewWidth/2 - averageScoreAreaWidth/2 + 20;
    public static final int averageScoreAreaY = 50;

    // averageScoreLabel
    public static final int averageScoreLabelWidth = 120;
    public static final int averageScoreLabelHeight = 20;
    public static final int averageScoreLabelX = analyViewWidth/2 - averageScoreLabelWidth + 20;
    public static final int averageScoreLabelY = 50;
}
