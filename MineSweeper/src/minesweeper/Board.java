
package minesweeper;

import java.io.IOException;
import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Board implements Serializable{
    final Cell cells[][];
    private int remaining;
    JFrame game;
    
    Board(Cell c[][], JFrame g, int mines){
        cells = c;
        game = g;
        remaining = c.length * c[0].length - mines;
    }

    
    public Cell[][] getCells(){
        return cells;
    }

    public Cell getCellAt(int i, int j) {
        return cells[i][j];
    }

    
    public void setCellAt(int i, int j, Cell c) {
        this.cells[i][j] = c;
    }
    
    
    public void decrementRem(){
        if(--remaining == 0){
            wonGame();
        }
    }
    
    public void wonGame(){
        int upload = JOptionPane.showConfirmDialog(null, "Congrats you win!! Do you want to upload your score?");
        if(upload == 0){
            uploadScore();
        }
        restartGame();
    }
    
    public void lostGame(){
        int upload = JOptionPane.showConfirmDialog(null, "You Lost! Do you want to upload your score?");
        if(upload == 0){
            uploadScore();
        }
        restartGame();
    }
    
    public void uploadScore(){
        String name = JOptionPane.showInputDialog("Please enter your name");
        if(name != null){
            ScoreUploader sUploader = new ScoreUploader(remaining, name);
            try{
                sUploader.uploadScore();
                JOptionPane.showMessageDialog(null, "Your score has been uploaded successfully!");
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Sorry, couldn't upload your score\n"
                        + "Reason: " + ex.getMessage());
            }
        }
    }
    
    public void restartGame(){
        MineSweeper.initializeGame();
    }
    
}
