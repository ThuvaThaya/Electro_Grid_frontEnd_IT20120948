package com.main.user.services;

import java.sql.*;
import java.util.ArrayList;

import com.main.user.models.User;


public class UserService {
	
	private Connection connection;
	
	
	public UserService() {
		String url = "jdbc:mysql://localhost/electro_grid?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "root";
		String password = "";
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			connection = DriverManager.getConnection(url, username, password);
			
			if(connection != null){
                System.out.println("Successfully connected to database.");
            }
			   
        } catch (SQLException e) {
			System.out.println(e + ". Failed to conncet to the DB.");
		}

	}
	
	public ArrayList<User> getUsers() {
		String cmd = "select * from user"; 
		
		
		ArrayList<User> users = new ArrayList<>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(cmd);
			ResultSet result = ps.executeQuery();
			
			if (result == null) {
				return new ArrayList<>();
			}
			
			
			while(result.next()) {

				users.add(new User(result.getInt("user_id"), result.getString("firstname"), result.getString("lastname"),
						result.getString("email"),result.getString("password"), result.getString("phone"), result.getString("nic"),
						result.getInt("branch_id")));
			
			}
		
	
		}catch (Exception e) {
			System.out.println(e + ". Could'nt able to get data.");
		}
		
		return users;
	}
	
	public ArrayList<User> getUserById(int user_id) {
		String cmd = "select * from user where user_id = ?"; 
		
		ArrayList<User> users = new ArrayList<>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(cmd);
			ps.setInt(1, user_id);
			
			ResultSet result = ps.executeQuery();
			
			if (result == null) {
				return new ArrayList<User>();
			}
			
			while(result.next()) {

				users.add(new User(result.getInt("user_id"), result.getString("firstname"), result.getString("lastname"),
						result.getString("email"),result.getString("password"), result.getString("phone"), result.getString("nic"),
						result.getInt("branch_id")));
			}
	
		}catch (Exception e) {
			System.out.println(e + ". Could'nt able to get data.");
		}
		
		return users;
	}
	
	public ArrayList<User> getUserByEmail(String email) {
		String cmd = "select * from user where email = ?"; 
		
		ArrayList<User> users = new ArrayList<>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(cmd);
			ps.setString(1, email);
			
			ResultSet result = ps.executeQuery();
			
			if (result == null) {
				return new ArrayList<User>();
			}
			
			while(result.next()) {

				users.add(new User(result.getInt("user_id"), result.getString("firstname"), result.getString("lastname"),
						result.getString("email"),result.getString("password"), result.getString("phone"), result.getString("nic"),
						result.getInt("branch_id")));
			}
	
		}catch (Exception e) {
			System.out.println(e + ". Could'nt able to get data.");
		}
		
		return users;
	}
	
	public User validateUser(String email, String password) {
		String cmd = "select * from user where email = ? AND password = PASSWORD(?)"; 
		
		ArrayList<User> users = new ArrayList<>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(cmd);
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet result = ps.executeQuery();
			
			if (result == null) {
				return null;
			}
			
			while(result.next()) {

				users.add(new User(result.getInt("user_id"), result.getString("firstname"), result.getString("lastname"),
						result.getString("email"),result.getString("password"), result.getString("phone"), result.getString("nic"),
						result.getInt("branch_id")));
			}
	
		}catch (Exception e) {
			System.out.println(e + ". Could'nt able to get data.");
		}
		
		if(users.size() == 1) {
			return users.get(0);
		} else {
			return null;
		}
	
	}
	
	public Object insertUser(User user) {
		String cmd = "insert into user(firstname,lastname,nic,phone,branch_id,email,password) values (?,?,?,?,?,?,PASSWORD(?))"; 
		
		Object o = null;
		
		try {
			PreparedStatement ps = connection.prepareStatement(cmd, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getNic());
			ps.setString(4, user.getPhoneNo());
			ps.setInt(5, user.getBranchId());
			ps.setString(6, user.getEmail());
			ps.setString(7, user.getPassword());
			ps.execute() ;
			
			ResultSet resultForId = ps.getGeneratedKeys();
			if(resultForId.next()) {
				User u = user;
				u.setUserId(resultForId.getInt(1));
				o = u;
			} else {
				o = "Failed to create data.";
			}
			
		}catch (Exception e) {
			System.out.println(e + " Failed to create data.");
			o = e + ". Failed to create data.";
		}
		
		return o;
	}
	
	public Object updateUser(User user) {
		String cmd = "update user set firstname= ?,lastname= ?,nic= ?,phone= ?,branch_id= ?,email= ?,password= PASSWORD(?) where user_id= ?"; 
		
		Object o = null;
		
		try {
			PreparedStatement ps = connection.prepareStatement(cmd);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getNic());
			ps.setString(4, user.getPhoneNo());
			ps.setInt(5, user.getBranchId());
			ps.setString(6, user.getEmail());
			ps.setString(7, user.getPassword());
			ps.setInt(8, user.getUserId());
			ps.executeUpdate();
			
			o = user;
			
		}catch (Exception e) {
			System.out.println(e + " Failed to create data.");
			o = e + ". Failed to create data.";
		}
		
		return o;
	}
	
	public Object deleteUser(int user_id, User user) {
		String cmd = "delete from user where user_id = ?"; 
		
		Object o = null;
		
		try {
			PreparedStatement ps = connection.prepareStatement(cmd);
			ps.setInt(1, user_id);
			ps.executeUpdate();
			
			o = user.getUserId();
			
	
		}catch (Exception e) {
			System.out.println(e + ". Could'nt able to delete record.");
			o = e + ". Could'nt able to delete record.";
		}
		
		return o;
	}

}
