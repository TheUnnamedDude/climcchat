package net.theunnameddude.mcchat;

import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.logging.ConsoleHandler;

public class ChatConsoleHandler extends ConsoleHandler {
    ConsoleReader reader;

    public ChatConsoleHandler(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public synchronized void flush() {
        try {
            reader.print(ConsoleReader.RESET_LINE + "");
            reader.flush();
            super.flush();
            try {
                reader.drawLine();
            } catch (Throwable ex) {
                reader.getCursorBuffer().clear();
            }
            reader.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
