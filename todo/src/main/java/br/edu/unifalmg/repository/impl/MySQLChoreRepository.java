package br.edu.unifalmg.repository.impl;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.repository.ChoreRepository;
import br.edu.unifalmg.repository.book.ChoreBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLChoreRepository implements ChoreRepository {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @Override
    public List<Chore> load() {
        if(!connectionToMySQL()){
            return new ArrayList<>();
        }
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(ChoreBook.FIND_ALL_CHORES);

            List<Chore> chores = new ArrayList<>();
            while(resultSet.next()){
                Chore chore = Chore.builder()
                        .description(resultSet.getString("description"))
                        .isCompleted(resultSet.getBoolean("isCompleted"))
                        .deadline(resultSet.getDate("deadline").toLocalDate())
                        .build();
                chores.add(chore);
            }
            return chores;
        }catch (SQLException exception){
            System.out.println("Error when connection to database.");
        }

        return null;
    }

    private void closeConnections() throws SQLException {
        connection.close();
        statement.close();
         preparedStatement.close();
        resultSet.close();
    }

    @Override
    public boolean save(List<Chore> chores) {
        return false;
    }

    private boolean connectionToMySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.1.254:3306/db2022108018?"
                            + "user=user2022108018&password=2022108018");
            return Boolean.TRUE;
        } catch (ClassNotFoundException | SQLException exception ) {
            System.out.println("Error when connection database.Try again.");
        }
        return Boolean.FALSE;
    }

}



