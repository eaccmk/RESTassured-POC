package rest.assured.api.testing;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAPIs {

	private String userID;
	private String userName;
	private String password;
	private String baseUrl;
	private String token;
	private String jsonString;
	private String bookId; 

	private RestAssured restassuredobj;
	private RequestSpecification request;
	private Response response;
	
	public void getAuthToken_GET_API() {
		/*Step - 1:  Test will start from generating Token for Authorization*/
		
		request.header("Content-Type", "application/json");

		Response response = request.body(
				"{ \"userName\":\"" + userName + "\", \"password\":\""
						+ password + "\"}").post("/Account/v1/GenerateToken");

		Assert.assertEquals(response.getStatusCode(), 200);

		jsonString = response.asString();
		Assert.assertTrue(jsonString.contains("token"));

		/*This token will be used in later requests*/
		String token = JsonPath.from(jsonString).get("token");
		setToken(token);
		System.out.println("\n Value of #token is: `" + token + "`\n");
	}

	public void getISBN_GET_API() {
		 /*Step - 2 :  Get Books - No Auth is required for this.*/
		response = request.get("/BookStore/v1/Books");

		Assert.assertEquals(response.getStatusCode(), 200);

		jsonString = response.asString();
		List<Map<String, String>> books = JsonPath.from(jsonString)
				.get("books");
		Assert.assertTrue(books.size() > 0);

		 /*This bookId will be used in later requests, to add the book with
		 respective isbn*/
		bookId = books.get(0).get("isbn");
		System.out.println("\n Value of #bookId/isbn is: `" + bookId + "`\n");
	}

	public void addISBN_POST_API() {

		 /*Step - 3 :  Add a book - with Auth
		 The token we had saved in the variable before from response in Step 1
		 we will be passing in the headers for each of the succeeding request*/
		request.header("Authorization", "Bearer " + token).header(
				"Content-Type", "application/json");

		response = request.body(
				"{ \"userId\": \"" + userID + "\", "
						+ "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId
						+ "\" } ]}").post("/BookStore/v1/Books");

		Assert.assertEquals(201, response.getStatusCode());
		
		int statusCodeValue = response.getStatusCode();
		System.out.println("\n Value of #Response_status_Code is `" + statusCodeValue + "`\n");
	}

	public void deleteBookAsISBN_DELETE_API() {
		 /*Step - 4 :  Delete a book - with Auth*/
		request.header("Authorization", "Bearer " + token).header(
				"Content-Type", "application/json");

		response = request.body(
				"{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + userID
						+ "\"}").delete("/BookStore/v1/Book");

		Assert.assertEquals(204, response.getStatusCode());
				
		int statusCodeValue = response.getStatusCode();
		System.out.println("\n Value of #Response_status_Code is: `" + statusCodeValue + "`\n");
	}

	@Test
	public void getNumberOfUser_GET_API() {

		 /*Step - 5 :  Get User*/
		this.request.header("Authorization", "Bearer " + token).header(
				"Content-Type", "application/json");

		response = request.get("/Account/v1/User/" + userID);
		Assert.assertEquals(200, response.getStatusCode());

		jsonString = response.asString();
		List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get(
				"books");
		Assert.assertEquals(0, booksOfUser.size());
		
		int numberOfUsersWithBooks = booksOfUser.size();
		System.out.println("\n Value of #Number_of_users is: `" + numberOfUsersWithBooks + "`\n");
	}

	
	/*
	 * ALL GETTERS and SETTERS are listed here
	 */
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public RestAssured getRestassuredobj() {
		return restassuredobj;
	}

	public void setRestassuredobj(RestAssured restassuredobj) {
		this.restassuredobj = restassuredobj;
	}

	public RequestSpecification getRequest() {
		return request;
	}

	public void setRequest(RequestSpecification request) {
		this.request = request;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getRequestObject() {
		return request;
	}

	public void setRequestObject(RequestSpecification request) {
		this.request = request;
	}

}
