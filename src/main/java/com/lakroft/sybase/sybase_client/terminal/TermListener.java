package com.lakroft.sybase.sybase_client.terminal;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.lakroft.sybase.sybase_client.DataBase;
import com.lakroft.sybase.sybase_client.Listener;
import com.lakroft.sybase.sybase_client.Renderer;

public class TermListener implements Listener {
	private DataBase dataBase;
	private Renderer renderer;
	
	public void run() {
		showMenu();
		while (true) {
			String command = getCommand();
			if (command.startsWith("sql ")) {
				command = command.replaceAll("sql ", "");
				executeSQL(command);
				continue;
			}
			
			if (command.startsWith("connectstr ")) {
				command = command.replaceAll("connectstr ", "");
				connectByURL(command);
				continue;
			}
			switch (command) {
			case "connect":
				// TODO подгружать из property файла
				renderer.showMessage("Will be implemented soon\n");
				break;
			case "connectby":
				connectByParam();
				break;
			case "tables":
				showTables();
				break;
			case "disc":
				try {
					dataBase.disconnect();
					renderer.showMessage("disconnected\n");
				} catch (SQLException e) {
					renderer.error(e);
				}
				break;
			case "menu":
			case "help":
			case "h":
				showMenu();
				break;
			case "exit":
			case "quit":
			case "q":
				return;
			default:
				renderer.showMessage("unknown command\n");
				break;
			}
			
		}
	}

	public void setDataBase(DataBase dataBase) {
		this.dataBase = dataBase;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}
	
	private void showMenu() {
		renderer.showMessage(MENU);
	}
	
	private String getCommand() {
		renderer.showMessage(">");
		return System.console().readLine();
	}
	
	private void executeSQL(String sql) {
		try {
			renderer.showResult(dataBase.execute(sql));
		} catch (SQLException e) {
			renderer.error(e);
		}
	}
	
	private void connectByURL(String url) {
		try {
			dataBase.connect(url);
			renderer.showMessage("done\n");
		} catch (SQLException e) {
			renderer.error(e);
		}
	}
	
	private void propsConnect() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("default.properties"));
			
		} catch (IOException e) {
			renderer.error(e);
		}
	}
	
	private void connectByParam() {
		renderer.showMessage("Inter user name:");
		String userName = getCommand();
		renderer.showMessage("Inter password:");
		String pass = getCommand();
		renderer.showMessage("Inter host IP:");
		String host = getCommand();
		renderer.showMessage("Inter port:");
		int port = -1;
		do {
			try {
				port = Integer.parseInt(getCommand());
				break;
			} catch (NumberFormatException ignored) {}
			renderer.showMessage("Not a number! Inter valid port number:");
		} while (port < 0);
		try {
			dataBase.connect(userName, pass, host, port);
			renderer.showMessage("done\n");
		} catch (Exception e) {
			renderer.error(e);
		}
	}
	
	private void showTables() {
		if (dataBase.isConnected()) {
			try {
				List<String> tables = dataBase.getTables();
				renderer.showMessage("Tables:\n");
				for (String table : tables) {
					renderer.showMessage("\t" + table + "\n");
				}
				renderer.showMessage("End.\n");
			} catch (SQLException e) {
				renderer.error(e);
			}
			
		} else {
			renderer.showMessage("No connection");
		}
	}
	
	private static final String MENU = "\t MENU:\n" +
			"connect - connect to DB with default settings\n" +
			"connectstr - connect dy URL string\n" + 
			"connectby - connect with params (whoud be asced)\n" +
			"tables - shows list of tables\n" +
			"sql [sql] - execute sql command\n" +
			"disc - disconnect from DB\n" +
			"menu|help|h - show this menu\n" +
			"exit|quit|q - exit program\n";
}
