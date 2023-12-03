package hu.nye.progtech;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/wumpus_game";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Egyetem10";

    public static void insertMapState(MapState mapState) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Insert into mapState table
            String mapStateQuery = "INSERT INTO mapState (mapSize, heroRow, heroCol, facingDirection) VALUES (?, ?, ?, ?)";
            try (PreparedStatement mapStateStatement = connection.prepareStatement(mapStateQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                mapStateStatement.setInt(1, mapState.getMapSize());
                mapStateStatement.setInt(2, mapState.getHero().getRow());
                mapStateStatement.setInt(3, mapState.getHero().getCol());
                mapStateStatement.setString(4, String.valueOf(mapState.getFacingDirectionSymbol()));

                mapStateStatement.executeUpdate();

                // Retrieve the generated mapStateID
                int mapStateID = getGeneratedMapStateID(mapStateStatement);

                // Set the generated mapStateID in the MapState object
                mapState.setMapStateID(mapStateID);

                // Insert into map table
                String mapQuery = "INSERT INTO map (mapStateID, rowNumber, rowData) VALUES (?, ?, ?)";
                try (PreparedStatement mapStatement = connection.prepareStatement(mapQuery)) {
                    for (int i = 0; i < mapState.getMap().length; i++) {
                        mapStatement.setInt(1, mapStateID);
                        mapStatement.setInt(2, i + 1);
                        mapStatement.setString(3, new String(mapState.getMap()[i]));

                        mapStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getGeneratedMapStateID(PreparedStatement preparedStatement) throws SQLException {
        int mapStateID = -1;
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            if (resultSet.next()) {
                mapStateID = resultSet.getInt(1);
            }
        }
        return mapStateID;
    }
}
