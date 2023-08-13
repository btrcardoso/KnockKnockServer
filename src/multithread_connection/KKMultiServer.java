package multithread_connection;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class KKMultiServer {
	
	private static ArrayList<Socket> audience = new ArrayList<Socket>();
	private static final int MAX_CAPACITY = 3;
	
	
	public static void main(String[] args) {
		
		int portNumber = 3322;
		
		try(
			ServerSocket kkServerSocket = new ServerSocket(portNumber);
		){
			
			while(true) {
				
				for(Socket clientConnection : audience) {
					if(clientConnection.isClosed()) {
						audience.remove(clientConnection);
					}
				}
				
				if(audience.size() != MAX_CAPACITY) {
					
					// a conexão do cliente.
					Socket client = kkServerSocket.accept();
					audience.add(client);
					printAllClientsInfo();
								
					// o .start() é um método da classe Thread, que inicializa a Thread
					new KKMultiServerThread(client).start();	
					
				} else {
					//System.out.println("Cliente à espera");
				}
				
							
			}
			
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
		}
		
		
		
	}
	
	public static void printAllClientsInfo() {
		for(Socket client : audience) {
			System.out.println("Cliente: "+ client.getRemoteSocketAddress());
		}
		System.out.println("");
	}
	
	public static void printClientInfo(Socket client) {
		System.out.println("\nCliente" 
				+ "\nRemote Socket Address: " + client.getRemoteSocketAddress()
				+ "\nInet Address: " + client.getInetAddress()
			);
	}

}
