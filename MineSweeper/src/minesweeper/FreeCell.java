/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minesweeper;

import java.awt.Color;

/**
 *
 * @author Adel
 */
public class FreeCell extends Cell{
    FreeCell(Board b){
        super(b);
    }
    FreeCell(int r, int c, Board b){
        super(r, c, b);
    }
    

    @Override
    public void clickResponse() {
        showCell();
    }
    

}
