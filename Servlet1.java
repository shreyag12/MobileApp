package Servlet1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	int num;
	String base64,line=null;
	byte[]b=new byte[50];
    public Servlet1() {
        // TODO Auto-generated constructor stub
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			
		ServletInputStream sin=request.getInputStream();
		num=sin.readLine(b, 0, b.length);		
		 base64=new String(b);
		System.out.println(base64);
		OutputStreamWriter writer=new OutputStreamWriter(response.getOutputStream());
		writer.write("message received"+"\n");
		writer.flush();
		writer.close();
		
		response.setStatus(HttpServletResponse.SC_OK);
		//sin.close();
		}
		catch(IOException e)
		{
			response.getWriter().println(e);
		}
	}

}
