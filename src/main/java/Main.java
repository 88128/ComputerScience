import org.sqlite.SQLiteConfig;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class Main {

    public static Connection db = null;

    public static void main(String[] args) {
    openDatabase("CourseWork.db");
    // code to get data from here
    closeDatabase();
    }

    private static void openDatabase(String dbFile){
        try{
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("\nDatabase connection established successfully.");
        } catch (Exception exception) {
            System.out.println("\nConnection Error:\n" + exception.getMessage());
        }
    }


    private static void closeDatabase(){
        try{
            db.close();
            System.out.println("\nDatabase connection established successfully.");

        } catch (Exception exception) {
            System.out.println("\nConnection Error:\n" + exception.getMessage());
        }
    }

}