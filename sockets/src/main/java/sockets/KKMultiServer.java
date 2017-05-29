package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class KKMultiServer {
	
	public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }
 
        int portNumber = Integer.parseInt(args[0]);
 
        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber)) {
        	
        	while ( true ) {
        		Socket clientSocket = serverSocket.accept();
        		new Thread(new KKMultiServerThread(clientSocket, portNumber)).start();
        	}
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
