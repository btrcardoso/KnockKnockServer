package multithread_connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import protocol.KnockKnockProtocol;

public class KKMultiServerThread extends Thread {
	
	private Socket clientSocket = null;
	
	public KKMultiServerThread(Socket socket) {
		
		super("KKMultiServerThread");
		this.clientSocket = socket;
		
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}
	
	public void run() {
		
		try(
				
			// pega o output stream do cliente, para que possa escrever nele com o PrintWriter
			// PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
				
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            PrintWriter out = new PrintWriter(bufferedWriter, true);	
			
			// pega o input stream do cliente para ler as informações que o cliente escreveu
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
				
		){
				
			KnockKnockProtocol protocol = new KnockKnockProtocol();
			String serverMsg = protocol.processInput(null);
			out.println("Server: " + serverMsg);
				
			String clientMsg;
				
			while((clientMsg = in.readLine()) != null) {
				serverMsg = protocol.processInput(clientMsg);
				out.println("Server: " + serverMsg);
				
				if(protocol.getState() == 4) {
					break;
				}
			}
			
			clientSocket.close();
			
			
		} catch(IOException e) {
				
			e.printStackTrace();
				
		}
		
	}

}
