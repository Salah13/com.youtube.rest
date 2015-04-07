package com.youtube.rest.inventory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

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
			if(brand==null){
				return Response.status(400).entity("Error : Please specify brand for this search").build();
			}
			Schema308Tube dao = new Schema308Tube();
			json = dao.queryReturnBrandCompte(brand);
			returnString=json.toString();
			

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}
}
