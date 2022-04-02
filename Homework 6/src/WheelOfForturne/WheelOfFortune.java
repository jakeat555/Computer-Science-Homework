package WheelOfForturne;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static javafx.util.Duration.millis;

public class WheelOfFortune extends Application {
    private String phrase;
    private Circle wheel;

    private char [] phraseAsChar;
    private Text [] answer;

    private BooleanProperty [] shouldAnswerLetterBeVisibleProperty;
    private BooleanProperty [] hasLetterBeenGuessedProperty;
    private BooleanProperty isGuessedPartOfPhrase;
    private StringProperty currentGuessProperty;
    private StringProperty playerScoreAsTextProperty;

    private BorderPane inputPane;
    private GridPane alphabetPane;

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        initializeValues();
        Pane wheelPane = makeWheelPane();
        Pane answerPane = makeAnswerPane();
        inputPane = makeInputPane();
        alphabetPane = makeGuessedAlphabet();
        Pane scorePane = makeScorePane();
        Pane correctOrNotPane = makeCorrectOrNotPane();
        ComboBox<String> resetMenu = makeComboBox();

        VBox rightSide = new VBox();
        rightSide.setSpacing(50);
        rightSide.getChildren().add(answerPane);
        rightSide.getChildren().add(resetMenu);
        rightSide.getChildren().add(correctOrNotPane);

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(50,50,50,50));
        bp.setCenter(wheelPane);
        bp.setBottom(inputPane);
        bp.setRight(rightSide);
        bp.setLeft(alphabetPane);
        bp.setTop(scorePane);

        this.primaryStage = primaryStage;
        Scene scene = new Scene(bp);
        this.primaryStage.setTitle("Wheel of Fortune");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private Pane makeCorrectOrNotPane(){
        Pane pane = new Pane();
        Text message = new Text();
        message.setText("Correct");
        message.setFont(new Font(50));
        message.visibleProperty().bind(isGuessedPartOfPhrase);

        Text message1 = new Text();
        message1.setText("Incorrect");
        message1.setFont(new Font(50));
        message1.setVisible(false);
        message1.visibleProperty().bind(isGuessedPartOfPhrase.not());
        pane.getChildren().addAll(message,message1);
        return pane;
    }

    private ComboBox<String> makeComboBox(){
        ComboBox<String> options = new ComboBox<>();
        options.getItems().addAll("Reset","Rules");
        options.setOnAction(e-> reset());

        EventHandler<ActionEvent> event =
                e -> {
                    //selected.setText(options.getValue() + " selected");
                    if(options.getValue().equals("Reset")){
                        reset();
                    }
                    else if(options.getValue().equals("Rules")){
                        openRulesWindow();
                    }
                };

        // Set on action
        options.setOnAction(event);
        return options;
    }

    private void reset(){
        try {
            primaryStage.close();
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pane makeScorePane(){
        Pane pane = new Pane();
        Text score = new Text();
        score.setFont(new Font(50));
        score.setText(playerScoreAsTextProperty.getValue());
        score.textProperty().bind(playerScoreAsTextProperty);
        pane.getChildren().add(score);
        return pane;
    }

    private Pane makeAnswerPane() {
        Text dashedPhrase= new Text("");

        // make the underlined portion
        for(int i = 0; i < phrase.length() ;i++){
            if(phraseAsChar[i]==' '){
                dashedPhrase.setText(dashedPhrase.getText()+" ");
            }
            else{
                dashedPhrase.setText(dashedPhrase.getText()+"-");
            }
        }
        dashedPhrase.setFont(new Font(45));

        //make the answer invisible and bind to if it should be visible
        for(int i = 0; i < phrase.length(); i++){
            answer[i]= new Text(phraseAsChar[i]+"");
            answer[i].setFont(new Font(30));
            if(!answer[i].getText().equals(" ")){
                answer[i].visibleProperty().bind(shouldAnswerLetterBeVisibleProperty[i]);
            }
            else{
                answer[i].setFont(new Font(50));
            }

        }

        HBox top = new HBox(answer);
        HBox bottom = new HBox(dashedPhrase);

        return new Pane(top,bottom);
    }

    private BorderPane makeInputPane(){
        //get that data from the user
        TextField tbox = new TextField();
        BorderPane inputPane = new BorderPane();
        Label lb1 = new Label("Type your guessed letter, then hit enter OR click a letter on the right");

        lb1.setAlignment(Pos.CENTER_LEFT);
        tbox.setMaxWidth(200);
        tbox.setAlignment(Pos.CENTER_LEFT);
        tbox.setOnAction(e -> {
            try {
                pullData(tbox);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        inputPane.setPadding(new Insets(30,30,30,30));

        inputPane.setBottom(tbox);
        inputPane.setLeft(lb1);
        inputPane.setVisible(false);
        return inputPane;
    }

    private GridPane makeGuessedAlphabet(){
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(30,30,30,30));
        Text [] textABCs = new Text[26];

        //set each letter to be visible until guessed
        for(int i=0; i < 26; i++){
            textABCs[i] = new Text();
            textABCs[i].setText((char) (i + 65) + " ");
            textABCs[i].setFont(new Font(50));
            textABCs[i].visibleProperty().bind(hasLetterBeenGuessedProperty[i].not());
            TextField tbox = new TextField();
            tbox.setText(textABCs[i].getText());

            //make it so that the letters are also buttons
            textABCs[i].setOnMouseClicked(e -> {
                try {
                    pullData(tbox);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            gp.add(textABCs[i],i%5,i/5);
        }
        gp.setVisible(false);
        return gp;
    }

    private Pane makeWheelPane(){
        Pane pane = new Pane();
        Image wheelBackground = new Image("https://wheel.fhtl.byu.edu/images/wheel.png");
        ImagePattern background = new ImagePattern(wheelBackground);

        wheel.setStroke(Color.BLACK);
        wheel.setFill(background);
        wheel.setRotate(7.5);

        Polygon marker = new Polygon(290,95,310,95,300,110);
        marker.setFill(Color.LIMEGREEN);

        pane.getChildren().addAll(wheel,marker);

        Circle circleBt = new Circle(77);
        circleBt.setCenterX(300);
        circleBt.setCenterY(300);
        circleBt.setFill(Color.TRANSPARENT);
        circleBt.setStroke(Color.BLACK);
        circleBt.setOnMouseClicked(e -> spinningAnimation());

        Text text = new Text("Spin\nthe\nWheel");
        text.setOnMouseClicked(e->spinningAnimation());
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.RED);
        text.setFont(new Font(25));
        text.setX(270);
        text.setY(275);



        pane.getChildren().addAll(circleBt,text);
        return pane;
    }

    private void spinningAnimation(){
        //Wheel is segmented into 72 parts, 3 per tiles
        //rotation is the number of segments to be rotated
        int rotation = 72 + (int)(Math.random()*72);
        RotateTransition a2 = new RotateTransition(millis(2500),wheel);
        a2.setByAngle(360.0*rotation/72);
        a2.play();
        inputPane.setVisible(true);
        alphabetPane.setVisible(true);
    }

    private int getBoardValue(){
        //Board value is 0 if lose a turn, -1 if bankrupt, the board value otherwise
        int boardValue;
        double rotate = wheel.getRotate()%360;
        if(rotate>345){
            boardValue=500;
        }
        else if(rotate > 330){
            boardValue=900;
        }
        else if(rotate > 315){
            boardValue=700;
        }
        else if(rotate > 300){
            boardValue=300;
        }
        else if(rotate > 285){
            boardValue=800;
        }
        else if(rotate > 270){
            boardValue=550;
        }
        else if(rotate > 255){
            boardValue=400;
        }
        else if(rotate > 240){
            boardValue=500;
        }
        else if(rotate > 225){
            boardValue=600;
        }
        else if(rotate > 210){
            boardValue=350;
        }
        else if(rotate > 195){
            boardValue=500;
        }
        else if(rotate > 180){
            boardValue=900;
        }
        else if(rotate > 165){
            boardValue=-1;
        }
        else if(rotate > 150){
            boardValue=650;
        }
        else if(rotate > 135){
            boardValue=0;
        }
        else if(rotate > 120){
            boardValue=700;
        }
        else if(rotate > 105){
            boardValue=0;
        }
        else if(rotate > 90){
            boardValue=800;
        }
        else if(rotate > 75){
            boardValue=500;
        }
        else if(rotate > 60){
            boardValue=450;
        }
        else if(rotate > 45){
            boardValue=500;
        }
        else if(rotate > 30){
            boardValue=300;
        }
        else if(rotate > 15){
            boardValue=-1;
        }
        else {
            boardValue=5000;
        }
        return boardValue;
    }

    private void pullData(TextField tbox){
        currentGuessProperty.setValue(tbox.getText());

        int scoreToAdd =getBoardValue();
        char currentGuess = currentGuessProperty.getValue().toUpperCase().charAt(0);
        int currentGuessIndex = currentGuessProperty.getValue().toUpperCase().charAt(0)-65;
        tbox.setText("");

        isGuessedPartOfPhrase.setValue(phrase.toUpperCase().contains(currentGuess+""));
        //bind the current guess to if the letter is in the solution
        // if letter is in the solution
        if(isGuessedPartOfPhrase.getValue()) {
            //add letter to the solutuion
            //shouldAnswerLetterBeVisibleProperty[currentGuessIndex].setValue(true);
            for (int i = 0; i < phraseAsChar.length; i++) {
                if(currentGuess == phraseAsChar[i]) {
                    shouldAnswerLetterBeVisibleProperty[i].setValue(true);
                }
            }


            // if normal tile
            if(scoreToAdd>0){
                //add score
                int oldScore = Integer.valueOf(playerScoreAsTextProperty.getValue());
                playerScoreAsTextProperty.setValue( String.valueOf( oldScore + scoreToAdd));
                // display happy message
            }
            //if bankrupt is spun
            else if(scoreToAdd<0){
                playerScoreAsTextProperty.setValue("0");
            }

            boolean isGameDone = true;
            for(Text x : answer){
                isGameDone = x.visibleProperty().getValue() && isGameDone;
            }
            if(isGameDone){
                openGameDoneWindow();
            }
        }

        hasLetterBeenGuessedProperty[currentGuessIndex].setValue(true);
        inputPane.setVisible(false);
        alphabetPane.setVisible(false);
    }

    private String getPhrase() throws Exception{
        File file = new File("Phrases.txt");
        Scanner in = new Scanner(file);
        ArrayList<String> allPhrases = new ArrayList<>();
        Random rand = new Random();

        while(in.hasNextLine()){
            allPhrases.add(0,in.nextLine());
        }
        in.close();
        return allPhrases.get(rand.nextInt(allPhrases.size()));
    }

    private void initializeValues() throws Exception{
        wheel = new Circle(300,300,200);
        phrase = getPhrase();
        answer = new Text[phrase.length()];
        currentGuessProperty = new SimpleStringProperty("");
        phraseAsChar = phrase.toUpperCase().toCharArray();

        shouldAnswerLetterBeVisibleProperty = new SimpleBooleanProperty[phrase.length()];
        isGuessedPartOfPhrase = new SimpleBooleanProperty(false);
        hasLetterBeenGuessedProperty = new SimpleBooleanProperty[26];
        playerScoreAsTextProperty = new SimpleStringProperty("0");

        for(int i=0; i < 26; i++){
            hasLetterBeenGuessedProperty[i] = new SimpleBooleanProperty(false);
        }

        for(int i=0; i< phrase.length(); i++){
            shouldAnswerLetterBeVisibleProperty[i] = new SimpleBooleanProperty(false);
        }

    }

    private void openRulesWindow(){

        Text rules = new Text();
        rules.setText("\n \nWelcome to Wheel of Fortune\n" +
                "First spin the wheel!\n" +
                "Then pick a letter by typing it in or pressing it on the screen\n"+
                "If its right, we'll add your points\n" +
                "Beware, if you spin a Bankrupt, you could lose all your points!\n"+
                "Currently the Lose a Turn and Free Spin tiles will allow you to guess, but will award 0 points");
        rules.setFont(new Font(20));
        rules.setVisible(true);

        Pane pane = new Pane();
        pane.setMinHeight(100);
        pane.getChildren().add(rules);
        Scene secondScene = new Scene(pane, 800, 400);

        Stage newWindow = new Stage();
        newWindow.setTitle("Rules");
        newWindow.setScene(secondScene);


        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);

        newWindow.setX(primaryStage.getX() + 400);
        newWindow.setY(primaryStage.getY() + 400);

        newWindow.show();
    }

    private void openGameDoneWindow(){
        Text text = new Text();
        text.setText("\nCongratulations! You won!\nYour final score was "+playerScoreAsTextProperty.getValue()+" points");
        text.setFont(new Font(20));
        text.setVisible(true);

        Pane pane = new Pane();
        pane.setMinHeight(100);
        pane.getChildren().add(text);
        Scene secondScene = new Scene(pane, 800, 400);

        Stage newWindow = new Stage();
        newWindow.setTitle("You Won!");
        newWindow.setScene(secondScene);


        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);

        newWindow.setX(primaryStage.getX() + 400);
        newWindow.setY(primaryStage.getY() + 400);

        newWindow.show();
    }
}