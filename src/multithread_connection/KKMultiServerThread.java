package multithread_connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import protocol.KnockKnockProtocol;

public class KKMultiServerThread extends Thread {
	
	private Socket clientSocket = null;
	
	public KKMultiServerThread(Socket socket) {
		
		super("KKMultiServerThread");
		this.clientSocket = socket;
		
	}
	
	public void run() {
		
		try(
				
			// pega o output stream do cliente, para que possa escrever nele com o PrintWriter
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					
			// pega o input stream do cliente para ler as informações que o cliente escreveu
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
		){
				
			KnockKnockProtocol protocol = new KnockKnockProtocol();
			String serverMsg = protocol.processInput(null);
			out.println("Server: " + serverMsg);
				
			String clientMsg;
				
			while((clientMsg = in.readLine()) != null) {
				serverMsg = protocol.processInput(clientMsg);
				out.println("Server: " + serverMsg);
				
				if(serverMsg.equalsIgnoreCase("Tchau.")) {
					break;
				}
			}
			
			clientSocket.close();
			
			
		} catch(IOException e) {
				
			e.printStackTrace();
				
		}
		
	}

}
