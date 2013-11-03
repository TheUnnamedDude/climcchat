package net.theunnameddude.mcchat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.regex.Pattern;

public class ChatConsoleFormatter extends Formatter {
    private Pattern pattern = Pattern.compile("\\x1B\\[([0-9]{1,2}(;[0-9]{1,2})*)?[m|K]");
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        if ( record.getLevel() == Level.OFF ) {
            sb.append( '[' ).append(format.format( new Date( record.getMillis() ) ) ).append( ']' );
            sb.append( ' ' ).append( record.getMessage() ).append( '\n' );
            return pattern.matcher( sb ).replaceAll( "" );
        } else {
            sb.append( format.format( new Date( record.getMillis() ) ) ).append( '[' ).append( record.getLevel() )
                    .append( ']' ).append( ' ' ).append( record.getMessage() ).append( '\n' );
            return sb.toString();
        }

    }
}
