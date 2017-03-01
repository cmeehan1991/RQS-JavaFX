/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;

import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cmeehan
 */
public class DbConnection {
    private Connection connection;
    public Connection connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException ex){
            System.out.println("Error: Class not found\n" + ex.getMessage());
        }
        
         try{
            InetAddress addr; 
            Socket sock = new Socket("192.185.35.244", 3306);
            addr = sock.getInetAddress();
            sock.close();
            
        }catch(java.io.IOException e){
            System.out.println("Error: "+e);
        }
        
        String URL = "jdbc:mysql://192.185.35.244/cmeehan_kline_rqs";
        String dbUser = "cmeehan_rqs_adm";
        String dbPass = "Sh1pp!ngC4r3";
        
        try{
            connection = (Connection)DriverManager.getConnection(URL, dbUser, dbPass);
        }catch(SQLException ex){
            System.out.println("Connection Error!\n" + ex.getMessage() + "\n" + ex.getSQLState());
        }
        
        return connection;
    }
}
