package minesweeperserver;

import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Adel
 */
public class MineSweeperServer {
    private static final int serverPORT = 2015;
    public static void main(String[] args) {
         try{
            DBManager db = new DBManager("scores.db");
            if(!db.doesExist("record")){
                db.execute("CREATE TABLE record("
                        + "name TEXT NOT NULL,"
                        + "score TEXT NOT NULL)");
            }
            ServerSocket serverSocket = new ServerSocket(serverPORT);
            JFrame frame = new JFrame("MineSweeper Server");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 100);
            frame.getContentPane().add(new JLabel("MineSweeper Server is running"));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            while(true){
                Socket socket = serverSocket.accept();
                ClientThread thread = new ClientThread(socket, db);
                thread.start();
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
}
