package rest.assured.api.testing;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class EntryPointMain {
	static String userID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
	static String userName = "TOOLSQA-Test";
	static String password = "Test@@123";
	static String baseUrl = "http://bookstore.toolsqa.com";

	public static void main(String[] args) {
		System.out.println("*** --THIS IS THE STARTING POINT main()-- ***");

		RestAPIs restobj = new RestAPIs();
		restobj.setUserID(userID);
		restobj.setUserName(userName);
		restobj.setPassword(password);
		restobj.setBaseUrl(baseUrl);
try{
		RestAssured.baseURI = restobj.getBaseUrl();
}catch(Exception e){
	System.out.println(e.getMessage());
}
		RequestSpecification request = RestAssured.given();
		restobj.setRequest(request);

//		System.out.println("*** --calling getAuthToken_GET_API()-- ***\n");
		restobj.getAuthToken_GET_API();
//		System.out.println("*** --calling getISBN_GET_API()-- ***\n");
		restobj.getISBN_GET_API();
//		System.out.println("*** --calling addISBN_POST_API()-- ***\n");
		restobj.addISBN_POST_API();
//		System.out.println("*** --calling deleteISBN_DELETE_API()-- ***\n");
		restobj.deleteBookAsISBN_DELETE_API();
//		System.out.println("*** --calling getNumberOfUser_GET_API()-- ***\n");
		restobj.getNumberOfUser_GET_API();
	}
}