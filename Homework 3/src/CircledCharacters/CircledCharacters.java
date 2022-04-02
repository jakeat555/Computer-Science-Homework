package CircledCharacters;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Scanner;

public class CircledCharacters extends Application{

    public void start(Stage primaryStage){
        Pane pane = new Pane();
        //initialize the phrase
        String phrase = "WELCOME TO JAVA ";
        //String phrase = "12345678";
        int length = phrase.length();

        // pull the characters and compute how much each is rotated and its position
        char [] message = new char[length];
        double [] alignment = new double[length];
        Point2D[] centers = new Point2D[length];
        for(int i=0; i<length; i++){
            //The character to place
            message[i]=phrase.charAt(i);


            //How much to rotate the character by
            alignment[i]=(2*Math.PI/length)*(i)+(Math.PI/2);

            //Where the character should be placed
            centers[i]= new Point2D(Math.sin(alignment[i])*100+200,-1*Math.cos(alignment[i])*100+200);

            //Putting the character where its at
            Text addMe = new Text(centers[i].getX(),centers[i].getY(),message[i]+"");
            addMe.setFont(new Font(50));
            addMe.setRotate(Math.toDegrees(alignment[i]));

            pane.getChildren().add(addMe);
        }


        Scene scene = new Scene(pane);
        primaryStage.setTitle("Circled Characters"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}
