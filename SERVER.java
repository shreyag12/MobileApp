/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SERVER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author shreya
 */

public class SERVER {

    /**
     * @param args the command line arguments
     */
    private static Socket sock;
    private static ServerSocket sersock;
   
    
    public static void main(String[] args)throws Exception {
        // TODO code application logic here
        String text,ip,number;
         char[]a=new char[20];
    char[]b=new char[20];
   
        while(true)
        {
        try
        {
             
			
			 sersock=new ServerSocket(4444);
                        System.out.println("Server ready for connection"); 
                        try
                        {
			 sock = sersock.accept();
                        }
                        catch(IOException e)
                        {
                            System.out.println("cannot listen to port"+e);
                        }
                        
                        
                        
                        
                        
			System.out.println("Connection is successful ");
			InputStreamReader istream = new InputStreamReader(sock.getInputStream());
			BufferedReader in =new BufferedReader(istream);
			 text=in.readLine();
                         System.out.println(text);
                          int len=text.length();
                  //System.out.println(len);
                text.getChars(11, len-1, a, 0);
                text.getChars(0, 10, b, 0);
                //System.out.println(a);
                //System.out.println(b);
                ip=new String(a);
                number=new String(b);
                //System.out.println(ip);
                //System.out.println(number);
                Connection connection=null;
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    String url="jdbc:mysql://localhost:3306/new_schema";
                    String user="root";
                    String password="shreya";
                    connection=DriverManager.getConnection(url, user, password);
                    Statement stmt=connection.createStatement();
                    String sql="UPDATE server "
                            + "SET IPaddress = '"+ip+"'WHERE mobileno = '"+number+"'";
                    stmt.execute(sql);
                }
                catch(ClassNotFoundException e)
                {
                   System.out.println("cannot find class"+e); 
                            
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if(connection!=null)
                    {
                        connection.close();
                    }
                }
			OutputStream ostream = sock.getOutputStream( );
			PrintWriter pwrite = new PrintWriter(ostream, true); 
			String str="Mobile number received";
		     pwrite.println(str+"\n");
		     sock.close(); 
                     sersock.close();
       
    }
        catch(IOException e)
        {
            System.out.println(e);
        }
        }
}
}