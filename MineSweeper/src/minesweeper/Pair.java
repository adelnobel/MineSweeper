/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minesweeper;

/**
 *
 * @author Adel
 */
class Pair<F,S> {
    private F first;
    private S second;

    public Pair(F first, S second) { 
        this.first = first;
        this.second = second;
    }

    public F getFirst() { return first; }
    public S getSecond() { return second; }
    public void setFirst(F v){
        first = v;
    }
    public void setSecond(S v){
        second = v;
    }
}


