package Problem2;

import javafx.application.Application;

//1. Review 14.4, Stage, Scene, Pane structure.  Review UML page 539
//2. Show a circle, show each function
//3. Show grid page 541
//Pop QUIZ: where does circle get added...
//How do I change title?
//goto ShowCircleCentered

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HelloWorld extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

        // Create a pane to hold the text
        Pane pane = new Pane();

        Scene scene = new Scene(pane, 200, 200,Color.BEIGE);

        Text text1 = new Text(25,125,"Hello");
        text1.setFill(Color.SEAGREEN);
        text1.setFont(Font.font("Helvetica", FontWeight.MEDIUM, FontPosture.ITALIC,40));

        Text text2 = new Text(125,125,"World");
        text2.setFill(Color.BLUEVIOLET);
        text2.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR,50));


        Button clickMe = new Button("Click Me");

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                pane.getChildren().add(text1);
                pane.getChildren().add(text2);
                pane.getChildren().remove(clickMe);
            }
        };
        clickMe.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
        pane.getChildren().add(clickMe);



        // Create a scene and place it in the stage
        primaryStage.setTitle("Hello There"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
