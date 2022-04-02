package SudokuHomework;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


class CellLogic {

    Rectangle cell = new Rectangle(60,60);
    Text [] pencilNums = new Text[9];
    Text bigNum;
    StackPane allTogether = new StackPane();
    private boolean canEdit;

    boolean getCanEdit() {
        return canEdit;
    }

    void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    private CellLogic(boolean isBigNumVisible, Color bigNumColor, String bigNumText){
        cell.setArcHeight(10);
        cell.setArcWidth(10);
        cell.setFill(Color.TRANSPARENT);
        cell.setStroke(Color.BLACK);

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10,10,10,10));
        gp.setHgap(15);
        gp.setVgap(3);

        for (int i = 0; i < 9; i++) {
            pencilNums[i] = new Text();
            pencilNums[i].setText((i+1)+"");
            pencilNums[i].setFill(Color.GREY);
            pencilNums[i].setFont(new Font(10));
            pencilNums[i].setVisible(!isBigNumVisible);
            gp.add(pencilNums[i],i%3,i/3);
        }

        bigNum = new Text(bigNumText);
        bigNum.setVisible(isBigNumVisible);
        bigNum.setFont(new Font(35));
        bigNum.setFill(bigNumColor);

        allTogether.getChildren().add(gp);
        allTogether.getChildren().addAll(bigNum,cell);

    }

    CellLogic(){
        this(true,Color.BLACK,"");
    }

    void changeToSolution(int x){
        String sol = x+"";
        //If the num entered was wrong
        if(!sol.equals(bigNum.getText())){
            //if the cell was empty, fill with correct answer and make blue
            if(bigNum.getText().equals("")){
                bigNum.setText(sol);
                bigNum.setFill(Color.BLUE);
            }
            //If the guess was wrong, set the color to red
            else {
                bigNum.setFill(Color.RED);
            }
        }
    }

    void unsolve(){
        if(bigNum.getFill().equals(Color.BLUE)){
            bigNum.setText("");
        }
        bigNum.setFill(Color.BLACK);
    }
}