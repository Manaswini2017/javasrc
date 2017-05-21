package com.socialnet.db;
import java.sql.*;

import com.socialnet.conections.AbstractDataAccessObject;
public class DBFactory 
{
	private static Connection con=null;
	public static Connection getConnection()
	{
		AbstractDataAccessObject ado=new AbstractDataAccessObject();
		try{
		   con=ado.getConnection();
		   
		}
		catch(Exception se)
		{
			
			se.printStackTrace();
		}
		return con;
	}

}
