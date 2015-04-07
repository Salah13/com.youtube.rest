package com.youtube.dao;


import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

import java.sql.*;

public class Schema308Tube extends Oracle308tube{
	
	public JSONArray queryReturnBrandCompte(String brand) throws Exception{
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json= new JSONArray();
		
		

		try {
			conn = OracleConnection();
			query = conn.prepareStatement("select * " + "from users "
					+ "where UPPER(username)=?");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery(); 
			
			
			json = converter.toJSONArray(rs);
			query.close();
			

		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
			}
		
		catch (Exception e) {
			e.printStackTrace();
			return json;
			
			
		}finally {
			if (conn != null)
				conn.close();

		}
		return json;
	}

}
