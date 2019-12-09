package Controllers;
import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path ("Scores/")
public class ScoreController {

    public static void Scores() {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserName, TotalScore FROM Scores");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String UserName = results.getString(1);
                int TotalScore = results.getInt(2);
            }

        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateThing(	@FormDataParam("UserName") String UserName, @FormDataParam("TotalScore") Integer TotalScore, @CookieParam("UserToken") String UserToken) {

        try {
            if (UserName == null || TotalScore == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Scores/update UserName=" + UserName);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Scores SET TotalScore = ? WHERE UserName=?");
            ps.setInt(1, TotalScore);
            ps.setString(2, UserName);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }


    @GET
    @Path("get/{Username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThing(@PathParam("Username") String UserName){
        try {
            if (UserName == null) {
                throw new Exception("Scores' 'Username' is missing in the HTTP request's URL.");
            }
            System.out.println("Users/get/" + UserName);
            JSONObject item = new JSONObject();

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserName, TotalScore FROM Scores WHERE UserName = ?");
            ps.setString(1, UserName);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("UserName",UserName);
                item.put("TotalScore", results.getInt(2));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listScores() {
        System.out.println("Coursework/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Username, TotalScore FROM Scores");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Username", results.getString(1));
                item.put("TotalScore", results.getInt(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }
}












