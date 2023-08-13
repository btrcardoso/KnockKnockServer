package multithread_connection;

import java.io.*;
import java.net.*;

public class KKMultiServer {
	
	public static void main(String[] args) {
		
		int portNumber = 3322;
		
		try(
			ServerSocket kkServerSocket = new ServerSocket(portNumber);
		){
			int audience = 0;
			while(true) {
				// a conexão do cliente.
				Socket client = kkServerSocket.accept();
				
				audience ++;
				
				System.out.println("\nNovo cliente" 
					+ "\nRemote Socket Address: " + client.getRemoteSocketAddress()
					+ "\nInet Address: " + client.getInetAddress()
					+ "\nNúmero de clientes que já conectaram: " + audience
				);
				// o .start() é um método da classe Thread, que inicializa a Thread
				new KKMultiServerThread(client).start();				
			}
			
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
		}
		
		
		
	}

}
