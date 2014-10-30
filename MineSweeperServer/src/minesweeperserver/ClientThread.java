

package minesweeperserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Adel
 */
public class ClientThread extends Thread{
    private static final String END = "#~END~#";
    Socket socket;
    DBManager db;
    

    public ClientThread(Socket s, DBManager d) {
        socket = s;
        db = d;
    }
    
    @Override
    public void run(){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String service = dis.readUTF();
            if(service.compareTo("ADD") == 0){
                int score = dis.readInt();
                String name = dis.readUTF();
                db.executeUpdate("INSERT INTO record VALUES('"+name+"', '"+score+"')");
            }else if(service.compareTo("GET") == 0){
                ResultSet rs = db.executeQuery("SELECT * FROM record ORDER BY score ASC LIMIT 10");
                while(rs.next()){
                    dos.writeUTF(rs.getString("name"));
                    dos.writeUTF(rs.getString("score"));
                }
                dos.writeUTF(END);
            }
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IOException: " + ex.getMessage());
            System.err.println(ex.getMessage());
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage());
            System.err.println(ex.getMessage());
        }
    }
    
}
