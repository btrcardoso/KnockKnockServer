package multithread_connection;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class KKMultiServer {
	
	private static ArrayList<KKMultiServerThread> audience = new ArrayList<KKMultiServerThread>();
	private static final int MAX_CAPACITY = 1;
	
	
	public static void main(String[] args) throws InterruptedException {
		
		int portNumber = 3322;
		
		try(
			ServerSocket kkServerSocket = new ServerSocket(portNumber);
		){
			
			while(true) {
								
				if(audience.size() != MAX_CAPACITY) {
					
					// a conexão do cliente.
					Socket client = kkServerSocket.accept();
								
					// o .start() é um método da classe Thread, que inicializa a Thread
					KKMultiServerThread thread = new KKMultiServerThread(client);	
					thread.start();
					
					audience.add(thread);
					printAllClientsInfo();
					
				} else {
					
		            Thread.sleep(3000); //pausa por 3 segundos
		            
					for(KKMultiServerThread clientThread : audience) {
						System.out.println(clientThread.getState());
						if(!clientThread.isAlive()) {
							// esta linha dá problema, pois estamos alterando a lista enquanto percorremos por ela
							audience.remove(clientThread);
						}
					}
				}
				
							
			}
			
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
		}
		
		
		
	}
	
	public static void printAllClientsInfo() {
		for(KKMultiServerThread client : audience) {
			System.out.println("Cliente: "+ client.getClientSocket().getRemoteSocketAddress());
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
