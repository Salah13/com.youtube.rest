package com.youtube.rest.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.sql.*;

import com.youtube.dao.*;

@Path("v1/status")
public class V1_status {

	private static final String api_version = "00.02.00";

	public static String getApiVersion() {
		return api_version;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>Java Web Service</p>";
	}

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version :</p>" + getApiVersion();
	}

	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception {

		PreparedStatement query = null;
		String MyString = null;
		String passw = null;
		String returnString = null;
		Connection conn = null;

		try {

			conn = Oracle308tube.oracle308tubeConn().getConnection();
			query = conn
					.prepareStatement("select * from COMPTE",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = query.executeQuery();

			while (rs.next()) {

				MyString = rs.getString("username");
				passw = rs.getString("password");
			}
			
			returnString = "<p>Database Status</p>" + "<table> " + "<tr>"
					+ "<th>password</th><th>username</th>" + "</tr>"
					+ "<tr>" + "<td>" + passw + "</td><td>" + MyString
					+ "</td>	" + "</tr>" + "</table>";
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();

		}
		return returnString;
	}

}
