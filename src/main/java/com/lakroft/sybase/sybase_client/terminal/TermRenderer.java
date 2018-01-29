package com.lakroft.sybase.sybase_client.terminal;

import java.util.List;

import com.lakroft.sybase.sybase_client.Renderer;

public class TermRenderer implements Renderer {

	public void error(Exception e) {
		e.printStackTrace();
	}

	public void showResult(List<List<String>> result) {
		for (List<String> row : result) {
			for (String column : row) {
				System.out.println(column);
			}
		}
	}

	public void showMessage(String message) {
		System.out.print(message);
	}

}
