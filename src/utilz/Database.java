package utilz;

import java.sql.*;

public class Database {
    private static Connection connectDB()
    {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return  c;

    }




    public static void saveScoreToDatabase(String playerName, int score) {
        try {
            Connection c = connectDB();
            Statement statement = c.createStatement();
            String query = "SELECT Score FROM LEADERBOARD WHERE Name = '" + playerName + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int existingScore = resultSet.getInt("Score");

                if (score > existingScore) {
                    String updateQuery = "UPDATE LEADERBOARD SET Score = " + score + " WHERE Name = '" + playerName + "'";
                    statement.executeUpdate(updateQuery);
                }
            } else {
                String insertQuery = "INSERT INTO LEADERBOARD (Name, Score) VALUES ('" + playerName + "', " + score + ")";
                statement.executeUpdate(insertQuery);
            }

            resultSet.close();
            statement.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}




