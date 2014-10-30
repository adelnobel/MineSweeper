package minesweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Adel
 */
public class ScoreRetriever {

    public ScoreRetriever() throws IOException {
        displayScores();
    }
    
    private void displayScores() throws IOException{
        JFrame frame = new JFrame();
        BorderLayout bl = new BorderLayout();
        frame.getContentPane().setLayout(bl);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.add(new JLabel("Top Scores"));
        frame.getContentPane().add(top, BorderLayout.NORTH);
        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new GridLayout(10, 1));
        String[] scores = getScores();
        for(int i = 0; i < scores.length; i += 2){
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel name = new JLabel((i / 2 + 1) + ". " + scores[i]);
            JLabel score = new JLabel(scores[i + 1] + "%");
            rowPanel.add(name);
            rowPanel.add(score);
            scoresPanel.add(rowPanel);
        }
        frame.getContentPane().add(scoresPanel, BorderLayout.CENTER);
        frame.setSize(200, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private String[] getScores() throws IOException{
        ArrayList<String> list = new ArrayList<>();
        Socket socket = new Socket(MineSweeper.serverIP, MineSweeper.serverPORT);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("GET");
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String s;
        while((s = dis.readUTF()).compareTo(MineSweeper.END) != 0){
            list.add(s);
            list.add(dis.readUTF());
        }
        return list.toArray(new String[list.size()]);
    }
    
}
