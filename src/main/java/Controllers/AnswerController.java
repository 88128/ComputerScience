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

@Path ("Answer/")
public class AnswerController {

    public static void Answer() {
        try {

                //list for phase 2
            //JSONArray list = new JSONArray();

            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionID, Answer, Correct FROM Answers");
            ResultSet results = ps.executeQuery();
            while (results.next()) {

                //phase 1
                int QuestionID = results.getInt(1);
                String Answer = results.getString(2);
                Boolean Correct = results.getBoolean(3);

                //phase 2 - get item and add to list
                //JSONObject item = new JSONObject();
                //item.put("QuestionId", results.getInt(1));
                //item.put("Answer", results.getString(2));
                //item.put("Correct", results.getBoolean(3));
                //list.add(item);

            }

            //return list.toString();

        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
        }
    }

   /* @GET
    @Path("get/{QuestionID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThing(@PathParam("QuestionID") String QuestionID){
        try {
            if (QuestionID == null) {
                throw new Exception("Thing's 'QuestionID' is missing in the HTTP request's URL.");
            }
            System.out.println("thing/get/" + QuestionID);
            JSONObject item = new JSONObject();

            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionID, Answer, Correct FROM Answers WHERE QuestionID = ?");
            ps.setString(1, QuestionID);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("QuestionID", QuestionID);
                item.put("Answer", results.getString(2));
                item.put("Correct", results.getBoolean(3));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }

    */

    @GET
    @Path("get/{QuestionID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThing(@PathParam("QuestionID") String QuestionID){
        try {
            if (QuestionID == null) {
                throw new Exception("QuestionID is missing in the HTTP request's URL.");
            }
            System.out.println("Question/get/" + QuestionID);
            JSONObject item = new JSONObject();
            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionID, Answer, Correct FROM Answers WHERE QuestionID = ?");
            ps.setString(1, QuestionID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("QuestionID",QuestionID);
                item.put("Answer", results.getString(2));
                item.put("Correct", results.getBoolean(3));
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
    public String listAnswers() {
        System.out.println("Coursework/Answer/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionID, Answer, Correct FROM Answers");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("QuestionID", results.getInt(1));
                item.put("Answer", results.getString(2));
                item.put("Correct", results.getBoolean(3));
                list.add(item);
            }

            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }
}
