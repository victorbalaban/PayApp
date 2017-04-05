package server2;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

	/**
	 * Class that represents a Request to the server
	 */
public class Request implements Runnable, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Request represented by one of the constants in the class {@link}Command
	 */
	private int request;
	
	/**
	 * Object required for some operaions in the server i.e. updating
	 */
	private Object object;
	
	/**
	 * Value needed for some operations on the server i.e. updating a field in the given Object with this value or it represents a certain field
	 */
	private String value;
	
	/**
	 * The Result/Response received from the server 
	 */
	private Object response;
	
	public Request(){
		
	}
	
	public Request(int request, Object object, String value){
		this.request = request;
		this.object = object;
		this.value = value;
	}
	
	public Request(int request, Object object){
		this.request = request;
		this.object = object;
	}
	
	public Request(int request, String value){
		this.request = request;
		this.value = value;
	}
	
	
	public int getRequest() {
		return request;
	}

	public Object getObject() {
		return object;
	}

	public String getValue() {
		return value;
	}


	
	
	@Override
	public void run(){
		
		try(Socket socket = new Socket(NetworkServerMain.getHost(), NetworkServerMain.getPort());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); ){
			
			System.out.println("Sending request..." + request);
			out.flush();
			out.writeObject(this);
			
			response = in.readObject();

			System.out.println("Finished");
			
		}catch(EOFException e){
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns a response coresponding to the request given by the param. request after getting it from the server
	 */
	public Object getResponse(int request, Object object, String value){
		this.request = request;
		this.object = object;
		this.value = value;
		Thread current = new Thread(this);
		current.start();
		while(current.isAlive()){
			//wait
		}
		
		return response;
	}
	
	/**
	 * Returns a response coresponding to the request given by the param. request after getting it from the server
	 */
	public Object getResponse(int request, Object object){
		this.request = request;
		this.object = object;
		Thread current = new Thread(this);
		current.start();
		while(current.isAlive()){
			//wait
		}
		
		return response;
	}
	
	/**
	 * Returns a response coresponding to the request given by the param. request after getting it from the server
	 */
	public Object getResponse(int request, String value){
		this.request = request;
		this.value = value;
		Thread current = new Thread(this);
		current.start();
		while(current.isAlive()){
			//wait
		}
		
		return response;
	}

	
	/**
	 * Returns a response coresponding to the request given by the param. request after getting it from the server
	 */
	public Object getResponse(int request){
		Thread current = new Thread(this);
		current.start();
		while(current.isAlive()){
			//wait
		}
		
		return response;
	}
	
}
