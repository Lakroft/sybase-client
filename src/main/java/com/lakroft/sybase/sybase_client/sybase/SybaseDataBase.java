package com.lakroft.sybase.sybase_client.sybase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lakroft.sybase.sybase_client.DataBase;

import net.sourceforge.jtds.jdbcx.JtdsDataSource;

public class SybaseDataBase implements DataBase {
	private Connection connection;
	private Statement statement;
	
	public List<List<String>> execute(String sql) throws SQLException {
		List<List<String>> result = new ArrayList<List<String>>();
		
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		List<String> current = new ArrayList<String>();
		for (int i = 1; i <= columnCount; i++) {
			current.add(rsmd.getColumnName(i));
		}
		result.add(current);
		while (rs.next()) {
			current = new ArrayList<String>();
			for (int i = 1; i <= columnCount; i++) {
				try {
					current.add(rs.getString(i));
				} catch(Exception e) {
					String errText = "Error. Type:" + rsmd.getColumnTypeName(i) + ", class:" + rsmd.getColumnClassName(i);
					current.add(errText);
				}
				
			}
			result.add(current);
		}
		return result;
	}

	public void connect(String url) throws SQLException {
		if (isConnected()) disconnect();
		
		connection = DriverManager.getConnection(url);
		statement = connection.createStatement();
	}

	public void connect(String user, String pass, String ip, int portNum) throws SQLException { //user, pass, host ip, port num;
		if (isConnected()) disconnect();
		JtdsDataSource jtds = new JtdsDataSource();
		
		connection = jtds.getConnection();
		statement = connection.createStatement();
	}

	public Boolean isConnected() {
		try {
			return (connection != null && !connection.isClosed()) || (statement != null && !statement.isClosed());
		} catch (SQLException e) {
			return false;
		}
	}

	public void disconnect() throws SQLException {
		if (connection != null) {
			if (!connection.isClosed()) connection.close();
			connection = null;
		}
		if (statement != null) {
			if (!statement.isClosed()) statement.close();
			statement = null;
		}
	}

}
