
package minesweeper;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;


public abstract class Cell implements Response, java.io.Serializable{
    private int row, column;
    Board board;
    private boolean clicked, checked;
    private JLabel label;
    
    Cell(Board b){
        board = b;
        clicked = false;
        checked = false;
        initializeLabel();
    }
    
    Cell(int r, int c, Board b){
        this(b);
        row = r; 
        column = c;
    }
    
    private void initializeLabel(){
        setLabel(new JLabel(""));
        getLabel().setOpaque(true);
        getLabel().setBackground(Color.GRAY);
        getLabel().addMouseListener(new SerializableMouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Clicked!");
                if(e.getButton() == MouseEvent.BUTTON3){
                    if(!clicked){
                        checked = !checked;
                        if(checked){
                            label.setForeground(Color.WHITE);
                            label.setBackground(Color.ORANGE);
                            label.setText("C");
                        }else{
                            label.setForeground(Color.BLACK);
                            getLabel().setBackground(Color.GRAY);
                            label.setText("");
                        }
                    }
                }else if(!isChecked()){
                    clickResponse();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        getLabel().setHorizontalAlignment(JLabel.CENTER);
        getLabel().setOpaque(true);
    }

    public int getRow() {
        return row;
    }


    public void setRow(int row) {
        this.row = row;
    }


    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    public int countInfected(){
        Cell[][] b = board.getCells();
        int total = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int r = this.getRow() + i, c = this.getColumn() + j;
                if(!(r < 0 || r >= b.length || c < 0 || c >= b[r].length)){
                    if(b[r][c] instanceof MinedCell){
                        total++;
                    }
                }
            }
        }
        return total;
    }
    

    public void showCell(){
        if(isClicked()) return;
        board.decrementRem();
        //System.out.println("clicked " + row + " " + column);
        setClicked(true);
        getLabel().setBackground(Color.WHITE);
        int totalInfected = countInfected();
        if(totalInfected > 0){
            getLabel().setText(String.valueOf(totalInfected));
        }else{
            showNeighbours();
        }
    }
    
    private void showNeighbours(){
        Cell[][] b = board.getCells();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int r = this.getRow() + i, c = this.getColumn() + j;
                if(!(i == 0 && j == 0)){
                    if(!(r < 0 || r >= b.length || c < 0 || c >= b[r].length)){
                        if(!(b[r][c] instanceof MinedCell)){
                            b[r][c].showCell();
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean equals(Object obj){
        boolean ret = false;
        if(obj instanceof Cell){
            Cell c = (Cell)obj;
            if(c.getRow() == this.getRow() && c.getColumn() == this.getColumn()) ret = true;
        }
        return ret;
    }
    
    @Override
    public String toString(){
        return "Cell's row = " + String.valueOf(this.getRow()) + "\nCell's column = " + String.valueOf(this.getColumn());
    }


    public boolean isClicked() {
        return clicked;
    }


    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }


    public boolean isChecked() {
        return checked;
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    /**
     * @return the label
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(JLabel label) {
        this.label = label;
    }
    
}
