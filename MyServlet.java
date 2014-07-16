

import java.net.Socket;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")

public class MyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	String ip,res,str,base64;
	String message = null;
	int num,num1;
	/**
     * Default constructor. 
     */
    public MyServlet() {
        // TODO Auto-generated constructor stub
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*System.out.println("Hello World");
		
		PrintWriter out=response.getWriter();
		out.write("mobile no recived");
		out.flush();
		out.close();
		*/
		
		processRequest(request,response);	
		/*try
		{
		byte buf[]=new byte[40];
		byte b[]=new byte[40];
		
		
		ServletInputStream sin=request.getInputStream();
		
		num=sin.readLine(buf, 0, buf.length);//readline returns the actual number of bytes read
		String message=new String(buf);
		System.out.println(message);
		
		
		//response.setStatus(HttpServletResponse.SC_OK);
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
                + "SET IPaddress = '"+ip+"'WHERE mobileno = '"+message+"'";
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
                  try {
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
              }
          }
		OutputStreamWriter writer=new OutputStreamWriter(response.getOutputStream());
		res="mobile no. received";
		str="hello client";
		System.out.println(res);
		writer.write(res+"\n");
		System.out.println(str);//string which is to be base64 encoded
		writer.write(str+"\n");
		writer.flush();
		//writer.close();
		//ServletInputStream sin=request.getInputStream();
		num1=sin.readLine(b,0,b.length);
		base64=new String(b);
		System.out.println(num1);
		sin.close();
		//sin1.close();
		response.setStatus(HttpServletResponse.SC_OK);
		
		
		
		
		
		
		writer.close();
		
		
		
		
		}
		catch(IOException e)
		{
			response.getWriter().println(e);
		}
	*/	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processRequest(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		try
		{
		ip=request.getRemoteAddr();
		System.out.println(ip);
		BufferedReader bufferedReader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while((line = bufferedReader.readLine()) != null) {
			buffer.append(line);
		}
		message=new String(buffer);
		System.out.println(message);
		
		
		
		//response.setStatus(HttpServletResponse.SC_OK);
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
                + "SET IPaddress = '"+ip+"'WHERE imei = '"+message+"'";
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
                  try {
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
              }
          }
		OutputStreamWriter writer=new OutputStreamWriter(response.getOutputStream());
		res="IMEI no. received";
		System.out.println(res);
		writer.write(res+"\n");
		str="hello client";
		System.out.println(str);
		writer.write(str+"\n");
	     writer.flush();
		
		
	
		
		
		
		/*is.close();*/
		//sin1.close();
		response.setStatus(HttpServletResponse.SC_OK);
		
		
		
		
		
		
	writer.close();
		
		
		
		
		}
		catch(IOException e)
		{
			response.getWriter().println(e);
		}
	}

}
