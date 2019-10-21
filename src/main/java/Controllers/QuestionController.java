package Controllers;
import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
                int QuizID = results.getInt(2);
                int QuestionNumber = results.getInt(3);
                int QuestionID = results.getInt(4);
            }

        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
        }
    }

    @GET
    @Path("get/{QuizID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThing(@PathParam("QuizID") String QuizID){
        try {
            if (QuizID == null) {
                throw new Exception("QuizID is missing in the HTTP request's URL.");
            }
            System.out.println("Question/get/" + QuizID);
            JSONObject item = new JSONObject();
            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionName, QuizID, QuestionNumber, QuestionID FROM Questions WHERE QuizID = ?");
            ps.setString(1, QuizID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("QuestionName", results.getString(4));
                item.put("QuizID",QuizID);

                item.put("QuestionNumber", results.getInt(3));
                item.put("QuestionID", results.getInt(2));
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
    public String listQuestions() {
        System.out.println("Coursework/list/Question");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionName, QuizID, QuestionNumber, QuestionID FROM Questions");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("QuestionName", results.getString(1));
                item.put("QuizID", results.getInt(2));
                item.put("QuestionNumber", results.getInt(3));
                item.put("QuestionID", results.getInt(4));
                list.add(item);
            }

            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }
}
