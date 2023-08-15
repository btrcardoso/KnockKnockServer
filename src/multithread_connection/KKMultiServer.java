package multithread_connection;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

import protocol.KnockKnockProtocol;

public class KKMultiServer {
	
	private static ArrayList<KKMultiServerThread> audience = new ArrayList<KKMultiServerThread>();
	private static final int MAX_CAPACITY = 1;
	
	
	public static void main(String[] args) throws InterruptedException {
		
		int portNumber = 3323;
		
		try(
			ServerSocket kkServerSocket = new ServerSocket(portNumber);
		){
			
			while(true) {
								
				if(audience.size() <= MAX_CAPACITY) {
					
					KnockKnockProtocol.clientsWaitingServer = false;
					
					// a conexão do cliente.
					Socket client = kkServerSocket.accept();
								
					// o .start() é um método da classe Thread, que inicializa a Thread do cliente
					KKMultiServerThread thread = new KKMultiServerThread(client);	
					thread.start();
					
					audience.add(thread);
					printAllClientsInfo();
					
				} else {
					
		            KnockKnockProtocol.clientsWaitingServer = true;
					
					// pausa por 3 segundos para verificar disponibilidade do servidor
		            Thread.sleep(3000); 
		            
		            // busca por clientes que já finalizaram para retirá-los da audiência
		            Iterator<KKMultiServerThread> it = audience.iterator();
		            while(it.hasNext()) {
		            	KKMultiServerThread clientThread = it.next(); 
		            	System.out.println(clientThread.getState());
						if(!clientThread.isAlive()) {
							it.remove();
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
