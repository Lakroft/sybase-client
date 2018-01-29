package com.lakroft.sybase.sybase_client;

import com.lakroft.sybase.sybase_client.sybase.SybaseDataBase;
import com.lakroft.sybase.sybase_client.terminal.TermListener;
import com.lakroft.sybase.sybase_client.terminal.TermRenderer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Listener listener = new TermListener();
        listener.setDataBase(new SybaseDataBase());
        listener.setRenderer(new TermRenderer());
        listener.run();
    }
}
