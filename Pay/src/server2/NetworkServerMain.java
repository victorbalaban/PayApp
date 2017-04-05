package server2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
	
	/** 
	 * Main Class for the Server
	 */
public class NetworkServerMain {
	
	
	
	private static final int PORT = 11111;
	private static final String HOST = "localhost";

	/** 
	 * Returns the PORT of the server
	 * */
	public static int getPort(){
		return PORT;
	}
	
	/** 
	 * Returns the host of the server
	 * */
	public static String getHost(){
		return HOST;
	}
	
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		try(ServerSocket server = new ServerSocket(PORT) ){
			System.out.println("Server running...");
			boolean activity = true;
			
//		executor.execute(new Auto());
			while(activity){
				Socket socket = server.accept();
				executor.execute(new RequestHandler(socket));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
