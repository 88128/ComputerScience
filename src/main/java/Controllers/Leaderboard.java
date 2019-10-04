package Controllers;
import Server.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Leaderboard {
    public static void ListScores() {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, TotalScore FROM Scores");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int UserID = results.getInt(1);
                int TotalScore = results.getInt(2);
            }

        }catch(Exception exception){
            System.out.println("Database Error:" +exception.getMessage());
        }
    }
}



