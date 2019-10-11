package Controllers;
import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path ("Users/")
public class UserController {

    public static void ListUsers() {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, UserName, Userpassword,UserSkillLevel,UserToken FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String UserName = results.getString(1);
                String UserPassword = results.getString(2);
                int UserID = results.getInt(3);
                int UserSkillLevel = results.getInt(4);
            }

        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listUsers() {
        System.out.println("Coursework/listUsers");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, UserName, UserPassword,UserSkillLevel,UserToken FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("UserID", results.getInt(1));
                item.put("UserName", results.getString(2));
                item.put("UserPassword", results.getString(3));
                item.put("UserSkillLevel", results.getInt(4));
                list.add(item);
            }

            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }
}
