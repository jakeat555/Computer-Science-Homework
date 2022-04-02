package Hangman;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman extends Application {
    //fix display incorrect correct
    private IntegerProperty numOfWrongGuessesProperty;
    private BooleanProperty isGuessedPartOfPhrase;
    private StringProperty currentGuessProperty;
    private BooleanProperty[] hasLetterBeenGuessedProperty;
    private BooleanProperty[] shouldAnswerLetterBeVisibleProperty;
    private BooleanProperty[] isHangManPartVisibleProperty;

    private String phrase;
    private char[] phraseAsChar;
    private Text[] answer;


    private Shape[] hangManBody;
    private Shape[] gallows;

    private Pane hangManPane;
    private Pane correctOrNotPane;
    private GridPane guessedAlphabet;
    private BorderPane inputPane;
    private Pane answerPane;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        initializeValues();

        makePanes();

        //arrange the panes and window
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(30,30,30,30));
        bp.setLeft(hangManPane);
        bp.setCenter(guessedAlphabet);
        bp.setTop(correctOrNotPane);
        bp.setBottom(inputPane);
        bp.setRight(answerPane);

        this.primaryStage = primaryStage;
        Scene scene = new Scene(bp);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private Shape[] makeGallows(){
        //Make the gallows (outline that stays)
        Line noose = new Line(400, 100, 400, 200);
        Line horizontal = new Line(200, 100, 400, 100);
        Line vertical = new Line(200, 100, 200, 450);
        Arc base = new Arc(200, 525, 150, 75, 15, 150);
        return new Shape[]{noose,horizontal,vertical,base};
    }

    private Shape[] hangManBody(){
        //make humanoid figure from Washington University
        Circle head = new Circle(400, 250, 50);
        Line leftArm = new Line(350, 320, 380, 340);
        Line rightArm = new Line(450, 320, 420, 340);
        Line leftLeg = new Line(400, 390, 350, 450);
        Line rightLeg = new Line(400, 390, 450, 450);
        Image shirt = new Image("https://rfathead-res.cloudinary.com/image/upload/h_300,w_300/logos/lgo_ncaa_washington_huskies.png");
        Rectangle body = new Rectangle(380,300,40,90);
        body.setStroke(Color.BLACK);
        ImagePattern background = new ImagePattern(shirt);
        body.setFill(background);
        return new Shape []  {head, body,leftArm,rightArm,leftLeg,rightLeg};
    }

    private Pane makeHangManPane(){
        //Make the gallows look normal
        gallows[3].setFill(Color.TRANSPARENT);
        gallows[3].setStroke(Color.BLACK);

        //Make the human look normal
        hangManBody[0].setFill(Color.TRANSPARENT);
        hangManBody[0].setStroke(Color.BLACK);

        //initialize the figure to be invisible and bind to if they should be
        for (int i = 0; i < 6; i++) {
            isHangManPartVisibleProperty[i] = new SimpleBooleanProperty(false);
            hangManBody[i].visibleProperty().bind(isHangManPartVisibleProperty[i]);
        }


        Pane pane = new Pane();
        pane.getChildren().addAll(gallows);
        pane.getChildren().addAll(hangManBody);
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
        Label lb1 = new Label("Type your guessed letter, then hit enter or click a letter on the right");

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
        return gp;
    }

    private Pane makeCorrectOrNotPane(){
        Label incorrect = new Label();
        incorrect.setText("Incorrect");
        incorrect.setFont(new Font(40));
        incorrect.setTextFill(Color.RED);
        incorrect.setVisible(false);

        Label correct = new Label();
        correct.setText("Correct");
        correct.setFont(new Font(40));
        correct.setTextFill(Color.GREEN);
        correct.setVisible(false);

        incorrect.visibleProperty().bind(isGuessedPartOfPhrase.not());
        correct.visibleProperty().bind(isGuessedPartOfPhrase);

        Pane pane = new Pane();
        pane.getChildren().add(incorrect);
        pane.getChildren().add(correct);
        return pane;
    }

    private String getPhrase() throws Exception{
        File file = new File("Phrases.txt");
        Scanner in = new Scanner(file);
        ArrayList<String> allPhrases = new ArrayList<>();
        while(in.hasNextLine()){
            allPhrases.add(0,in.nextLine());
        }
        in.close();
        Random rand = new Random();
        String phrase = allPhrases.get(rand.nextInt(allPhrases.size()));
        return phrase;
    }

    private void pullData(TextField tbox){
        currentGuessProperty.setValue(tbox.getText());

        char currentGuess = currentGuessProperty.getValue().toUpperCase().charAt(0);
        int currentGuessIndex = currentGuessProperty.getValue().toUpperCase().charAt(0)-65;


        //if guessed letter is part of phrase
        if(!phrase.toUpperCase().contains(currentGuess+"")){
            //if letter not has been guessed before increase wrong guess count
            if(!hasLetterBeenGuessedProperty[currentGuessIndex].getValue()) {
                //increase wrong guess count
                numOfWrongGuessesProperty.setValue(numOfWrongGuessesProperty.getValue() + 1);

                //make the next hangman part visible
                isHangManPartVisibleProperty[numOfWrongGuessesProperty.getValue()-1].setValue(true);
            }
            //displays the revealed solution


            //displays the correct/ incorrect
            isGuessedPartOfPhrase.setValue(false);
        }
        else{
            isGuessedPartOfPhrase.setValue(true);
            for (int i = 0; i < phraseAsChar.length; i++) {
                if(currentGuess == phraseAsChar[i]){
                    shouldAnswerLetterBeVisibleProperty[i].setValue(true);
                }
            }

        }
        if(numOfWrongGuessesProperty.getValue()>=6){
            popUpWindow("You Lost :(");
        }
        boolean isGameDone = true;
        for(Text x : answer){
            isGameDone = x.visibleProperty().getValue() && isGameDone;
        }
        if(isGameDone){
            popUpWindow("You Won!");
        }

        //says that the letter has been guessed
        hasLetterBeenGuessedProperty[currentGuessIndex].setValue(true);
        tbox.setText("");
    }

    private void initializeValues() throws Exception{
        numOfWrongGuessesProperty = new SimpleIntegerProperty(0);
        isGuessedPartOfPhrase = new SimpleBooleanProperty(false);
        phrase = getPhrase();
        answer = new Text[phrase.length()];
        currentGuessProperty = new SimpleStringProperty("");
        hasLetterBeenGuessedProperty = new SimpleBooleanProperty[26];
        phraseAsChar = phrase.toUpperCase().toCharArray();
        hangManBody = hangManBody();
        gallows = makeGallows();
        isHangManPartVisibleProperty = new SimpleBooleanProperty[6];

        numOfWrongGuessesProperty.setValue(0);



        for(int i=0; i < 26; i++){
            hasLetterBeenGuessedProperty[i] = new SimpleBooleanProperty(false);
        }

        shouldAnswerLetterBeVisibleProperty = new SimpleBooleanProperty[phrase.length()];
        for(int i=0; i< phrase.length(); i++){
            shouldAnswerLetterBeVisibleProperty[i] = new SimpleBooleanProperty(false);
        }
    }

    private void makePanes(){
        hangManPane= makeHangManPane();
        correctOrNotPane = makeCorrectOrNotPane();
        guessedAlphabet = makeGuessedAlphabet();
        inputPane = makeInputPane();
        answerPane = makeAnswerPane();
    }

    private void popUpWindow(String winOrLose){
        Label secondLabel = new Label(winOrLose);
        secondLabel.setFont(new Font(40));

        Button newGameB = new Button("New Game");
        newGameB.setOnAction(o -> {
            try {
                primaryStage.close();
                start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button quitB = new Button("Quit");
        quitB.setOnAction(o -> primaryStage.close());

        FlowPane secondaryLayout = new FlowPane();
        secondaryLayout.setHgap(10);
        secondaryLayout.getChildren().add(secondLabel);
        secondaryLayout.getChildren().add(quitB);
        secondaryLayout.getChildren().add(newGameB);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        Stage newWindow = new Stage();
        newWindow.setTitle("Game Over");
        newWindow.setScene(secondScene);


        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);

        newWindow.setX(primaryStage.getX() + 400);
        newWindow.setY(primaryStage.getY() + 400);


        newWindow.show();
    }
}
