package server2;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.MyClientsDAO;

public class Test2 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
//		executor.execute(new Request(Command.FIND_CLIENT_ID, "1"));
//		executor.execute(new Request(Command.FIND_CLIENT_ID, "1"));
//		executor.execute(new Request(Command.FIND_CLIENT_ID, "1"));
//		executor.execute(new Request(Command.FIND_CLIENT_ID, "1"));
//		executor.execute(new Request(Command.FIND_CLIENT_ID, "1"));
//		executor.execute(new Request(Command.FIND_CLIENT_ID, "1"));
		
		executor.shutdown();
		
		System.out.println(new MyClientsDAO().getAll());
	}
	
	
	 public static Date asDate(LocalDate localDate) {
		    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	 }	
	 
	 public static LocalDate asLocalDate(Date date) {
		    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	 }

}
