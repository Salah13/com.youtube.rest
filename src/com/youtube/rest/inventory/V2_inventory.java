package com.youtube.rest.inventory;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jackson.map.ObjectMapper;

import com.youtube.dao.Schema308Tube;

@Path("/v2/inventory")
public class V2_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandCompte(@QueryParam("brand") String brand)
			throws Exception {

		String returnString = null;
		JSONArray json = new JSONArray();

		try {
			if (brand == null) {
				return Response.status(400)
						.entity("Error : Please specify brand for this search")
						.build();
			}
			Schema308Tube dao = new Schema308Tube();
			json = dao.queryReturnBrandUser(brand);
			returnString = json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}

	/*
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response returnErrorBrand()
	 * throws Exception{ return Response.status(400)
	 * .entity("Error : Please specify brand for this search") .build();}
	 */

	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam("brand") String brand)
			throws Exception {

		String returnString = null;

		JSONArray json = new JSONArray();

		try {

			Schema308Tube dao = new Schema308Tube();

			json = dao.queryReturnBrandUser(brand);
			returnString = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}

	@Path("/{brand}/{code}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam("brand") String brand,
			@PathParam("code") int code) throws Exception {

		String returnString = null;

		JSONArray json = new JSONArray();

		try {

			Schema308Tube dao = new Schema308Tube();

			json = dao.queryReturnBrandAndCodeUser(brand, code);
			returnString = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON })
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(String incomingData) throws Exception {

		String returnString = null;
		// JSONArray jsonArray = new JSONArray(); //not needed
		Schema308Tube dao = new Schema308Tube();

		try {
			System.out.println("incomingData: " + incomingData);

			/*
			 * ObjectMapper is from Jackson Processor framework
			 * http://jackson.codehaus.org/
			 * 
			 * Using the readValue method, you can parse the json from the http
			 * request and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper();
			ItemEntry itemEntry = mapper.readValue(incomingData,
					ItemEntry.class);

			int http_code = dao.insertUser(itemEntry.sqlDate,itemEntry.password,
					itemEntry.username);

			if (http_code == 200) {
				// returnString = jsonArray.toString();
				returnString = "user inserted";
			} else {
				return Response.status(500).entity("Unable to process user")
						.build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}
}

class ItemEntry {
	public String username;
 	public String password;
 	public Date createdate = new Date();
 	public java.sql.Date sqlDate = new java.sql.Date(createdate.getTime());

}
