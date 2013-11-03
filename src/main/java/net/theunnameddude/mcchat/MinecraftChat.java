package net.theunnameddude.mcchat;

import net.theunnameddude.mcclient.api.ClientListener;
import net.theunnameddude.mcclient.api.MinecraftClient;
import net.theunnameddude.mcclient.api.MinecraftClientConnector;
import net.theunnameddude.mcclient.api.Version;
import net.theunnameddude.mcclient.api.auth.AuthenticationResponse;
import net.theunnameddude.mcclient.api.auth.Authenticator;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MinecraftChat extends ClientListener {
    Logger log = Logger.getLogger( "MinecraftChat" );
    Console console = new Console( this );
    ChatConsoleHandler consoleHandler = new ChatConsoleHandler( console.reader );
    MinecraftClient client;

    public MinecraftChat(String[] args) {
        log.setUseParentHandlers( false );
        consoleHandler.setFormatter( new ChatConsoleFormatter() );
        log.addHandler( consoleHandler );
        String username = console.readString("Please enter username(enter offline for offline-mode): ");
        AuthenticationResponse auth = null;
        if ( username.equalsIgnoreCase( "offline" ) ) {
            String nick = console.readString( "Please enter nick: " );
            auth = Authenticator.offlineMode( nick );
        } else {
            String password = console.readPassword();
            auth = Authenticator.sendRequest( username, password );
        }

        log.info( "Logging in" );

        if ( auth == null ) {
            log.info( "Authentication failed, servers might be down." );
        //} else if ( auth.getClientToken() == null ) {
        //    log.info( "Authentication failed, wrong username or password?");
        } else {
            client = MinecraftClientConnector.connect( console.readString( "Please enter IP: " ), 25565, auth, Version.get1_7_2() );
            client.addListener( this );
            console.start();
        }
    }

    @Override
    public void onChat(JSONObject message) {
        try {
            log.log( Level.OFF, message.getString( "text" ) );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MinecraftChat( args );
    }
}
