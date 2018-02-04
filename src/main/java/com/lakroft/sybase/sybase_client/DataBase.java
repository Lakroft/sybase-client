package com.lakroft.sybase.sybase_client;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public interface DataBase {
	public abstract List<List<String>> execute (String sql) throws SQLException;
	
	public abstract void connect (String url)  throws SQLException;
	public abstract void connect (String user, String pass, String ip, int portNum) throws SQLException;
	
	public abstract List <String> getTables() throws SQLException;
	
	public abstract Boolean isConnected ();
	public abstract void disconnect() throws SQLException;
}
