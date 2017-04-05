package server2;


/**
	 * interface which holds constants of the Commands that can be processed by the server
	 */
public interface Command {
	
	
	
	//Comenzi pt request
	public static final int INSERT_CLIENT = 1; 
	public static final int INSERT_SUPPLIER = 2;
	public static final int INSERT_INVOICE = 3;
	public static final int INSERT_MYCLIENT = 4;
	
	public static final int CONTAINS_CLIENT_EMAIL = 5;
	public static final int CHECK_SUPPLIER_PASS = 6;

	public static final int UPDATE_INVOICE = 7;
	public static final int UPDATE_MYCLIENT_BANNED = 8;
	public static final int UPDATE_MYCLIENT_SUBSCRIBED = 9;
	public static final int UPDATE_SUPPLIER = 10;
	public static final int UPDATE_CLIENT = 23;
	
	public static final int FIND_CLIENT_ID = 11;
	public static final int FIND_CLIENT_USER = 12;
	public static final int FIND_SUPPLIER_ID = 13;
	public static final int FIND_SUPPLIER_USER = 14;
	public static final int FIND_INVOICE_CLIENT = 15;
	public static final int FIND_INVOICE_SUPPLIER = 16;
	public static final int FIND_INVOICE_UNPAID = 17;
	
	public static final int CONTAINS_CLIENT_USERNAME = 18;
	public static final int CONTAINS_SUPPLIER_USERNAME = 19;
	
	public static final int FIND_BY_MY_SUPPLIER = 20;
	public static final int FIND_BY_MY_CLIENT = 21;
	public static final int DELETE_MY_CLIENT = 22;
	
	public static final int FIND_MYCLIENT_CLIENT_SUPPLIER = 24;
	public static final int PAY_INVOICES = 25;
	public static final int CONTAINS_MYCLIENT = 26;
}
