package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class KKMultiServerThread implements Runnable {

	private Socket clientSocket;
	private int portNumber;
	private int counter;
	
	public KKMultiServerThread(Socket clientSocket, int portNumber) {
		this.clientSocket = clientSocket;
		this.portNumber = portNumber;
	}
	
	@Override
	public void run() {

		 try ( 
		            PrintWriter out =
		                new PrintWriter(clientSocket.getOutputStream(), true);
		            BufferedReader in = new BufferedReader(
		                new InputStreamReader(clientSocket.getInputStream()));
		        ) {
		         
		            String inputLine, outputLine;
		             
		            // Initiate conversation with client
		            KnockKnockProtocol kkp = new KnockKnockProtocol();
		            outputLine = kkp.processInput(null);
		            out.println(outputLine);
		 
		            while ((inputLine = in.readLine()) != null) {
		            	counter++;
		            	System.out.println("The counter is "+ counter);
		                outputLine = kkp.processInput(inputLine);
		                out.println(outputLine);
		                if (outputLine.equals("Bye."))
		                    break;
		            }
		        } catch (IOException e) {
		            System.out.println("Exception caught when trying to listen on port "
		                + portNumber + " or listening for a connection");
		            System.out.println(e.getMessage());
		        }
	}

}
