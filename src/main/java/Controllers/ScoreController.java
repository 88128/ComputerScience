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


public class ScoreController {

    public static void ListScores() {
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

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listScores() {
        System.out.println("Coursework/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Username,TotalScore FROM Scores");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Username", results.getString(1));
                item.put("TotalScore", results.getInt(2));
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }
}












