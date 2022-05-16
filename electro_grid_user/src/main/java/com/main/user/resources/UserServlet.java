package com.main.user.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.user.extras.JsonArray;
import com.main.user.extras.Util;
import com.main.user.models.User;
import com.main.user.services.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServlet extends HttpServlet {

	private UserService service = new UserService();
	private static final Gson GSON = new GsonBuilder().create();

	public boolean isNum(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String uri = req.getRequestURI();
		String[] urlElements = uri.trim().split("/");

		Integer user_id = 0;

		if (isNum(urlElements[urlElements.length - 1])) {
			user_id = Integer.parseInt(urlElements[urlElements.length - 1]);
		}

		if (user_id == null || user_id == 0) {
			List<User> users = new ArrayList<User>();
			users = service.getUsers();

			String json = GSON.toJson(users);
			
			String output = ""; 
			output = "<table class=\"table\"><tr><th scope=\"col\">User Id</th>\n"
					+ "      <th scope=\"col\">First Name</th>\n"
					+ "      <th scope=\"col\">Last Name</th>\n"
					+ "      <th scope=\"col\">Email</th>\n"
					+ "      <th scope=\"col\">Contact Number</th>\n"
					+ "      <th scope=\"col\">NIC</th>\n"
					+ "      <th scope=\"col\">Branch ID</th>\n"
					+ "       <th scope=\"col\">Action</th></tr>";
			
			for(int i =0; i<users.size(); i++) {
				output += "<tr><td><input id='hidUserIDUpdate' name='hidUserIDUpdate' type='hidden'>" + users.get(i).getUserId() +" </td>";
				output+= "<td>" + users.get(i).getFirstName()  + "</td>";
				output+= "<td>" + users.get(i).getLastName()  + "</td>";
				output+= "<td>" + users.get(i).getEmail() + "</td>";
				output+= "<td>" +  users.get(i).getPhoneNo()+ "</td>";
				output+= "<td>" + users.get(i).getNic() + "</td>";
				output+= "<td>" + users.get(i).getBranchId() + "</td>";
				output+="<td><button id ='btnUpdate'class = ' btnUpdate' onclick='updateUser("+users.get(i).getUserId()+")' style='border:none;\n"
						+ "		outline:none;\n"
						+ "		height:50px;\n"
						+ "		width:100%;\n"
						+ "		background-color:black;\n"
						+ "		color:white;\n"
						+ "		border-radius:4px;\n"
						+ "		font-weight:bold;\n"
						+ "		margin-top:5%;\n"
						+ "		 display: inline-block;\n"
						+ "  		margin:2px;' >Update</button></td><td><button id ='btnRemove' class = ' btnDelete' onclick='deleteUser("+users.get(i).getUserId()+")' style='bborder:none;\n"
						+ "		outline:none;\n"
						+ "		height:50px;\n"
						+ "		width:100%;\n"
						+ "		background-color:black;\n"
						+ "		color:white;\n"
						+ "		border-radius:4px;\n"
						+ "		font-weight:bold;\n"
						+ "		margin-top:5%;\n"
						+ "		 display: inline-block;\n"
						+ "  		margin:2px;' >Delete</button>";
			}
			
			output += "</table>"; 

			resp.setStatus(200);
			resp.setHeader("ContentType", "text/html");
			resp.getOutputStream().println(output);
			// resp.getWriter().write(json);

		} else {
			List<User> users = new ArrayList<User>();
			users = service.getUserById(user_id);

			if (users.size() == 0) {

				JsonArray jsonObj = new JsonArray();
				jsonObj.addJsonObject("error", "No user exists with id " + user_id);

				resp.setStatus(404);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jsonObj.getJsonObject());

			} else {
				String json = GSON.toJson(users);

				resp.setStatus(200);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(json);
			}

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String uri = req.getRequestURI();

		try {
			String json = Util.readInputStream(req.getInputStream());

			if (json == null || json.equals("")) {
				JsonArray jsonObj = new JsonArray();
				jsonObj.addJsonObject("error", "Provide a valid user body.");

				resp.setStatus(400);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jsonObj.getJsonObject());
			} else {
				User user = GSON.fromJson(json, User.class);

				Object o = service.insertUser(user);

				if (o == null || o instanceof String) {
					JsonArray jsonObj = new JsonArray();
					jsonObj.addJsonObject("error", (String) o);

					resp.setStatus(400);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(jsonObj.getJsonObject());

				} else {
					resp.setStatus(200);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(GSON.toJson(o));
				}
			}

		} catch (Exception e) {
			JsonArray jsonObj = new JsonArray();
			jsonObj.addJsonObject("error", "Provide a valid user body.");

			resp.setStatus(400);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(jsonObj.getJsonObject());
		}

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String uri = req.getRequestURI();
		String[] urlElements = uri.trim().split("/");

		Integer user_id = 0;

		if (isNum(urlElements[urlElements.length - 1])) {
			user_id = Integer.parseInt(urlElements[urlElements.length - 1]);
		}

		if (user_id == null || user_id == 0) {
			JsonArray jsonObj = new JsonArray();
			jsonObj.addJsonObject("error", "user_id cannot be blank");

			resp.setStatus(400);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(jsonObj.getJsonObject());

		} else {

			List<User> users = new ArrayList<User>();
			users = service.getUserById(user_id);

			if (users.size() == 0) {

				JsonArray jsonObj = new JsonArray();
				jsonObj.addJsonObject("error", "No user exists with id " + user_id);

				resp.setStatus(404);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jsonObj.getJsonObject());
			} else {
				try {
					String json = Util.readInputStream(req.getInputStream());

					if (json == null || json.equals("")) {
						JsonArray jsonObj = new JsonArray();
						jsonObj.addJsonObject("error", "Provide a valid user body.");

						resp.setStatus(400);
						resp.setHeader("Content-Type", "application/json");
						resp.getWriter().write(jsonObj.getJsonObject());
					} else {
						User user = GSON.fromJson(json, User.class);

						user.setUserId(user_id);

						Object o = service.updateUser(user);

						if (o == null || o instanceof String) {

							JsonArray jsonObj = new JsonArray();
							jsonObj.addJsonObject("error", (String) o);

							resp.setStatus(404);
							resp.setContentType("application/json");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write(jsonObj.getJsonObject());

						} else {
							resp.setStatus(200);
							resp.setContentType("application/json");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write(GSON.toJson(o));
						}
					}
				} catch (Exception e) {
					JsonArray jsonObj = new JsonArray();
					jsonObj.addJsonObject("error", "Provide a valid user body.");

					resp.setStatus(400);
					resp.setHeader("Content-Type", "application/json");
					resp.getWriter().write(jsonObj.getJsonObject());
				}

			}
		}

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String uri = req.getRequestURI();
		String[] urlElements = uri.trim().split("/");

		Integer user_id = 0;

		if (isNum(urlElements[urlElements.length - 1])) {
			user_id = Integer.parseInt(urlElements[urlElements.length - 1]);
		}

		if (user_id == null || user_id == 0) {
			JsonArray jsonObj = new JsonArray();
			jsonObj.addJsonObject("error", "user_id cannot be blank");

			resp.setStatus(400);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(jsonObj.getJsonObject());

		} else {
			List<User> users = new ArrayList<User>();
			users = service.getUserById(user_id);

			if (users.size() == 0) {

				JsonArray jsonObj = new JsonArray();
				jsonObj.addJsonObject("error", "No user exists with id " + user_id);

				resp.setStatus(404);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jsonObj.getJsonObject());
			} else {
				Object o = service.deleteUser(user_id, users.get(0));

				if (o == null || o instanceof String) {
					JsonArray jsonObj = new JsonArray();
					jsonObj.addJsonObject("error", (String) o);

					resp.setStatus(404);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(jsonObj.getJsonObject());
				} else {
					resp.setStatus(200);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getOutputStream().println(GSON.toJson(o));
				}
			}

		}
	}
}