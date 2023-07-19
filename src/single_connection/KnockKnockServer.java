package single_connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import protocol.KnockKnockProtocol;

public class KnockKnockServer {
	
	public static void main(String[] args) {
		
		int portNumber = 3322;
		
		try(
			ServerSocket serverSocket = new ServerSocket(portNumber);
			
			// bloqueante, só continuará a execução quando um cliente se conectar
			Socket clientSocket = serverSocket.accept();
			
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
			
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
	        System.out.println(e.getMessage());
		}
		
	}

}
