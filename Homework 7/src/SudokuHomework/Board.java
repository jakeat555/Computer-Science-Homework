package SudokuHomework;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Board{
    CellLogic[][] cells = new CellLogic[9][9];
    private CellLogic selectedCell = new CellLogic();
    private TextArea loggingArea = new TextArea();
    Pane pane;

    Board(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j]=new CellLogic();
            }
        }

        try {
            pullDataFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pane = makePane();
    }

    private Pane makePane(){
        System.out.println("making the board");
        //pull data from file gives empty array

        GridPane board = new GridPane();
        board.setVgap(5);
        board.setHgap(5);

        //make an individual cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final int k=i;
                final int l = j;
                board.add(cells[i][j].allTogether,i,j);
                cells[i][j].allTogether.setOnMouseClicked(e-> handleClick(k,l));
            }
        }

        board.setLayoutX(5);
        board.setLayoutY(5);
        double width = 655;
        double height = 655;
        double thickness = 3.5;
        Rectangle outside = new Rectangle(width,height);
        outside.setFill(Color.TRANSPARENT);
        outside.setStroke(Color.BLACK);
        Line vert1 = new Line(width/3,0,width/3,height);
        Line vert2 = new Line(2*width/3,0,2*width/3,height);
        Line horz1 = new Line(0,height/3,width,height/3);
        Line horz2 = new Line(0,2*height/3,width,2*height/3);

        vert1.setStrokeWidth(thickness);
        vert2.setStrokeWidth(thickness);
        horz1.setStrokeWidth(thickness);
        horz2.setStrokeWidth(thickness);
        outside.setStrokeWidth(thickness);

        loggingArea.setMinSize(width,height);
        loggingArea.setMaxSize(width,height);
        loggingArea.setFont(new Font(0));
        loggingArea.setOnKeyTyped(e->handleKey(selectedCell,e));

        Pane pane = new Pane();
        pane.getChildren().addAll(loggingArea,vert1,vert2,horz1,horz2,outside,board);
        return pane;
    }

    //Generates the file path for the puzzle file
    private String getFileName(){
        System.out.println("getting file name");
        String fileName = "Sudoku";
        double rand = Math.random();
        rand=(int)(rand*8);
        if(rand!=0){
            fileName+=rand;
        }
        fileName = fileName.replaceFirst(".0","");
        fileName +=".txt";
        System.out.println(fileName);
        return fileName;
    }

    //Pulls data from file and puts it in a 2-D int array
    private void pullDataFromFile() throws FileNotFoundException {
        System.out.println("pulling data from file");
        File file = new File(getFileName());
        Scanner in = new Scanner(file);

        int index =0;

        while (in.hasNext()){
            String data = in.next().trim();

            if(!data.equals("0")){
                cells[index%9][index/9].bigNum.setText(data);
                cells[index%9][index/9].setCanEdit(false);
            }
            else{
                cells[index%9][index/9].bigNum.setText("");
                cells[index%9][index/9].setCanEdit(true);
            }

            index++;
        }
        cells[0][0].bigNum.setText("");
        cells[0][0].setCanEdit(true);
        in.close();
    }

    private void handleKey(CellLogic cell, KeyEvent event){
        String data = event.getCharacter().trim();
        System.out.println("You entered: "+data);
        int dataAsIndex = Integer.parseInt(data)-1;
        if(selectedCell.getCanEdit()) {
            //if there's only one number in the cell
            if (cell.bigNum.isVisible()) {
                //If there's nothing in the cell
                if (cell.bigNum.getText().equals("")) {
                    cell.bigNum.setText(data);
                }
                //if they want to get rid of the current enrty
                else if (cell.bigNum.getText().equals(data)) {
                    cell.bigNum.setText("");
                }
                //if theres something there i.e. big num but no pencils
                else {
                    int bigNumIndex = Integer.parseInt(cell.bigNum.getText()) - 1;
                    cell.bigNum.setText("");
                    cell.bigNum.setVisible(false);
                    cell.pencilNums[dataAsIndex].setVisible(!cell.pencilNums[dataAsIndex].isVisible());
                    cell.pencilNums[bigNumIndex].setVisible(true);
                }
            }
            //if the pencils are already visible
            else {
                cell.pencilNums[dataAsIndex].setVisible(!cell.pencilNums[dataAsIndex].isVisible());
                int numOfVisiblePencils = 0;
                int lastVisibleIndex = -1;
                for (int i = 0; i < 9; i++) {
                    if (cell.pencilNums[i].isVisible()) {
                        numOfVisiblePencils++;
                        lastVisibleIndex = i;
                    }
                }
                if (numOfVisiblePencils == 1) {
                    cell.bigNum.setVisible(true);
                    cell.bigNum.setText(cell.pencilNums[lastVisibleIndex].getText());
                    cell.pencilNums[lastVisibleIndex].setVisible(false);
                }
            }
        }
    }

    private void handleClick(int row, int col){
        System.out.println("you clicked me");
        selectedCell.cell.setStroke(Color.BLACK);
        selectedCell = cells[row][col];
        selectedCell.cell.setStroke(Color.YELLOW);
    }

    void convertCellsToInts(int[][] grid){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String data = cells[i][j].bigNum.getText();
                if(data.equals("")){
                    data = "0";
                }
                if(!cells[i][j].getCanEdit()){
                    grid[j][i] = Integer.parseInt(data);
                }
                else{
                    grid[j][i] = 0;
                }
            }
        }
    }
}
