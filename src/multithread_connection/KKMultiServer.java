package multithread_connection;

import java.io.IOException;
import java.net.ServerSocket;

public class KKMultiServer {
	
	public static void main(String[] args) {
		
		int portNumber = 3322;
		
		try(
			ServerSocket kkServerSocket = new ServerSocket(portNumber);
		){
			
			while(true) {
				new KKMultiServerThread(kkServerSocket.accept()).start();				
			}
			
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
		}
		
		
		
	}

}
