package com.java.ejb.authConsumer.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.appserv.jdbc.DataSource;

public class DataSourceFactory {
	
	public static DataSource datasource;
	
	public static DataSource getMySQLDataSource() throws NamingException
	{
		InitialContext ctx = new InitialContext();
		if(datasource==null)
		 datasource=(DataSource) ctx.lookup("testDB");
		return datasource;
		
	}
	
	

}
