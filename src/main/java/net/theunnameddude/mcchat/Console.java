package net.theunnameddude.mcchat;

import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.logging.Logger;

public class Console implements Runnable {
    ConsoleReader reader;
    MinecraftChat chat;
    Logger log;
    boolean running = true;
    Thread consoleThread;
    public Console(MinecraftChat chat) {
        this.chat = chat;
        this.log = chat.log;
        try {
            reader = new ConsoleReader( System.in, System.out );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString(String prompt) {
        reader.setPrompt( prompt );
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.setPrompt( ">" );
        }
    }

    public String readPassword() {
        reader.setPrompt( "Please enter password: " );
        try {
            return reader.readLine( '*' );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.setPrompt( ">" );
        }
    }

    public void start() {
        consoleThread = new Thread( this, "console" );
        consoleThread.start();
    }

    public void stop() {
        running = false;
        try {
            reader.killLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while ( running ) {
            try {
                String line = reader.readLine( ">", null );
                if ( line.equalsIgnoreCase( "~quit" ) ) {
                    stop();
                    chat.client.shutdown();
                } else {
                    chat.client.sendMessage( line );
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
