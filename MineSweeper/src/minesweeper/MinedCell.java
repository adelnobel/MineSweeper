/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minesweeper;

import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author Adel
 */
public class MinedCell extends Cell{

    public MinedCell(Board b) {
        super(b);
    }
    public MinedCell(int r, int c, Board b){
        super(r, c, b);
    }

    @Override
    public void clickResponse() {
        board.lostGame();
    }


}
