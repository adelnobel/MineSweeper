package minesweeper;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class MineSweeper {
    
    public static int rows = 14, columns = 16, mines = 30;
    public static final String serverIP = "127.0.0.1";
    public static final int serverPORT = 2015;
    public static final String END = "#~END~#";
    static Board board;
    static JFrame frame;
    
    
    public static void main(String[] args){
        
        frame = new JFrame("Mine Sweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(780, 580);
        frame.setLocationRelativeTo(null);
        initializeGame();
        frame.setVisible(true);
    }
    
    private static JMenuBar initializeMenu(final Board b){
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem restart = new JMenuItem("Restart Game");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b.restartGame();
            }
        });
        JMenuItem save = new JMenuItem("Save Game");
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
        JMenuItem load = new JMenuItem("Load Game");
        load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
        
        JMenuItem scores = new JMenuItem("High Scores");
        scores.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new ScoreRetriever();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Sorry, couldn't retrieve scores\n"
                        + "Reason: " + ex.getMessage());
                }
            }
        });
        
        menu.add(restart);
        menu.add(save);
        menu.add(load);
        menu.add(scores);
        bar.add(menu);
        return bar;
    }
    
    public static void initializeGame(){
        
        board = new Board(new Cell[rows][columns], frame, mines);
        removeAll();
        ArrayList< Pair<Integer, Integer> > list = new ArrayList<>();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                list.add(new Pair<>(i, j));
                board.setCellAt(i, j, new FreeCell(i, j, board));
            }
        }
        Collections.shuffle(list);
        for(int i = 0; i < mines; i++){
            int r = list.get(i).getFirst(), c = list.get(i).getSecond();
            board.setCellAt(r, c, new MinedCell(r, c, board));
        }
        drawBoard();
    }
    
    private static void removeAll(){
        frame.getContentPane().removeAll();
        GridLayout gl = new GridLayout(board.cells.length, board.cells[0].length, 1, 1);
        frame.setLayout(gl);
        frame.setJMenuBar(initializeMenu(board));
    }
    
    private static void drawBoard(){
        for(int i = 0; i < board.cells.length; i++){
            for(int j = 0; j < board.cells[i].length; j++){
                frame.add(board.getCellAt(i, j).getLabel());
            }
        }
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }
    
    private static void reDraw(){
        removeAll();
        drawBoard();
        for(int i = 0; i < board.cells.length; i++){
            for(int j = 0; j < board.cells[i].length; j++){
                if(board.cells[i][j].isClicked()){
                    board.cells[i][j].showCell();
                }
            }
        }
    }
    
    private static void saveGame(){
        try{
            // Serialize data object to a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game.sav"));
            //out.writeObject(frame);
            out.writeObject(board);
            out.close();
            JOptionPane.showMessageDialog(null, "Game saved successfully!");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    private static void loadGame(){
        try{ 
            FileInputStream door = new FileInputStream("game.sav");
            ObjectInputStream reader = new ObjectInputStream(door); 
            //frame = (JFrame)reader.readObject();
            board = (Board)reader.readObject();
            reDraw();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
}
