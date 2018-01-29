package com.lakroft.sybase.sybase_client;

import java.util.List;

public interface Renderer {
	
	public void error(Exception e);
	public void showMessage(String message);
	public void showResult(List<List<String>> result);
}
