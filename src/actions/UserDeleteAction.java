package com.socialnet.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import com.socialnet.bean.*;
import com.socialnet.db.DBFactory;

public class UserDeleteAction extends HttpServlet {

	Connection con=null;
	PreparedStatement pstmt,pstmt1=null;
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		PrintWriter out = response.getWriter();
		doPost(request,response);
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
       
		
		PrintWriter out = response.getWriter();
		
	    try{
	    
	    	pstmt=con.prepareStatement("delete from USER_PHOTOS where useridref=?");
	    	pstmt.setString(1, request.getParameter("uid"));
	    	int count=pstmt.executeUpdate();
	    	pstmt1=con.prepareStatement("delete from LOGIN_DETAILS where login_id=?");
	    	pstmt1.setString(1, request.getParameter("uid"));
	    	int count1=pstmt1.executeUpdate();
	    	if(count>0 && count1>0)
	    	{
	    		
	    		response.sendRedirect("ViewAllAccounts.jsp");
	    	}
	    	else{
	    		
	    		response.sendRedirect("ViewAllAccounts.jsp");
	    	}
	    }catch(SQLException e)
	    {
	    	e.printStackTrace();
	    }
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException 
	{
		try{
		    con=DBFactory.getConnection();
		// Put your code here
	     	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
