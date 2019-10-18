package Controllers;
        import Server.Main;
        import com.sun.jersey.multipart.FormDataParam;
        import org.json.simple.JSONArray;
        import org.json.simple.JSONObject;

        import javax.ws.rs.*;
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

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertThing(@FormDataParam("UserSkillLevel") Integer UserSkillLevel, @FormDataParam("UserName") String UserName){
        try {
            if (id == null || name == null ||  == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new id=" + id);

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Things (Id, Name, Quantity) VALUES (?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, quantity);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }


    @GET
    @Path("get/{Username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThing(@PathParam("Username") String UserName){
        try {
            if (UserName == null) {
                throw new Exception("Thing's 'Username' is missing in the HTTP request's URL.");
            }
            System.out.println("thing/get/" + UserName);
            JSONObject item = new JSONObject();

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, UserName, UserPassword, UserSkillLevel, UserToken FROM Users WHERE UserName = ?");
            ps.setString(1, UserName);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("UserID", results.getInt(1));
                item.put("UserName", UserName);
                item.put("UserPassword", results.getString(3));
                item.put("UserSkillLevel", results.getInt(4));
                item.put("UserToken", results.getString(5));
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
