package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getRuoli() {

		String sql = "SELECT DISTINCT(role) FROM authorship";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add( res.getString("role"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public HashMap<Integer, Artist> listArtists() {

		String sql = "SELECT * FROM artists";
		HashMap<Integer, Artist> result = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Artist a = new Artist(res.getInt("artist_id"), res.getString("name"));
				System.out.println("Artista  " +a.toString());
				result.put(a.getID(), a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Artist> getVertici(String ruolo, HashMap<Integer, Artist> idMap) {
		String sql = "SELECT DISTINCT(artist_id) FROM authorship WHERE role = ?";
		
		List<Artist> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Artist a = idMap.get( res.getInt("artist_id") );
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Arco> getArchi(String ruolo, HashMap<Integer, Artist> idMap) {
		String sql = "SELECT a1.artist_id as ar1, a2.artist_id as ar2, count(Distinct(eo1.exhibition_id)) as peso " + 
				"FROM authorship a1, authorship a2, 	exhibition_objects eo1, exhibition_objects eo2 " + 
				"WHERE a1.artist_id > a2.artist_id " + 
				"AND a1.role = ? " +
				"AND a2.role = ? " +
				"AND a1.object_id = eo1.object_id " + 
				"AND a2.object_id = eo2.object_id " + 
				"AND eo1.exhibition_id = eo2.exhibition_id " + 
				"GROUP by a1.artist_id, a2.artist_id ";
		
		List<Arco> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);st.setString(2, ruolo);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Artist a1 = idMap.get(res.getInt("ar1"));
				Artist a2 = idMap.get(res.getInt("ar2"));
				int peso = res.getInt("peso");
				
				Arco a = new Arco( a1, a2, peso);
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
