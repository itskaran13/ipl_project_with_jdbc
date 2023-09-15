// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.sql.*;
import java.util.*;
public class Main {
    public static void findNumberofMatchesPerYear(Connection connection){
        Map<String,String> matches = new HashMap<>();
        try{
            String query = "select season,count(id) as matchesPlayed from matches group by season order by season;";
            PreparedStatement statement = connection.prepareStatement(query);
//performing operation on statement
            ResultSet resultSet = statement.executeQuery();
//Retrieving data from result
            while (resultSet.next()) {
                matches.put(resultSet.getString("season"),resultSet.getString("matchesPlayed"));
            }
        }
        catch(Exception E){
            E.printStackTrace();
        }
        //System.out.println(matches);

    }
    public static void findNumberofMatchesWonByEveryTeam(Connection connection){
        Map<String,String> matches = new HashMap<>();
        try{
            String query = "select winner ,count(*) as Match_winner from matches  where winner is not null group by winner order by Match_winner desc;";
            PreparedStatement statement = connection.prepareStatement(query);
//performing operation on statement
            ResultSet resultSet = statement.executeQuery();
//Retrieving data from result
            while (resultSet.next()) {
                matches.put(resultSet.getString("winner"),resultSet.getString("Match_winner"));
            }
        }
        catch(Exception E){
            E.printStackTrace();
        }
        //System.out.println(matches);

    }

    public static void extraRunConcededIn2016(Connection connection){
        Map<String,String> matches = new HashMap<>();
        try{
            String query = "select  D.batting_team ,sum(D.extra_runs) as extra_runs from deliveries D join matches M on D.match_id = M.id where season= 2016 group by D.batting_team order by extra_runs asc;";
            PreparedStatement statement = connection.prepareStatement(query);
//performing operation on statement
            ResultSet resultSet = statement.executeQuery();
//Retrieving data from result
            while (resultSet.next()) {
                matches.put(resultSet.getString("batting_team"),resultSet.getString("extra_runs"));
            }
        }
        catch(Exception E){
            E.printStackTrace();
        }
        //System.out.println(matches);

    }

    public static void topEconomicalBowlerIn2015(Connection connection){
        Map<String,String> matches = new HashMap<>();
        try{
            String query = " select D.bowler, sum(D.total_runs -(D.bye_runs + D.legbye_runs))*6.0/count( Case when (noball_runs = 0 and wide_runs = 0) then 1 else null end) as Economy from deliveries D join matches M on D.match_id = M.id where season = 2015 group by D.bowler order by Economy asc;";
            PreparedStatement statement = connection.prepareStatement(query);
//performing operation on statement
            ResultSet resultSet = statement.executeQuery();
//Retrieving data from result
            while (resultSet.next()) {
                matches.put(resultSet.getString("bowler"),resultSet.getString("Economy"));
            }
        }
        catch(Exception E){
            E.printStackTrace();
        }
        System.out.println(matches);

    }



    public static void main(String[] args) throws SQLException, ClassNotFoundException{

        Class.forName("org.postgresql.Driver");
//getting connection
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String userName = "postgres";
        String passWord = "Kalra123#$";
        Connection connection = DriverManager.getConnection(url, userName, passWord);
//getting statement object
        findNumberofMatchesPerYear(connection);
        findNumberofMatchesWonByEveryTeam(connection);
        extraRunConcededIn2016(connection);
        topEconomicalBowlerIn2015(connection);

        connection.close();
        }

    }
