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

@Path ("Question/")
public class QuestionController {

    public static void ListQuestion() {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionName, QuizID, QuestionNumber, QuestionID FROM Questions");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String QuestionName = results.getString(1);
                int QuizID = results.getInt(3);
                int QuestionNumber = results.getInt(4);
                int QuestionID = results.getInt(4);
            }

        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listUsers() {
        System.out.println("Coursework/list/Question");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionName, QuizID, QuestionNumber, QuestionID FROM Questions");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Name", results.getString(2));
                item.put("Name", results.getString(2));
                item.put("UserPassword", results.getString(3));

            }

            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }
}
