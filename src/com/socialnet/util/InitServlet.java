package com.socialnet.util;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpServlet;



import com.socialnet.conections.AbstractDataAccessObject;
import com.socialnet.exception.DataAcessException;
public class InitServlet extends HttpServlet
{
	AbstractDataAccessObject dobject;

	public void init(ServletConfig sc)
	{
		ServletContext ctx = sc.getServletContext();
		InputStream is = ctx.getResourceAsStream(sc.getInitParameter("config"));
		Properties props = new Properties();
		try
		{
			props.load(is);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		dobject = new AbstractDataAccessObject();
		dobject.setProperties(props);
	}
}
