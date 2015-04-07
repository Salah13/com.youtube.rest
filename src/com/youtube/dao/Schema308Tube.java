package com.youtube.dao;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

import java.sql.*;

public class Schema308Tube extends Oracle308tube {

	public int insertUser(java.sql.Date date, String passwd, String username)
			throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = OracleConnection();
			query = conn.prepareStatement("insert into users "
					+ "(create_date,password,username) " + "value(?,?,?)");

			query.setDate(1, date);
			query.setString(2, passwd);
			query.setString(3, username);

			query.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			return 500;

		} finally {
			if (conn != null)
				conn.close();

		}
		return 200;
	}

	public JSONArray queryReturnBrandUser(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

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

		} finally {
			if (conn != null)
				conn.close();

		}
		return json;
	}

	public JSONArray queryReturnBrandAndCodeUser(String brand, int code)
			throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = OracleConnection();
			query = conn.prepareStatement("select * " + "from users "
					+ "where UPPER(username)=? " + "and id=?");
			query.setString(1, brand.toUpperCase());
			query.setInt(2, code);
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

		} finally {
			if (conn != null)
				conn.close();

		}
		return json;
	}

}
