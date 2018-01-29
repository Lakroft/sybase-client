package com.lakroft.sybase.sybase_client;

public interface Listener {
	
	public abstract void run();
	public abstract void setDataBase(DataBase dataBase);
	public abstract void setRenderer(Renderer renderer);
}
