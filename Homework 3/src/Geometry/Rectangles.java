package Geometry;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Scanner;


public class Rectangles extends Application {

    private void getData(Rectangle2D[] rectangle2DS, Rectangle[] addableRectangles){
        Scanner in = new Scanner(System.in);
        for(int i =1; i <3;i++){
            String name= "";
            if(i==1){
                name = "first";
            }
            else{
                name = "second";
            }

            //snag the data from the user
            System.out.println("What is the width of your "+name+" rectangle? Width: ");
            double W = in.nextDouble();
            System.out.println("What is the height of your "+name+" rectangle? Height: ");
            double H = in.nextDouble();
            System.out.println("Where should the center of the "+name+" rectangle be? (use a space to separate coordinates): ");
            double X =in.nextDouble();
            double Y = in.nextDouble();

            //Build the rectangle
            rectangle2DS[i-1]  = new Rectangle2D(X,Y,W,H);
            addableRectangles[i-1] = new Rectangle(X,Y,W,H);
            addableRectangles[i-1].setFill(Color.TRANSPARENT);
            addableRectangles[i-1].setStroke(Color.BLACK);
        }
    }

    private String getRelationship(Rectangle2D[] rectangle2DS){
        if(rectangle2DS[0].contains(rectangle2DS[1])){
            return "Rectangle one contains rectangle two";
        }
        else if(rectangle2DS[1].contains(rectangle2DS[0])){
            return "Rectangle two contains rectangle one";
        }
        else if(rectangle2DS[1].intersects(rectangle2DS[0])||rectangle2DS[0].intersects(rectangle2DS[1])){
            return "The rectangles overlap";
        }
        else {
            return "The rectangles do not overlap";
        }
    }

    private Point2D getTextPos(Rectangle2D[] rectangle2DS){
        double x;
        double y;
        if(rectangle2DS[1].getMaxX()>=rectangle2DS[0].getMaxX()){
            x = rectangle2DS[1].getMaxX()/4;
        }
        else{
            x =rectangle2DS[0].getMaxX()/4;
        }

        if(rectangle2DS[1].getMaxY()>=rectangle2DS[0].getMaxY()){
            y = rectangle2DS[1].getMaxY() + 50;
        }
        else{
            y =rectangle2DS[0].getMaxY()+50;
        }
        return new Point2D(x,y);
    }

    public void start(Stage primaryStage){


        Rectangle2D [] rectangle2DS=  new Rectangle2D[2];
        Rectangle [] addableRectangles = new Rectangle[2];

        //get the data from the user, make the rectangles, put em in the array
        getData(rectangle2DS,addableRectangles);

        //check the relationships and give the correct string relatoinship
        String relationship = getRelationship(rectangle2DS);



        //Figure out where to place the text box
        Point2D whereToPlace = getTextPos(rectangle2DS);



        //make the text object with its spot and words
        Text relate = new Text(whereToPlace.getX(),whereToPlace.getY(),relationship);

        Pane pane = new Pane();
        pane.getChildren().addAll(addableRectangles[1],addableRectangles[0],relate);


        Scene scene = new Scene(pane);
        primaryStage.setTitle("Rectangle"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}
