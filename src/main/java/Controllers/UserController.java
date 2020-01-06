package Controllers;
        import Server.Main;
        import org.glassfish.jersey.media.multipart.FormDataParam;
        import org.json.simple.JSONArray;
        import org.json.simple.JSONObject;

        import javax.ws.rs.*;
        import javax.ws.rs.core.MediaType;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.util.UUID;
@Path ("Users/")
public class UserController {

    public static boolean validUserToken(String UserToken) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserName FROM Users WHERE UserToken = ?");
            ps.setString(1, UserToken);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            return false;
        }
    }

    public static void ListUsers() {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, UserName, UserPassword,UserSkillLevel,UserToken FROM Users");
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
    @Path("get/{UserName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThing(@PathParam("UserName") String UserName){
        try {
            if (UserName == null) {
                throw new Exception("Thing's 'Username' is missing in the HTTP request's URL.");
            }
            System.out.println("thing/get/" + UserName);
            JSONObject item = new JSONObject();

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserName, UserSkillLevel FROM Users WHERE UserName = ?");
            ps.setString(1, UserName);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("UserSkillLevel", results.getInt(2));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }



    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateThing(	@FormDataParam("UserName") String UserName, @FormDataParam("UserScore") Integer UserScore, @CookieParam("UserToken") String UserToken) {
        try {
            if (UserName == null || UserScore == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
             }
            // if (!UserName.validUserToken(UserToken)) {
               //  return "{\"error\": \"You don't appear to be logged in.\"}";
            // }
            System.out.println("Scores/update UserName=" + UserName);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET UserScore = ? WHERE UserName=?");
            ps.setInt(1, UserScore);
            ps.setString(2, UserName);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("incrementscore")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String incrementUserScore(@FormDataParam("UserName") String UserName, @FormDataParam("ScoreIncrease") Integer ScoreIncrease, @CookieParam("UserToken") String UserToken) {
        try {
            if (UserName == null || ScoreIncrease == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            // if (!UserName.validUserToken(UserToken)) {
            //  return "{\"error\": \"You don't appear to be logged in.\"}";
            // }
            System.out.println("Users/incrementscore UserName=" + UserName);

            PreparedStatement ps1 = Main.db.prepareStatement("UPDATE Users SET UserScore = UserScore + ? WHERE UserName = ?");
            ps1.setInt(1, ScoreIncrease);
            ps1.setString(2, UserName);
            ps1.execute();

            PreparedStatement ps2 = Main.db.prepareStatement("SELECT UserScore FROM Users WHERE UserName = ?");
            ps2.setString(1, UserName);
            ResultSet results = ps2.executeQuery();
            Integer score = results.getInt(1);

            return "{\"newscore\": \"" + score + "\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertThing(@FormDataParam("UserSkillLevel") Integer UserSkillLevel, @FormDataParam("UserName") String UserName, @FormDataParam("UserPassword") String UserPassword){
        try {
            if (UserPassword == null || UserSkillLevel == null || UserName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new UserName=" + UserName);

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (Username, UserPassword, UserSkillLevel) VALUES (?, ?, ?)");
            ps.setString(1, UserName);
            ps.setString(2, UserPassword);
            ps.setInt(3, UserSkillLevel);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("getskill/{Username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getskill(@PathParam("Username") String UserName){
        try {
            if (UserName == null) {
                throw new Exception("Thing's 'Username' is missing in the HTTP request's URL.");
            }
            System.out.println("thing/get/" + UserName);
            JSONObject item = new JSONObject();

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, UserName, UserPassword, UserSkillLevel, UserToken, UserScore FROM Users WHERE UserName = ?");
            ps.setString(1, UserName);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("UserID", results.getInt(1));
                item.put("UserPassword", results.getString(3));
                item.put("UserSkillLevel", results.getInt(4));
                item.put("UserToken", results.getString(5));
                item.put("UserScore", results.getInt(6));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteThing(@FormDataParam("UserName") String UserName) {

        try {
            if (UserName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete UserName=" + UserName);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE UserName = ?");

            ps.setString(1, UserName);

            ps.execute();

            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("getscore/{Username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getscore(@PathParam("Username") String UserName){
        try {
            if (UserName == null) {
                throw new Exception("Thing's 'Username' is missing in the HTTP request's URL.");
            }
            System.out.println("thing/get/" + UserName);
            JSONObject item = new JSONObject();

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserName, UserScore FROM Users WHERE UserName = ?");
            ps.setString(1, UserName);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("Username", results.getString(1));
                item.put("UserScore", results.getInt(2));
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

    @GET
    @Path("listscores")
    @Produces(MediaType.APPLICATION_JSON)
    public String listScores() {
        System.out.println("Coursework/listscores");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserName, UserScore FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("UserName", results.getString(1));
                item.put("UserScore", results.getInt(2));
                list.add(item);
            }

            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("logout")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutUser(@CookieParam("UserToken") String UserToken) {

        try {

            System.out.println("user/logout");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT UserID, UserToken FROM Users WHERE UserToken = ?");
            ps1.setString(1, UserToken);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {
                int UserID = logoutResults.getInt(1);
                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET UserToken = NULL WHERE UserID = ?");
                ps2.setInt(1, UserID);
                ps2.executeUpdate();

                return "{\"status\": \"OK\"}";
            } else {

                return "{\"error\": \"Invalid token!\"}";

            }

        } catch (Exception exception){
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }

    }

    public static boolean validToken(String UserToken) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID FROM Users WHERE UserToken = ?");
            ps.setString(1, UserToken);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return false;
        }
    }

@POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("UserName") String UserName, @FormDataParam("UserPassword") String UserPassword) {

    try {

        if (UserName == null || UserPassword == null) {
            throw new Exception("Something is missing in the HTTP request.");
        }
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT UserPassword FROM Users WHERE UserName = ?");
            ps1.setString(1, UserName);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {

                String correctPassword = loginResults.getString(1);

                if (UserPassword.equals(correctPassword)) {

                    String UserToken = UUID.randomUUID().toString();

                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET UserToken = ? WHERE UserName = ?");
                    ps2.setString(1, UserToken);
                    ps2.setString(2, UserName);
                    ps2.executeUpdate();

                    JSONObject response = new JSONObject();
                    response.put("UserName", UserName);
                    response.put("UserToken", UserToken);
                    return response.toString();



                } else {

                    return "{\"error\": \"Incorrect password!\"}";

                }

            } else {

                return "{\"error\": \"Unknown user!\"}";

            }

        }catch (Exception exception){
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }


    }


}
