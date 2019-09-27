import java.sql.PreparedStatement;

public class Scoreboard {

    public static void DeleteID(int id) {

        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM -- WHERE Id = 1");

            ps.setInt(1, id);

            ps.execute();

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }


}