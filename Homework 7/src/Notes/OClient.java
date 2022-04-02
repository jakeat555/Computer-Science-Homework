package Notes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class OClient extends Application {

    GridPane gridPane = null;
    Socket socketClient = null;
    BufferedReader reader = null;
    BufferedWriter writer = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create UI
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Button t = null;
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++) {
                t = new Button("-");
                t.setOnAction(e -> {
                    try {
                        ((Button)e.getSource()).setText("O");

                        sendMoveReceiveMove(e);
                    } catch (Exception ex) {
                    }
                });
                gridPane.add(t, j, i);
            }
        gridPane.add(new Label("O Player wait for X move..."),3,3);
        gridPane.add(new Label("Then click a square, and wait..."),4,3);


        // Set properties for UI
        // Process events

        // Create a scene and place it in the stage
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setTitle("TicTacToe O Player"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        connect();
    }

    private void connect() throws IOException {
        try {
            socketClient = new Socket("129.123.120.160",5557);
            System.out.println("Server: " + "Connection Established");
        } catch (Exception ex) {
            System.err.println(ex + "Client couldn't connect");
        }

        reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

        writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));


    }

    private void sendMoveReceiveMove(ActionEvent ae) throws IOException {
        Button t = (Button)ae.getSource();
        int row = gridPane.getRowIndex(t);
        int col = gridPane.getColumnIndex(t);

        writer.write(row+"\r\n");
        writer.write(col+"\r\n");
        writer.flush();

        int orow = Integer.parseInt(reader.readLine().trim());
        int ocol = Integer.parseInt(reader.readLine().trim());

        gridPane.add(new Button("X"),ocol,orow);


    }
}
