package com.lakroft.sybase.sybase_client.terminal;

import java.sql.SQLException;

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
				break;
			case "connectby":
				connectByParam();
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
	
	private static final String MENU = "\t MENU:\n" +
			"connect - connect to DB with default settings\n" +
			"connectstr - connect dy URL string\n" + 
			"connectby - connect with params (whoud be asced)\n" +
			"sql [sql] - execute sql command\n" +
			"disc - disconnect from DB\n" +
			"menu|help|h - show this menu" +
			"exit|quit|q - exit program\n";
}
