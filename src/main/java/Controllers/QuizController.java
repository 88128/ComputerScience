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

@Path ("Quiz/")
public class QuizController {

    public static void Quizzes() {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT QuizTitle, QuizSkillLevel, QuizId FROM Quizzes");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String QuizTitle = results.getString(1);
                int QuizSkillLevel = results.getInt(2);
                int QuizID = results.getInt(3);
            }

        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listQuizzes() {
        System.out.println("Coursework/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT QuizTitle, QuizSkillLevel, QuizId FROM Quizzes");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("QuizTitle", results.getString(1));
                item.put("QuizSkillLevel", results.getInt(2));
                item.put("QuizID", results.getInt(3));
                list.add(item);


            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }
}

