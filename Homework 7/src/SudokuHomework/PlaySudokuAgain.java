package SudokuHomework;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class PlaySudokuAgain extends Application {
    private Board board = new Board();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        BorderPane userInterface = new BorderPane();

        Pane buttons = makeButtons();

        userInterface.setTop(board.pane);
        userInterface.setBottom(buttons);

        Scene scene = new Scene(userInterface);
        this.primaryStage = primaryStage;
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }

    //Makes the solve and clear buttons and their respective actions
    private Pane makeButtons(){
        FlowPane pane = new FlowPane();
        ToggleButton solve = new ToggleButton("Solve");
        Button clear = new Button("Clear");

        solve.setOnAction(e-> solveBoard(solve.isSelected()));

        clear.setOnAction(e-> clearBoard());

        pane.setHgap(3);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(solve,clear);
        return pane;
    }

    private void solveBoard(boolean isOn){
        System.out.println("Solving");
        //System.out.println(isOn);
        int[][] grid = new int[9][9];
        board.convertCellsToInts(grid);

        if (!Sudoku.isValid(grid))
            System.out.println("Invalid input");
        else if (Sudoku.search(grid)) {
            System.out.println("The solution is found:");
            Sudoku.printGrid(grid);
        }
        else
            System.out.println("No solution");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(isOn) {
                    board.cells[i][j].changeToSolution(grid[j][i]);
                }
                else {
                    board.cells[i][j].unsolve();
                }
            }
        }
    }

    private void clearBoard(){
        primaryStage.close();

        board = new Board();
        BorderPane userInterface = new BorderPane();

        Pane buttons = makeButtons();

        userInterface.setTop(board.pane);
        userInterface.setBottom(buttons);

        Scene scene = new Scene(userInterface);

        Stage primaryStage = new Stage();
        this.primaryStage = primaryStage;
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }

}
