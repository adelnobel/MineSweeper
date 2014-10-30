package minesweeper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Adel
 */
public class ScoreUploader {
    
    int remaining;
    String name;

    public ScoreUploader(int r, String n) {
        remaining = r;
        name = n;
    }
    
    public void uploadScore() throws IOException{
        if(name.compareTo(MineSweeper.END) != 0){
            int total = MineSweeper.rows * MineSweeper.columns - MineSweeper.mines;
            int finished = total - remaining;
            double percentage = ((double)finished) / total;
            percentage *= 100;
            JOptionPane.showMessageDialog(null, "Your score is being uploaded! Score: " + (int)percentage + "%");
            Socket socket = new Socket(MineSweeper.serverIP, MineSweeper.serverPORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("ADD");
            dos.writeInt((int)percentage);
            dos.writeUTF(name);
        }
    }
    
    
}
