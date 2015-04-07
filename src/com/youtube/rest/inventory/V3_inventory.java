package com.youtube.rest.inventory;


import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.youtube.dao.Schema308Tube;

@Path("/v3/inventory")
public class V3_inventory {

	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
			MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts2(String incomingData) throws Exception {

		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Schema308Tube dao = new Schema308Tube();

		try {

			/*
			 * We can create a new instance and it will accept a JSON string By
			 * doing this, we can now access the data.
			 */
			JSONObject partsData = new JSONObject(incomingData);
			System.out.println("jsonData: " + partsData.toString());

			/*
			 * In order to access the data, you will need to use one of the
			 * method in JSONArray or JSONObject. I recommend using the optXXXX
			 * methods instead of the get method.
			 * 
			 * Example: partsData.get("PC_PARTS_TITLE"); The example above will
			 * get you the data, the problem is, if PC_PARTS_TITLE does not
			 * exist, it will generate a java error. If you are using get, you
			 * need to use the has method first
			 * partsData.has("PC_PARTS_TITLE");.
			 * 
			 * Example: partsData.optString("PC_PARTS_TITLE"); The optString
			 * example above will also return data but if PC_PARTS_TITLE does
			 * not exist, it will return a BLANK string.
			 * 
			 * partsData.optString("PC_PARTS_TITLE", "NULL"); You can add a
			 * second parameter, it will return NULL if PC_PARTS_TITLE does not
			 * exist.
			 */
			Date createdate = new Date();
			java.sql.Date sqlDate = new java.sql.Date(createdate.getTime());

			int http_code = dao.insertUser(sqlDate,
					partsData.optString("password"),
					partsData.optString("username"));

			if (http_code == 200) {
				/*
				 * The put method allows you to add data to a JSONObject. The
				 * first parameter is the KEY (no spaces) The second parameter
				 * is the Value
				 */
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG",
						"Item has been entered successfully, Version 3");
				/*
				 * When you are dealing with JSONArrays, the put method is used
				 * to add JSONObjects into JSONArray.
				 */
				returnString = jsonArray.put(jsonObject).toString();
			} else {
				return Response.status(500).entity("Unable to enter Item")
						.build();
			}

			System.out.println("returnString: " + returnString);

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}

}
