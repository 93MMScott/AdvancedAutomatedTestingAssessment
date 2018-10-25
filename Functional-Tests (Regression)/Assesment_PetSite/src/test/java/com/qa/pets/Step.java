package com.qa.pets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Step {
	
	RequestSpecification request = RestAssured.given();
	Response response;

	public static int userid_count = 0; 

	public long ownerid_count;
	public long ownerid_update;
	
	public long getNextID() throws ParseException {
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/");
		String tempString = response.asString();
		JSONParser parser = new JSONParser(); 
		JSONArray jsonArray = (JSONArray) parser.parse(tempString);
		
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) parser.parse(object.toString());
		}
		
		request.header("Content-Type", "application/json");
		JSONObject add = new JSONObject();
		add.put("address", "Last");
		add.put("city", "Last");
		add.put("firstName", "Last");
		add.put("id", 10);
		add.put("lastName", "Last");
		
		//PETS ARRAY
		JSONArray pets = new JSONArray();
		add.put("pets", pets);
		
		add.put("telephone","0123456789");
		
		request.body(add);
		response = request.post("http://10.0.10.10:9966/petclinic/api/owners/");
		response.then().statusCode(201);
		
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/");
		String tempString2 = response.asString();

		JSONArray jsonArray2 = (JSONArray) parser.parse(tempString2);
		for (Object object : jsonArray2) {
			JSONObject jsonObject = (JSONObject) parser.parse(object.toString());
		}
		
		JSONArray objsToRemove = new JSONArray();
		for(Object object : jsonArray2) {
			if(jsonArray.contains(object))
			{
				objsToRemove.add(object);
			}
		}
		
		for(Object object : objsToRemove)
		{
			jsonArray2.remove(object);
		}

		JSONObject finalJSON = (JSONObject) jsonArray2.get(0);
		System.out.println("RESULT");
		Object finalInt = finalJSON.get("id") ;
		System.out.println(finalInt);
		return (Long) finalInt + 1;
	}
	
	// # POSTING USER (ADMIN) #
	@Given("^the user endpoint$")
	public void the_user_endpoint() throws Throwable {
		// http://10.0.10.10:9966/petclinic/api/users
	}

	@When("^a user is posted$")
	public void a_user_is_posted() throws Throwable {
		request.header("Content-Type", "application/json");

		// BODY OUTLINE:
		//		{
		//			  "enabled": true,
		//			  "password": "string",
		//			  "roles": [
		//			    {
		//			      "id": 0,
		//			      "name": "string"
		//			    }
		//			  ],
		//			  "username": "string"
		//		}
	
		System.out.println("Creating new JSON Object (user)");
		JSONObject json = new JSONObject();
		json.put("enabled", true);
		json.put("password", "password");

		JSONArray array = new JSONArray();
		JSONObject roles = new JSONObject();
		roles.put("id", userid_count);
		roles.put("name", "admin");
		
		array.add(roles);
		
		json.put("roles", array);
		json.put("username", "Admin");
		request.body(json);
		
		System.out.println("Posting new user to /users/");
		response = request.post("http://10.0.10.10:9966/petclinic/api/users");
		System.out.println("Posted.");
	}

	@Then("^the respose is two zero one$")
	public void the_respose_is_two_zero_one() throws Throwable {
		response.then().statusCode(201);
		System.out.println("Status Code: Created (201)");
	}
	
	// # POSTING OWNER #
	@Given("^the owners page$")
	public void the_owners_page() throws Throwable {
		ownerid_count = getNextID();
		System.out.println("Connecting to the owners end-point..");
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/");
		response.then().statusCode(200);
		System.out.println("Connected.");
	}

	@When("^I post an owner$")
	public void i_post_an_owner() throws Throwable {	
		
		request.header("Content-Type", "application/json");
		// BODY OUTLINE
		//		{
		//			  "address": "string",
		//			  "city": "string",
		//			  "firstName": "string",
		//			  "id": 0,
		//			  "lastName": "string",
		//			  "pets": [
		//			    {
		//			      "birthDate": "2018-10-25T08:36:42.127Z",
		//			      "id": 0,
		//			      "name": "string",
		//			      "owner": {},
		//			      "type": {
		//			        "id": 0,
		//			        "name": "string"
		//			      },
		//			      "visits": [
		//			        {
		//			          "date": "yyyy/MM/dd",
		//			          "description": "string",
		//			          "id": 0,
		//			          "pet": {}
		//			        }
		//			      ]
		//			    }
		//			  ],
		//			  "telephone": "string"
		//		}
		
		System.out.println("Creating owner data...");
		JSONObject json = new JSONObject();
		json.put("address", "123 Fake Street");
		json.put("city", "Springfield");
		json.put("firstName", "Homer");
		json.put("id", ownerid_count);
		json.put("lastName", "Simpson");
		
			//PETS ARRAY
			JSONArray pets = new JSONArray();
//				JSONObject pet1 = new JSONObject();
//				pet1.put("birthDate", "2018-10-25T10:42:50.094Z");
//				pet1.put("id", ownerid_count);
//				pet1.put("name", "Santa's Little Helper");
//				JSONObject emptyOwner = new JSONObject();
//				pet1.put("owner", emptyOwner);
			
			//TYPE
//				JSONObject type = new JSONObject();
//				type.put("name", "dog");
//				type.put("id", ownerid_count);
//				pet1.put("type", type);
//				
//					//VISITS ARRAY
//					JSONArray dates = new JSONArray();
//					JSONObject date1 = new JSONObject();
//					date1.put("date", "1994/10/30");
//					date1.put("description", "checkup");
//					date1.put("id", ownerid_count);
//					JSONObject emptyDate = new JSONObject();
//					date1.put("pet", emptyDate);
//					dates.add(date1);
//					pet1.put("visits", dates);
//			pets.add(pet1);
		
		json.put("pets", pets);
		json.put("telephone","0123456789");
		request.body(json);
		
		System.out.println(json);
		System.out.println("Posting owner..");
		response = request.post("http://10.0.10.10:9966/petclinic/api/owners/");
		System.out.println("Posted");
		//currently gets 400 error but focusing on user story tests
		response.then().statusCode(201); 
	}

	@When("^I get the list$")
	public void i_get_the_list() throws Throwable {
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/");
		response.then().statusCode(200);
	}

	@Then("^the new user appears on the list$")
	public void the_new_user_appears_on_the_list() throws Throwable {
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/" +ownerid_count);
		response.then().statusCode(200);
	}

	// # UPDATING #
	@Given("^the owner to update exists$")
	public void the_owner_to_update_exists() throws Throwable {
		ownerid_update = ownerid_count + 1;
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/" +ownerid_update);
		response.then().statusCode(200);
	}

	@When("^I update the owner$")
	public void i_update_the_owner() throws Throwable {

		request.header("Content-Type", "application/json");

		JSONObject owner = new JSONObject();
		owner.put("address", "123 Fake Street");
		owner.put("city", "Springfield");
		owner.put("firstName", "Bart");
		owner.put("id", ownerid_count);
		owner.put("lastName", "Pettigrew");
		JSONArray pets = new JSONArray();
		owner.put("pets", pets);
		owner.put("telephone","0123456789");
		request.body(owner);
		
		System.out.println(owner);
		
		response = request.put("http://10.0.10.10:9966/petclinic/api/owners/" +ownerid_update);
		response.then().statusCode(204);
	}

	@When("^I get the owner by lastname$")
	public void i_get_the_owner_by_lastname() throws Throwable {
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/*/lastname/Pettigrew");
		response.then().statusCode(200);
	}

	@Then("^the owner updates are shown$")
	public void the_owner_updates_are_shown() throws Throwable {
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/" +ownerid_update);
		response.then().statusCode(200);
	}

	// # DELETING #
	// not a clue why this doesn't run
	// not a clue in the world
	@Given("^the owner to delete exists$")
	public void the_owner_to_delete_exists() throws Throwable {
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/" +ownerid_count);
		response.then().statusCode(200);
	}

	@When("^I delete the owner$")
	public void i_delete_the_owner() throws Throwable {
		request.header("Accept", "application/json;charset=UTF-8");
		response = request.delete("http://10.0.10.10:9966/petclinic/api/owners/" +ownerid_count);
		response.then().statusCode(204);
	}

	@When("^I get the owner by ID$")
	public void i_get_the_owner_by_ID() throws Throwable {
		response = request.get("http://10.0.10.10:9966/petclinic/api/owners/" +(ownerid_count-1));
	}

	@Then("^the owner is deleted$")
	public void the_owner_is_deleted() throws Throwable {
		response.then().statusCode(404);
	}
}
