package Hangman;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Hangman extends Application {
    public void start(Stage primaryStage){
        Circle head = new Circle(400,250,50);
        head.setFill(Color.WHITE);
        head.setStroke(Color.BLACK);

        Line body = new Line(400,300,400,390);

        Line leftArm = new Line(370,320,400,340);

        Line rightArm = new Line(430,320,400,340);

        Line leftLeg = new Line(400,390,350,450);

        Line rightLeg = new Line(400,390,450,450);

        Line noose = new Line(400,100,400,200);

        Line horizontal = new Line(200,100,400,100);

        Line vertical = new Line(200,100,200,450);

        Arc base = new Arc(200,525,150,75,15,150);
        //base.setType(ArcType.ROUND);
        base.setStroke(Color.BLACK);
        base.setFill(Color.WHITE);

        Pane pane = new Pane();
        pane.getChildren().addAll(head,body,leftArm,rightArm,leftLeg,rightLeg,noose,horizontal,vertical,base);

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Hangman"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}